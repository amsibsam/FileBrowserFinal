package com.example.open.qiscusfilebrowser.utilities;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rahardyan on 8/6/2015.
 */
public class ModulRecentFileGenerator {
    private static Context context;

    public ModulRecentFileGenerator(Context context) {
        this.context = context;
    }


    private static String loadFakeRecentFile(){System.out.println("masuk loadfake");
        AssetManager assetManager = context.getAssets();
        String content = "";

        try {
            InputStream inputStream = assetManager.open("fake_api_recent_files.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            content = new String(buffer);
        } catch (IOException e) {
            System.out.println("catch 0");
            e.printStackTrace();
            System.out.println("catch 0");
        }

        return content;

    }
    public static List<QiscusRecentFile> generateRecentFile(){
        System.out.println("masuk generate");
        List<QiscusRecentFile> recentFiles = new ArrayList<>();

        String fakeData = loadFakeRecentFile();
        System.out.println("fake data "+fakeData);

        try {
            JSONObject objData = new JSONObject(fakeData);
            if(objData.getBoolean("success")){
                JSONArray arr = objData.getJSONArray("recent_files");

                for(int i=0; i<arr.length();i++){
                    JSONObject obj = arr.getJSONObject(i);
                    String fileName = obj.getString("name");
                    //String fileDate = obj.getString("upload_at");
                    String topicName = obj.getString("topic_name");

                    String uploader = obj.getString("username");

                    String urlFile = obj.getString("url_file");

                    int topicId = obj.getInt("topic_id");


                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse("2013-12-4");

                    System.out.println("add ke recentFile");


                    QiscusRecentFile recentFile = new QiscusRecentFile(fileName, urlFile, uploader, date);
                    recentFile.setTopicId(topicId);
                    recentFile.setTopicName(topicName);


                    recentFiles.add(recentFile);

                }
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("catch1");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("catch2");
        }

        return recentFiles;
    }


}
