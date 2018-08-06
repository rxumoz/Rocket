/* -*- Mode: Java; c-basic-offset: 4; tab-width: 20; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus;

import android.preference.PreferenceManager;

import com.squareup.leakcanary.LeakCanary;

import org.mozilla.focus.download.DownloadInfoManager;
import org.mozilla.focus.history.BrowsingHistoryManager;
import org.mozilla.focus.locale.LocaleAwareApplication;
import org.mozilla.focus.screenshot.ScreenshotManager;
import org.mozilla.focus.search.SearchEngineManager;
import org.mozilla.focus.telemetry.TelemetryWrapper;
import org.mozilla.focus.utils.AdjustHelper;

public class FocusApplication extends LocaleAwareApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        // Provide different strict mode penalty for ui testing and production code
        //Inject.enableStrictMode();
        SearchEngineManager.getInstance().init(this);

        TelemetryWrapper.init(this);
        TelemetryWrapper.startApp();
      //  AdjustHelper.setupAdjustIfNeeded(this);

        BrowsingHistoryManager.getInstance().init(this);
        ScreenshotManager.getInstance().init(this);
        DownloadInfoManager.getInstance().init(this);

    }


}
