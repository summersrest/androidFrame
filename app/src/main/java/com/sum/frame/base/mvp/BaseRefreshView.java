package com.sum.frame.base.mvp;

/**
 * @author liujiang
 * Desc:
 */
public interface BaseRefreshView extends BaseView {
    void notifyAdapter();

    void finishLoadMore();

    void finishRefresh();

    void loadMoreComplete();
}
