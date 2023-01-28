package com.sum.frame.application.main;

import android.os.Bundle;
import android.view.View;

import com.sum.frame.base.activity.BaseActivity;
import com.sum.frame.base.dialog.SimpleDialog;
import com.sum.frame.base.dialog.SimpleListDialog;
import com.sum.frame.databinding.ActivityMainBinding;

import java.util.Arrays;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void onClickEvent(View v) {
        super.onClickEvent(v);
    }
}