package com.xq.mytablayout;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public final class TestFragment extends Fragment {

    private String name;

    public TestFragment() {
    }

    @SuppressLint("ValidFragment")
    public TestFragment(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        TextView te = (TextView) view.findViewById(R.id.textView1);
        te.setText(name);
    }

    public void initView() {
    }
}
