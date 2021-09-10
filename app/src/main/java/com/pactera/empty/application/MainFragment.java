package com.pactera.empty.application;

import android.os.Bundle;

import com.pactera.empty.base.activity.BaseFragment;
import com.pactera.empty.databinding.FragmentTestBinding;

/**
 * @author liujiang
 * created at: 2021/9/10 9:39
 * Desc:
 */
public class MainFragment extends BaseFragment<FragmentTestBinding> {

    @Override
    protected FragmentTestBinding getViewBinding() {
        return FragmentTestBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        viewBinding.tvText.setText("我是fragment");
    }

}
