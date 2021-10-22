package com.pactera.empty.application.main;

import android.os.Bundle;

import com.pactera.empty.base.activity.BaseActivity;
import com.pactera.empty.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding> {
    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


}