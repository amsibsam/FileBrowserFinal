package com.example.open.qiscusfilebrowser.model.event;

/**
 * Created by Rahardyan on 8/4/2015.
 */
public interface ClientCallback<T> {
    void onSucceeded(T result);
    void onFailed();
    void onNoConnection();

}
