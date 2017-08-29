package com.example.lenovo.taoshop.fragments.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.example.lenovo.taoshop.CommentsActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.adapter.RecyclerviewCommentMessageAdapter;
import com.example.lenovo.taoshop.bean.common.TbCommentsMsg;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.injector.components.DaggerMessageComponent;
import com.example.lenovo.taoshop.injector.modules.MessageModule;
import com.example.lenovo.taoshop.mvp.present.MessagePresent;
import com.example.lenovo.taoshop.mvp.view.IMessageView;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017  五月  28  0028.
 */

public class CommentMessageFragment extends BaseFragment<MessagePresent> implements IMessageView<TbCommentsMsg> {
    @Bind(R.id.rv_message)
    RecyclerView rvMessage;
    private UserEntity userEntity;
    private RecyclerviewCommentMessageAdapter adapter;
    private int page = 1;
    private long userId = 1;
    private int type = 1;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initInjector() {
        DaggerMessageComponent.builder().messageModule(new MessageModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        userEntity = SharedPreferencesUtil.getInstance(mContext).getInfo();
       /* if (userEntity == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }*/
        userId = userEntity.getId();
        adapter = new RecyclerviewCommentMessageAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvMessage, false, adapter);
    }

    @Override
    protected void setListener() {
        adapter.setOnCardViewClickListener(new RecyclerviewCommentMessageAdapter.OnCardViewClickListener() {
            @Override
            public void onClick(Long itemId, Long muserId) {
                Intent intent = new Intent(getActivity(), CommentsActivity.class);
                intent.putExtra("itemId", itemId + "");
                intent.putExtra("muserId", muserId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_message_comment;
    }

    @Override
    protected void updateView() {
        start();
        myPresent.getCommentMessage(userId, page, type);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void fail(String msg) {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getCommentMessage(userId, page, type);
            }
        });
    }

    @Override
    public void load(List<TbCommentsMsg> list) {
        end();
        adapter.loadComplete();
        adapter.updateItems(list);
    }

    @Override
    public void loadMore(List<TbCommentsMsg> list) {
        adapter.loadComplete();
        adapter.addItems(list);
    }

    @Override
    public void noMore() {
        adapter.noMoreData();
    }

    @Override
    public void empty() {
        nodata(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getMessage(userId, page, type);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
