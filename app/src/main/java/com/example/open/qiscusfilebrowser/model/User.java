package com.example.open.qiscusfilebrowser.model;

import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.model.event.OnGetRoomListSucceeded;
import com.example.open.qiscusfilebrowser.model.event.OnRecentFilesSucceeded;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Rahardyan on 7/31/2015.
 */
public class User {


    //required
    private String username;
    private String token;

//optional
    public List<Room> rooms = new ArrayList<>();
    private QiscusClient client;
    public List<QiscusRecentFile> recentFiles = new ArrayList<>();

    public User(String username, String token, QiscusClient qiscusClient) {
        this.client = qiscusClient;
        this.username = username;
        this.token = token;
        this.rooms = new ArrayList<Room>();
    }



    public void getRecentFiles(){
        client.getRecentFile(new ClientCallback<List<QiscusRecentFile>>() {
            @Override
            public void onSucceeded(List<QiscusRecentFile> result) {
                System.out.println("on succeded");
                recentFiles.addAll(result);
                EventBus.getDefault().post(new OnRecentFilesSucceeded());

            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onNoConnection() {

            }
        });
    }

//    public void setRoomList(){
//        client.getRoomList(new ClientCallback<List<Room>>() {
//            @Override
//            public void onSucceeded(List<Room> result) {
//                System.out.println("masuk sukses setRoomlist");
//                rooms.addAll(result);
//                EventBus.getDefault().post(new OnGetRoomListSucceeded());
//            }
//
//            @Override
//            public void onFailed() {
//
//            }
//
//            @Override
//            public void onNoConnection() {
//
//            }
//        });
//    }

//setter
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //getter
    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
