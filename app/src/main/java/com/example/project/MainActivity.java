package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ProgressBar prgBar;
    String cat1,cat2,cat3;
    int catN1, catN2, catN3;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<NewsCategories> data = (List<NewsCategories>)message.obj;
            tabLayout.addTab(tabLayout.newTab().setText(data.get(0).getName()));
            tabLayout.addTab(tabLayout.newTab().setText(data.get(1).getName()));
            tabLayout.addTab(tabLayout.newTab().setText(data.get(2).getName()));

            catN1 = data.get(0).getId();
            catN2 = data.get(1).getId();
            catN3 = data.get(2).getId();
            prgBar.setVisibility(View.INVISIBLE);
            return true;
        }
    });


    private AdapterPage adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);



        setTitle("CS310 News");

        tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);
        prgBar = findViewById(R.id.progressBar);
        prgBar.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
         adapter = new AdapterPage(fragmentManager,getLifecycle());
         viewPager2.setAdapter(adapter);

         tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 viewPager2.setCurrentItem(tab.getPosition());
             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

         viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
             @Override
             public void onPageSelected(int position) {
                 tabLayout.selectTab(tabLayout.getTabAt(position));
             }
         });




        NewsRepo repo = new NewsRepo();
        repo.getAllCategories(((ThreadApplication) getApplication()).srv, handler);




    }
    public int getCatN1()
    {return catN1;}

    public int getCatN2()
    {return catN2;}

    public int getCatN3()
    {return catN3;}
}