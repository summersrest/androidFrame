package com.sum.frame.application.main;

import android.os.Bundle;

import com.sum.frame.base.activity.BaseActivity;
import com.sum.frame.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


}