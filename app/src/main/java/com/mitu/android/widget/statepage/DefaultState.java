package com.mitu.android.widget.statepage;

import android.view.ViewGroup;

/**
 * 状态接口的空实现类..表示默认状态
 * Created by jestar on 17-3-17.
 */

class DefaultState implements IState {

    @Override
    public void initView(ViewGroup parent) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public int getState() {
        return STATE_DEFAULT;
    }

}
