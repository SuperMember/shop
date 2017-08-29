package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.CommentMessageList;
import com.example.lenovo.taoshop.bean.common.OrderMessageList;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.mvp.view.IMessageView;
import com.example.lenovo.taoshop.utils.JsonUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  25  0025.
 */

public class MessagePresent extends BasePresent<IMessageView> {
    public MessagePresent() {

    }

    //交易信息
    public void getMessage(long userId, final int page, final int type) {
        addSubscription(ApiService.getInstanceGood().getMessage(userId, page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, OrderMessageList>() {
                    @Override
                    public OrderMessageList call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            String json = (String) taoTaoResult.getData();
                            return JsonUtils.jsonToPojo(json, OrderMessageList.class);
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<OrderMessageList>() {
                    @Override
                    public void call(OrderMessageList messageList) {
                        if (page > 1) {
                            if (messageList != null && messageList.getRows().size() != 0) {
                                myView.loadMore(messageList.getRows());
                            } else {
                                myView.noMore();
                            }
                        } else {
                            if (messageList != null && messageList.getRows().size() != 0) {
                                myView.load(messageList.getRows());
                            } else {
                                myView.empty();
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail(throwable.getMessage());
                    }
                }));
    }

    //互动消息
    public void getCommentMessage(long userId, final int page, final int type) {
        addSubscription(ApiService.getInstanceGood().getMessage(userId, page, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<TaoTaoResult, CommentMessageList>() {
                    @Override
                    public CommentMessageList call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            String json = (String) taoTaoResult.getData();
                            return JsonUtils.jsonToPojo(json, CommentMessageList.class);
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<CommentMessageList>() {
                    @Override
                    public void call(CommentMessageList messageList) {
                        if (page > 1) {
                            if (messageList != null && messageList.getRows().size() != 0) {
                                myView.loadMore(messageList.getRows());
                            } else {
                                myView.noMore();
                            }
                        } else {
                            if (messageList != null && messageList.getRows().size() != 0) {
                                myView.load(messageList.getRows());
                            } else {
                                myView.empty();
                            }
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
