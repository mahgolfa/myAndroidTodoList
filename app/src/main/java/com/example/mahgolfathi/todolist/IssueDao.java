package com.example.mahgolfathi.todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface IssueDao {

    @Query("SELECT * FROM Issue")
    List<Issue> getAll();

    @Insert
    void insert(Issue... issue);

    @Query("SELECT * FROM Issue WHERE title = :title")
    Issue fetchOneIssueByTitle(String title);

    @Delete
    void delete(Issue issue);

}
