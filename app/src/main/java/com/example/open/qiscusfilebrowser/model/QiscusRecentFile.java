package com.example.open.qiscusfilebrowser.model;

import java.util.Date;

/**
 * Created by Rahardyan on 8/5/2015.
 */
public class QiscusRecentFile extends QiscusFile {
    public String topicName;
    public int topicId;

    public QiscusRecentFile(String name, String url, String uploader, Date date) {
        super(name, url, uploader, date);
        System.out.println("///////////////////////////////////////// qrf");
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
        System.out.println("///////////////////////////////////////// qrf");
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

}
