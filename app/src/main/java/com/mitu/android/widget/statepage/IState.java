package com.mitu.android.widget.statepage;

import android.view.ViewGroup;

/**
 * Created by jestar on 17-3-9.
 */

public interface IState {
    int STATE_DEFAULT = 0xff0;
    int STATE_LOADING = 0xff1;//加载中
    int STATE_ERROR = 0xff2;//网络差、数据错误
    int STATE_EMPTY = 0xff3;//无数据
    int STATE_NETWORK_ERROR = 0xff4;//无网络

    void initView(ViewGroup parent);

    void show();

    void hide();

    int getState();
}
