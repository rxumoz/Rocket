/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsSessionToken;

import java.util.List;

public class CustomTabsService extends android.support.customtabs.CustomTabsService {

    @Override
    protected boolean warmup(long flags) {
        return false;
    }

    @Override
    protected boolean newSession(CustomTabsSessionToken sessionToken) {
        return false;
    }

    @Override
    protected boolean mayLaunchUrl(CustomTabsSessionToken sessionToken, Uri url, Bundle extras, List<Bundle> otherLikelyBundles) {
        return false;
    }

    @Override
    protected Bundle extraCommand(String commandName, Bundle args) {
        return null;
    }

    @Override
    protected boolean updateVisuals(CustomTabsSessionToken sessionToken, Bundle bundle) {
        return false;
    }

    @Override
    protected boolean requestPostMessageChannel(CustomTabsSessionToken sessionToken, Uri postMessageOrigin) {
        return false;
    }

    @Override
    protected int postMessage(CustomTabsSessionToken sessionToken, String message, Bundle extras) {
        return 0;
    }

    @Override
    protected boolean validateRelationship(CustomTabsSessionToken sessionToken, int relation, Uri origin, Bundle extras) {
        return false;
    }
}
