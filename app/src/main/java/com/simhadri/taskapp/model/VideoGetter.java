package com.simhadri.taskapp.model;

public class VideoGetter {

    String dateTime,status,thumbnail,fileSize,id;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VideoGetter(String dateTime, String status, String thumbnail, String fileSize, String id) {
        this.dateTime=dateTime;
        this.status = status;
        this.thumbnail=thumbnail;
        this.fileSize=fileSize;
        this.id=id;


    }
}
