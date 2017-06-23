package com.example.viewpagerindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by heyukun on 2017/6/15.
 */

public class SingleFragment extends Fragment {
    private TextView mTitleView;
    private String mTitle;
    public static final String BUNDLE_TITLE =  "bundle_title";

    public static SingleFragment newInstance(String title) {
        SingleFragment singleFragment = new SingleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE,title);
        singleFragment.setArguments(bundle);
        return singleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single,container,false);
        mTitleView = (TextView) view.findViewById(R.id.id_title);
        mTitle = getArguments().getString(BUNDLE_TITLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleView.setText(mTitle);
    }
}
