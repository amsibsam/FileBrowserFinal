package com.example.open.qiscusfilebrowser.model;

import java.util.Date;

/**
 * Created by Rahardyan on 7/31/2015.
 */
public class QiscusFile {
    public final String name;
    public final String url;
    public final String uploader;
    public final Date date;

    public QiscusFile(String name, String url, String uploader, Date date) {
        this.name = name;
        this.url = url;
        this.uploader = uploader;
        this.date = date;
        System.out.println("///////////////////////////////////////// qf");
    }
}
