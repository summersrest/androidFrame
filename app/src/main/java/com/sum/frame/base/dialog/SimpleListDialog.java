package com.sum.frame.base.dialog;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.sum.frame.base.dialog.formatter.TextFormatter;
import com.sum.frame.base.interfaces.OnDialogItemSelectedListener;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiang
 * created at: 2022/10/18 15:21
 * Desc:    list弹窗
 */
public class SimpleListDialog<T> {
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 1;
    private final Context context;
    private String title = "";
    private int align;
    private List<T> data = new ArrayList<>();
    private int[] iconIds;
    private int checkedPosition = -1;
    private TextFormatter<T> textFormatter;
    private OnDialogItemSelectedListener<T> onDialogItemSelectedListener;
    private boolean isDarkTheme;
    private boolean isDismissOnBackPressed = true;
    private boolean isDismissOnTouchOutside = true;
    private int maxHeight;
    private int maxWidth;

    public SimpleListDialog(Context context) {
        this.context = context;
        this.title = "请选择";

    }

    public SimpleListDialog<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    public SimpleListDialog<T> setData(List<T> data) {
        if (null != data)
            this.data = data;
        return this;
    }

    public SimpleListDialog<T> setIconIds(int[] iconIds) {
        this.iconIds = iconIds;
        return this;
    }

    public SimpleListDialog<T> setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        return this;
    }

    public SimpleListDialog<T> setAlign(int align) {
        this.align = align;
        return this;
    }

    public SimpleListDialog<T> setTextFormatter(TextFormatter<T> textFormatter) {
        this.textFormatter = textFormatter;
        return this;
    }

    public SimpleListDialog<T> setOnDialogItemSelectedListener(OnDialogItemSelectedListener<T> onDialogItemSelectedListener) {
        this.onDialogItemSelectedListener = onDialogItemSelectedListener;
        return this;
    }

    public SimpleListDialog<T> isDarkTheme(boolean isDarkTheme) {
        this.isDarkTheme = isDarkTheme;
        return this;
    }

    public SimpleListDialog<T> dismissOnBackPressed(boolean isDismissOnBackPressed) {
        this.isDismissOnBackPressed = isDismissOnBackPressed;
        return this;
    }

    public SimpleListDialog<T> setDismissOnTouchOutside(boolean dismissOnTouchOutside) {
        this.isDismissOnTouchOutside = dismissOnTouchOutside;
        return this;
    }

    public SimpleListDialog<T> setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public SimpleListDialog<T> setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }


    public void show() {
        String[] arr = new String[data.size()];
        boolean isAddIcon = false;
        //icon来源为set数组
        if (null != iconIds && iconIds.length == data.size()) {
            isAddIcon = true;
        }
        //获取数组中的icon
        List<Integer> iconList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (null != textFormatter) {
                arr[i] = textFormatter.format(data.get(i));
                if (!isAddIcon) {
                    if (0 != textFormatter.icon(data.get(i))) {
                        iconList.add(textFormatter.icon(data.get(i)));
                    }
                }
            } else {
                arr[i] = (String) data.get(i);
            }
        }
        //icon来源为item单个添加
        if (iconList.size() == data.size()) {
            iconIds = new int[iconList.size()];
            for (int i = 0; i < iconList.size(); i++) {
                iconIds[i] = iconList.get(i);
            }
        }
        if (checkedPosition >= data.size()) {
            checkedPosition = -1;
        }
        if (align == ALIGN_BOTTOM) {
            new XPopup.Builder(context)
                    .maxHeight(maxHeight)
                    .maxWidth(maxWidth)
                    .dismissOnBackPressed(isDismissOnBackPressed)
                    .dismissOnTouchOutside(isDismissOnTouchOutside)
                    .isDarkTheme(isDarkTheme)
                    .asBottomList(title, arr, iconIds, checkedPosition, (position, text) -> {
                        if (null != onDialogItemSelectedListener) {
                            onDialogItemSelectedListener.onSelected(position, data.get(position));
                        }
                    })
                    .show();

        } else if (align == ALIGN_CENTER) {
            new XPopup.Builder(context)
                    .isDarkTheme(isDarkTheme)
                    .maxHeight(maxHeight)
                    .maxWidth(maxWidth)
                    .dismissOnBackPressed(isDismissOnBackPressed)
                    .dismissOnTouchOutside(isDismissOnTouchOutside)
                    .asCenterList(title, arr, iconIds, checkedPosition, (position, text) -> {
                        if (null != onDialogItemSelectedListener) {
                            onDialogItemSelectedListener.onSelected(position, data.get(position));
                        }
                    })
                    .show();
        }
    }
}
