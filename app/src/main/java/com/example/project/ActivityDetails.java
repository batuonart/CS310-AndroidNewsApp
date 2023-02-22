package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDetails extends AppCompatActivity {

    ImageView imgDetails;
    TextView txtTitleDetail;
    TextView txtDateDetail;
    TextView txtInfoDetail;
    int news_id = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.mybutton) {
            Intent i = new Intent(ActivityDetails.this,CommentsActivity.class);
            i.putExtra("news_id",news_id);
            startActivity(i);
        }
        if(id==android.R.id.home){
            Intent i = new Intent(ActivityDetails.this,MainActivity.class);
            startActivity(i);
        }

        return true;
    }



    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            News newsDetail = (News) msg.obj;

            txtTitleDetail.setText(newsDetail.getTitle());
            txtDateDetail.setText(newsDetail.getDate());
            txtInfoDetail.setText(newsDetail.getText());
            news_id = newsDetail.getId();

            String cat = newsDetail.getCategory();
            setTitle(cat);


            NewsRepo repo = new NewsRepo();
            repo.downloadImage(((ThreadApplication)getApplication()).srv,imgHandler,newsDetail.getImagePath());

            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            Bitmap img = (Bitmap) msg.obj;
            imgDetails.setImageBitmap(img);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int id = getIntent().getIntExtra("id",1);

        imgDetails = findViewById(R.id.expandImage);
        txtDateDetail = findViewById(R.id.dateView);
        txtTitleDetail = findViewById(R.id.titleView);
        txtInfoDetail = findViewById(R.id.detailsView);

        NewsRepo repo = new NewsRepo();
        repo.getNewsById(((ThreadApplication)getApplication()).srv,dataHandler,id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}