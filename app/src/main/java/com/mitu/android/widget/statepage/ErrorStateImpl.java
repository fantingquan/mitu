package com.mitu.android.widget.statepage;

import android.view.View;

import com.mitu.android.R;


/**
 * Created by HuangQiang on 2018/5/10.
 * 网络差（获取数据失败）的状态实现(自定义)
 */

public class ErrorStateImpl extends AbstractStateImpl {

    private final View.OnClickListener mListener;

    public ErrorStateImpl(View.OnClickListener listener) {
        super(IState.STATE_ERROR, R.layout.layout_state_error);
        mListener = listener;
    }

    @Override
    protected void handleView() {
        mView.findViewById(R.id.btn_retry).setOnClickListener(mListener);
    }
}
