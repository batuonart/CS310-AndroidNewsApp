package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterPage extends FragmentStateAdapter {


    public AdapterPage(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
        {
            return new Category1();
        }
        else if (position == 1)
        {
            return new Category2();
        }
        else if (position == 2)
        {
            return new Category3();
        }
        return new Category1();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
