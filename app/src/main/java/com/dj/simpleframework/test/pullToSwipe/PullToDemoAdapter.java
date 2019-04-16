package com.dj.simpleframework.test.pullToSwipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dj.simpleframework.R;

import java.util.List;

/**
 * Created by dengjun on 2019/4/15.
 */

public class PullToDemoAdapter extends BaseAdapter {

    private List<String> mDatas;
    private LayoutInflater layoutInflater;

    public PullToDemoAdapter(Context context , List<String> datas){
        this.mDatas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public String getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tv = view.findViewById(R.id.list_item_tv);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv.setText(getItem(i));
        return view;
    }

    class ViewHolder{
        TextView tv;
    }

}
