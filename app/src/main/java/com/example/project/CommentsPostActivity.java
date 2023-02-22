package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;

public class CommentsPostActivity extends AppCompatActivity {
    EditText txtName;
    ProgressBar prg;
    EditText txtInfo;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            prg.setVisibility(View.INVISIBLE);

            return false;
        }
    });



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_post);

        setTitle("Post Comment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Integer news_comment_id = intent.getIntExtra("news_comment_id",0);

        prg = findViewById(R.id.postPB);
        prg.setVisibility(View.INVISIBLE);


        Button submitBtn = findViewById(R.id.button);

        txtName = findViewById(R.id.cmtNameText);
        txtInfo = findViewById(R.id.cmtInfoText);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String name = txtName.getText().toString();
                String text = txtInfo.getText().toString();

                if (name.isEmpty()|| text.isEmpty())
                {
                    Toast.makeText(CommentsPostActivity.this, "Name or text cant be blank!", Toast.LENGTH_SHORT).show();
                }
                else {
                    prg.setVisibility(View.VISIBLE);

                    ExecutorService srv = ((ThreadApplication) getApplication()).srv;
                    CommentsRepo repo = new CommentsRepo();

                    repo.postComment(srv, handler, name, text, news_comment_id);

                    Intent i = new Intent(CommentsPostActivity.this, CommentsActivity.class);
                    i.putExtra("news_id",news_comment_id);
                    startActivity(i);

                }
            }
        });





    }
}