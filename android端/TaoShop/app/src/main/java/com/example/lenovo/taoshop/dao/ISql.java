package com.example.lenovo.taoshop.dao;

import android.database.SQLException;

import java.util.List;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public interface ISql<T> {
    void update(T item) throws SQLException;

    List<T> select() throws SQLException;

    void delete(long id) throws SQLException;

    void insert(T item) throws SQLException;

    int getCount() throws SQLException;
}
