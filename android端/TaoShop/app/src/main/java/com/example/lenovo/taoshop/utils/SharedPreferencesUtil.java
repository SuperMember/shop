package com.example.lenovo.taoshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.TbAddr;
import com.example.lenovo.taoshop.bean.common.User;
import com.example.lenovo.taoshop.bean.common.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017  五月  18  0018.
 */

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;
    private static SharedPreferencesUtil sharedPreferencesUtil;

    private SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }


    public static SharedPreferencesUtil getInstance(Context context) {
        if (sharedPreferencesUtil == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (sharedPreferencesUtil == null) {
                    sharedPreferencesUtil = new SharedPreferencesUtil(context);
                }
            }
        }
        return sharedPreferencesUtil;
    }


    //修改头像地址
    public void editPor(String url) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img", url);
        editor.commit();
    }

    //存储用户信息
    public void save(UserEntity user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user.getUsername());
        editor.putString("img", user.getImg());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putLong("userId", user.getId());
        editor.putString("addr", JsonUtils.objectToJson(user.getTbAddrs() == null ? new ArrayList<TbAddr>() : user.getTbAddrs()));//json格式存储
        editor.commit();
    }

    //删除
    public void delete() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("img");
        editor.remove("email");
        editor.remove("phone");
        editor.remove("userId");
        editor.remove("addr");
        editor.commit();
    }

    //设置信息
    public UserEntity getInfo() {
        UserEntity user = new UserEntity();
        String username = sharedPreferences.getString("username", null);
        String img = sharedPreferences.getString("img", null);
        String email = sharedPreferences.getString("email", null);
        String phone = sharedPreferences.getString("phone", null);
        Long userId = sharedPreferences.getLong("userId", -1);
        String addr = sharedPreferences.getString("addr", "");

        user.setUsername(username);
        user.setImg(img);
        user.setPhone(phone);
        user.setEmail(email);
        user.setId(userId);
        user.setTbAddrs(JsonUtils.jsonToList(addr, TbAddr.class));
        return user;
    }


    //修改默认地址
    public void editAddr(Long addrid) {
        String addr = sharedPreferences.getString("addr", "");
        if (!addr.equals("")) {
            List<TbAddr> list = JsonUtils.jsonToList(addr, TbAddr.class);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAddrid().equals(addrid)) {
                    list.get(i).setDefaultaddr(1);
                } else {
                    list.get(i).setDefaultaddr(0);
                }
            }
            UserEntity userEntity = getInfo();
            userEntity.setTbAddrs(list);
            save(userEntity);
        }

    }

}
