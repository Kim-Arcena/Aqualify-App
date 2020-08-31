package com.sticollegeiloilo.aqualify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.MyViewHolder> {
    Context context;
    ArrayList<Progress> progress;

    public ProgressAdapter(Context c, ArrayList<Progress> p){
        context = c;
        progress = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int  i) {
        return new ProgressAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.progress_content,viewGroup,false));
    }



    @Override
    public void onBindViewHolder(@NonNull ProgressAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.progressTime.setText(progress.get(i).getProgress());
        final String getProgress = progress.get(i).getProgress();
    }

    @Override
    public int getItemCount() {
        return progress.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView progressTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            progressTime = itemView.findViewById(R.id.progressTimeTxtView);
        }
    }

}