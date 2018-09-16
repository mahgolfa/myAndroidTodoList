package com.example.mahgolfathi.todolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "issue")
public class Issue {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "tasks")
    private String tasks;
    @ColumnInfo(name = "title")
    private String title;

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public String getTasks() {
        return tasks;
    }


}
