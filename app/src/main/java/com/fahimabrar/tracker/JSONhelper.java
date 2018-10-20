package com.fahimabrar.tracker;

/**
 * Created by FAHIM on 7/5/2018.
 */

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by csa on 3/3/2017.
 */

public class JSONhelper{

    HttpURLConnection connection;
    List<DataModel> data;


    public List<DataModel> getdatafromurl(String url){



        try{
            URL url1 = new URL(url);

            connection = (HttpURLConnection) url1.openConnection();
            connection.connect();
            InputStream inputStream= connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line=reader.readLine())!=null){

                buffer.append(line);
                String result = buffer.toString();
                JSONArray jsonArray = new JSONArray(result);
                data = new ArrayList<>();

                for (int i =0; i<buffer.length();i++){
                    DataModel dataModel = new DataModel();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("username");
                    String id= jsonObject.getString("sid");
                    String image = jsonObject.getString("pic");
                    //  int ids = Integer.parseInt(id);
                    dataModel.setId(id);
                    dataModel.setTitle(name);
                    dataModel.setImage(image);
                    data.add(dataModel);
                }


            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

}
