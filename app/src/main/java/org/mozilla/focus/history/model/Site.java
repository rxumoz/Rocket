/* -*- Mode: Java; c-basic-offset: 4; tab-width: 4; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.history.model;

import android.graphics.Bitmap;

/**
 * Created by hart on 03/08/2017.
 */

public class Site {
    private long id;
    private String title;
    private String url;
    private long viewCount;
    private long lastViewTimestamp;
    private Bitmap favIcon;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getViewCount() {
        return this.viewCount;
    }

    public void setViewCount(long count) {
        this.viewCount = count;
    }

    public long getLastViewTimestamp() {
        return this.lastViewTimestamp;
    }

    public void setLastViewTimestamp(long timestamp) {
        this.lastViewTimestamp = timestamp;
    }

    public Bitmap getFavIcon() {
        return this.favIcon;
    }

    public void setFavIcon(Bitmap favIcon) {
        this.favIcon = favIcon;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Site) {
            if (((Site)obj).getId() == this.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
