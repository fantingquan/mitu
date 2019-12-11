package com.mitu.android.widget.statepage;

import android.widget.TextView;

import com.mitu.android.R;


/**
 * Created by HuangQiang on 2018/5/10.
 * 无内容的状态实现(自定义)
 */

public class EmptyStateImpl extends AbstractStateImpl {

    private static String empty;

    public EmptyStateImpl(String emptyStr) {
        super(IState.STATE_EMPTY, R.layout.layout_state_empty);
        empty = emptyStr;
    }

    public void setText(String text) {
        empty = text;
        ((TextView) mView.findViewById(R.id.text_error_message)).setText(text);
    }

    @Override
    protected void handleView() {
        ((TextView) mView.findViewById(R.id.text_error_message)).setText(empty);

    }

}
