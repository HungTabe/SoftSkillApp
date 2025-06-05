package com.example.softskillapp.data.model;

import java.util.List;

public class Mooc {
    private String id; // ID MOOC
    private String scenario; // Nội dung kịch bản
    private List<String> choices; // Các lựa chọn
    private String feedback; // Phản hồi dựa trên lựa chọn
    private int points; // Điểm thưởng (ví dụ: 50)

    public Mooc() {}

    public Mooc(String id, String scenario, List<String> choices, String feedback, int points) {
        this.id = id;
        this.scenario = scenario;
        this.choices = choices;
        this.feedback = feedback;
        this.points = points;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getScenario() { return scenario; }
    public void setScenario(String scenario) { this.scenario = scenario; }
    public List<String> getChoices() { return choices; }
    public void setChoices(List<String> choices) { this.choices = choices; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
