package com.example.open.qiscusfilebrowser.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.application.QiscusExplorerApplication;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.Room;
import com.example.open.qiscusfilebrowser.model.User;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ClientCallback<User> {
    private EditText email, password;
    private Button login;
    private ProgressDialog progressDialog;
    private User user;
    private QiscusClient qiscusClient;
    public static List<Room> rooms = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("loginActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_login:
                if(email.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(this, "input your email or password", Toast.LENGTH_SHORT).show();
                }else{
                QiscusClient.login(email.getText().toString(), password.getText().toString(), LoginActivity.this);

                progressDialog.show();}
                break;
        }
    }

    @Override
    public void onSucceeded(User result) {
        System.out.println(result.toString());
        progressDialog.dismiss();
        Intent intent = new Intent(this, RecentFilesActivity.class);
        startActivity(intent);

        QiscusExplorerApplication app = (QiscusExplorerApplication) getApplication();
        user = app.getUser();

        user.setUsername(qiscusClient.username);
        user.setToken(qiscusClient.token);

//        QiscusClient.getRoomlist(user.getToken());
//        rooms = QiscusClient.getRoomlist(user.getToken());
        finish();


    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();

        Toast.makeText(this, "password and email not match", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoConnection() {
        progressDialog.dismiss();

        Toast.makeText(this, "connection problem", Toast.LENGTH_SHORT).show();
    }

}
