package com.mitu.android.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.LongSparseArray

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mitu.android.base.mvp.BaseContract

import com.mitu.android.inject.component.ActivityComponent
import com.daydaycook.mitao.inject.component.ConfigPersistentComponent
import com.daydaycook.mitao.inject.component.DaggerConfigPersistentComponent
import com.mitu.android.inject.module.ActivityModule
import com.mitu.android.utils.SnackBarUtils

import com.gyf.barlibrary.ImmersionBar
import com.mitu.android.MyApplication
import com.mitu.android.R
import com.mitu.android.config.FixMemLeak
import com.mitu.android.utils.KeyboardUtils
import com.mitu.android.widget.statepage.EmptyStateImpl
import com.mitu.android.widget.statepage.ErrorStateImpl
import com.mitu.android.widget.statepage.IState
import com.mitu.android.widget.statepage.StatePageView
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.atomic.AtomicLong


/**
 * Created by HuangQiang on 18-4-27.
 */
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    private var mActivityComponent: ActivityComponent? = null
    private var mActivityId: Long = 0

    abstract val layout: Int
    protected open var mImmersionBar: ImmersionBar? = null
    protected var PAGE_ROW = 20 //分页条数
    protected open var spv: StatePageView? = null

    companion object {
        private const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
        private val sComponentsArray = LongSparseArray<ConfigPersistentComponent>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏

        mActivityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (sComponentsArray.get(mActivityId) == null) {
//            LogUtils.i("Creating new ConfigPersistentComponent id=%d", mActivityId)
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(MyApplication[this].component)
                    .build()
            sComponentsArray.put(mActivityId, configPersistentComponent)
        } else {
//            LogUtils.i("Reusing ConfigPersistentComponent id=%d", mActivityId)
            configPersistentComponent = sComponentsArray.get(mActivityId)!!
        }
        mActivityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
        mActivityComponent!!.inject(this)

        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar()

        if (isRegisterEventBus) {
            EventBus.getDefault().register(this)
        }
    }

    /**
     * 网络操作时弹出loading和取消loading框
     * @param boolean Boolean
     */
    override fun showProgress(boolean: Boolean) {
        if (boolean) {
//            showLoadingDialog(this)
        } else {
//            dismissLoadingDialog()
        }
    }

    protected open var isHasViewDefault = false//是否显示正常过，正常过就不再覆盖
    private var spvBgResId = 0//状态View的背景
    private var emptyStateImpl: EmptyStateImpl? = null
    /**
     * 初始化 页面状态
     * @param view 页面
     * @param errorListener? 空状态的显示 如果不传就没有
     * @View.OnClickListener? 加载失败的监听 为空的时候为不监听
     */
    protected fun initStatePageView(view: StatePageView, errorListener: View.OnClickListener?) {
        initStatePageView(view, "", errorListener)
    }

    /**
     * 初始化 页面状态
     * @param view StatePageView页面
     * @param emptyString? 空状态的显示 如果不传就没有
     * @param errorListener? 加载失败的监听 为空的时候为不监听
     */
    protected open fun initStatePageView(view: StatePageView, emptyString: String?, errorListener: View.OnClickListener?) {
        initStatePageView(view, emptyString, 0, errorListener)
    }

    /**
     * 初始化 页面状态
     * @param view StatePageView页面
     * @param emptyString? 空状态的显示 如果不传就没有
     * @param spvResId 可以为图片可以为Color
     * @param errorListener? 加载失败的监听 为空的时候为不监听
     */
    protected open fun initStatePageView(view: StatePageView, emptyString: String?, spvResId: Int, errorListener: View.OnClickListener?) {
        spv = view
        spvBgResId = spvResId
        if (errorListener != null) {
            spv?.addState(ErrorStateImpl(View.OnClickListener {
                errorListener.onClick(it)
                showProgress(true)
            }))
        }
        if (!emptyString.isNullOrEmpty()) {
            emptyStateImpl = EmptyStateImpl(emptyString)
            spv?.addState(emptyStateImpl)
        }
        spv?.initView()
        showPageState(IState.STATE_LOADING)
    }

    /**
     *设置空View文字
     */
    protected open fun setEmptyStateText(emptyString: String?) {
        emptyStateImpl?.setText(emptyString)
    }


    /**
     *  更新页面状态
     *  备注 当页面已经正常显示过数据就不再覆盖网络错误
     */
    override fun showPageState(state: Int) {
        //这一行主要处理当网络已经显示过之后就不再显示
        if (isHasViewDefault && state == IState.STATE_ERROR) {
            showSnackBar("网络不给力，请稍后重试", R.mipmap.toast_warn)
        } else {
            spv?.state = state
            if (spvBgResId != 0) {
                when (state) {
                    IState.STATE_DEFAULT -> spv?.setBackgroundResource(0)
                    else -> spv?.setBackgroundResource(spvBgResId)
                }
            }
        }
        if (state == IState.STATE_DEFAULT) {
            isHasViewDefault = true
        }
        when (state) {
            IState.STATE_DEFAULT, IState.STATE_ERROR, IState.STATE_EMPTY -> showProgress(false)
        }
    }

    /**
     * 提示SnackBar
     * @param msg String?
     * @param resId default图片
     */
    override fun showSnackBar(msg: String?, resId: Int) {
        SnackBarUtils.showSnackBar(this, msg, resId)
    }

    open fun initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.white)
        mImmersionBar?.init()
    }

    fun activityComponent(): ActivityComponent {
        return mActivityComponent as ActivityComponent
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }


    override fun onResume() {
        super.onResume()
//        MobclickAgent.onResume(this)//友盟统计
    }

    override fun onPause() {
        super.onPause()
//        MobclickAgent.onPause(this)//友盟统计
    }

    override fun onDestroy() {
        if (!isChangingConfigurations) {
//            LogUtils.i("Clearing ConfigPersistentComponent id=%d", mActivityId)
            sComponentsArray.remove(mActivityId)
        }

        if (isRegisterEventBus)
            EventBus.getDefault().unregister(this)

        if (isImmersionBarEnabled())
            mImmersionBar?.destroy()

        FixMemLeak.fixLeak(this)

        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        KeyboardUtils.hideSoftInput(this)
    }


    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected open val isRegisterEventBus: Boolean = false

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected open fun isImmersionBarEnabled(): Boolean = true


}