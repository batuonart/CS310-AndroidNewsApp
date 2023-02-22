package com.example.project;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadApplication extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();
}
