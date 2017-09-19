package org.mozilla.focus.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.mozilla.focus.FocusApplication;
import org.mozilla.focus.screenshot.ScreenshotStubActivity;


public class LauncherActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        filterFlags(intent);

        FocusApplication application = (FocusApplication) getApplication();
        if (application.isCaptureing()) {
            dispatchCaptureActivity(intent);
        } else {
            dispatchNormalBrowser(intent);
        }

        finish();
    }

    private void dispatchCaptureActivity(Intent intent) {
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setComponent(new ComponentName(this, ScreenshotStubActivity.class));
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void dispatchNormalBrowser(Intent intent) {
        intent.setComponent(new ComponentName(this, MainActivity.class));
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private static void filterFlags(Intent intent) {
        intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // LauncherActivity is started with the "exclude from recents" flag (set in manifest). We do
        // not want to propagate this flag from the launcher activity to the browser.
        intent.setFlags(intent.getFlags() & ~Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

}
