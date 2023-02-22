package com.example.project;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentsRepo {
    public  void getCommentsById(ExecutorService srv, Handler uiHandler, int id)
    {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(id));
                HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line= reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                //UPPER PART DOESN'T NEED TO BE CHANGED

                List<Comments> data = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray items = jsonObject.getJSONArray("items");
                for (int i = 0;i < items.length(); i++)
                {
                    JSONObject newsJson = items.getJSONObject(i);
                    Comments comments = new Comments(
                            newsJson.getInt("id"),
                            newsJson.getInt("news_id"),
                            newsJson.getString("text"),
                            newsJson.getString("name"));
                    data.add(comments);
                }
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


    public void postComment(ExecutorService srv, Handler uiHandler,String name, String text, int id){

        srv.execute(()->{

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("name",name);
                outputData.put("text",text);
                outputData.put("news_id",id);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());


                conn.disconnect();


                String retValStr = "Date:" + retVal.getString("date") +
                        ", fullname:" + retVal.getString("fullname");

                Message msg = new Message();
                msg.obj = retValStr;

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
}
