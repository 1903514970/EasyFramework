package com.dj.simpleframework.test.pullToSwipe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dj.simpleframework.R;

import java.util.ArrayList;

/**
 * Created by dengjun on 2019/4/15.
 */

public class PullToSwipeActivity extends Activity implements OnRefreshListener,OnLoadMoreListener {

    private SwipeToLoadLayout mSwipeToLoadLayout;

    private ArrayList<String> mDatas = new ArrayList<>();
    private SwipeMenuListView menuListView;
    private PullToDemoAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_demo);
        mSwipeToLoadLayout = findViewById(R.id.SwipeToLoadLayout);
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);

        mAdapter = new PullToDemoAdapter(this,mDatas);
        menuListView = findViewById(R.id.swipe_target);
        menuListView.setAdapter(mAdapter);
        menuListView.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(R.color.colorAccent);
                deleteItem.setWidth(180);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem collectionItem = new SwipeMenuItem(getApplicationContext());
                collectionItem.setBackground(R.color.colorPrimary);
                collectionItem.setWidth(180);
                collectionItem.setTitle("收藏");
                collectionItem.setTitleSize(18);
                collectionItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(collectionItem);
            }
        });
        menuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(index==0){
                    mDatas.remove(position);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(PullToSwipeActivity.this,"删除第"+position+"项",Toast.LENGTH_SHORT).show();
                }else if(index==1){
                    Toast.makeText(PullToSwipeActivity.this,"收藏第"+position+"项",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        autoRefresh();
    }

    private void autoRefresh() {
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onLoadMore() {
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMore();
                mSwipeToLoadLayout.setLoadingMore(false);
            }
        },2000);
    }

    @Override
    public void onRefresh() {
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
                mSwipeToLoadLayout.setRefreshing(false);
            }
        },2000);
    }

    private  int startPage = 0;
    private void refresh(){
        mDatas.clear();
        startPage = 0;
        int start = startPage * 10;
        int end = start+10;
        for(int i = start;i<end;i++){
            mDatas.add("新建item"+(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void loadMore(){
        startPage++;
        int start = startPage * 10;
        int end = start+10;
        for(int i = start;i<end;i++){
            mDatas.add("新建item"+(i));
        }
        mAdapter.notifyDataSetChanged();
    }


}
