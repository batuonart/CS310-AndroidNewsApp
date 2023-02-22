package com.example.project;

public class NewsHome {


    private int id;
    private String title;
    private String date;
    private String imagePath;

    public NewsHome()
    {

    }
    public NewsHome(int id, String title, String date, String imagePath) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.imagePath = imagePath;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }







}
