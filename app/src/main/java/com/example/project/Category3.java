package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class Category3 extends Fragment {

    int catNo;
    RecyclerView recyclerView;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<NewsHome> data = (List<NewsHome>)message.obj;
            NewsRecyclerViewAdapter adp = new NewsRecyclerViewAdapter(getContext(), data);
            recyclerView.setAdapter(adp);
            recyclerView.setVisibility(View.VISIBLE);
            return true;
        }
    });


    public Category3() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        catNo = activity.getCatN3();
        return inflater.inflate(R.layout.fragment_category1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        NewsRepo repo = new NewsRepo();
        repo.getNewsByCategories(((ThreadApplication) getActivity().getApplication()).srv, handler,catNo);


    }
}