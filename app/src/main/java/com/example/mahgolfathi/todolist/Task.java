package com.example.mahgolfathi.todolist;

public class Task {
    private boolean isDone = false;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone() {
        isDone = true;
    }
}
