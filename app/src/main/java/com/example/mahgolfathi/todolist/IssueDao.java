package com.example.mahgolfathi.todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface IssueDao {

    @Query("SELECT * FROM Issue")
    List<Issue> getAll();

    @Insert
    void insert(Issue... issue);

    @Query("SELECT * FROM Issue WHERE id = :id")
    Issue fetchIssueById(int id);

    @Delete
    void delete(Issue issue);

    @Query("UPDATE Issue SET tasks=:updatedTasks WHERE id = :id")
    void update(String updatedTasks, int id);

}
