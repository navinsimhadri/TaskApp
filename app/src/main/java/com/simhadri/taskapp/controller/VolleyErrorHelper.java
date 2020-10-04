package com.simhadri.taskapp.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.simhadri.taskapp.R;


/**
 * Created by rajup on 20-03-2018.
 */

public class VolleyErrorHelper {



    private VolleyErrorHelper() {
    }

    /**
     * @param error
     * @param context
     * @return Returns appropriate message which is to be displayed to the user against
     * <p>
     * the specified error object.
     */
    public static String getMessage(Object error, Context context) {
        try {
            if (error instanceof TimeoutError) {
                return context.getResources().getString(R.string.generic_server_timeout);
            } /*else if (isServerProblem(error)) {
                return handleServerError(error, context);
            }*/ else if (isNetworkProblem(error)) {
                ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null) {
                    return context.getResources().getString(R.string.no_internet);
                } else {
                    return context.getResources().getString(R.string.generic_server_timeout);
                }
            } else /*if (isServerProblem(error))*/ {
                return context.getResources().getString(R.string.generic_server_timeout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getString(R.string.no_internet);
    }

    /**
     * @param error
     * @param context
     * @return Return generic message for errors
     */




    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */

    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */



}
