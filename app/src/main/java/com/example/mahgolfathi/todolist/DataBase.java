package com.example.mahgolfathi.todolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Issue.class}, version = 2, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public abstract IssueDao daoAccess();
}