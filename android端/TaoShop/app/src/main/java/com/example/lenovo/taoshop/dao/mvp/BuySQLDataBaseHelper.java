package com.example.lenovo.taoshop.dao.mvp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.lenovo.taoshop.bean.common.BuyGoods;
import com.example.lenovo.taoshop.bean.common.TbItemResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public class BuySQLDataBaseHelper extends BaseSqlDataBaseHelper<BuyGoods> {
    private static BuySQLDataBaseHelper helper;
    private static final String tableName = "TABLE_BUY";
    private static final String table = "CREATE TABLE " + tableName + " (" +
            "  id BIGINT(20) NOT NULL  PRIMARY KEY ," +
            "  title VARCHAR(100) NOT NULL  ," +
            "  price BIGINT(20) NOT NULL ," +
            "  num INT(10) NOT NULL  ," +
            "  image VARCHAR(1500) DEFAULT NULL  ," +
            "  cid BIGINT(10) NOT NULL  ," +
            "  muser_id BIGINT(20) NOT NULL DEFAULT '1'," +
            "  created BIGINT(20) DEFAULT NULL " +
            ")";


    public BuySQLDataBaseHelper(Context context) {
        super(context, tableName, table);
    }
    /*private BuySQLDataBaseHelper(Context context) {
        super(context, tableName, table);
    }

    public static BuySQLDataBaseHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (BuySQLDataBaseHelper.class) {
                if (helper == null) {
                    helper = new BuySQLDataBaseHelper(context);
                }
            }
        }
        return helper;
    }*/

    @Override
    public void update(BuyGoods item) throws SQLException {
        String sql = "update " + tableName + " set num = " + item.getNum() + " where id = " + item.getId();
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public List<BuyGoods> select() throws SQLException {
        List<BuyGoods> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(tableName, new String[]{"id", "title", "price", "num", "image", "cid", "muser_id", "created"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BuyGoods buyGoods = new BuyGoods();
            buyGoods.setId(cursor.getLong(0));
            buyGoods.setTitle(cursor.getString(1));
            buyGoods.setPrice(cursor.getLong(2));
            buyGoods.setNum(cursor.getInt(3));
            buyGoods.setImage(cursor.getString(4));
            buyGoods.setCid(cursor.getLong(5));
            buyGoods.setMuser_id(cursor.getLong(6));
            buyGoods.setCreated(cursor.getLong(7));
            list.add(buyGoods);
        }
        return list;
    }

    @Override
    public void delete(long id) throws SQLException {
        String sql = "delete from " + tableName + " where id = " + id;
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void insert(BuyGoods item) throws SQLException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", item.getId());
        contentValues.put("title", item.getTitle());
        contentValues.put("price", item.getPrice());
        contentValues.put("num", item.getNum());
        contentValues.put("image", item.getImage());
        contentValues.put("cid", item.getCid());
        contentValues.put("muser_id", item.getMuser_id());
        contentValues.put("created", item.getCreated());
        sqLiteDatabase.insert(tableName, null, contentValues);
    }

    @Override
    public int getCount() throws SQLException {
        String sql = "select count(*) from " + tableName;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return -1;
    }
}
