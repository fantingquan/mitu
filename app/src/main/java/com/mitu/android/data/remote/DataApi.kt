package com.mitu.android.data.remote


import com.google.gson.JsonObject
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*

interface DataApi {

    /*
    Single类似于Observable，不同的是，它总是只发射一个值，或者一个错误通知，而不是发射一系列的值。
    因此，不同于Observable需要三个方法onNext, onError, onCompleted，订阅Single只需要两个方法：
    onSuccess - Single发射单个的值到这个方法
    onError - 如果无法发射需要的值，Single发射一个Throwable对象到这个方法
    Single只会调用这两个方法中的一个，而且只会调用一次，调用了任何一个方法之后，订阅关系终止
     */
    @POST
    fun postObserver(@Url url: String, @HeaderMap headerMap: HashMap<String, Any>, @QueryMap map: HashMap<String, Any>): Single<JsonObject>

    @POST
    fun postObserver(@Url url: String, @HeaderMap headerMap: HashMap<String, Any>, @Body body: JsonObject): Single<JsonObject>

    @GET
    fun getObserver(@Url url: String, @HeaderMap headerMap: HashMap<String, Any>, @QueryMap map: HashMap<String, Any>): Single<JsonObject>

    @GET
    fun getObserver(@Url url: String, @HeaderMap headerMap: HashMap<String, Any>): Single<JsonObject>


    @Streaming
    @GET
    fun getDownloadObserver(@Url url: String): Flowable<ResponseBody>

    @POST
    fun unloadFileObserver(@Url url: String, @HeaderMap headerMap: HashMap<String, Any>, @Body body: MultipartBody): Flowable<ResponseBody>

}
