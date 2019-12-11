package com.mitu.android.data.remote.download;

import com.mitu.android.constant.Constants;
import com.mitu.android.data.model.DownloadInfoBean;
import com.mitu.android.utils.FileUtils;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/29 15:30.
 * description:多文件下载和断点续传
 */
public class DownLoadManager {
    private static final AtomicReference<DownLoadManager> INSTANCE = new AtomicReference<>();
    private OkHttpClient mClient;
    private String downFile;
    private HashMap<String, Call> downCalls; //用来存放各个下载的请求

    public static DownLoadManager getInstance() {
        for (; ; ) {
            DownLoadManager current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new DownLoadManager();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    private DownLoadManager() {
        downCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
        downFile = Constants.INSTANCE.getSDCARD_ROOT();
    }

    /**
     * 查看是否在下载任务中
     *
     * @param url
     * @return
     */
    public boolean getDownloadUrl(String url) {
        return downCalls.containsKey(url);
    }

    /**
     * 开始下载
     *
     * @param url              下载请求的网址
     * @param downLoadObserver 用来回调的接口
     */
    public void download(String url, DownLoadObserver downLoadObserver) {
        Observable.just(url)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return !downCalls.containsKey(s);
                    }
                }) // 过滤 call的map中已经有了,就证明正在下载,则这次不下载
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) {
                        return Observable.just(createDownInfo(s));
                    }
                }) // 生成 DownloadInfoBean
                .map(new Function<Object, DownloadInfoBean>() {
                    @Override
                    public DownloadInfoBean apply(Object o) {
                        return getRealFileName((DownloadInfoBean) o);
                    }
                }) // 如果已经下载，重新命名
                .flatMap(new Function<DownloadInfoBean, ObservableSource<DownloadInfoBean>>() {
                    @Override
                    public ObservableSource<DownloadInfoBean> apply(DownloadInfoBean downloadInfoBean) {
                        return Observable.create(new DownloadSubscribe(downloadInfoBean));
                    }
                }) // 下载
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程中回调
                .subscribeOn(Schedulers.io()) //  在子线程中执行
                .subscribe(downLoadObserver); //  添加观察者，监听下载进度
    }

    /**
     * 下载取消或者暂停
     *
     * @param url
     */
    public void pauseDownload(String url) {
        Call call = downCalls.get(url);
        if (call != null) {
            call.cancel();//取消
        }
        downCalls.remove(url);
    }

    /**
     * 取消下载 删除本地文件
     *
     * @param info
     */
    public void cancelDownload(DownloadInfoBean info) {
        pauseDownload(info.getUrl());
        info.setProgress(0);
        info.setDownloadStatus(DownloadInfoBean.DOWNLOAD_CANCEL);
        EventBus.getDefault().post(info);
        FileUtils.deleteFilesByFile(info.getFilePath());
    }

    /**
     * 创建DownInfo
     *
     * @param url 请求网址
     * @return DownInfo
     */
    private DownloadInfoBean createDownInfo(String url) {
        DownloadInfoBean downloadInfoBean = new DownloadInfoBean(url);
        long contentLength = getContentLength(url);//获得文件大小
        downloadInfoBean.setTotal(contentLength);
        String fileName = url.substring(url.lastIndexOf("/"));
        downloadInfoBean.setFileName(fileName);
        return downloadInfoBean;
    }

    /**
     * 如果文件已下载重新命名新文件名
     *
     * @param downloadInfoBean
     * @return
     */
    private DownloadInfoBean getRealFileName(DownloadInfoBean downloadInfoBean) {
        String fileName = downloadInfoBean.getFileName();
        long downloadLength = 0, contentLength = downloadInfoBean.getTotal();
        File path = new File(downFile);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(downFile, fileName);
        int i = 1;
        while (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
//            downloadLength = file.length();
            //之前下载过,需要重新来一个文件（断点续传功能暂时没有）
//            if (contentLength > 0 && downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(downFile, fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
//            }
        }
        //设置改变过的文件名/大小
        downloadInfoBean.setProgress(downloadLength);
        downloadInfoBean.setFileName(file.getName());
        downloadInfoBean.setFilePath(file.getAbsolutePath());

        return downloadInfoBean;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfoBean> {
        private DownloadInfoBean downloadInfoBean;

        DownloadSubscribe(DownloadInfoBean downloadInfoBean) {
            this.downloadInfoBean = downloadInfoBean;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfoBean> emitter) throws Exception {
            String url = downloadInfoBean.getUrl();
            long downloadLength = downloadInfoBean.getProgress();//已经下载好的长度
            long contentLength = downloadInfoBean.getTotal();//文件的总长度
            //初始进度信息
            emitter.onNext(downloadInfoBean);
            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分（contentLength<=0 时有问题）
//                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(url, call);//把这个添加到call里,方便取消
            Response response = call.execute();
            File file = new File(downFile, downloadInfoBean.getFileName());
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                is = response.body().byteStream();
                fileOutputStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[2048];//缓冲数组2kB
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    downloadLength += len;
                    downloadInfoBean.setProgress(downloadLength);
                    emitter.onNext(downloadInfoBean);
                }
                fileOutputStream.flush();
                downCalls.remove(url);
            } finally {
                //关闭IO流
                try {
                    if (is != null) is.close();
                    if (fileOutputStream != null) fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            emitter.onComplete();//完成
        }
    }

    /**
     * 获取下载长度
     *
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = 0;
                if (response.body() != null) {
                    contentLength = response.body().contentLength();
                }
                response.close();
                return contentLength == 0 ? DownloadInfoBean.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfoBean.TOTAL_ERROR;
    }

}
