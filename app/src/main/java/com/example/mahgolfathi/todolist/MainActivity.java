package com.example.mahgolfathi.todolist;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "issues_db";
    private DataBase issueDataBase = Room.databaseBuilder(getApplicationContext(),
            DataBase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
    private List<Issue> allIssues = issueDataBase.daoAccess().getAll();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.issuesListView);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < allIssues.size(); i++) {
            list.add(allIssues.get(i).getTitle());
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, Tasks.class);
                startActivity(intent);
            }
        });
    }

    public void addIssue(View v) {
        EditText editText = findViewById(R.id.textInput);
        String issueTitle = String.valueOf(editText.getText());
        Issue newIssue = new Issue();
        newIssue.setTitle(issueTitle);
        issueDataBase.daoAccess().insert(newIssue);
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
