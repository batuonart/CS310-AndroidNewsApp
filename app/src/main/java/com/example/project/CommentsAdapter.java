package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ListHolder>{

    Context context;
    List<Comments> data;

    public CommentsAdapter(Context context, List<Comments> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentsAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.comments_recycler_row,parent,false);
        CommentsAdapter.ListHolder holder = new CommentsAdapter.ListHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ListHolder holder, int position) {
        holder.txtName.setText(data.get(holder.getAbsoluteAdapterPosition()).getName());
        holder.txtMsg.setText(data.get(holder.getAbsoluteAdapterPosition()).getText());
        holder.imgView.setImageResource(R.drawable.smiley);
    }

    @Override
    public int getItemCount() {
        return data.size();}

    class ListHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtMsg;
        ImageView imgView;

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            txtMsg = itemView.findViewById(R.id.infoText);
            txtName = itemView.findViewById(R.id.dateText);
            imgView = itemView.findViewById(R.id.imageView2);
        }
    }
}
