package com.sum.frame.base.activity;

import static com.sum.frame.base.utils.FastDoubleClick.isFastDoubleClick;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.sum.frame.R;
import com.sum.frame.base.pojo.EventMessage;
import com.sum.frame.base.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author liujiang
 * Desc:基类
 */
public abstract class BaseFragment<V extends ViewBinding> extends Fragment implements View.OnClickListener {
    private LoadingPopupView loadDialog = null;

    protected AppCompatActivity activity;

    protected Context context;

    protected abstract V getViewBinding();

    protected V viewBinding;

    public abstract void initView();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
        this.context = context;
    }

    /**
     * 也可以选择反射的方式完成ViewBinding，牺牲一点运行效率，增加编码便捷性
     * 是否使用反射，个人选择
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Type superclass = getClass().getGenericSuperclass();
//        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
//        try {
//            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class,ViewGroup.class,boolean.class);
//            viewBinding = (V) method.invoke(null, getLayoutInflater(),container,false);
//        } catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
//            e.printStackTrace();
//        }
        viewBinding = getViewBinding();
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventMessage event) {
        onEvent(event);
    }

    /**
     * 复写此方法接收eventBus消息
     * @param event
     */
    protected void onEvent(EventMessage event) {

    }

    /**
     * 弹出进度条弹窗
     *
     * @param msg
     */
    protected void showDialog(String msg) {
        if (!TextUtils.isEmpty(msg))
            setDialog(msg);
        else
            setDialog(getResources().getString(R.string.loading));
    }

    /**
     * 改变弹窗文字
     * @param text
     */
    protected void setDialogText(String text) {
        if (loadDialog != null) {
            loadDialog.setTitle(null == text ? "" : text);
        }
    }

    /**
     * 隐藏进度条弹窗
     *
     * @param msg
     */
    protected void hintDialog(String msg) {
        if (!TextUtils.isEmpty(msg))
            ToastUtils.showShort(msg);
        // 取消加载对话框
        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }
    }

    /**
     * 设置进度条
     */
    private void setDialog(String text) {
        // 正在加载对话框
        if (loadDialog == null) {
            loadDialog = (LoadingPopupView) new XPopup.Builder(getActivity())
                    .dismissOnBackPressed(false)
                    .dismissOnTouchOutside(false)
                    .isLightNavigationBar(true)
                    .asLoading(text)
                    .show();
        } else {
            loadDialog.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

    }

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick()) {
            return;
        }
        onClickEvent(v);
    }

    /**
     * 点击事件
     */
    protected void onClickEvent(View v) {

    }
}
