package com.example.mahgolfathi.todolist;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface IssueDao {

        @Query("SELECT * FROM issue")
        List<com.example.mahgolfathi.todolist.Issue> getAll();


        @Insert
        void insertAll(com.example.mahgolfathi.todolist.Issue... issues);

        @Delete
        void delete(com.example.mahgolfathi.todolist.Issue issues);

}
