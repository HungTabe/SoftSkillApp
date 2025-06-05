package com.example.softskillapp.data.model;

import java.util.List;

public class Quiz {
    private String id; // ID quiz trong Firestore
    private String question; // Câu hỏi
    private List<String> options; // 4 đáp án
    private int correctAnswer; // Chỉ số đáp án đúng (0-3)

    public Quiz() {}

    public Quiz(String id, String question, List<String> options, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }
}
