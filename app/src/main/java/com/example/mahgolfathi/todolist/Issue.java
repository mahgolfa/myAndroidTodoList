package com.example.mahgolfathi.myfirstapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "issue")

public class Issue {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tasks")
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public void addTask(String taskString) {
         Task newTask = new Task();
         newTask.isDone = false;
         newTask.title = taskString;
        this.tasks.add(newTask);
    }

    private  class Task{
      public boolean isDone ;
      public String title ;
    }

    public ArrayList<Task> getTasks() {
       return tasks ;
    }

}
