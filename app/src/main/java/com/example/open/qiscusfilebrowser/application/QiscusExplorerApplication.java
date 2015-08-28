package com.example.open.qiscusfilebrowser.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.User;
import com.example.open.qiscusfilebrowser.utilities.ModulRecentFileGenerator;

/**
 * Created by Rahardyan on 8/4/2015.
 */
public class QiscusExplorerApplication extends Application {
    private QiscusClient client;
    private RequestQueue requestQueue;
    private User user;


    @Override
    public void onCreate() {

        super.onCreate();

        requestQueue = Volley.newRequestQueue(this);
        ModulRecentFileGenerator recentFileGenerator = new ModulRecentFileGenerator(this);

        client = new QiscusClient(requestQueue);

        user = new User("Bisma","1234",client);

        System.out.println("masuk app");


    }

    public User getUser() {
        return user;
    }
}
