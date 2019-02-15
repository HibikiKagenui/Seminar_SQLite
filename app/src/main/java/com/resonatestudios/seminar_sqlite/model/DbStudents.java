package com.resonatestudios.seminar_sqlite.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbStudents {
    private static final String TABLE_NAME = "STUDENTS";
    private static final String[] columns = {"ID", "NAME", "PHONE"};
    private final OpenHelper dbHelper;
    private SQLiteDatabase db;

    public DbStudents(Context context) {
        dbHelper = new OpenHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public List<Student> getAll() {
        List<Student> studentList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, columns, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            studentList.add(new Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            ));
            while (cursor.moveToNext()) {
                studentList.add(new Student(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            }
        }
        cursor.close();
        return studentList;
    }

    public Student get(String name) {
        Cursor cursor;
        Student student = new Student();

        String[] param = {name};

        cursor = db.query(TABLE_NAME, columns, "NAME=?", param, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            student.id = cursor.getInt(0);
            student.name = cursor.getString(1);
            student.phone = cursor.getString(2);
        }

        cursor.close();

        return student;
    }

    public boolean insert(String name, String phone) {
        ContentValues newValue = new ContentValues();
        newValue.put("NAME", name);
        newValue.put("PHONE", phone);

        return db.insert(TABLE_NAME, null, newValue) > 0;
    }

    public boolean update(int id, String name, String phone) {
        ContentValues newValue = new ContentValues();

        newValue.put("NAME", name);
        newValue.put("PHONE", phone);

        return db.update(TABLE_NAME, newValue, "ID=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean delete(int id) {
        return db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)}) > 0;
    }

    public static class Student {
        public int id;
        public String name;
        public String phone;

        Student() {
        }

        Student(int id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }
    }

}
