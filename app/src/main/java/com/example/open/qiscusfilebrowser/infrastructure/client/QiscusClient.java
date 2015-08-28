package com.example.open.qiscusfilebrowser.infrastructure.client;

import android.net.ParseException;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.open.qiscusfilebrowser.model.QiscusFile;
import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;
import com.example.open.qiscusfilebrowser.model.Room;
import com.example.open.qiscusfilebrowser.model.Topic;
import com.example.open.qiscusfilebrowser.model.User;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.ui.adapter.TopicAdapter;
import com.example.open.qiscusfilebrowser.utilities.ModulRecentFileGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Rahardyan on 7/31/2015.
 */
public class QiscusClient {

    private static final String BASE_URL="https://www.qisc.us";
    private static final String LOGIN=BASE_URL+"/users/sign_in.json";
    private static final String REGISTER=BASE_URL+"/users.json";
    private static final String GET_ROOM=BASE_URL+"/api/v1/mobile/rooms_only?token=%s";
    private static final String GET_TOPIC=BASE_URL+"/api/v1/mobile/topics";
    private static final String GET_FILES=BASE_URL+"/api/v1/mobile/topic/getFiles/";
    private static final String GET_LINKS=BASE_URL+"/api/v1/mobile/topic/getLinks/:topic_id";

    public static String username;
    public static String token;

    private static RequestQueue requestQueue;
    private static QiscusFile file;
    private static User user;
    private static Room room;
    private static Topic topic;


    public QiscusClient(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public  static  void  login(final String email, final String password, final ClientCallback<User> callback){
        StringRequest request =  new StringRequest(Request.Method.POST, LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response server = "+ response);

                try {
                    JSONObject objData = new JSONObject(response);
                    boolean jsSuccess = objData.getBoolean("success");
                    if(jsSuccess){
                        token = objData.getString("token");
                        username = objData.getString("username");

                        user = new User(username, token, null);
                        System.out.println("username method login "+user.getUsername());

                        callback.onSucceeded(user);
                    }else{
                        callback.onFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFailed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("Error ini !!!");
                callback.onNoConnection();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> pars = new HashMap<String, String>();
                pars.put("Content-Type", "application/json");
                return pars;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user[email]",email);
                params.put("user[password]",password);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);


    }

    public  void getRecentFile(ClientCallback<List<QiscusRecentFile>> callback){

        List<QiscusRecentFile> recentFiles = ModulRecentFileGenerator.generateRecentFile();
        callback.onSucceeded(recentFiles);
    }


    public static List<Room> getRoomlist(final String inputtoken, final ClientCallback<Room> callback){
        System.out.println("masuk Roomlist method");
        String url = String.format(GET_ROOM, inputtoken);
        System.out.println("ini url "+url);
        final List<Room> rooms = new ArrayList<>();


        StringRequest requestRoom = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("ini response server"+response);
                try {
                    JSONObject objDataRoom = new JSONObject(response);
                    int status =objDataRoom.getInt("status");
                    if (status==200){
                        JSONArray jsonArrayRoom = objDataRoom.getJSONArray("results");
                        for (int i = 0;i<jsonArrayRoom.length();i++){
                            JSONObject objRoom = jsonArrayRoom.getJSONObject(i);
                            int idRoom = objRoom.getInt("id");
                            String namaRoom = objRoom.getString("name");

                            room = new Room(idRoom,namaRoom);
//                            System.out.println("ini idRoom :" + idRoom);
//                            System.out.println("ini nameRoom :" + namaRoom);

                            rooms.add(room);
                            callback.onSucceeded(room);
                            user.setRooms(rooms);

                        }



                    }else{
                        System.out.println("belum di sukses ambil data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Ini eror di room " + error);
            }
        });

        requestRoom.setRetryPolicy(new DefaultRetryPolicy(27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(requestRoom);
        return rooms ;
    }

    public static List<Topic>  getTopicList( final int idForList,final String tokenForList, final ClientCallback<Topic> callback){
        final List<Topic> topics = new ArrayList<>();

        StringRequest requestTopic = new StringRequest(Request.Method.POST, GET_TOPIC, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                System.out.println("ini respons topic" +  response);

                try {
                    System.out.println("ini masuk try catch jSON");

                    JSONObject objDataTopic = new JSONObject(response);

                    JSONObject objDataGetRoom = objDataTopic.getJSONObject("results");

                    //ini ambil objek dari objek result bernama rooms
                    JSONObject objDataTopicGetidRoom = objDataGetRoom.getJSONObject("rooms");

                    int roomId = objDataTopicGetidRoom.getInt("id");

                    if (roomId == idForList){
                        JSONArray objDataArrayTopic = objDataGetRoom.getJSONArray("topics");
                        for (int i=0;i<objDataArrayTopic.length();i++){
                            JSONObject objDataIsiTopic = objDataArrayTopic.getJSONObject(i);
                            String nameTopic = objDataIsiTopic.getString("title");
                            int idTopic = objDataIsiTopic.getInt("id");

                            topic = new Topic(idTopic,nameTopic);

                            System.out.println("name Topic "+ nameTopic);
                            System.out.println("id Topic "+ idTopic);

                            topics.add(topic);
                            room.setTopics(topics);
                            callback.onSucceeded(topic);
                        }

                    }else{
                        System.out.println("Id input Beda dengan Id Room");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ini error bagian topic" + error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> pars = new HashMap<>();
                return pars;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("token",tokenForList);
                params.put("room_id", String.valueOf(idForList));

                return params;
            }
        };
        requestTopic.setRetryPolicy(new DefaultRetryPolicy(27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(requestTopic);
        return topics ;
    }

    public static List<QiscusFile> getFileTopic(final int idTopic, final String inputToken, final ClientCallback<QiscusFile> callback){
        //https://www.qisc.us/api/v1/mobile/topic/getFiles/12939?token=7YdsZbSJ1fNyvPY2bu2r

        final List<QiscusFile> files = new ArrayList<>();


        System.out.println("ini masuk fileTopic");

        //String site = "https://www.qisc.us/api/v1/mobile/topic/getFiles/"+idTopic + "?"+"token="+inputToken;

        String site = GET_FILES +idTopic + "?"+"token="+inputToken;

        StringRequest requestFile = new StringRequest(Request.Method.GET, site, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject objResultFile = new JSONObject(response);
                    System.out.println("ini objFile :" + objResultFile);
                    JSONObject objFile = objResultFile.getJSONObject("results");
                    System.out.println("ini coba objFile " + objFile);

                    if (objFile != null){
                        JSONArray objDataArrayFiles = objFile.getJSONArray("files");
                        System.out.println("ini objDataFile" + objDataArrayFiles);
                        for (int i =0;i<objDataArrayFiles.length();i++) {
                            System.out.println("ini masuk for utk ambil data");

                            JSONObject objContainFiles = objDataArrayFiles.getJSONObject(i);
                            System.out.println("ini objFile" + objContainFiles);
                            String nama = objContainFiles.getString("name");
                            String username = objContainFiles.getString("username");
                            String url_file = objContainFiles.getString("url_file");

                            System.out.println("ini nama file " + nama );
                            System.out.println("ini url file " + url_file);

                            String timeDate = objContainFiles.getString("uploaded_at");
                            System.out.println("ini time date " + timeDate);
                            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //Datetime date = DateTime.parse("","");
                            //System.out.println("ini data tanggal " + dateFormat);
                            try {
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = dateFormat.parse(timeDate);
                                System.out.println("ini date nya " + date);
//                                DateTime date = DateTime.parse("04/02/2011 20:27:05",
//                                        DateTimeFormat.forPattern("dd/MM/yyyy HH🇲🇲ss"));
                                //Datetime date = dateFormat.parse(timeDate);
                                file = new QiscusFile(nama,url_file,username,date);
                                files.add(file);
                                topic.setFiles(files);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (java.text.ParseException e) {
                                e.printStackTrace();
                            }

                            callback.onSucceeded(file);
                        }

                    }else {
                        System.out.println("File kosong");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ini error di file" + error);
            }
        });
        requestQueue.add(requestFile);

        requestFile.setRetryPolicy(new DefaultRetryPolicy(27000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return files;

    }




//    public  void getRoomList(ClientCallback<List<Room>> callback){
//        List<Room> rooms = getRoomlist(token);
//        System.out.println("Token dlm QiscusClient : "+token);
//        callback.onSucceeded(rooms);
//    }




}
