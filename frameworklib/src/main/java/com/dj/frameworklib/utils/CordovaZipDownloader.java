package com.dj.frameworklib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dengjun on 2019/2/13.
 * 下载 zip并解压
 */

public class CordovaZipDownloader {

    private static final String TAG = "CordovaZipDownloader";

    /**
     * 下载监听
     */
    public abstract static class OnDownloadListener {
        public abstract void onSuccess(File cacheDir);

        public void onFailure(Throwable e, String errorMsg){};

        public void onProgress(int progress){};
    }

    private static OkHttpClient okHttpClient;

    private static void initOkHttp() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * 下载并解压cordova zip文件
     *
     * @param downloadUrl 下载地址
     * @param context
     * @param listener    回调监听
     */
    public static void download(final String downloadUrl, final Context context, final OnDownloadListener listener) {

        Disposable disposable = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter){
                File cacheDir;
                Exception exception = null;

                try {
                    //下载并且解压文件
                    cacheDir = downloadAndUnZip(downloadUrl, context,listener);
                    Log.i(TAG, downloadUrl+" 下载解压成功");
                } catch (Exception e) {
                    //发生异常后，直接获取缓存文件
                    cacheDir = createDirFromUrl(downloadUrl,context);
                    exception = e;
                    Log.i(TAG, downloadUrl+"发生异常："+e.getMessage());
                }


                //我们的目标是获取缓存文件夹。不管下载成功与否，只要缓存文件夹存在，那么，这次下载就是成功的
                if(isDirHasChildDir(cacheDir)){
                    emitter.onNext(cacheDir);
                    emitter.onComplete();
                }else{
                    if(exception == null){
                        exception = new IllegalStateException("cache file is not exists");
                    }
                    emitter.onError(exception);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File dir) throws Exception {
                        if (listener != null) {
                            listener.onSuccess(dir);
                        }
                        Log.i(TAG, downloadUrl+" 缓存文件存在，可以使用");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (listener != null) {
                            listener.onFailure(throwable, throwable.getMessage());
                        }
                        Log.i(TAG, downloadUrl+" 下载解压报错:" + throwable.getMessage());
                    }
                });
    }

    /**
     * 判断目录下是否有解压的文件夹
     */
    private static boolean isDirHasChildDir(File dir){
        if(dir == null || !dir.exists()){
            return false;
        }

        File[] files = dir.listFiles();
        for (File file : files){
            if(file.isDirectory()){
                return true;
            }
        }
        return false;
    }

    /**
     * 下载并解压缩。
     *
     * 如果下载成功，则会替换缓存。
     * 如果下载不成功，则直接使用缓存
     *
     * @param downloadUrl zip下载路径
     * @param context
     * @return File zip解压保存路径
     */
    private static File downloadAndUnZip(String downloadUrl, Context context,OnDownloadListener listener) throws Exception {

        if (downloadUrl == null) {
            throw new NullPointerException("downloadUrl is null");
        }
        if (context == null) {
            throw new NullPointerException("context is null");
        }

        //根据downloadUrl得到缓存文件夹
        File cacheDir = createDirFromUrl(downloadUrl, context);

        initOkHttp();
        Request request = new Request.Builder()
                .addHeader("If-Modified-Since", getLastModifyTime(context, downloadUrl))//在zip有改动时才获取
                .url(downloadUrl).build();
        Response response = okHttpClient.newCall(request).execute();
        Log.i(TAG, downloadUrl+",responseCode:" + response.code());
        if (response.isSuccessful()) {
            //获取服务器zip流
            InputStream zipInputStream = response.body().byteStream();
            //空指针不用管，要的就是能抛出异常，终止代码继续运行
            if (!cacheDir.exists()) {
                //如果文件夹不存在，创建文件夹
                cacheDir.mkdirs();
            }

            long length = Integer.parseInt(response.header("Content-Length"));

            //保存zip
            File zipFile = saveZip(cacheDir,zipInputStream,length,listener);
            //解压并保存到相应的缓存目录
            ZipUtil.unpack(zipFile,cacheDir);
            //保存last-modified
            setLastModifyTime(context, downloadUrl, response.header("last-modified"));
            return cacheDir;
        } else if (response.code() == 304) {//zip文件无改动
            //根据downloadUrl得到缓存文件夹,在304的情况下，一般是下载过后，文件夹是存在的
            return cacheDir;
        }
        //如果下载不成功，则尝试使用缓存中的文件夹
        return cacheDir;
    }

    /**
     * 保存zip文件
     * @param dir
     * @param zipInputStream
     * @param zipSize
     * @param listener
     * @return
     */
    private static File saveZip(File dir,InputStream zipInputStream,long zipSize,OnDownloadListener listener) {
        File file = new File(dir,"download.zip");
        OutputStream outputStream = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[50*1024];
            long progress = 0;
            int count;
            while ((count = zipInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,count);
                progress += count;
                Log.i(TAG, " 缓存zip进度:"+progress+"/"+zipSize);
                if(listener != null){
                    listener.onProgress((int)((progress*1.0f/zipSize)*100));
                }
            }
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG, " 缓存zip出错:"+e.getMessage());
        }finally {
            IOUtil.close(outputStream,zipInputStream);
        }
        return file;
    }

    /**
     * 根据downloadUrl得到缓存文件夹
     *
     * @param downloadUrl zip下载地址
     * @param context
     * @return
     */
    private static File createDirFromUrl(String downloadUrl, Context context) {
        try {
            URL url = new URL(downloadUrl);
            //替换zip或者rar文件名
            String path = url.getPath().replaceAll("(/\\w+).(zip|rar)", "");
            Log.i(TAG, "path=" + path);
            return new File(context.getCacheDir(), path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缓存last-modified
     *
     * @param context
     * @param downloadUrl    zip下载地址
     * @param lastModifyTime last-modified
     */
    private static void setLastModifyTime(Context context, String downloadUrl, String lastModifyTime) {
        SharedPreferences.Editor editor = context.getSharedPreferences("cordova_zip", Context.MODE_PRIVATE).edit();
        editor.putString(downloadUrl, lastModifyTime);
        editor.apply();
    }

    /**
     * 获取last-modified
     *
     * @param context
     * @param downloadUrl zip下载地址
     * @return last-modified
     */
    private static String getLastModifyTime(Context context, String downloadUrl) {
        return context.getSharedPreferences("cordova_zip", Context.MODE_PRIVATE).getString(downloadUrl, "");
    }

}
