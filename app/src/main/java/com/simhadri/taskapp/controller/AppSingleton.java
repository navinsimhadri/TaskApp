    package com.simhadri.taskapp.controller;

    import android.content.Context;
    import android.graphics.Bitmap;
    import android.util.LruCache;

    import com.android.volley.DefaultRetryPolicy;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.toolbox.ImageLoader;
    import com.android.volley.toolbox.Volley;

    /**
     * Created by androidtutorialpoint on 5/11/16.
     */
    public class AppSingleton {
        private static AppSingleton mAppSingletonInstance;
        private RequestQueue mRequestQueue;
        private ImageLoader mImageLoader;
        private static Context mContext;

        private AppSingleton(Context context) {

            try {





            mContext = context;
            mRequestQueue = getRequestQueue();

            mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                }
            );


            }catch (Exception e){
                e.printStackTrace();
            }


        }

        public static synchronized AppSingleton getInstance(Context context) {
            try {



            if (mAppSingletonInstance == null) {
                mAppSingletonInstance = new AppSingleton(context);
            }


            }catch (Exception e){
                e.printStackTrace();
            }

            return mAppSingletonInstance;
        }

        public RequestQueue getRequestQueue() {
            try {

            if (mRequestQueue == null) {
                // getApplicationContext() is key, it keeps you from leaking the
                // Activity or BroadcastReceiver if someone passes one in.
                mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
            return mRequestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req, String tag) {
            try {


            req.setTag(tag);
            req.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getRequestQueue().add(req);



            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public ImageLoader getImageLoader() {
            return mImageLoader;
        }

        public void cancelPendingRequests(Object tag) {
            if (mRequestQueue != null) {
                mRequestQueue.cancelAll(tag);
            }
        }
    }