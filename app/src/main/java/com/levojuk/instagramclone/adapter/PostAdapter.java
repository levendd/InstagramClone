package com.levojuk.instagramclone.adapter;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.levojuk.instagramclone.databinding.RecyclerRowBinding;
import com.levojuk.instagramclone.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private static final String EXPECTED_STRING_DATE = "Aug 1, 2018 12:00 PM";
    private static final String DATE_FORMAT = "MMM d, yyyy HH:mm a";
    Date tvDisplayDate ;
    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }

    private ArrayList<Post> postArrayList;

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.binding.recyclerViewUserEmailText.setText(postArrayList.get(position).useremail);

        holder.binding.recyclerViewCommentText.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.binding.recyclerViewImageView);
        holder.binding.datetime.setText(postArrayList.get(position).timestamp);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding binding;
        public PostHolder(@NonNull RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.binding=recyclerRowBinding;
        }
    }
}
