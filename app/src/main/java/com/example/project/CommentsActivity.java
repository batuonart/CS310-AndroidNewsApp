package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    ProgressBar prg;
    RecyclerView recyclerView;
    int news_comment_id = 0;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<Comments> data = (List<Comments>)message.obj;
            CommentsAdapter adp = new CommentsAdapter( CommentsActivity.this, data);
            recyclerView.setAdapter(adp);
            recyclerView.setVisibility(View.VISIBLE);
            prg.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mycommentmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.myybutton) {
            Intent i = new Intent(CommentsActivity.this, CommentsPostActivity.class);
            i.putExtra("news_comment_id",news_comment_id);
            startActivity(i);
        }


        if(id==android.R.id.home){
            Intent i = new Intent(CommentsActivity.this, ActivityDetails.class);
            i.putExtra("id",news_comment_id);
            startActivity(i);
        }

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setTitle("Comments");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.commentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prg = findViewById(R.id.commentsPB);
        prg.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        news_comment_id = intent.getIntExtra("news_id",0);

        CommentsRepo repo = new CommentsRepo();
        repo.getCommentsById(((ThreadApplication)getApplication()).srv, handler, news_comment_id);


    }
}