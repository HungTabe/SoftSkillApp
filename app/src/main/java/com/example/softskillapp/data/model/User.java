package com.example.softskillapp.data.model;

import java.util.List;
import java.util.Map;

public class User {
    private String uid; // ID người dùng từ Firebase Auth
    private String email;
    private int points; // Điểm số gamification
    private List<String> badges; // Huy hiệu
    private Map<String, Boolean> progress; // Tiến trình học (video, quiz)

    // Constructor rỗng cho Firestore
    public User() {}

    public User(String uid, String email, int points, List<String> badges, Map<String, Boolean> progress) {
        this.uid = uid;
        this.email = email;
        this.points = points;
        this.badges = badges;
        this.progress = progress;
    }

    // Getter và Setter
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public List<String> getBadges() { return badges; }
    public void setBadges(List<String> badges) { this.badges = badges; }
    public Map<String, Boolean> getProgress() { return progress; }
    public void setProgress(Map<String, Boolean> progress) { this.progress = progress; }
}
