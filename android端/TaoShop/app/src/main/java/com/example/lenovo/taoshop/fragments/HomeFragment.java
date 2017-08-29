package com.example.lenovo.taoshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.example.lenovo.taoshop.GoodDetailActivity;
import com.example.lenovo.taoshop.HomeItemDetailActivity;
import com.example.lenovo.taoshop.R;
import com.example.lenovo.taoshop.SearchActivity;
import com.example.lenovo.taoshop.adapter.GridViewIndexAdapter;
import com.example.lenovo.taoshop.adapter.RecyclerViewHomeAdapter;
import com.example.lenovo.taoshop.adapter.ViewPagerIndexAdapter;
import com.example.lenovo.taoshop.bean.common.AdItem;
import com.example.lenovo.taoshop.bean.common.IndexMulEntity;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.fragments.base.BaseFragment;
import com.example.lenovo.taoshop.injector.components.DaggerHomeComponent;
import com.example.lenovo.taoshop.injector.modules.HomeModule;
import com.example.lenovo.taoshop.mvp.present.HomePresent;
import com.example.lenovo.taoshop.mvp.view.IHomeView;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.hankkin.library.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017  五月  01  0001.
 */

public class HomeFragment extends BaseFragment<HomePresent> implements IHomeView, ViewTreeObserver.OnGlobalLayoutListener {

    @Bind(R.id.text_search)
    TextView textSearch;
    @Bind(R.id.rv_home)
    RecyclerView rvHome;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swiperefreshLayout;
    // @Bind(R.id.view_title)
    // View viewTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.header)
    View header;
    @Bind(R.id.img_up)
    CircleImageView imgUp;

    private List<String> mTitle;
    private ConvenientBanner convenientBanner;//轮播图
    private ViewPager viewpager;
    private ViewPagerIndexAdapter viewPagerIndexAdapter;
    private GridViewIndexAdapter gridViewIndexAdapter;
    private int height = 0;//获取轮播图的高度
    private RecyclerViewHomeAdapter adapter;
    private int[] indication = new int[]{R.mipmap.icon_indication_normal, R.mipmap.icon_indication_select};
    private View view;
    private int oldY = 0;
    private int page = 2;

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

    }

    /*
     *   注入present
     * */
    @Override
    protected void initInjector() {
        DaggerHomeComponent.builder().homeModule(new HomeModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        mTitle = new ArrayList<>();
        mTitle.add("新品");
        mTitle.add("热销");
        mTitle.add("推荐");
        mTitle.add("手机");
        mTitle.add("电脑");
        mTitle.add("服装");
        mTitle.add("家电");
        mTitle.add("分类");

        view = LayoutInflater.from(mContext).inflate(R.layout.header_banner, null);
        convenientBanner = (ConvenientBanner) view.findViewById(R.id.banner);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        List<GridView> list = new ArrayList<>();
        list.add(getGridView());
        viewPagerIndexAdapter = new ViewPagerIndexAdapter(list);
        viewpager.setAdapter(viewPagerIndexAdapter);

        adapter = new RecyclerViewHomeAdapter(mContext);
        RecyclerViewHelper.initRecyclerViewV(mContext, rvHome, false, adapter);

        header.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight()));
    }


    @Override
    protected void setListener() {
        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                       @Override
                                       public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                           super.onScrollStateChanged(recyclerView, newState);
                                       }

                                       @Override
                                       public void onScrolled(RecyclerView recyclerView, int dx, int y) {
                                           super.onScrolled(recyclerView, dx, y);
                                       /*    int newY = y - oldY;
                                           if (newY <= 0) {
                                               //设置标题的背景颜色
                                               viewTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                                               toolbar.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                                           } else if (newY > 0 && newY <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                                               float scale = (float) y / height;
                                               float alpha = (255 * scale);
                                               toolbar.setBackgroundColor(Color.argb((int) alpha, 63, 81, 181));
                                               viewTitle.setBackgroundColor(Color.argb((int) alpha, 63, 81, 181));
                                           } else {
                                               //滑动到banner下面设置普通颜色
                                               toolbar.setBackgroundColor(Color.parseColor("#ff3F51B5"));
                                               viewTitle.setBackgroundColor(Color.parseColor("#ff3F51B5"));
                                           }
                                           oldY = y;*/
                                       }
                                   }

        );

        adapter.setOnGoodClickListener(new RecyclerViewHomeAdapter.OnGoodClickListener() {
            @Override
            public void onClick(String imgs, String title, Long price, Long itemId, Long muserId) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                ItemList itemList = new ItemList();
                itemList.setImage(imgs);
                itemList.setTitle(title);
                itemList.setPrice(price.intValue());
                itemList.setId(itemId + "");
                itemList.setMuser_id(muserId);
                intent.putExtra("item", itemList);
                startActivity(intent);
            }
        });

        adapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                myPresent.getIndexMore(page);
                page++;
            }
        });
        swiperefreshLayout.setOnRefreshListener(listener);

        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int y = getScollYDistance();
                if (y <= 0) {
                    //显示刷新条
                    //progressBar.setVisibility(View.VISIBLE);
                    //设置标题的背景颜色
                    toolbar.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                } else if (y > 0 && y <= 500) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                    float scale = (float) y / 500;
                    float alpha = (255 * scale);
                    //title.setTextColor(Color.argb((int) alpha, 1, 24, 28));
                    //getResources().getColor(R.color.colorPrimary, null);
                    toolbar.setBackgroundColor(Color.argb((int) alpha, 63, 81, 181));
                    header.setBackgroundColor(Color.argb((int) alpha, 63, 81, 181));
                    if (imgUp.getVisibility() == View.VISIBLE) {
                        imgUp.setVisibility(View.GONE);
                    }
                } else {
                    //滑动到banner下面设置普通颜色
                    toolbar.setBackgroundColor(Color.parseColor("#ff3F51B5"));
                    header.setBackgroundColor(Color.parseColor("#ff3F51B5"));
                    if (imgUp.getVisibility() == View.GONE) {
                        imgUp.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        gridViewIndexAdapter.setOnItemIndexClickListener(new GridViewIndexAdapter.OnItemIndexClickListener() {
            @Override
            public void onClick(int position, String title) {
                Intent intent = new Intent(getActivity(), HomeItemDetailActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }


    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvHome.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getBottom();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    public SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            myPresent.getDatas();
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void updateView() {
        //  myPresent.getDatas();
        swiperefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swiperefreshLayout.setRefreshing(true);
                listener.onRefresh();
            }
        });
    }

    /*
    * 初始化toolbar
    * */
    @Override
    protected void initToolbar() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.text_search, R.id.img_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_search:
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.img_up:
                rvHome.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void loadAdData(final List<AdItem> list) {
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, list).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                ItemList itemList = new ItemList();
                itemList.setMuser_id(list.get(position).getMuserId());
                itemList.setId(list.get(position).getId() + "");
                itemList.setTitle(list.get(position).getTitle());
                itemList.setPrice(list.get(position).getPrice().intValue());
                itemList.setImage(list.get(position).getImage());
                intent.putExtra("item", itemList);
                startActivity(intent);
            }
        }).setPageIndicator(indication).startTurning(3000).setCanLoop(true);
        if (adapter.getHeaderView() == null) adapter.addHeaderView(view);


    }

    @Override
    public void load(List<IndexMulEntity> list) {
        if (swiperefreshLayout != null && swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(false);
        }
        adapter.updateItems(list);
    }

    @Override
    public void fail(String msg) {
        showToast(msg);
        if (swiperefreshLayout != null && swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void loadMore(List<IndexMulEntity> list) {
        adapter.loadComplete();
        adapter.addItems(list);
    }

    @Override
    public void noMore() {
        adapter.loadComplete();
        adapter.noMoreData();
    }

    @Override
    public void onGlobalLayout() {
        //获取轮播图view的高度
        height = convenientBanner.getMeasuredHeight();
    }


    public GridView getGridView() {
        GridView gridView = (GridView) LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
        gridViewIndexAdapter = new GridViewIndexAdapter(mTitle, mContext);
        gridView.setAdapter(gridViewIndexAdapter);
        return gridView;
    }

    public class LocalImageHolderView implements Holder<AdItem> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, AdItem adItem) {
            ImageLoader.getInstance().displayImage(context, adItem.getDc(), imageView);
        }
    }

}
