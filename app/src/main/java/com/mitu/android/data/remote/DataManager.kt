package com.mitu.android.data.remote

import com.google.gson.JsonObject
import com.mitu.android.data.remote.upload.FileUploadObserver
import com.mitu.android.data.remote.upload.MultipartBuilder
import com.mitu.android.data.remote.upload.UploadFileRequestBody
import com.mitu.android.helper.AccountHelper
import com.mitu.android.utils.AppUtils

import io.reactivex.FlowableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(private val dataApi: DataApi) {

    /**
     * get请求 body传map
     */
    fun getObservable(url: String, bodyMap: HashMap<String, Any>): Single<JsonObject> {
        return dataApi.getObserver(url, getHeadMap(), bodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * get请求 body传Json
     */
    fun getObservable(url: String): Single<JsonObject> {
        return dataApi.getObserver(url, getHeadMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * post请求 body传map
     */
    fun postObservable(url: String, bodyMap: HashMap<String, Any>): Single<JsonObject> {
        return dataApi.postObserver(url, getHeadMap(), bodyMap)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * post请求 body传Json
     */
    fun postObservable(url: String, jsonObject: JsonObject): Single<JsonObject> {
        return dataApi.postObserver(url, getHeadMap(), jsonObject)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 上传单个文件
     */
    fun uploadFileObservable(url: String, file: File, fileUploadObserver: FileUploadObserver<ResponseBody>) {
        return dataApi.unloadFileObserver(url, getHeadMap(), MultipartBuilder.fileToMultipartBody(file, object : UploadFileRequestBody(file, fileUploadObserver) {}))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ioMainDownload())
                .subscribe(fileUploadObserver)

    }

    /**
     * 上传多个文件
     */
    fun uploadFileListObservable(url: String, fileList: List<File>, fileUploadObserver: FileUploadObserver<ResponseBody>) {
        return dataApi.unloadFileObserver(url, getHeadMap(), MultipartBuilder.filesToMultipartBody(fileList, fileUploadObserver))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .compose(ioMainDownload())
                .subscribe(fileUploadObserver)
    }

    /**
     * 下载文件（请看DownLoadManager类）
     */
    fun getDownloadObservable(url: String, fileDownLoadSubscriber: ResourceSubscriber<ResponseBody>) {
        return dataApi.getDownloadObserver(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ioMainDownload())
                .subscribe(fileDownLoadSubscriber)

    }

    private fun ioMainDownload(): FlowableTransformer<ResponseBody, ResponseBody> {
        return FlowableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .map { responseBody -> responseBody }
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getHeadMap(): HashMap<String, Any> {
        val headerParams = HashMap<String, Any>()
        headerParams["token"] = AccountHelper.getUser()?.token ?: ""
        headerParams["platform"] = "Android"
        headerParams["version"] = AppUtils.getAppVersionName()
        headerParams["deviceType"] = AppUtils.getBrand() + AppUtils.getModel()
        headerParams["osVersion"] = AppUtils.getVersion()

        return headerParams
    }
}