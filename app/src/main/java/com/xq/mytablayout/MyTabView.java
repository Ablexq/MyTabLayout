package com.xq.mytablayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.lang.reflect.Field;


public class MyTabView implements OnClickListener {
    private Context context = null;
    private Handler msgHandler = null;
    private LinearLayout galleryLinearLayout = null;
    private HorizontalScrollView hScrollView = null;
    private int screenW;
    private int itemW;
    private String[] menuStrings = null;
    private int icon_res;
    private int lastSelectedIndex = 0;
    private int currentSelectedIndex = 0;
    private int color_normal = Color.parseColor("#b5bbca");
    private int color_select = Color.parseColor("#FF7612");
    private ImageView[] imgaViews = null;
    private TextView[] txtViews = null;

    public MyTabView(Context context, Handler handler, String[] menuArray, int icon) {
        this.context = context;
        msgHandler = handler;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenW = dm.widthPixels;
        System.out.println("screenW====" + screenW);
        // 默认每一个屏幕显示数量
        if (menuArray.length == 2) {
            itemW = screenW / 2;
        } else if (menuArray.length == 3) {
            itemW = screenW / 3;
        } else {
            itemW = screenW / 4;
        }
        /* 这里根据需求来修改 */
        menuStrings = menuArray;
        this.icon_res = icon;
    }

    public View getView() {// 返回容纳tab所有item的整个布局
        LayoutParams layoutParams = new LayoutParams(1, 1);
        layoutParams.width = itemW;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 容纳item的父布局：水平滑动的布局
        View layoutView = inflater.inflate(R.layout.frame_layout_mytab_menu, null);
        hScrollView = (HorizontalScrollView) layoutView;
        // 容纳item的布�?inearLayout
        galleryLinearLayout = (LinearLayout) layoutView.findViewById(R.id.ll_mytab_hs_container);
        View itemView = null;
        TextView textView = null;
        ImageView imageView = null;
        imgaViews = new ImageView[menuStrings.length];// imageview的数组
        txtViews = new TextView[menuStrings.length];// textView的数组
        for (int i = 0; i < menuStrings.length; i++) {
            // item布局
            itemView = inflater.inflate(R.layout.frame_layout_mytab_item, null);
            // 设置item的LayoutParams
            itemView.setLayoutParams(layoutParams);
            // tab名称
            textView = (TextView) itemView.findViewById(R.id.tv_name);
            textView.setTextColor(color_normal);
            textView.setText(menuStrings[i]);
            // tab下划线
            imageView = (ImageView) itemView.findViewById(R.id.iv_line_select);
            imageView.setBackgroundResource(icon_res);
            imageView.setVisibility(View.INVISIBLE);

            itemView.setTag(i);
            galleryLinearLayout.addView(itemView, i);// item布局的容器：线性布局
            itemView.setOnClickListener(this);
            // 初始选中
            if (i == 0) {
                imageView.setVisibility(View.VISIBLE);
                textView.setTextColor(color_select);
            }
            imgaViews[i] = imageView;
            txtViews[i] = textView;
        }
        return layoutView;
    }

    /**
     * 获取图片资源id
     */
    public static int getDrawableId(String name) {
        Class<R.drawable> drawable = R.drawable.class;
        try {
            Field field = drawable.getDeclaredField(name);
            return field.getInt(name);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public void onClick(View v) {
        int index = (Integer) v.getTag();
        lastSelectedIndex = currentSelectedIndex;
        currentSelectedIndex = index;// 获取选中的itemtab
        if (null != msgHandler) {
            msgHandler.sendEmptyMessage(index);
        }
        changBg(index);
        int left = (txtViews[0].getWidth()) * (index);

        // 使当前点中item居中显示
        hScrollView.smoothScrollTo(left - (screenW / 2) + (itemW / 2), 0);
        if (null != itemSelectListener) {
            itemSelectListener.onItemSelect(index);
        }
    }

    private void changBg(int index) {
        // 默认的item
        for (ImageView imageView : imgaViews) {
            imageView.setVisibility(View.INVISIBLE);
        }
        for (TextView textView : txtViews) {
            textView.setTextColor(color_normal);
        }
        // 选中的item
        imgaViews[index].setVisibility(View.VISIBLE);
        txtViews[index].setTextColor(color_select);
    }

    public void setSelected(int index) {
        lastSelectedIndex = currentSelectedIndex;
        currentSelectedIndex = index;
        RelativeLayout itemLayout = (RelativeLayout) galleryLinearLayout.getChildAt(index);
        changBg(index);
        int left = (txtViews[0].getWidth()) * (index);
        hScrollView.smoothScrollTo(left - (screenW / 2) + (itemW / 2), 0);
    }

    public void setLastSelected() {
        setSelected(lastSelectedIndex);
    }

    public void setEnabled(boolean enable) {
        View itemView = null;
        for (int i = 0; i < menuStrings.length; i++) {
            itemView = (View) galleryLinearLayout.getChildAt(i);
            if (itemView != null)
                itemView.setEnabled(enable);
        }
    }

    private Handler scrollHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    hScrollView.smoothScrollTo(itemW * menuStrings.length, 0);
                    break;
                case 1:
                    hScrollView.smoothScrollTo(0, 0);
                    break;

                default:
                    break;
            }
        }

    };

    public void doScrollAnimation() {
        scrollHandler.sendEmptyMessageDelayed(0, 1000);
        scrollHandler.sendEmptyMessageDelayed(1, 1500);
    }

    private OnItemSelectListener itemSelectListener;

    public void setItemSelectListener(OnItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }

    public interface OnItemSelectListener {
        void onItemSelect(int position);
    }

}
