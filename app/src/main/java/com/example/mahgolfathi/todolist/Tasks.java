package com.example.mahgolfathi.todolist;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tasks extends AppCompatActivity {


    private static final String DATABASE_NAME = "issues_db";
    private DataBase issueDataBase;
    private ArrayList<String> tasks = new ArrayList<String>();
    private Context context;
    private int issueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Intent intent = getIntent();
        issueId = intent.getIntExtra("selectedIssue", 0);
        issueDataBase = Room.databaseBuilder(getApplicationContext(),
                DataBase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<String> allTasks = Converters.fromString(issueDataBase.daoAccess().fetchIssueById(issueId).getTasks());
                if (allTasks != null) {
                    tasks = allTasks;
                }
                loadTasks();
            }
        }).start();


    }

    public void loadTasks() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText editText = findViewById(R.id.textInput);
                editText.setText("");

                final ListView listview = (ListView) findViewById(R.id.tasksListView);

                final StableArrayAdapter adapter = new StableArrayAdapter(context,
                        android.R.layout.simple_list_item_1, tasks);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final String item = (String) parent.getItemAtPosition(position);
                        view.animate().setDuration(2000).alpha(0)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        tasks.remove(item);
                                        view.setAlpha(1);
                                        adapter.notifyDataSetChanged();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                issueDataBase.daoAccess().fetchIssueById(issueId).setTasks(Converters.fromArrayList(tasks));
                                                issueDataBase.daoAccess().update(Converters.fromArrayList(tasks), issueId);
                                                tasks = Converters.fromString(issueDataBase.daoAccess().fetchIssueById(issueId).getTasks());
                                            }}).start();
                                    }
                                });
                    }

                });
            }
        });

    }


    public void addTask(View v) {
        EditText editText = findViewById(R.id.textInput);
        String newTask = String.valueOf(editText.getText());
        tasks.add(newTask);
        new Thread(new Runnable() {
            @Override
            public void run() {
                issueDataBase.daoAccess().fetchIssueById(issueId).setTasks(Converters.fromArrayList(tasks));
                issueDataBase.daoAccess().update(Converters.fromArrayList(tasks), issueId);
                tasks = Converters.fromString(issueDataBase.daoAccess().fetchIssueById(issueId).getTasks());
                loadTasks();
            }
        }).start();

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);

            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }

        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}

