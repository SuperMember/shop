package com.example.lenovo.taoshop.dao.mvp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lenovo.taoshop.dao.ISql;
import com.example.lenovo.taoshop.dao.SQLDataBaseHelper;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public abstract class BaseSqlDataBaseHelper<T> implements ISql<T> {
    private SQLDataBase sqlDataBase;
    protected SQLiteDatabase sqLiteDatabase;

    public BaseSqlDataBaseHelper(Context context, String tableName, String table) {
        sqlDataBase = new SQLDataBase(context, tableName, 1, table);
        sqLiteDatabase = sqlDataBase.getWritableDatabase();
    }

    protected void close(){
        sqlDataBase.close();
    }

    static class SQLDataBase extends SQLiteOpenHelper {
        private String table;

        public SQLDataBase(Context context, String name, int version, String table) {
            super(context, name, null, version);
            this.table = table;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.delete(table, "1", null);
            db.execSQL(table);
        }
    }
}
