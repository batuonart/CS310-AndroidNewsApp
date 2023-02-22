package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ListHolder> {

    Context context;
    List<NewsHome> newsData;

    public NewsRecyclerViewAdapter(Context context, List<NewsHome> newsData) {
        this.context = context;
        this.newsData = newsData;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_row,parent,false);
        ListHolder holder = new ListHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        holder.txtName.setText(newsData.get(holder.getAbsoluteAdapterPosition()).getTitle());
        holder.txtDate.setText(newsData.get(holder.getAbsoluteAdapterPosition()).getDate());
        ThreadApplication app = (ThreadApplication)((AppCompatActivity)context).getApplication();
        holder.downloadImage(app.srv,newsData.get(holder.getAbsoluteAdapterPosition()).getImagePath());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,ActivityDetails.class);
                i.putExtra("id",newsData.get(holder.getAbsoluteAdapterPosition()).getId());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return newsData.size();}

    class ListHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtDate;
        ConstraintLayout row;
        ImageView imgList;
        boolean imageDownloaded;

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Bitmap img = (Bitmap)msg.obj;
                imgList.setImageBitmap(img);
                imageDownloaded = true;

                return true;
            }
        });

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.infoText);
            txtDate = itemView.findViewById(R.id.dateText);
            row = itemView.findViewById(R.id.row_list);
            imgList = itemView.findViewById(R.id.imageView);

        }
        public void downloadImage(ExecutorService srv, String path)
        {
            if(!imageDownloaded)
            {
                NewsRepo repo = new NewsRepo();
                repo.downloadImage(srv,imgHandler,path);

            }
        }


    }


}
