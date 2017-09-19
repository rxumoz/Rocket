package org.mozilla.focus.screenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.mozilla.focus.FocusApplication;
import org.mozilla.focus.R;
import org.mozilla.focus.fragment.ScreenCaptureDialogFragment;


public class ScreenshotStubActivity extends AppCompatActivity {

    BroadcastReceiver captureBridgeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent == null) {
                return;
            }

            final boolean captureSuccess;
            if (ScreenshotIntent.ACTION_CAPTURE_SUCCESS.equals(intent.getAction())) {
                captureSuccess = true;
                ScreenshotStubActivity.this.setResult(ScreenshotIntent.RESULT_SUCCESS, intent);
            } else if (ScreenshotIntent.ACTION_CAPTURE_FAIL.equals(intent.getAction())) {
                captureSuccess = false;
                ScreenshotStubActivity.this.setResult(ScreenshotIntent.RESULT_FAIL, null);
            } else {
                throw new IllegalArgumentException("unknown Intent action: " + intent.getAction());
            }
            captureFragmentStop(captureSuccess);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_screenshot_stub);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScreenshotIntent.ACTION_CAPTURE_SUCCESS);
        intentFilter.addAction(ScreenshotIntent.ACTION_CAPTURE_FAIL);
        registerReceiver(captureBridgeReceiver, intentFilter, ScreenshotIntent.PERMISSION_APP_DEFAULT, null);
        ((FocusApplication)getApplication()).activityCreated(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        captureFragmentStart();
        ScreenshotIntent.notifyCaptureStart(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(captureBridgeReceiver);
        ((FocusApplication)getApplication()).activityDestroyed(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Do nothing to consume back key
    }

    private void captureFragmentStart() {
        ScreenCaptureDialogFragment screenCaptureDialogFragment = (ScreenCaptureDialogFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (screenCaptureDialogFragment != null) {
            screenCaptureDialogFragment.start();
        }
    }

    private void captureFragmentStop(boolean captureSuccess) {
        ScreenCaptureDialogFragment screenCaptureDialogFragment = (ScreenCaptureDialogFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (screenCaptureDialogFragment != null) {
            screenCaptureDialogFragment.dismiss(captureSuccess);
        }
    }
}
