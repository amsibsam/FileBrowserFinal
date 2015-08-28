package com.example.open.qiscusfilebrowser.ui.activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.application.QiscusExplorerApplication;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;
import com.example.open.qiscusfilebrowser.model.Topic;
import com.example.open.qiscusfilebrowser.model.User;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.model.event.OnRecentFilesSucceeded;
import com.example.open.qiscusfilebrowser.ui.adapter.RecentFileAdapter;
import com.example.open.qiscusfilebrowser.ui.adapter.TopicAdapter;

import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class RecentFilesFragment extends android.support.v4.app.Fragment implements ClientCallback<Topic> {
    private ListView listView;
    public static RecentFileAdapter adapter;
    private List<QiscusRecentFile> recentFiles;
    private User user;

    public RecentFilesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recent_file, container, false);
        listView = (ListView) rootView.findViewById(R.id.listViewRecentFile);
        EventBus.getDefault().register(this);

        QiscusExplorerApplication app = (QiscusExplorerApplication) getActivity().getApplication();
        user = app.getUser();

        recentFiles = user.recentFiles;

        adapter = new RecentFileAdapter(getActivity(), R.layout.item_recent_file, recentFiles);
        listView.setAdapter(adapter);
        user.getRecentFiles();
//        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Subscribe
    public void onEvent(OnRecentFilesSucceeded e){
        adapter.notifyDataSetInvalidated();

    }
    @Override
    public void onSucceeded(Topic result) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onNoConnection() {

    }
}