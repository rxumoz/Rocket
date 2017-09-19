package org.mozilla.focus.screenshot;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;

import org.mozilla.focus.BuildConfig;
import org.mozilla.focus.utils.ActivityLifecycleCallbackAdapter;

public class ScreenshotIntent {

    public static final int REQUEST_CODE = 2000;

    public static final int RESULT_SUCCESS = REQUEST_CODE + 1;
    public static final int RESULT_FAIL = REQUEST_CODE + 2;

    public static final String PERMISSION_APP_DEFAULT = BuildConfig.APPLICATION_ID + ".permission.APP_DEFAULT";

    public static final String ACTION_CAPTURE_START = BuildConfig.APPLICATION_ID + ".intent.action.CAPTURE_START";
    public static final String ACTION_CAPTURE_SUCCESS = BuildConfig.APPLICATION_ID + ".intent.action.CAPTURE_SUCCESS";
    public static final String ACTION_CAPTURE_FAIL = BuildConfig.APPLICATION_ID + ".intent.action.CAPTURE_FAIL";

    private static final String KEY_EXTRA_STRING_FILE_PATH = "key-extra-string-file-path";

    public static void startStubActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(activity, ScreenshotStubActivity.class));
        activity.startActivityForResult(intent, REQUEST_CODE);
        activity.overridePendingTransition(0, 0);
    }

    public static String parseFilePath(Intent intent) {
        if (intent == null) {
            return null;
        }
        String path = intent.getStringExtra(KEY_EXTRA_STRING_FILE_PATH);
        return path;
    }

    public static void notifyCaptureStart(Context context) {
        Intent intent = new Intent(ACTION_CAPTURE_START);
        context.sendBroadcast(intent, PERMISSION_APP_DEFAULT);
    }

    public static void notifyCaptureSuccess(Context context, String filePath) {
        Intent intent = new Intent(ACTION_CAPTURE_SUCCESS);
        intent.putExtra(KEY_EXTRA_STRING_FILE_PATH, filePath);
        context.sendBroadcast(intent, PERMISSION_APP_DEFAULT);
    }

    public static void notifyCaptureFail(Context context) {
        Intent intent = new Intent(ACTION_CAPTURE_FAIL);
        context.sendBroadcast(intent, PERMISSION_APP_DEFAULT);
    }

    public static void startCaptureBridge(Activity activity, CaptureCallback captureCallback) {
        BroadcastReceiver captureBridgeReceiver = new CaptureBridgeReceiver(captureCallback);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScreenshotIntent.ACTION_CAPTURE_START);
        activity.registerReceiver(captureBridgeReceiver, intentFilter, PERMISSION_APP_DEFAULT, null);
        hookAutoLifecycle(activity, captureBridgeReceiver);
    }

    private static void hookAutoLifecycle(final Activity activity, final BroadcastReceiver captureBridgeReceiver) {
        final Application application = activity.getApplication();
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbackAdapter() {

            Activity bindActivity = activity;
            BroadcastReceiver broadcastReceiver = captureBridgeReceiver;

            @Override
            public void onActivityResumed(Activity activity) {
                unregister(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                unregister(activity);
            }

            void unregister(Activity activity) {
                if (broadcastReceiver == null) {
                    return;
                }

                if (bindActivity == null) {
                    return;
                }

                if (activity != bindActivity) {
                    return;
                }

                bindActivity.unregisterReceiver(broadcastReceiver);
                application.unregisterActivityLifecycleCallbacks(this);

                bindActivity = null;
                broadcastReceiver = null;
            }
        });
    }

    public static final class CaptureBridgeReceiver extends BroadcastReceiver {

        private final CaptureCallback captureCallback;

        CaptureBridgeReceiver(@NonNull CaptureCallback callback) {
            captureCallback = callback;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            switch (intent.getAction()) {
                case ACTION_CAPTURE_START:
                    captureCallback.onCaptureStart();
                    break;
                case ACTION_CAPTURE_SUCCESS:
                    captureCallback.onCaptureSuccess(parseFilePath(intent));
                    break;
                case ACTION_CAPTURE_FAIL:
                    captureCallback.onCaptureFail();
                default:
                    Log.e(CaptureBridgeReceiver.class.getSimpleName(), "unknown action: " + intent.getAction());
                    break;
            }
        }
    }
}
