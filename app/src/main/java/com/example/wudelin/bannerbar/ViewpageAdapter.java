package com.example.wudelin.bannerbar;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wudelin on 2017/11/25.
 */

public class ViewpageAdapter extends PagerAdapter {
    private List<ImageView> viewList;
    private ViewPager viewPager;

    public ViewpageAdapter(List<ImageView> viewList, ViewPager viewPager) {
        this.viewList = viewList;
        this.viewPager = viewPager;
    }

    /**
     * 返回的int的值, 会作为ViewPager的总长度来使用.
     * 返回最大数，实现伪无限轮播
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 初始化一个条目
     * position 就是当前需要加载条目的索引
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*
        * 将position对应的ImageView添加到viewPage中
        */
        ImageView imageView = viewList.get(position%viewList.size());
        viewPager.addView(imageView);

        //将当前添加的imageview返回
        return imageView;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        /*
        * 将position对应的ImageView从viewPage中移除
        */
        viewPager.removeView(viewList.get(position%viewList.size()));
    }
}
