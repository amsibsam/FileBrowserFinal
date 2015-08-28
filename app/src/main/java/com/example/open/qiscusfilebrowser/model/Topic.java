package com.example.open.qiscusfilebrowser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahardyan on 7/31/2015.
 */
public class Topic {
    //required
    private final int id;
    private final String name;
    //optional
    private List<QiscusFile> files;
    private List<QiscusLink> links;

//constructor
    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
        files = new ArrayList<>();
        links = new ArrayList<>();
    }

//////setter
    public void setFiles(List<QiscusFile> files) {
        this.files.addAll(files);
    }

    public void setLink(List<QiscusLink> links) {
        this.links.addAll(links);
    }

//////getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<QiscusFile> getFiles() {
        return files;
    }

    public List<QiscusLink> getLinks() {
        return links;
    }
}
