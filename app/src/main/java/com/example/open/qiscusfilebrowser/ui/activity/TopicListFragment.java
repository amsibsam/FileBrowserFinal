package com.example.open.qiscusfilebrowser.ui.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.Topic;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.ui.adapter.TopicAdapter;

import java.io.File;
import java.util.List;

public class TopicListFragment extends android.support.v4.app.Fragment implements ClientCallback<Topic> {
    private ListView listView;
    public static TopicAdapter adapter;
    private List<Topic> topics;
    ProgressDialog progressDialog;




    public TopicListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (ListView) rootView.findViewById(R.id.listViewTopic);

        topics = QiscusClient.getTopicList(DrawerFragment.idRoom, QiscusClient.token, TopicListFragment.this);


        adapter = new TopicAdapter(getActivity(), R.layout.item_topic, topics);
        listView.setAdapter(adapter);

        final Intent intent = new Intent(getActivity(), FileActivity.class);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idtopic = topics.get(position).getId();
                String namaTopic = topics.get(position).getName();
                System.out.println("idtopic in adapter topic " + idtopic);
                intent.putExtra("idtopic", idtopic);
                intent.putExtra("namaTopic", namaTopic);
                startActivity(intent);
            }
        });
//        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onSucceeded(Topic result) {
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onNoConnection() {

    }
}