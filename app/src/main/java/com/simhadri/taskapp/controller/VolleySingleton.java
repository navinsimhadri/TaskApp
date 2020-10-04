package com.simhadri.taskapp.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by nilesh on 14/5/16.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton(Context context)
    {
        mRequestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(mRequestQueue,new ImageLoader.ImageCache(){
            private LruCache<String, Bitmap> cashe =new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override

            public Bitmap getBitmap(String url){
                return cashe.get(url);
            }

            public void putBitmap(String url,Bitmap bitmap){
                cashe.put(url,bitmap);

            }

        });

    }
    public  static VolleySingleton getInstance(Context context){
        if (sInstance == null)
        {
            sInstance = new VolleySingleton(context);
        }
        return sInstance;

    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;

    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }

}
