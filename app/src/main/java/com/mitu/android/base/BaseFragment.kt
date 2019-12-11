package com.mitu.android.base

import android.content.Context
import android.os.Bundle
import android.util.LongSparseArray

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mitu.android.base.mvp.BaseContract

import com.daydaycook.mitao.inject.component.ConfigPersistentComponent
import com.daydaycook.mitao.inject.component.DaggerConfigPersistentComponent
import com.mitu.android.inject.component.FragmentComponent
import com.mitu.android.inject.module.FragmentModule
import com.mitu.android.utils.SnackBarUtils

import com.gyf.barlibrary.ImmersionBar
import com.mitu.android.MyApplication
import com.mitu.android.R
import com.mitu.android.widget.statepage.EmptyStateImpl
import com.mitu.android.widget.statepage.ErrorStateImpl
import com.mitu.android.widget.statepage.IState
import com.mitu.android.widget.statepage.StatePageView
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.atomic.AtomicLong

/**
 * Abstract Fragment that every other Fragment in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent are kept
 * across configuration changes.
 */
abstract class BaseFragment : Fragment(), BaseContract.View {

    private var fragmentComponent: FragmentComponent? = null
    private var fragmentId = 0L

    private var isInit = false
    private var isLoad = false

    private var mImmersionBar: ImmersionBar? = null
    protected open var spv: StatePageView? = null


    companion object {
        private const val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        private val componentsArray = LongSparseArray<ConfigPersistentComponent>()
        private val NEXT_ID = AtomicLong(0)
    }

    /**
     * 网络操作时弹出loading和取消loading框
     * @param boolean Boolean
     */
    override fun showProgress(boolean: Boolean) {
        if (activity == null) return
        if (boolean) {
//            showLoadingDialog(activity!!)
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
     *  @see com.daydaycook.mitao.widget.statepage.IState
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
     * @param resId Int 图片id
     */
    override fun showSnackBar(msg: String?, resId: Int) {
        SnackBarUtils.showSnackBar(activity!!, msg, resId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the FragmentComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        fragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (componentsArray.get(fragmentId) == null) {
//            LogUtils.i("Creating new ConfigPersistentComponent id=%d", fragmentId)
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(MyApplication[activity as Context].component)
                    .build()
            componentsArray.put(fragmentId, configPersistentComponent)
        } else {
//            LogUtils.i("Reusing ConfigPersistentComponent id=%d", fragmentId)
            configPersistentComponent = componentsArray.get(fragmentId)!!
        }
        fragmentComponent = configPersistentComponent.fragmentComponent(FragmentModule(this))

    }

    /**
     * 初始化沉浸式
     */
    open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).navigationBarColor(R.color.white)
        mImmersionBar?.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isInit = true
        /**初始化的时候去加载数据**/
        isCanLoadData()

        if (isRegisterEventBus)
            EventBus.getDefault().register(this)
    }


    /**
     * 视图是否已经对用户可见，系统的方法
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {

        super.setUserVisibleHint(isVisibleToUser)
        if (!isInit)
            return
        else
            isCanLoadData()
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     * 3.是否已加载过
     */
    private fun isCanLoadData() {
        if (!isInit) {
            return
        }

        if (userVisibleHint && !isLoad) {

            if (isImmersionBarEnabled)
                initImmersionBar()
            //isLoad = true就不会重复lazyLoad,isLoad = false每次visible都lazyLoad数据
            if (!needAlwaysLoad) {
                isLoad = true
            }
            lazyLoad()
        } else {
            if (isLoad) {
                stopLoad()
            }
        }
    }


    @LayoutRes
    abstract fun layoutId(): Int

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_FRAGMENT_ID, fragmentId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isInit = false
        isLoad = false
    }

    override fun onDestroy() {

        if (!activity!!.isChangingConfigurations) {
//            LogUtils.i("Clearing ConfigPersistentComponent id=%d", fragmentId)
            componentsArray.remove(fragmentId)
        }

        super.onDestroy()

        if (isRegisterEventBus)
            EventBus.getDefault().unregister(this)

        if (isImmersionBarEnabled)
            mImmersionBar?.destroy()

    }

    fun fragmentComponent() = fragmentComponent as FragmentComponent

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected open val isRegisterEventBus: Boolean = false

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected open val isImmersionBarEnabled: Boolean = true

    /**
     * 是否每次可见都要load数据
     *
     * @return the boolean
     */
    protected open val needAlwaysLoad: Boolean = false

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected open fun lazyLoad() {

    }

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected open fun stopLoad() {

    }

}