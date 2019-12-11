package com.mitu.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : utils about activity
 * </pre>
 */
public final class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return whether the activity exists.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExists(@NonNull final String pkg,
                                           @NonNull final String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        return !(Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getApp().getPackageManager()) == null ||
                Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * Start home activity.
     */
    public static void startHomeActivity() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        startActivity(homeIntent, Utils.getTopActivityOrApp());
    }

    /**
     * Return the list of activity.
     *
     * @return the list of activity
     */
    public static List<Activity> getActivityList() {
        return Utils.getActivityList();
    }

    /**
     * Return the top activity in activity's stack.
     *
     * @return the top activity in activity's stack
     */
    public static Activity getTopActivity() {
        return Utils.getTopActivity();
    }

    /**
     * Return whether the activity exists in activity's stack.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExistsInStack(@NonNull final Activity activity) {
        List<Activity> activities = Utils.getActivityList();
        for (Activity aActivity : activities) {
            if (aActivity.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether the activity exists in activity's stack.
     *
     * @param clz The activity class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExistsInStack(@NonNull final Class<?> clz) {
        List<Activity> activities = Utils.getActivityList();
        for (Activity aActivity : activities) {
            if (aActivity.getClass().equals(clz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finish to the activity.
     *
     * @param activity      The activity.
     * @param isIncludeSelf True to include the activity, false otherwise.
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf) {
        return finishToActivity(activity, isIncludeSelf, false);
    }

    /**
     * Finish to the activity.
     *
     * @param activity      The activity.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @param isLoadAnim    True to use animation for the outgoing activity, false otherwise.
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf True to include the activity, false otherwise.
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf) {
        return finishToActivity(clz, isIncludeSelf, false);
    }

    /**
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @param isLoadAnim    True to use animation for the outgoing activity, false otherwise.
     */
    public static boolean finishToActivity(@NonNull final Class<?> clz,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * Finish all of activities.
     *
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishAllActivities(final boolean isLoadAnim) {
        List<Activity> activityList = Utils.getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }


    /**
     * Finish all of activities except the newest activity.
     */
    public static void finishAllActivitiesExceptNewest() {
        finishAllActivitiesExceptNewest(false);
    }

    /**
     * Finish all of activities except the newest activity.
     *
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishAllActivitiesExceptNewest(final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 2; i >= 0; i--) {
            finishActivity(activities.get(i), isLoadAnim);
        }
    }

    /**
     * Finish the activity.
     *
     * @param activity   The activity.
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    private static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }



    private static void startActivity(final Intent intent,
                                      final Context context) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

    }

}
