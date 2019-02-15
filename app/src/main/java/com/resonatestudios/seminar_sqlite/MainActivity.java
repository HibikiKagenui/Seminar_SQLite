package com.resonatestudios.seminar_sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.resonatestudios.seminar_sqlite.R;
import com.resonatestudios.seminar_sqlite.model.DbStudents;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // create DbStudents object
        DbStudents dbStudents = new DbStudents(this);
        // get the textview from activity_main.xml
        TextView textView = findViewById(R.id.text_view);
        // insert some data, and then getAll
        dbStudents.open();
        dbStudents.insert("Abel", "123");
        dbStudents.insert("Ilham", "123");
        dbStudents.insert("Putri", "123");
        List<DbStudents.Student> students = dbStudents.getAll();
        dbStudents.close();
        // arrange the students list items to a single string
        StringBuilder value = new StringBuilder();
        for (DbStudents.Student item : students) {
            value.append(item.name)
                    .append(" - ")
                    .append(item.phone)
                    .append("\n");
        }
        // change the textview text to the string
        textView.setText(value.toString());
    }
}
