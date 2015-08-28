package com.example.open.qiscusfilebrowser.model;

import java.util.Date;

/**
 * Created by Rahardyan on 7/31/2015.
 */
public class QiscusLink {
    public final String name;
    public final String url;
    public final String uploader;
    public final Date date;

    public QiscusLink(String name, String url, String uploader, Date date) {
        this.name = name;
        this.url = url;
        this.uploader = uploader;
        this.date = date;
    }
}
