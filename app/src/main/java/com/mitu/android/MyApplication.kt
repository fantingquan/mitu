package com.mitu.android

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

import com.mitu.android.inject.component.ApplicationComponent
import com.mitu.android.inject.component.DaggerApplicationComponent
import com.mitu.android.inject.module.ApplicationModule
import com.mitu.android.inject.module.NetWorkModule
import com.mitu.android.utils.IpHostUtils
import com.mitu.android.utils.LogUtils
import com.mitu.android.utils.Utils
import com.zhihu.matisse.ui.MatisseActivity


import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.external.ExternalAdaptInfo
import me.jessyan.autosize.external.ExternalAdaptManager
import me.jessyan.autosize.internal.CustomAdapt
import me.jessyan.autosize.onAdaptListener
import java.util.*

/**
 * Created by HuangQiang on 18/7/2.
 */
class MyApplication : Application() {

    private var appComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        Utils.init(this)

        initLog()
        initUM()
        initAutoSize()
//        initSmartRefreshLayout()

        initApiServer()
    }

    private fun initApiServer() {

        IpHostUtils.changeApi(if (BuildConfig.DEBUG) IpHostUtils.getHost() else IpHostUtils.Environment.product)

    }


//
//    private fun initSmartRefreshLayout() {
//        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
//            layout.setPrimaryColorsId(R.color.white, R.color.c_202932)//全局设置主题颜色
//            ClassicsHeader(context)
//        }
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
//            ClassicsFooter(context)
//        }
//    }


    private fun initLog() {
        LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
    }


    private fun initUM() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "")
//        UMConfigure.setLogEnabled(false)//设置组件化的Log开关 参数: boolean 默认为false，如需查看LOG设置为true
//        PlatformConfig.setWeixin(Constants.WECHAT_APP_ID, Constants.WECHAT_APP_SC)
    }


    private fun initAutoSize() {
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance()
                .setLog(false)//是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
                .setCustomFragment(true).onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(target: Any, activity: Activity) {
                me.jessyan.autosize.utils.LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.javaClass.name))
            }

            override fun onAdaptAfter(target: Any, activity: Activity) {
                me.jessyan.autosize.utils.LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.javaClass.name))
            }
        }

        AutoSize.initCompatMultiProcess(this)  //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        customAdaptForExternal()
    }

    /**
     * 给外部的三方库 [Activity] 自定义适配参数, 因为三方库的 [Activity] 并不能通过实现
     * [CustomAdapt] 接口的方式来提供自定义适配参数 (因为远程依赖改不了源码)
     * 所以使用 [ExternalAdaptManager] 来替代实现接口的方式, 来提供自定义适配参数
     */
    private fun customAdaptForExternal() {
        /**
         * [ExternalAdaptManager] 是一个管理外部三方库的适配信息和状态的管理类, 详细介绍请看 [ExternalAdaptManager] 的类注释
         */
        AutoSizeConfig.getInstance().externalAdaptManager
                .addExternalAdaptInfoOfActivity(MatisseActivity::class.java, ExternalAdaptInfo(true, 400f))
    }

    companion object {
        operator fun get(context: Context): MyApplication {
            return context.applicationContext as MyApplication
        }
    }

    // Needed to replace the component with a test specific one
    var component: ApplicationComponent
        get() {
            if (appComponent == null) {
                appComponent = DaggerApplicationComponent.builder()
                        .applicationModule(ApplicationModule(this))
                        .netWorkModule(NetWorkModule())
                        .build()
            }
            return appComponent as ApplicationComponent
        }
        set(appComponent) {
            this.appComponent = appComponent
        }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}