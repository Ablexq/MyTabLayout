package com.xq.mytablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/22.
 */

public class MainActivity extends AppCompatActivity implements MyTabView.OnItemSelectListener {

    private ViewPager viewPager = null;
    private MyTabView mTabView;
//        private String[] tabNames = new String[]{"推荐", "热点"};
//    private String[] tabNames = new String[]{"推荐", "热点", "视频"};
//    private String[] tabNames = new String[]{"全部", "贷农添力", "贷信优享", "贷财稳赢"};
    private String[] tabNames = new String[]{"全部", "贷农添力", "贷信优享", "贷财稳赢", "我的推荐"};
//    private String[] tabNames = new String[]{"全部", "贷农添力", "贷信优享", "贷财稳赢", "我的推荐", "热点", "视频", "本地", "科技", "健康"};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        mTabView = new MyTabView(this, null, tabNames, R.drawable.bg_tab_vp_select);
        mTabView.setItemSelectListener(this);
        LinearLayout menuLayout = (LinearLayout) this.findViewById(R.id.ll_tab_menu);
        menuLayout.addView(mTabView.getView());
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.addOnPageChangeListener(pageListener);
        for (String tabName : tabNames) {
            fragmentList.add(new TestFragment(tabName));
        }
        FragmentPagerAdapter adapter = new TabVpAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }

    private ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mTabView.setSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onItemSelect(int position) {
        viewPager.setCurrentItem(position);
    }


}
