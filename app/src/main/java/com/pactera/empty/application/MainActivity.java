package com.pactera.empty.application;

import android.os.Bundle;

import com.pactera.empty.base.activity.BaseActivity;
import com.pactera.empty.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }


}