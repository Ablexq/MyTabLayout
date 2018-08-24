package com.xq.mytablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xq.mytablayout.adapter.TabVpAdapter;
import com.xq.mytablayout.adapter.TabVpStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/6/22.
 */

public class MainActivity extends AppCompatActivity implements MyTabView.OnItemSelectListener, View.OnClickListener {

    private ViewPager viewPager = null;
    private MyTabView mTabView;
    //        private String[] tabNames = new String[]{"推荐", "热点"};
    private String[] tabNames1 = new String[]{"0000", "1111", "2222"};
    private String[] tabNames2 = new String[]{"lingling", "yiyiyi", "ererer", "sansansan"};
    private String[] tabNames3 = new String[]{"零零零", "壹壹壹", "贰贰贰", "叁叁叁", "肆肆肆"};
    //    private String[] tabNames = new String[]{"全部", "贷农添力", "贷信优享", "贷财稳赢", "我的推荐", "热点", "视频", "本地", "科技", "健康"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
//    private TabVpAdapter adapter;
    private TabVpStateAdapter adapter;
    private LinearLayout menuLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        tv1 = ((TextView) this.findViewById(R.id.btn1));
        tv2 = ((TextView) this.findViewById(R.id.btn2));
        tv3 = ((TextView) this.findViewById(R.id.btn3));
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        mTabView = new MyTabView(this, null, tabNames2, R.drawable.bg_tab_vp_select);
        mTabView.setItemSelectListener(this);
        menuLayout = (LinearLayout) this.findViewById(R.id.ll_tab_menu);
        menuLayout.addView(mTabView.getView());
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.addOnPageChangeListener(pageListener);
        for (String tabName : tabNames2) {
            fragmentList.add(new TestFragment(tabName));
        }
        adapter = new TabVpStateAdapter(getSupportFragmentManager(), fragmentList);
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


    @Override
    public void onClick(View v) {
        fragmentList.clear();
        if (menuLayout.getChildCount() > 0) {
            menuLayout.removeAllViews();
        }

        switch (v.getId()) {
            case R.id.btn1:
                mTabView = new MyTabView(this, null, tabNames1, R.drawable.bg_tab_vp_select);
                mTabView.setItemSelectListener(this);
                menuLayout.addView(mTabView.getView());

                for (String tabName : tabNames1) {
                    fragmentList.add(new TestFragment(tabName));
                }
                break;

            case R.id.btn2:
                mTabView = new MyTabView(this, null, tabNames2, R.drawable.bg_tab_vp_select);
                mTabView.setItemSelectListener(this);
                menuLayout.addView(mTabView.getView());

                for (String tabName : tabNames2) {
                    fragmentList.add(new TestFragment(tabName));
                }
                break;

            case R.id.btn3:
                mTabView = new MyTabView(this, null, tabNames3, R.drawable.bg_tab_vp_select);
                mTabView.setItemSelectListener(this);
                menuLayout.addView(mTabView.getView());

                for (String tabName : tabNames3) {
                    fragmentList.add(new TestFragment(tabName));
                }
                break;
        }

//        if (adapter == null) {
//            adapter = new TabVpAdapter(getSupportFragmentManager(), fragmentList);
            adapter = new TabVpStateAdapter(getSupportFragmentManager(), fragmentList);
            viewPager.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
        viewPager.addOnPageChangeListener(pageListener);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(fragmentList.size());

    }
}
