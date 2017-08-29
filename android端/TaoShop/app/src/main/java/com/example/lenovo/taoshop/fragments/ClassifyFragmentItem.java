package com.example.lenovo.taoshop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRecyclerViewItemClickListener;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.adapter.ListViewClassifyAdapter;
import com.example.lenovo.taoshop.adapter.RecyclerViewClassifyAdapter;
import com.example.lenovo.taoshop.bean.common.TbItemCat;
import com.example.lenovo.taoshop.bean.common.TbItemCatResult;
import com.example.lenovo.taoshop.bean.common.TbItemResult;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.injector.components.DaggerGoodComponent;
import com.example.lenovo.taoshop.injector.modules.GoodModule;
import com.example.lenovo.taoshop.mvp.present.ClassifyPresent;
import com.example.lenovo.taoshop.mvp.view.IClassifyView;
import com.example.lenovo.taoshop.widget.ClassifyPopupWindow;
import com.example.lenovo.taoshop.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017  五月  10  0010.
 */

public class ClassifyFragmentItem extends BaseFragment<ClassifyPresent> implements IClassifyView {
    @Bind(R.id.lv_index)
    ListView lvIndex;
    @Bind(R.id.text_classify)
    TextView textClassify;
    @Bind(R.id.img_classify)
    ImageView imgClassify;//弹出框
    private long type;

    // private RecyclerViewClassifyAdapter adapter;
    private ListViewClassifyAdapter adapter;
    private ClassifyFragmentItemTab fragmentTab;
    private boolean isPrepare = false;
    private boolean isFirst = true;
    private int clickId = 0;//保存在popupwindow中点击的是哪个按钮
    private long itemid;//保存当前是第几个item
    private ClassifyPopupWindow popupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepare || !isVisible) return;
        else {
            if (isFirst) {
                start();
                initfragment();
                myPresent.getClassify(type);//获取分类信息
                isFirst = false;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) lazyLoad();
    }

    @Override
    protected void initInjector() {
        DaggerGoodComponent.builder().goodModule(new GoodModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        type = bundle.getLong("type");//获取初始化参数
        isPrepare = true;
        initpopupWindow();
        adapter = new ListViewClassifyAdapter(mContext);
        lvIndex.setAdapter(adapter);
    }

    private void initpopupWindow() {
        popupWindow = new ClassifyPopupWindow(mContext);
    }

    //初始化fragment
    public void initfragment() {
        fragmentTab = new ClassifyFragmentItemTab();//初始化listview右边的fragment
        FragmentManager fragmentManager = getChildFragmentManager();//吊炸天
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout_container, fragmentTab);
        fragmentTransaction.commitAllowingStateLoss();//吊炸天
    }

    @Override
    protected void setListener() {
        //监听点击事件
      /*  adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //请求商品
                TbItemCat tbItemCat = adapter.getItem(position).getTbItemCat();
                long itemId = tbItemCat.getId();
                fragmentTab.setDatas(itemId);
                textClassify.setText(tbItemCat.getName());
                popupWindow.setDatas(adapter.getItem(position).getList());
            }
        });*/

        adapter.setOnItemClickListener(new ListViewClassifyAdapter.OnItemClickListener() {
            @Override
            public void onClick(TbItemCatResult tbItemCatResult) {
                TbItemCat tbItemCat = tbItemCatResult.getTbItemCat();
                long itemId = tbItemCat.getId();
                itemid = itemId;
                fragmentTab.setDatas(itemId, null);
                textClassify.setText(tbItemCat.getName());
                popupWindow.setDatas(tbItemCatResult.getList());
                adapter.notifyDataSetChanged();
            }
        });

        //监听弹出框内容的点击事件
        popupWindow.setOnViewClickListener(new ClassifyPopupWindow.OnViewClickListener() {
            @Override
            public void onClick(Long id, String name, int resId) {
                //根据不同的点击，加载不同的数据
                textClassify.setText(name);
                //加载数据
                fragmentTab.setDatas(itemid, id);
                clickId = resId;//保存Id，方便回显
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_classify_item;
    }

    @Override
    protected void updateView() {

    }

    @Override
    protected void initToolbar() {

    }

    public static ClassifyFragmentItem getInstance(long type) {
        ClassifyFragmentItem classifyFragmentItem = new ClassifyFragmentItem();
        Bundle bundle = new Bundle();
        bundle.putLong("type", type);
        classifyFragmentItem.setArguments(bundle);
        return classifyFragmentItem;
    }

    @Override
    public void get(List<TbItemResult> tbItems) {

    }

    @Override
    public void fail(String msg) {
        //showToast(msg);
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getClassify(type);
            }
        });
    }

    @Override
    public void getMore(List<TbItemResult> tbItems) {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void empty() {
        nodata();
    }

    @Override
    public void getClassify(List<TbItemCatResult> list) {
        end();
       /* List<String> strings = new ArrayList<>();
        for (TbItemCatResult tr : list) {
            strings.add(tr.getTbItemCat().getName());
        }*/
        // adapter.updateItems(strings);
        adapter.addItems(list);


        //加载右边第一项fragment的商品
        textClassify.setText(list.get(0).getTbItemCat().getName());
        fragmentTab.setDatas(list.get(0).getTbItemCat().getId(), null);
        itemid = list.get(0).getTbItemCat().getId();//保存id

        popupWindow.setDatas(list.get(0).getList());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.img_classify)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_classify:
                //弹出窗口
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.show(imgClassify, 0, 0, clickId);//显示
                }
                break;
        }
    }
}
