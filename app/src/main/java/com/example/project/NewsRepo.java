package com.example.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import android.os.Handler;
import android.widget.Toast;


public class NewsRepo {

    public  void getAllCategories(ExecutorService srv, Handler uiHandler)
    {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");
                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line= reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                //UPPER PART DOESN'T NEED TO BE CHANGED
                List<NewsCategories> data = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0;i < items.length(); i++)
                {
                    JSONObject newsJson = items.getJSONObject(i);
                    NewsCategories category = new NewsCategories(
                            newsJson.getInt("id"),
                            newsJson.getString("name"));
                    data.add(category);
                }
                //

                conn.disconnect();
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public  void getNewsByCategories(ExecutorService srv, Handler uiHandler, int id)
    {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(id));
                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line= reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                //UPPER PART DOESN'T NEED TO BE CHANGED
                List<NewsHome> data = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0;i < items.length(); i++)
                {
                    JSONObject newsJson = items.getJSONObject(i);
                    NewsHome homeNews = new NewsHome(
                            newsJson.getInt("id"),
                            newsJson.getString("title"),
                            newsJson.getString("date").substring(0,10),
                            newsJson.getString("image"));
                    data.add(homeNews);
                }
                //m.getString("categoryName"));


                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public  void getNewsById(ExecutorService srv, Handler uiHandler, int id)
    {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(id));
                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line= reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                //UPPER PART DOESN'T NEED TO BE CHANGED

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray items = jsonObject.getJSONArray("items");

                    JSONObject item = items.getJSONObject(0);
                    News detailNews = new News(
                            item.getInt("id"),
                            item.getString("title"),
                            item.getString("text"),
                            item.getString("date").substring(0,10),
                            item.getString("image"),
                            item.getString("categoryName"));


                Message msg = new Message();
                msg.obj = detailNews;
                uiHandler.sendMessage(msg);




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public  void getEconomyNews(ExecutorService srv, Handler uiHandler)
    {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/1");
                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line= reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                //UPPER PART DOESN'T NEED TO BE CHANGED
                List<NewsHome> data = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0;i < items.length(); i++)
                {
                    JSONObject newsJson = items.getJSONObject(i);
                    NewsHome homeNews = new NewsHome(
                            newsJson.getInt("id"),
                            newsJson.getString("title"),
                            newsJson.getString("date").substring(0,10),
                            newsJson.getString("image"));
                    data.add(homeNews);
                }
                //

                conn.disconnect();
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void  downloadImage(ExecutorService srv, Handler uiHandler, String path) {
        srv.execute(() -> {
            {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());

                    Message msg = new Message();
                    msg.obj = bitmap;
                    uiHandler.sendMessage(msg);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }
}
