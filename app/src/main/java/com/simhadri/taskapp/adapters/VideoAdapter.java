package com.simhadri.taskapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.simhadri.taskapp.MainActivity;
import com.simhadri.taskapp.R;
import com.simhadri.taskapp.model.VideoGetter;
import com.simhadri.taskapp.utilities.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VideoAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<VideoGetter> categorylist = new ArrayList<>();
    List<VideoGetter> arraylist;

    private Context mContext;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;


    public VideoAdapter(Context context, List<VideoGetter> categorylist) {
        this.categorylist = categorylist;
        mContext = context;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(categorylist);
    }


//    public void filter(String charText) {
//        try {
//
//            charText = charText.toLowerCase(Locale.getDefault());
//            categorylist.clear();
//            if (charText.length() == 0) {
//                categorylist.addAll(arraylist);
//            } else {
//                for (VideoGetter wp : arraylist) {
//
//                    String title = wp.getSubcategoryName().toLowerCase(Locale.getDefault());
//
//
//                    if (title.contains(charText)) {
//                        categorylist.add(wp);
//                    }
//                }
//            }
//            notifyDataSetChanged();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    public void clear() {
        categorylist.clear();
        notifyDataSetChanged();
    }






    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_adapter, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        viewHolder.setIsRecyclable(false);
        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return categorylist == null ? 0 : categorylist.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return categorylist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }




//        @Override
//        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_cart_list_adapter, parent, false);
//            return new ProductAdapter.ViewHolder(view);
//        }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    public void populateItemRows(final ItemViewHolder holder, final int position) {


        final VideoGetter records = categorylist.get(position);


        Glide.with(mContext)
                .load(records.getThumbnail()).into(holder.iv_exam_img);

        holder.tv_file_Size.setText(records.getFileSize());

        holder.tv_video_date.setText(records.getDateTime());

        if(records.getStatus().equalsIgnoreCase("STATUS_UPLOADED")){
            Glide.with(mContext)
                    .load("https://interview-images-roads.s3.amazonaws.com/STATUS_UPLOADED.png").into(holder.iv_status);

        }else if(records.getStatus().equalsIgnoreCase("STATUS_DOWNLOADED")){
            Glide.with(mContext)
                    .load("https://interview-images-roads.s3.amazonaws.com/STATUS_DOWNLOADED.png").into(holder.iv_status);
        }else if(records.getStatus().equalsIgnoreCase("STATUS_NONE")){
            Glide.with(mContext)
                    .load("https://interview-images-roads.s3.amazonaws.com/STATUS_NONE.png").into(holder.iv_status);
        }


    }

//        @Override
//        public int getItemCount() {
//            return this.categorylist.size();
//        }


    @Override
    public long getItemId(int position) {
        return position;
    }

//        @Override
//        public int getItemViewType(int position) {
//            return position;
//        }

    private class ItemViewHolder extends RecyclerView.ViewHolder {


        CircleImageView iv_exam_img;
        TextView tv_file_Size;
        ImageView iv_status;
        TextView tv_video_date;

        public ItemViewHolder(View itemView) {
            super(itemView);

            iv_exam_img = itemView.findViewById(R.id.iv_video_img);
            tv_file_Size = itemView.findViewById(R.id.tv_file_Size);
            iv_status = itemView.findViewById(R.id.iv_status);
            tv_video_date= itemView.findViewById(R.id.tv_video_date);
        }
    }
}
