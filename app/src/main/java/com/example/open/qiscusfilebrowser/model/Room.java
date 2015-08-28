package com.example.open.qiscusfilebrowser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahardyan on 7/31/2015.
 */
public class Room {
    //required
    public final int id;
    public final String name;

    //optioinal
    public List<Topic> topics;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
        this.topics = new ArrayList<Topic>();
    }

    public void setTopics(List<Topic> topics) {
        this.topics.addAll(topics);
    }

    //getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
