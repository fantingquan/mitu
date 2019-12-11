package com.mitu.android.features.splash

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle

import com.master.permissionhelper.PermissionHelper
import com.mitu.android.R
import com.mitu.android.base.BaseActivity
import com.mitu.android.features.MainActivity
import com.mitu.android.helper.AccountHelper
import com.mitu.android.helper.DialogHelper
import com.mitu.android.utils.LogUtils
import com.mitu.android.utils.PermissionUtils
import javax.inject.Inject

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/7/10 15:27.
 * description: 启动页
 */
class SplashActivity : BaseActivity(), SplashContract.View {

    @Inject
    lateinit var splashPresenter: SplashPresenter
    override val layout: Int
        get() = R.layout.activity_splash

    private var permissionHelper: PermissionHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        splashPresenter.attachView(this)

    }

    private fun requestStoragePermission() {
        permissionHelper?.request(object : PermissionHelper.PermissionCallback {
            override fun onPermissionGranted() {
                LogUtils.i("onPermissionGranted")
                if (AccountHelper.hasLogin()) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
//                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }

            override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                LogUtils.i("onIndividualPermissionGranted")
            }

            override fun onPermissionDenied() {
                LogUtils.i("onPermissionDenied")
                DialogHelper.showRationaleDialog(this@SplashActivity, getString(R.string.storage_permission_rationale_message),
                        DialogInterface.OnClickListener { _, _ ->
                            requestStoragePermission()
                        },
                        DialogInterface.OnClickListener { _, _ ->
                            finish()
                        })

            }

            override fun onPermissionDeniedBySystem() {
                LogUtils.i("onPermissionDeniedBySystem")
                DialogHelper.showOpenAppSettingDialog(this@SplashActivity, getString(R.string.storage_permission_denied_forever_message),
                        DialogInterface.OnClickListener { _, _ ->
                            PermissionUtils.launchAppDetailsSettings()
                        },
                        DialogInterface.OnClickListener { _, _ ->
                            finish()
                        })
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onDestroy() {
        super.onDestroy()
        splashPresenter.detachView()
        permissionHelper = null
    }

    override fun onStart() {
        super.onStart()
        LogUtils.i("onStart")
        if (permissionHelper == null) {
            permissionHelper = PermissionHelper(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 100)
            requestStoragePermission()
        } else {
            requestStoragePermission()
        }
    }

}
