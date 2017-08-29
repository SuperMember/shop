package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.app.MyApplication;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.mvp.view.IInfoView;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  六月  01  0001.
 */

public class InfoPresent extends BasePresent<IInfoView> {
    public InfoPresent() {
    }

    public void edit(String url, Long userId) {
        // 用户id
        String descriptionString = userId + "";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        File file = new File(url);
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("multipartFile", file.getName(), requestFile);

        addSubscription(ApiService.getInstanceGood().edit(description, body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map>() {
                    @Override
                    public void call(Map map) {
                        double result = (double) map.get("error");
                        if (result == 0) {
                            //成功
                            String url = (String) map.get("url");
                            //修改信息
                            SharedPreferencesUtil.getInstance(MyApplication.getContext()).editPor(url);
                            myView.success("上传成功", url);
                        } else {
                            myView.success("上传失败", null);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }
}
