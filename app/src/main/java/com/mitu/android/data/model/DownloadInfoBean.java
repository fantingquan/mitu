package com.mitu.android.data.model;

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/29 15:29.
 * description:
 */
public class DownloadInfoBean {
    /**
     * 下载状态
     */
    public static final String DOWNLOAD = "download";
    public static final String DOWNLOAD_PAUSE = "pause";
    public static final String DOWNLOAD_CANCEL = "cancel";
    public static final String DOWNLOAD_OVER = "over";
    public static final String DOWNLOAD_ERROR = "error";

    public static final long TOTAL_ERROR = -1;//获取进度失败

    private String url;
    private String fileName;
    private String downloadStatus;
    private long total;
    private long progress;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String filePath;


    public DownloadInfoBean(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
