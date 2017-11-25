package com.example.wudelin.bannerbar;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/*第一步：初始化轮播数据
* 第二步：创建适配器
* 第三步：设置viewpager的轮播监听器
* 第四步：设置刚刚打开是显示的图片
* 第五步：设置自动播放（4S一次）
* 第六步：设置活动结束取消自动播放
* */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private List<ImageView> viewList;
    private int[] imageId = {R.mipmap.a,
            R.mipmap.b,
            R.mipmap.c,
            R.mipmap.d};
    private int previousPosition = 0; // 前一个被选中的position
    private boolean isStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.banner_bar);
        initData();
        ViewpageAdapter viewpageAdapter = new ViewpageAdapter(viewList, viewPager);
        viewPager.setAdapter(viewpageAdapter);
        viewPager.addOnPageChangeListener(this);
        setFirstLocation();
        autoPlay();
    }

    public void autoPlay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStop = true;
    }

    /*
        *第一步：初始化轮播图片数据
        * */
    private void initData() {
        viewList = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(imageId[i]);
            //自适应填充
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewList.add(iv);
        }

    }

    /**
     * 第四步：设置刚打开时显示的图片
     */
    private void setFirstLocation() {
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / 2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (Integer.MAX_VALUE / 2) % viewList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        viewPager.setCurrentItem(currentPosition);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 当ViewPager页面被选中时, 触发此方法.
     *
     * @param position 当前被选中的页面的索引
     */
    @Override
    public void onPageSelected(int position) {
        //返回的position可能为无限大
        int mPosition = position % viewList.size();
        previousPosition = mPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
