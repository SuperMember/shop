package com.example.lenovo.taoshop.dao.mvp.present;

import android.content.Context;
import android.database.SQLException;

import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.BuyGoods;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.dao.mvp.BuySQLDataBaseHelper;
import com.example.lenovo.taoshop.dao.mvp.view.ISqlView;
import com.example.lenovo.taoshop.mvp.present.BasePresent;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

/**
 * Created by lenovo on 2017  五月  15  0015.
 */

public class SqlPresent extends BasePresent<ISqlView> implements ISqlPresent<BuyGoods> {
    private BuySQLDataBaseHelper dataBaseHelper;

    @Inject
    public SqlPresent() {
        dataBaseHelper = new BuySQLDataBaseHelper(MyApplication.getContext());
    }


    @Override
    public void insert(BuyGoods tbItemResult) {
        try {
            dataBaseHelper.insert(tbItemResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            dataBaseHelper.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BuyGoods buyGoods) {
        try {
            dataBaseHelper.update(buyGoods);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void select() {
        try {
            List<BuyGoods> list = dataBaseHelper.select();
            if (list != null && list.size() != 0) {
                myView.show(list);
            } else {
                myView.nodata();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            myView.error(e.getMessage());
        }
    }


    //获取订单数量
    @Override
    public int getCount() {
        try {
            return dataBaseHelper.getCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
