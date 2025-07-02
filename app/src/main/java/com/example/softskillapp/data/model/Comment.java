package com.example.softskillapp.data.model;

public class Comment {
    private String id; // ID bình luận
    private String userId; // ID người dùng
    private String text; // Nội dung bình luận
    private long timestamp; // Thời gian tạo

    public Comment() {}

    public Comment(String id, String userId, String text, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
