package com.example.lenovo.taoshop.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  二月  05  0005.
 */

public class SQLDataBaseHelper {
    private SQLDataBase sqlDataBase;
    private static final String TABLE_NAME = "HISTORY";
    private static final String TABLE = "create table " + TABLE_NAME + "(" +
            "id integer primary key autoincrement ,content varchar(100)" +
            ");";
    private SQLiteDatabase sqLiteDatabase;

    public SQLDataBaseHelper(Context context) {
        sqlDataBase = new SQLDataBase(context, TABLE_NAME, 1);
        sqLiteDatabase = sqlDataBase.getWritableDatabase();
    }

    public void insert(String content) {
        String sql = "insert into " + TABLE_NAME + " values(?);";
        sqLiteDatabase.execSQL(sql,new String[]{content});
    }
    //删除某一条记录
    public void delete(int id){
        String sql = "delete from " + TABLE_NAME + " where id=?";
        sqLiteDatabase.execSQL(sql,new Integer[]{id});
    }
    //删除全部记录
    public void deleteAll() {
        String sql = "delete from " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
    }

    public List<String> get() {
        List<String> list = new ArrayList<>();
        String sql = "select * from " + TABLE_NAME + ";";
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
           for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
               list.add(cursor.getString(cursor.getColumnIndex("content")));
           }
        }
        cursor.close();
        return list;
    }

    public void close(){
        sqlDataBase.close();
        sqLiteDatabase.close();
    }

    static class SQLDataBase extends SQLiteOpenHelper {

        public SQLDataBase(Context context, String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.delete(TABLE, "1", null);
            db.execSQL(TABLE);
        }
    }
}
