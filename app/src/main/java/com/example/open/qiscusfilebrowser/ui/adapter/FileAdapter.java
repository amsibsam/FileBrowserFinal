package com.example.open.qiscusfilebrowser.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.open.qiscusfilebrowser.R;
import com.example.open.qiscusfilebrowser.model.QiscusFile;
import com.example.open.qiscusfilebrowser.model.QiscusRecentFile;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rahardyan on 8/21/2015.
 */
public class FileAdapter extends ArrayAdapter<QiscusFile> {
    private int resource;
    private LayoutInflater inflater;
    private QiscusFile file;
    private Holder holder;
    final Dialog settingsDialog = new Dialog(getContext());


    public FileAdapter(Context context, int resource, List<QiscusFile> objects) {

        super(context, resource, objects);
        this.resource = resource;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        System.out.println("inflating asdsa");

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        System.out.println("///////////////////////////////////////// adapterfile");
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        file = getItem(position);

        if(convertView==null){
            holder = new Holder();

            convertView = inflater.inflate(resource, parent, false);
            System.out.println("inflating now");
            holder.textViewFileName = (TextView) convertView.findViewById(R.id.textViewFileNameItemFile);
            holder.textViewUploader = (TextView) convertView.findViewById(R.id.uploader);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            holder.imageViewIconFile = (ImageView) convertView.findViewById(R.id.imagViewIconFile);
            holder.imageViewImagePopup = (ImageView) convertView.findViewById(R.id.imageViewPopup);

            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }

        System.out.println("nama file " + file.name);
        holder.textViewFileName.setText(file.name);
        holder.textViewUploader.setText(file.uploader);
        holder.textViewDate.setText(file.date.toString());
        if(file.name.contains(".png")||file.name.contains(".jpeg")||file.name.contains(".jpg")||
                file.name.contains(".PNG")||file.name.contains("JPG")||file.name.contains(".JPEG")){
        Picasso.with(getContext()).load(file.url)
                .resize(120, 120).error(R.drawable.icon_unidentifie)
                .into(holder.imageViewIconFile);}
        else if(file.name.contains(".pdf")|| file.name.contains(".PDF")){
            holder.imageViewIconFile.setImageResource(R.drawable.icon_pdf);
        }
        else if(file.name.contains(".mp3")|| file.name.contains(".MP3")||file.name.contains(".mp4")|| file.name.contains(".MP4")||
                file.name.contains(".mkv")|| file.name.contains(".MKV")){
            holder.imageViewIconFile.setImageResource(R.drawable.icon_pdf);
        }
        else{
            holder.imageViewIconFile.setImageResource(R.drawable.icon_unidentifie);
        }

        holder.imageViewIconFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file = getItem(position);
                System.out.println("ini get resource :" + holder.imageViewIconFile.getResources().toString());
                settingsDialog.setContentView(R.layout.image_popup);
                holder.close = (ImageButton) settingsDialog.findViewById(R.id.imageButtonClose);
                holder.imageViewImagePopup = (ImageView) settingsDialog.findViewById(R.id.imageViewPopup);
                holder.download = (ImageButton) settingsDialog.findViewById(R.id.imageButtonDownload);

                Picasso.with(getContext()).load(file.url)
                        .error(R.drawable.icon_unidentifie)
                        .into(holder.imageViewImagePopup);

                if(file.name.contains("png")||file.name.contains("PNG")||file.name.contains("jpg")||
                        file.name.contains("JPG")||file.name.contains("JPEG")||
                        file.name.contains("jpeg")){
                    holder.close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            settingsDialog.dismiss();
                        }
                    });
                    holder.download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(file.url); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            getContext().startActivity(intent);
                        }
                    });
                    settingsDialog.show();
                    System.out.println("clicked");
                }else {
                    Uri uri = Uri.parse(file.url); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    getContext().startActivity(intent);
                }
            }
        });







        return convertView;
    }

    private static class Holder{
        TextView textViewFileName, textViewUploader, textViewDate, urlFile;
        ImageView imageViewIconFile, imageViewImagePopup;
        ImageButton close, download;

    }
}
