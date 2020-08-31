package com.sticollegeiloilo.aqualify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>{
    Context context;
    ArrayList<Notes> notes;


    public NotesAdapter(Context c, ArrayList<Notes> p){
        context = c;
        notes = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.note_content,viewGroup,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.notesTitle.setText(notes.get(i).getTitleNote());
        myViewHolder.content.setText(notes.get(i).getContentNote());
        myViewHolder.deadlineNote.setText(notes.get(i).getDeadline());
        final String getNotetitle = notes.get(i).getTitleNote();
        final String getContent = notes.get(i).getContentNote();
        final String getDeadline = notes.get(i).getDeadline();
        final String getNoteKey = notes.get(i).getNoteId();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,EditNote.class);
                i.putExtra("titleNote", getNotetitle);
                i.putExtra("deadline", getDeadline);
                i.putExtra("contentNote", getContent);
                i.putExtra("noteId", getNoteKey);



                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

    TextView notesTitle,content,deadlineNote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notesTitle = itemView.findViewById(R.id.titleNote);
            content = itemView.findViewById(R.id.contentNote);
            deadlineNote = itemView.findViewById(R.id.deadline);
        }
    }

}
