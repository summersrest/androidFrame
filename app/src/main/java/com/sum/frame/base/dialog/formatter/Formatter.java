package com.sum.frame.base.dialog.formatter;


/**
 * @author liujiang
 * created at: 2022/4/29 10:51
 * Desc:
 */
public interface Formatter<T> {
    String format(T item);

    int icon(T item);
}
