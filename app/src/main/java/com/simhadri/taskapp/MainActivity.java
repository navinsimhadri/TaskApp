package com.simhadri.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.simhadri.taskapp.adapters.VideoAdapter;
import com.simhadri.taskapp.constants.AppConstant;
import com.simhadri.taskapp.controller.DataModel;
import com.simhadri.taskapp.controller.HttpsTrustManager;
import com.simhadri.taskapp.controller.VolleySingleton;
import com.simhadri.taskapp.model.VideoGetter;
import com.simhadri.taskapp.utilities.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.simhadri.taskapp.constants.AppConstant.BASE_VIDEO_LIST_URL;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_videos_list;
    ArrayList<VideoGetter> videolist;
    boolean isLoading = false;
    int upcomingpagenumber = 0;
     VideoAdapter adapter;

    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_videos_list = (RecyclerView) findViewById(R.id.rv_videos_list);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        videolist = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_videos_list.setLayoutManager(layoutManager);
        adapter = new VideoAdapter(MainActivity.this, videolist);
        rv_videos_list.setAdapter(adapter);


        loadvideoslist();


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.

                if(adapter!=null){
                    adapter.clear();
                }
                isLoading = false;
                upcomingpagenumber = 0;
                loadvideoslist();
            }
        });


        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        rv_videos_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == videolist.size() - 1) {
                        //bottom of list!
                        upcomingpagenumber = upcomingpagenumber+6;

                        loadvideolistmore(upcomingpagenumber);

                        isLoading = true;
                    }
                }


            }
        });

    }


    private void loadvideoslist(){

        try {

            String catURL = AppConstant.BASE_VIDEO_LIST_URL;
            catURL += "?offset=";
            catURL += upcomingpagenumber;
            catURL += "&limit=6";

            swipeContainer.setRefreshing(true);

            DataModel.sharedInstance().getResponsefromGETAPI(false, MainActivity.this, catURL, new DataModel.ServerCallback() {
                public void onSuccess(String response) {


                    swipeContainer.setRefreshing(false);
                    try {

                        if (response != null) {
                            try {
//                                VolleyLog.v("Response:%n %s", response.toString(4));
//                                Log.d("Sample" ,response.toString());
//                                JSONObject jsonObject = response;

                                JSONArray examjson = new JSONArray(response);


                                for (int i = 0; i < examjson.length(); i++) {
                                    JSONObject jobject = examjson.getJSONObject(i);
                                    String dateTime = jobject.getString("dateTime");
                                    String status = jobject.getString("status");
                                    String thumbnail = jobject.getString("thumbnail");
                                    String fileSize = jobject.getString("fileSize");
                                    String id = jobject.getString("id");


                                    VideoGetter record1 = new VideoGetter(dateTime,status,thumbnail,fileSize,id);
                                    videolist.add(record1);
                                }


                                if(videolist.size()!=0){

                                    adapter = new VideoAdapter(MainActivity.this, videolist);
                                    rv_videos_list.setAdapter(adapter);

                                }else {
                                    //showpopgameover("No Videos Found!");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void loadvideolistmore(int upcomingpagenumbers){

        try {


            if(videolist.size()>1) {
                videolist.add(null);
                adapter.notifyItemInserted(videolist.size() - 1);
            }

            String catURL = AppConstant.BASE_VIDEO_LIST_URL;
            catURL += "?offset=";
            catURL += upcomingpagenumbers;
            catURL += "&limit=6";

            DataModel.sharedInstance().getResponsefromGETAPI(false, MainActivity.this, catURL, new DataModel.ServerCallback() {
                public void onSuccess(String response) {


                    try {

                        if(videolist.size() !=0){
                            int value = videolist.size() - 1;
                            VideoGetter valuess =videolist.get(value);
                            if(valuess == null){
                                videolist.remove(videolist.size() - 1);
                                int scrollPosition = videolist.size();
                                adapter.notifyItemRemoved(scrollPosition);
                            }

                        }


                        if (response != null) {
                            try {
//                                VolleyLog.v("Response:%n %s", response.toString(4));
//                                Log.d("Sample" ,response.toString());
//                                JSONObject jsonObject = response;

                                JSONArray examjson = new JSONArray(response);


                                for (int i = 0; i < examjson.length(); i++) {
                                    JSONObject jobject = examjson.getJSONObject(i);
                                    String dateTime = jobject.getString("dateTime");
                                    String status = jobject.getString("status");
                                    String thumbnail = jobject.getString("thumbnail");
                                    String fileSize = jobject.getString("fileSize");
                                    String id = jobject.getString("id");


                                    VideoGetter record1 = new VideoGetter(dateTime,status,thumbnail,fileSize,id);
                                    videolist.add(record1);
                                }


                                if (videolist.size() > 0) {

                                    adapter.notifyDataSetChanged();
                                    isLoading = false;

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            adapter.notifyDataSetChanged();
                            isLoading = false;
                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }





}