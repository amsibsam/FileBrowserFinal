package com.example.open.qiscusfilebrowser.ui.adapter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.infrastructure.client.QiscusClient;
import com.example.open.qiscusfilebrowser.model.QiscusFile;
import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;
import com.example.open.qiscusfilebrowser.model.Topic;
import com.example.open.qiscusfilebrowser.model.event.ClientCallback;
import com.example.open.qiscusfilebrowser.model.event.OnRecentFilesSucceeded;
import com.example.open.qiscusfilebrowser.ui.activity.DrawerFragment;
import com.example.open.qiscusfilebrowser.ui.activity.FileActivity;
import com.example.open.qiscusfilebrowser.ui.activity.RecentFilesActivity;
import com.example.open.qiscusfilebrowser.ui.activity.TopicListFragment;

import java.io.File;
import java.util.List;

import de.greenrobot.event.Subscribe;

/**
 * Created by Rahardyan on 8/5/2015.
 */
public class TopicAdapter extends ArrayAdapter<Topic> implements ClientCallback<Topic> {
    private int resource;
    private LayoutInflater inflater;
    private Holder holder;
    private Topic topic;
    public static int idtopic;
    public static String namaTopic;


    public TopicAdapter(Context context, int resource, List<Topic> objects) {

        super(context, resource, objects);
        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        topic = getItem(position);



        if(convertView==null){
            holder = new Holder();

            convertView = inflater.inflate(resource, parent, false);

            holder.textViewTopic = (TextView) convertView.findViewById(R.id.textViewTopic);
//            holder.parentThumbnail = (LinearLayout) convertView.findViewById(R.id.parent_file_linear_layout);

//            for(int i=0; i<3;i++){
//                holder.parentItem = new LinearLayout(getContext());
//                holder.parentItem.setOrientation(LinearLayout.VERTICAL);;;
//
//                holder.textFileName = new TextView(getContext());
//                holder.textFileName.setText("File Name");
//                holder.thumbnail = new ImageButton(getContext());
//                holder.thumbnail.setImageResource(R.drawable.icon_unidentifie_white_small);
//                holder.thumbnail.setLayoutParams(new LinearLayout.LayoutParams(
//                        180,
//                        180));
//
//                holder.parentItem.addView(holder.thumbnail);
//                holder.parentItem.addView(holder.textFileName);
//                LinearLayout.LayoutParams layoutSet = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutSet.setMargins(20, 20, 0, 20);
//                holder.parentItem.setLayoutParams(layoutSet);
//                holder.parentThumbnail.addView(holder.parentItem);
//                }

//            holder.loadmore = new ImageButton(getContext());
//            holder.loadmore.setImageResource(R.drawable.icon_loadmore);
//            holder.parentThumbnail.addView(holder.loadmore);
//            LinearLayout.LayoutParams layoutSetLoadmore = new LinearLayout.LayoutParams(holder.loadmore.getLayoutParams());
//            layoutSetLoadmore.setMargins(0, 10, 0, 0);
//            holder.loadmore.setLayoutParams(layoutSetLoadmore);






//            holder.gridView = (GridView) convertView.findViewById(R.id.gridView);
//
//            topics = QiscusClient.getTopicList(DrawerFragment.idRoom, QiscusClient.token, TopicAdapter.this);
//
//            adapter = new TopicAdapterTesGrid(getContext(), R.layout.item_topic_tes_grid, topics);
//            holder.gridView.setAdapter(adapter);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.textViewTopic.setText(topic.getName());
        idtopic = topic.getId();
        namaTopic = topic.getName();

        System.out.println("idtopic outside onclick "+idtopic);


//        holder.loadmore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//

//
//            }
//        });
        idtopic = topic.getId();




        return convertView;
    }

    @Override
    public void onSucceeded(Topic result) {


    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onNoConnection() {

    }



    private static class Holder{
        TextView textViewTopic;
        TextView textFileName;
        LinearLayout parentItem;
        GridView gridView;
        LinearLayout parentThumbnail;
        ImageButton thumbnail;
        ImageButton loadmore;

    }
}
