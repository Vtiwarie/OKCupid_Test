package com.vishaan.okcupid.classes;

import android.content.Context;

import java.io.File;

/**
 * Class to handle disk caching operations
 */
public class DiskCache {

    /**
     * File cache reference
     */
    private File mDiskCache;

    public DiskCache(Context context) {
        mDiskCache = context.getCacheDir();
        if (!mDiskCache.exists())
            mDiskCache.mkdirs();
    }

    /**
     * Get the cache file
     *
     * @param url to use a key for the filename
     * @return File
     */
    public File getCacheFile(String url) {
        String filename = String.valueOf(url.hashCode());
        return new File(mDiskCache, filename);

    }
}