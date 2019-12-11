package com.mitu.android.data.remote.download;


import com.mitu.android.data.model.DownloadInfoBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/29 15:35.
 * description:
 */
public abstract class DownLoadObserver implements Observer<DownloadInfoBean> {
    protected Disposable d;//可以用于取消注册的监听者
    protected DownloadInfoBean downloadInfoBean;

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(DownloadInfoBean downloadInfoBean) {
        this.downloadInfoBean = downloadInfoBean;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}