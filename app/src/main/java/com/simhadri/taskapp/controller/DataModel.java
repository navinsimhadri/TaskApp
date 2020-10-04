package com.simhadri.taskapp.controller;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class DataModel {

    public static DataModel mInstance = null;
    public static ProgressDialog progressDialog;

    protected DataModel(){}

    public static synchronized DataModel sharedInstance(){
        if(null == mInstance){
            mInstance = new DataModel();
        }
        return mInstance;
    }



    public void getResponsefromGETAPI(final Boolean showProgressDialog, final Context ctx, String url, final ServerCallback callback) {
        try {


                progressDialog = new ProgressDialog(ctx);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCancelable(false);
            if (showProgressDialog) {
                progressDialog.show();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("skip--", response);
                            try {
                                if (showProgressDialog) {
                                    progressDialog.dismiss();
                                    progressDialog.cancel();
                                }
                                Log.d("VolleySuccess Handler: ", response.toString());
                                callback.onSuccess(response);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (showProgressDialog) {
                                progressDialog.dismiss();
                                progressDialog.cancel();
                            }
                            error.printStackTrace();
                            String strMsg = VolleyErrorHelper.getMessage(error, ctx);
                            try {

                               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                                // set title
                                alertDialogBuilder.setTitle("Alert!");
                                // set dialog message
                                alertDialogBuilder.setMessage(strMsg)
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    return params;
                }


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    int mStatusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = new HashMap<>();

                   // headers.put("Authorization","Basic aGdkZW1vOmhnMjAyMA=");

                    headers.put("x-api-key","jvmNAyPNr1JhiCeUlYmB2ae517p3Th0aGG6syqMb");

                    return headers;

                    //return super.getHeaders();
                }
            };

//            RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
//            requestQueue.add(stringRequest);

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = VolleySingleton.getInstance(ctx).getRequestQueue();
            requestQueue.add(stringRequest);


        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public interface ServerCallback{
        void onSuccess(String result);

    }




}
