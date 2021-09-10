package com.pactera.empty.application;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.pactera.empty.R;
import com.pactera.empty.base.activity.BaseActivity;
import com.pactera.empty.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layout_frame, new MainFragment())
                .commit();
    }



}