package com.example.softskillapp.data.model;

public class Video {
    private String id; // ID video trong Firestore
    private String title; // Tiêu đề
    private String url; // URL từ Firebase Storage
    private String description; // Mô tả

    public Video() {}

    public Video(String id, String title, String url, String description) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
