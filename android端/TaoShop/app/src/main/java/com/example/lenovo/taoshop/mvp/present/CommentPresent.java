package com.example.lenovo.taoshop.mvp.present;

import com.example.lenovo.taoshop.api.ApiService;
import com.example.lenovo.taoshop.bean.common.CommentsResult;
import com.example.lenovo.taoshop.bean.common.TaoTaoResult;
import com.example.lenovo.taoshop.bean.common.TbComments;
import com.example.lenovo.taoshop.bean.common.TbCommentsReply;
import com.example.lenovo.taoshop.bean.common.TbCommentsResult;
import com.example.lenovo.taoshop.mvp.view.ICommentView;
import com.example.lenovo.taoshop.utils.JsonUtils;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2017  五月  09  0009.
 */

public class CommentPresent extends BasePresent<ICommentView> {
    public CommentPresent() {
    }

    public void getComments(long userId, final long page) {
        addSubscription(ApiService.getInstanceComment().getComments(userId, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<TaoTaoResult, List<TbCommentsResult>>() {
                    @Override
                    public List<TbCommentsResult> call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            CommentsResult commentsResult = JsonUtils.jsonToPojo((String) taoTaoResult.getData(), CommentsResult.class);
                            List<TbCommentsResult> list = commentsResult.getUserlist();
                            if (list != null) {
                                return list;
                            }
                        }
                        return null;
                    }
                })
                .subscribe(new Action1<List<TbCommentsResult>>() {
                    @Override
                    public void call(List<TbCommentsResult> list) {
                        if (page > 1) {
                            if (list != null && list.size() != 0) {
                                myView.getMore(list);
                            } else {
                                myView.noMore();
                            }
                        } else {
                            if (list != null && list.size() != 0) {
                                myView.show(list);
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

    public void submitComments(TbComments tbComments, long orderId) {
        addSubscription(ApiService.getInstanceComment().submit(tbComments, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TaoTaoResult>() {
                    @Override
                    public void call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            myView.submit("提交成功");
                        } else {
                            myView.fail("提交失败，请重新提交");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        myView.fail("提交失败，请重新提交");
                    }
                }));
    }

    //回复
    public void submitCommentsReply(TbCommentsReply tbCommentsReply, Integer type) {
        addSubscription(ApiService.getInstanceComment().submitReply(tbCommentsReply, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<TaoTaoResult>() {
                    @Override
                    public void call(TaoTaoResult taoTaoResult) {
                        if (taoTaoResult.getStatus() == 200) {
                            myView.submit("评论成功");
                        } else {
                            myView.submit("评论失败，请重试");
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
