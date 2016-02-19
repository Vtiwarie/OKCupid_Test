package com.vishaan.okcupid.classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to assist with loading images onto an imageview,
 * either by retrieving a cached version or loading from
 * the web
 */
public class ImageViewLoader {

    /**
     * Tag used for debugging
     */
    private static final String TAG = ImageViewLoader.class.getSimpleName();

    /**
     * Executor services to create multiple threads in parallel
     */
    private ExecutorService mExecutorService;

    /**
     * Object used to manage cache files
     */
    private DiskCache mDiskCachefileCache;

    /**
     * Hash map to store image view references
     */
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    /**
     * Placeholder image, in case no image is found
     */
    private static final int placeholderId = android.R.drawable.ic_lock_idle_lock;

    public ImageViewLoader(Context context) {
        mDiskCachefileCache = new DiskCache(context);
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Display the Image on an imageview
     *
     * @param url       to fetch the image from is the image is not cached
     * @param imageView to load the image on
     */
    public void DisplayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = null;
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            startLoaderThread(url, imageView);
            imageView.setImageResource(placeholderId);
        }
    }

    /**
     * Begin a new thread to either load the image from cache or retrieve from network
     *
     * @param url       to retrieve the image from
     * @param imageView to load the image on
     */
    private void startLoaderThread(String url, ImageView imageView) {
        PhotoLoader p = new PhotoLoader(url, imageView);
        mExecutorService.submit(new PhotosLoader(p));
    }

    /**
     * Generates a mBitmap using the url, either as a key to retrieve
     * the cache file, or to load the image view from the web
     *
     * @param url
     * @return Bitmap
     */
    private Bitmap generateBitmap(String url) {
        //first attempt to retrieve from cache
        File file = mDiskCachefileCache.getCacheFile(url);
        Bitmap bitmap = resizeBitmap(file);
        if (bitmap != null)
            return bitmap;

        //if image is not cached, attempt to retrieve from the web
        return getBitmapFromWeb(url, file);
    }

    /**
     * Retrieve image from the web and cache it
     *
     * @param url  to load image from
     * @param file to cache image into
     * @return Bitmap
     */
    private Bitmap getBitmapFromWeb(String url, File file) {
        try {
            URL urlImage = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlImage.openConnection();
            conn.setInstanceFollowRedirects(true);
            InputStream inputStream = conn.getInputStream();
            OutputStream outputStream = new FileOutputStream(file);
            Helper.CopyStream(inputStream, outputStream);
            outputStream.close();
            return resizeBitmap(file);
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Resize and decode the mBitmap, so as to avoid out-of-memory exceptions
     *
     * @param file
     * @return Bitmap
     */
    private Bitmap resizeBitmap(File file) {
        try {
            //decode image size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);

            //Find the scale value using the power of 2
            final int REQUIRED_SIZE = 500;
            int width = options.outWidth, height = options.outHeight;
            int scale = 1;
            while (true) {
                if (width / 2 < REQUIRED_SIZE || height / 2 < REQUIRED_SIZE)
                    break;
                width /= 2;
                height /= 2;
                scale *= 2;
            }

            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(file), null, options2);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //Class for loading images
    private class PhotoLoader {
        public String url;
        public ImageView imageView;

        public PhotoLoader(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }
    }

    //generate the mBitmap and update the UI thread
    private class PhotosLoader implements Runnable {
        PhotoLoader mPhotoLoader;

        PhotosLoader(PhotoLoader photoToLoad) {
            mPhotoLoader = photoToLoad;
        }

        @Override
        public void run() {
            if (isImageViewReused(mPhotoLoader)) {
                return;
            }
            Bitmap bmp = generateBitmap(mPhotoLoader.url);
            if (isImageViewReused(mPhotoLoader)) {
                return;
            }
            ImageDisplay imageDisplay = new ImageDisplay(bmp, mPhotoLoader);
            Activity activity = (Activity) mPhotoLoader.imageView.getContext();
            activity.runOnUiThread(imageDisplay);
        }
    }

    //Used to display mBitmap in the UI thread
    private class ImageDisplay implements Runnable {
        Bitmap mBitmap;
        PhotoLoader mPhotoLoader;

        public ImageDisplay(Bitmap bitmap, PhotoLoader photoLoader) {
            mBitmap = bitmap;
            mPhotoLoader = photoLoader;
        }

        public void run() {
            if (isImageViewReused(mPhotoLoader))
                return;
            if (mBitmap != null)
                mPhotoLoader.imageView.setImageBitmap(mBitmap);
            else
                mPhotoLoader.imageView.setImageResource(placeholderId);
        }
    }

    /**
     * Check that the image wasn't already loaded
     *
     * @param photoToLoad
     * @return boolean
     */
    boolean isImageViewReused(PhotoLoader photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url)) {
            return true;
        }
        return false;
    }
}