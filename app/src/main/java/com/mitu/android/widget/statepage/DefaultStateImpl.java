package com.mitu.android.widget.statepage;


import androidx.annotation.LayoutRes;

/**
 * 默认的状态实现类
 * Created by jestar on 17-3-9.
 */

public class DefaultStateImpl extends AbstractStateImpl {

    DefaultStateImpl(int state, @LayoutRes int layout) {
        super(state, layout);
    }

    @Override
    protected void handleView() {

    }
}
