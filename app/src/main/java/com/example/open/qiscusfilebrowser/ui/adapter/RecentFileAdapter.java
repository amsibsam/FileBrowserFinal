package com.example.open.qiscusfilebrowser.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;

import java.util.List;

/**
 * Created by Rahardyan on 8/5/2015.
 */
public class RecentFileAdapter extends ArrayAdapter<QiscusRecentFile>{
    private int resource;
    private LayoutInflater inflater;

    public RecentFileAdapter(Context context, int resource, List<QiscusRecentFile> objects) {

        super(context, resource, objects);
        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("///////////////////////////////////////// adapter");
        Holder holder;
        QiscusRecentFile currentFile = getItem(position);

        if(convertView==null){
            holder = new Holder();

            convertView = inflater.inflate(resource, parent, false);
            System.out.println("inflating now");
            holder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            holder.textViewFileName = (TextView) convertView.findViewById(R.id.textViewFileName);
            holder.textViewUploader = (TextView) convertView.findViewById(R.id.textViewUploader);
            holder.textViewTopic = (TextView) convertView.findViewById(R.id.textViewTopic);
            holder.imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);


            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        holder.textViewUploader.setText(currentFile.uploader);
        holder.textViewTopic.setText(currentFile.topicName);
        holder.textViewDate.setText(currentFile.date.toString());
        holder.textViewFileName.setText(currentFile.name);



        return convertView;
    }

    private static class Holder{
        ImageView imageViewIcon;
        TextView textViewFileName, textViewUploader, textViewDate, textViewTopic;


    }
}
