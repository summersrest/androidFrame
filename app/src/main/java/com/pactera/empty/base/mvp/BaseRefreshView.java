package com.pactera.empty.base.mvp;

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
