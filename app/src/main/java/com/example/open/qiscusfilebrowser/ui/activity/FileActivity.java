package com.example.open.qiscusfilebrowser.ui.activity;

import android.app.Dialog;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.QiscusFile;
import com.example.open.qiscusfilebrowser.model.Topic;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.ui.adapter.FileAdapter;
import com.example.open.qiscusfilebrowser.ui.adapter.TopicAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class FileActivity extends AppCompatActivity implements ClientCallback<QiscusFile> {
    private android.support.v7.widget.Toolbar toolbar;
    private ListView listViewFile;
    private FileAdapter adapter;
    private List<QiscusFile> files;
    private Dialog settingsDialog;
    private TextView urlFile;
    private ImageButton close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        Window w = getWindow();
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        listViewFile = (ListView) findViewById(R.id.listViewFile);
        close = (ImageButton) findViewById(R.id.imageButtonClose);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        settingsDialog = new Dialog(this);


        Bundle extras = getIntent().getExtras();
        int idtopic = extras.getInt("idtopic");
        String namaTopic = extras.getString("namaTopic");
        setTitle(namaTopic);
        System.out.println("idtopic di fileactivity "+idtopic);
        files = QiscusClient.getFileTopic(idtopic, QiscusClient.token, FileActivity.this);

        adapter = new FileAdapter(this, R.layout.item_file, files);
        listViewFile.setAdapter(adapter);
        System.out.println("set adapter");

        listViewFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("ini click posisi "+position);
            }
        });



    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSucceeded(QiscusFile result) {
        adapter.notifyDataSetChanged();
        System.out.println("notify di file");
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onNoConnection() {

    }
}
