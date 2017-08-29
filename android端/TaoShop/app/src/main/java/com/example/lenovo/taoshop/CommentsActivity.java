package com.example.lenovo.taoshop;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.example.lenovo.taoshop.adapter.CommentAdapter;
import com.example.lenovo.taoshop.bean.common.TbCommentsReply;
import com.example.lenovo.taoshop.bean.common.TbCommentsResult;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.injector.components.DaggerCommentComponent;
import com.example.lenovo.taoshop.injector.modules.CommentModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.CommentPresent;
import com.example.lenovo.taoshop.mvp.view.ICommentView;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class CommentsActivity extends BaseActivity<CommentPresent> implements ICommentView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_comment)
    RecyclerView rvComment;
    @Bind(R.id.edit_comment)
    EditText editComment;
    @Bind(R.id.btn_send)
    Button btnSend;
    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout swiperefreshLayout;
    private long itemId;
    private long page = 1;


    private CommentAdapter adapter;
    private ProgressDialog dialog;
    private UserEntity userEntity;
    private TbCommentsReply tbCommentsReply;
    private Long muserId;//卖家id
    private Long replyId;//回复的用户id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        swiperefreshLayout.setOnRefreshListener(listener);

        //加载更多
        adapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                page++;
                myPresent.getComments(itemId, page);
            }
        });

        adapter.setOnTextClickListener(new CommentAdapter.OnTextClickListener() {
            @Override
            public void onClick(TbCommentsReply tr, int index) {
                switch (index) {
                    case 0:
                        //回复者
                        editComment.setHint("回复" + tr.getName());
                        tbCommentsReply.setReplyname(tr.getName());
                        tbCommentsReply.setReplyid(tr.getUserid());
                        replyId = tr.getUserid();
                        break;
                    case 1:
                        //被回复者
                        editComment.setHint("回复" + tr.getReplyname());
                        tbCommentsReply.setReplyname(tr.getReplyname());
                        tbCommentsReply.setReplyid(tr.getReplyid());
                        replyId = tr.getReplyid();
                        break;
                }

                tbCommentsReply.setName(userEntity.getUsername());
                tbCommentsReply.setPic(userEntity.getImg());
                tbCommentsReply.setUserid(userEntity.getId());
                tbCommentsReply.setItemId(tr.getItemId());
                tbCommentsReply.setMuserid(tr.getMuserid());
                tbCommentsReply.setParentid(tr.getParentid());

                editComment.setFocusable(true);
                editComment.setFocusableInTouchMode(true);
                editComment.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    protected void inject() {
        DaggerCommentComponent.builder().commentModule(new CommentModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        itemId = Long.parseLong(getIntent().getStringExtra("itemId"));
        muserId = getIntent().getLongExtra("muserId", 0);
        //itemId = 536563;
        //初始化toolbar
        initToolbar("评论", true, true, toolbar);

        adapter = new CommentAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(this, rvComment, true, adapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("提交中...");
        dialog.setTitle("提交评论");

        userEntity = SharedPreferencesUtil.getInstance(this).getInfo();
        tbCommentsReply = new TbCommentsReply();
    }

    public SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            swiperefreshLayout.setRefreshing(true);
            myPresent.getComments(itemId, page);
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_comments;
    }

    @Override
    protected void updateView() {
        start();
        myPresent.getComments(itemId, page);
    }

    //没有更多
    @Override
    public void noMore() {
        adapter.loadComplete();
        adapter.noMoreData();
    }

    //没有数据
    @Override
    public void empty() {
        nodata();
    }

    @Override
    public void submit(String msg) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        showToast(msg);
        listener.onRefresh();
    }

    //获取更多
    @Override
    public void getMore(List<TbCommentsResult> list) {
        end();
        adapter.addItems(list);
    }

    //展示数据
    @Override
    public void show(List<TbCommentsResult> list) {
        if (swiperefreshLayout != null && swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(false);
        }
        end();
        adapter.updateItems(list);
    }

    //失败
    @Override
    public void fail(String msg) {
        if (swiperefreshLayout != null && swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(false);
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        //showToast(msg);
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getComments(itemId, page);
            }
        });
    }

    @OnClick({R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String comment = editComment.getText().toString().trim();
                if (comment.equals("")) {
                    //内容不能为空
                    showToast("回复内容不能为空");
                    break;
                }
                dialog.show();
                tbCommentsReply.setReplycomments(comment);
                //判断回复的是卖家还是普通买家
                //根据id和点击的用户id比较
                //提交评论
                if (muserId.equals(replyId)) {
                    //回复卖家
                    myPresent.submitCommentsReply(tbCommentsReply, 1);//数字2表示买家回复买家,1表示买家回复卖家
                } else {
                    myPresent.submitCommentsReply(tbCommentsReply, 2);//数字2表示买家回复买家,1表示买家回复卖家
                }
                break;
        }
    }
}
