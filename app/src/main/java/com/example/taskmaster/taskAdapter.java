package com.example.taskmaster;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.TaskViewHolder>{
    List<Task> tasksList = new ArrayList<Task>();
    private OnNoteListener mOnNoteListener;


    public taskAdapter(List<Task> tasksList,OnNoteListener onNoteListener) {
        this.tasksList = tasksList;
        this.mOnNoteListener = onNoteListener;
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 3.4 add the view and the model object
        public Task task;
        public View itemView;
        public OnNoteListener onNoteListener;

        public TaskViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
            this.itemView = itemView;
        }

        @Override
        public void onClick(View v) {
//            Log.d(TAG, "onClick: " + getAdapterPosition());
            onNoteListener.onNoteClick(getAdapterPosition(),task);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view,mOnNoteListener);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task=tasksList.get(position);
        TextView Title = holder.itemView.findViewById(R.id.Title);
        TextView Body = holder.itemView.findViewById(R.id.Body);
        TextView State = holder.itemView.findViewById(R.id.State);

        Title.setText(holder.task.getTitle());
        Body.setText(holder.task.getBody());
        State.setText(holder.task.getState());


    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position,Task task);
    }

}

