package com.mitu.android.event

import com.mitu.android.data.model.AccountModel

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/14 17:53.
 * description: 账号信息改变
 */
class AccountChangeEvent(data: AccountModel?) : AppEvent<AccountModel>(data)