package com.mitu.android.widget.statepage;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * 状态对象接口的抽象实现类,实现了状态的切换,定义了初始化模式
 * Created by jestar on 17-3-9.
 */

public abstract class AbstractStateImpl implements IState {
    private final int mState;
    private final int mLayout;
    protected View mView;

    public AbstractStateImpl(int state, @LayoutRes int layout) {
        mState = state;
        mLayout = layout;
    }
    @Override
    public final void initView(@NonNull ViewGroup parent) {
        createView(parent);
        handleView();
        parent.addView(mView);
    }

    private void createView(@NonNull ViewGroup parent) {
        Context context = parent.getContext();
        mView = LayoutInflater.from(context).inflate(mLayout, parent, false);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        hide();
    }

    protected abstract void handleView();

    @Override
    public void show() {
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        mView.setVisibility(View.GONE);
    }

    @Override
    public int getState() {
        return mState;
    }
}
