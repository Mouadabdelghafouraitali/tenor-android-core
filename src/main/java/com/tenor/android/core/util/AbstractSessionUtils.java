package com.tenor.android.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.tenor.android.core.constant.StringConstant;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The session utility class
 */
public abstract class AbstractSessionUtils {
    private static final String DEVICE_PREF = "device_preferences";

    private static final String KEY_INSTALLED_PACKAGES_SET_ID = "KEY_INSTALLED_PACKAGES_SET_ID";
    private static final String KEY_KEYBOARD_ID = "KEY_KEYBOARD_ID";
    private static final String KEY_GOOGLE_CLOUD_MESSAGING_ID = "KEY_GOOGLE_CLOUD_MESSAGING_ID";
    private static final String KEY_ANDROID_ADVERTISE_ID = "KEY_ANDROID_ADVERTISE_ID";

    private static final String KEY_KEYBOARD_RESPONSE_CACHE_SIZE = "KEY_KEYBOARD_RESPONSE_CACHE_SIZE";
    private static final String KEY_KEYBOARD_RESPONSE_CACHE_TIMEOUT = "KEY_KEYBOARD_RESPONSE_CACHE_TIMEOUT";
    private static final String KEY_KEYBOARD_REQUEST_DELAY = "KEY_KEYBOARD_REQUEST_DELAY";

    protected static SharedPreferences getPreferences(@NonNull final Context context) {
        return context.getSharedPreferences(DEVICE_PREF, Context.MODE_PRIVATE);
    }

    protected static void remove(@NonNull final Context context, String... keys) {
        SharedPreferences.Editor edit = getPreferences(context).edit();
        for (String key : keys) {
            edit.remove(key);
        }
        edit.apply();
    }

    @NonNull
    public static Set<String> getInstalledPackages(@NonNull final Context context) {
        String json = getPreferences(context).getString(
                KEY_INSTALLED_PACKAGES_SET_ID, StringConstant.EMPTY);
        if (TextUtils.isEmpty(json)) {
            return new HashSet<>();
        }

        Type type = new TypeToken<Set<String>>() {
        }.getType();
        return AbstractGsonUtils.getInstance().fromJson(json, type);
    }

    public static void setInstalledPackages(@NonNull final Context context, @Nullable final Set<String> set) {
        String json = StringConstant.EMPTY;
        if (set != null && !set.isEmpty()) {
            json = AbstractGsonUtils.getInstance().toJson(set);
        }
        getPreferences(context).edit().putString(KEY_INSTALLED_PACKAGES_SET_ID, json).apply();
    }

    public static synchronized void setKeyboardId(@NonNull final Context context, @Nullable final String keyboardId) {
        if (TextUtils.isEmpty(keyboardId)) {
            return;
        }
        getPreferences(context).edit().putString(KEY_KEYBOARD_ID, keyboardId).apply();
    }

    public static synchronized String getKeyboardId(@NonNull final Context context) {
        return getPreferences(context).getString(KEY_KEYBOARD_ID, StringConstant.EMPTY);
    }

    public static boolean hasKeyboardId(@NonNull final Context context) {
        return !TextUtils.isEmpty(getKeyboardId(context));
    }

    public static void setGoogleCloudMessagingId(@NonNull final Context context,
                                                 @Nullable final String googleCloudMessagingId) {
        if (TextUtils.isEmpty(googleCloudMessagingId)) {
            return;
        }
        getPreferences(context).edit().putString(KEY_GOOGLE_CLOUD_MESSAGING_ID, googleCloudMessagingId).apply();
    }

    public static String getGoogleCloudMessagingId(@NonNull final Context context) {
        return getPreferences(context).getString(KEY_GOOGLE_CLOUD_MESSAGING_ID, StringConstant.EMPTY);
    }

    public static void setAndroidAdvertiseId(@NonNull final Context context,
                                             @Nullable final String aaid) {
        // empty string is used if aaid is not available
        if (aaid == null) {
            return;
        }
        getPreferences(context).edit().putString(KEY_ANDROID_ADVERTISE_ID, aaid).apply();
    }

    public static String getAndroidAdvertiseId(@NonNull final Context context) {
        return getPreferences(context).getString(KEY_ANDROID_ADVERTISE_ID, StringConstant.EMPTY);
    }

    protected static void setKeyboardResponseCacheSize(@NonNull final Context context,
                                                       final int size) {
        getPreferences(context).edit().putInt(KEY_KEYBOARD_RESPONSE_CACHE_SIZE, size).apply();
    }

    public static int getKeyboardResponseCacheSize(@NonNull final Context context) {
        // default 25 items
        return getPreferences(context).getInt(KEY_KEYBOARD_RESPONSE_CACHE_SIZE, 25);
    }

    protected static void setKeyboardResponseCacheTimeout(@NonNull final Context context,
                                                          final long timeout) {
        getPreferences(context).edit().putLong(KEY_KEYBOARD_RESPONSE_CACHE_TIMEOUT, timeout).apply();
    }

    public static long getKeyboardResponseCacheTimeout(@NonNull final Context context) {
        // default 30 minutes
        return getPreferences(context).getLong(KEY_KEYBOARD_RESPONSE_CACHE_TIMEOUT, TimeUnit.MINUTES.toMillis(30));
    }

    protected static void setKeyboardRequestDelay(@NonNull final Context context,
                                                  final long timeout) {
        getPreferences(context).edit().putLong(KEY_KEYBOARD_REQUEST_DELAY, timeout).apply();
    }

    public static long getKeyboardRequestDelay(@NonNull final Context context) {
        // default 100 ms
        return getPreferences(context).getLong(KEY_KEYBOARD_REQUEST_DELAY, 100);
    }
}