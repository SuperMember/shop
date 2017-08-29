package com.example.lenovo.taoshop.fragments.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.example.lenovo.taoshop.LogisticsActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.adapter.RecyclerviewMessageAdapter;
import com.example.lenovo.taoshop.bean.common.OrderItem;
import com.example.lenovo.taoshop.bean.common.TbOrderMsg;
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
 * Created by lenovo on 2017  五月  25  0025.
 */

public class OrderMessageFragment extends BaseFragment<MessagePresent> implements IMessageView<TbOrderMsg> {
    @Bind(R.id.rv_message)
    RecyclerView rvMessage;
    private int type = 0;
    private UserEntity userEntity;
    private RecyclerviewMessageAdapter adapter;
    private int page = 1;
    private long userId = 1;


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
        adapter = new RecyclerviewMessageAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvMessage, false, adapter);
    }

    @Override
    protected void setListener() {
        adapter.setOnCardViewClickListener(new RecyclerviewMessageAdapter.OnCardViewClickListener() {
            @Override
            public void onClick(TbOrderMsg tbOrderMsg) {
                //跳转
                Intent intent = new Intent(getActivity(), LogisticsActivity.class);
                OrderItem orderItem = new OrderItem();
                orderItem.setPic_path(tbOrderMsg.getPic());
                if (tbOrderMsg.getLogisticsid() == null) {
                    orderItem.setShipping_code("暂无");
                } else {
                    orderItem.setShipping_code(tbOrderMsg.getLogisticsid() + "");
                }
                if (tbOrderMsg.getCompany() == null) {
                    orderItem.setShipping_name("暂无");
                } else {
                    orderItem.setShipping_name(tbOrderMsg.getCompany());
                }
                orderItem.setBuyer_message(tbOrderMsg.getMsg());//用用户留言代替物流信息
                intent.putExtra("order", orderItem);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_message_order;
    }

    @Override
    protected void updateView() {
        start();
        myPresent.getMessage(userId, page, type);
    }

    @Override
    protected void initToolbar() {
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

    @Override
    public void fail(String msg) {
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getMessage(userId, page, type);
            }
        });
    }

    @Override
    public void load(List<TbOrderMsg> list) {
        end();
        adapter.loadComplete();
        adapter.updateItems(list);
    }

    @Override
    public void loadMore(List<TbOrderMsg> list) {
        end();
        adapter.loadComplete();
        adapter.addItems(list);
    }

    @Override
    public void noMore() {
        adapter.loadComplete();
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
}
