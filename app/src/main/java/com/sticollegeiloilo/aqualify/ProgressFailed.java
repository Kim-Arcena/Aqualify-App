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

public class ProgressFailed extends RecyclerView.Adapter<ProgressFailed.MyViewHolder> {
    Context context;
    ArrayList<Pfailed> pFailed;

    public ProgressFailed(Context c, ArrayList<Pfailed> p){
        context = c;
        pFailed = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int  i) {
        return new ProgressFailed.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.progress_failed_content,viewGroup,false));
    }



    @Override
    public void onBindViewHolder(@NonNull ProgressFailed.MyViewHolder myViewHolder, int i) {
        myViewHolder.progressTime.setText(pFailed.get(i).getPfailed());
        final String getPfailed = pFailed.get(i).getPfailed();
    }

    @Override
    public int getItemCount() {
        return pFailed.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView progressTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            progressTime = itemView.findViewById(R.id.progressTimeTxtView1);
        }
    }

}