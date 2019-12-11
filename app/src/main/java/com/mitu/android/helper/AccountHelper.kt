package com.mitu.android.helper

import android.webkit.WebStorage
import com.mitu.android.constant.Constants
import com.mitu.android.data.local.SPManager
import com.mitu.android.data.model.AccountModel
import com.mitu.android.utils.LogUtils


/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/7/16 16:21.
 * description: 账号帮助类
 */
object AccountHelper {

    /**
     * 全局用户账号信息
     */
    private var user: AccountModel? = null


    /**
     * 全局判断是否登录
     * @return Boolean
     */
    fun hasLogin(): Boolean {
        LogUtils.e("getUser()=" + getUser() + " getUser()?.token=" + getUser()?.token)
        return getUser() != null && getUser()?.token?.isNotEmpty() ?: false
    }

    /**
     * 全局获取登录后的用户信息
     * @return
     */
    fun getUser(): AccountModel? {
        if (user == null)
            account
        return user
    }

    /**
     * 全局使用的判断登录操作
     * @param context
     * @return 返回true没有登录 返回false已经登录
     */
//    fun login(context: Context): Boolean {
//        return if (!hasLogin()) {
//            val intent = Intent(context, LoginActivity::class.java)
//            context.startActivity(intent)
//            true
//        } else {
//            false
//        }
//    }


    /**
     * 全局使用的退出登录，清除账号数据
     */
    fun loginOut() {
        deleteAccount()
        WebStorage.getInstance().deleteAllData()//清空LocalStorage
    }


    /**
     * 删除用户登录后的用户信息
     */
    private fun deleteAccount() {
        user = null
        SPManager.remove(Constants.SP_ACCOUNT)
    }

    /**
     * 获取登录后的用户信息
     *
     * @return
     */
    /**
     * 存储登录后的用户信息
     *
     * @param
     */
    var account: AccountModel?
        get() {
            val accountModel = SPManager.getObject(Constants.SP_ACCOUNT, AccountModel::class.java)
            user = accountModel
            return accountModel
        }
        set(accountModel) {
            SPManager.putObject(Constants.SP_ACCOUNT, accountModel)
            user = accountModel
        }


}
