package com.mitu.android.config;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Life Is Better With DayDayCook
 * author: HuangQiang
 * created on: 2018/12/13 20:47.
 * description:关于华为inputMethodManager内存泄露解决
 */
public class FixMemLeak {
    private static Field field;

    public static void fixLeak(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mLastSrvView"};
        for (String param : arr) {
            try {
                if (field == null) {
                    field = imm.getClass().getDeclaredField(param);
                }
                field.setAccessible(true);
                field.set(imm, null);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
