package com.example.lenovo.taoshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.lenovo.taoshop.adapter.ListViewDetailAdapter;
import com.example.lenovo.taoshop.adapter.ViewPagerImageAdapter;
import com.example.lenovo.taoshop.bean.common.ItemDetail;
import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.TbComments;
import com.example.lenovo.taoshop.injector.components.DaggerGoodComponent;
import com.example.lenovo.taoshop.injector.modules.GoodModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.GoodPresent;
import com.example.lenovo.taoshop.mvp.view.IGoodView;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.widget.EmptyLayout;
import com.hankkin.library.CircleImageView;
import com.hankkin.library.GradationScrollView;
import com.hankkin.library.NoScrollListView;
import com.hankkin.library.ScrollViewContainer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class GoodDetailActivity extends BaseActivity<GoodPresent> implements GradationScrollView.ScrollViewListener, IGoodView {
    @Bind(R.id.scrollview)
    GradationScrollView scrollView;
    /* @Bind(R.id.iv_good_detai_img)
     ViewPager vp_detail;//轮播图*/
    @Bind(R.id.iv_good_detai_img)
    ConvenientBanner vp_detail;//轮播图
    @Bind(R.id.ll_good_detail)
    RelativeLayout llTitle;
    @Bind(R.id.ll_offset)
    LinearLayout llOffset;

    @Bind(R.id.sv_container)
    ScrollViewContainer container;
    @Bind(R.id.tv_good_detail_title_good)
    TextView tvGoodTitle;

    @Bind(R.id.nlv_good_detial_imgs)
    NoScrollListView nlvImgs;//图片详情

    @Bind(R.id.text_title)
    TextView textTitle;
    @Bind(R.id.text_price)
    TextView textPrice;

    //评论区
    @Bind(R.id.user_img)
    CircleImageView userImg;//用户头像
    @Bind(R.id.text_username)
    TextView textUsername;//用户名
    @Bind(R.id.text_time)
    TextView textTime;//评论时间
    @Bind(R.id.text_comment)
    TextView textComment;//评论内容
    @Bind(R.id.tv_talent_detail_open)
    TextView tvTalentDetailOpen;//查看更多评论
    @Bind(R.id.linearlayout_comment)
    LinearLayout linearlayoutComment;
    @Bind(R.id.tv_good_detail_buy)
    TextView tvGoodDetailBuy;
    @Bind(R.id.linearlayout_star)
    LinearLayout linearlayoutStar;
    @Bind(R.id.iv_good_detai_back)
    ImageView ivGoodDetaiBack;

    private int height;
    private int width;
    private ViewPagerImageAdapter viewPagerImageAdapter;
    private ItemList item;
    private long itemId;
    private ListViewDetailAdapter adapter;
    private int[] indication = new int[]{R.mipmap.icon_indication_normal, R.mipmap.icon_indication_select};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {
        ViewTreeObserver vto = vp_detail.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = vp_detail.getHeight();

                scrollView.setScrollViewListener(GoodDetailActivity.this);
            }
        });
        //监听查看更多
        tvTalentDetailOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开评论页面
                Intent intent = new Intent();
                intent.putExtra("itemId", item.getId());
                intent.putExtra("muserId", item.getMuser_id());
                intent.setClass(GoodDetailActivity.this, CommentsActivity.class);
                invoke(false, intent);
            }
        });
        //图片详情的图片点击事件
        adapter.setOnImageViewClickListener(new ListViewDetailAdapter.OnImageViewClickListener() {
            @Override
            public void onClick(List<String> urls, int position) {
                PhotoShowActivity.launch(GoodDetailActivity.this, urls, position);
            }
        });
    }

    @Override
    protected void inject() {
        DaggerGoodComponent.builder().goodModule(new GoodModule()).build().inject(this);
        myPresent.attachView(this);
    }

    @Override
    protected void initView() {
        item = (ItemList) getIntent().getSerializableExtra("item");
        itemId = Long.parseLong(item.getId());

        adapter = new ListViewDetailAdapter(this);
        nlvImgs.setAdapter(adapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void updateView() {
        start();
        myPresent.getDetail(itemId);//加载数据
    }


    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            llTitle.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvGoodTitle.setTextColor(Color.argb((int) alpha, 1, 24, 28));
            llTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        } else {    //滑动到banner下面设置普通颜色
            llTitle.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
        }
    }

    @Override
    public void show(ItemDetail itemDetail) {
        end();
        //填充数据
        setDataView(itemDetail);
    }

    //填充数据
    private void setDataView(ItemDetail itemDetail) {
        //设置轮播图
        if (!item.getImage().equals("")) {
            String[] imgs = item.getImage().split(",");
            final List<String> list = Arrays.asList(imgs);
            /*List<ImageView> imgsList = new ArrayList<>();
            for (String img : list
                    ) {
                ImageView imageView = new ImageView(this);
                ImageLoader.getInstance().displayImage(this, img, imageView);
                imgsList.add(imageView);
            }*/
            // viewPagerImageAdapter = new ViewPagerImageAdapter(imgsList);
            // vp_detail.setAdapter(viewPagerImageAdapter);

            vp_detail.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
                @Override
                public LocalImageHolderView createHolder() {
                    return new LocalImageHolderView();
                }
            }, list).setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    PhotoShowActivity.launch(GoodDetailActivity.this, list, position);
                }
            }).setPageIndicator(indication).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        }
        //设置商品信息
        //设置商品标题
        textTitle.setText(item.getTitle());
        //设置商品价格
        textPrice.setText(String.valueOf(item.getPrice()));

        //评论区
        TbComments tc = itemDetail.getTbComment();
        if (tc != null) {
            //用户头像
            ImageLoader.getInstance().displayImage(this, tc.getPic(), userImg, R.mipmap.user);
            textComment.setText(tc.getComments());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String time = simpleDateFormat.format(new Date(tc.getTime()));
            textTime.setText(time);
            textUsername.setText(tc.getBuyername());
            //星星
            int stars = Integer.parseInt(tc.getDegree());
            for (int i = 1; i < stars; i++) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 0, 0, 0);
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.mipmap.da_ren_list_star);
                linearlayoutStar.addView(imageView, layoutParams);
            }
        } else {
            linearlayoutComment.setVisibility(View.GONE);
            tvTalentDetailOpen.setText("暂无评论");
        }

        //图片详情
        if (itemDetail.getContent() != null) {
            List<String> imgs = getImages(itemDetail.getContent());
            adapter.addItems(imgs);
        }
    }


    //获取图片
    public List<String> getImages(String content) {
        List<String> images = new ArrayList<>();
        //解析html
        Document document = Jsoup.parse(content);
        Elements es = document.select("img");
        for (Element element :
                es) {
            String url = "http:" + element.attr("data-lazyload");
            images.add(url);
        }
        return images;
    }

    @Override
    public void fail(String msg) {
        showToast(msg);
        start();
        error(new EmptyLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                start();
                myPresent.getDetail(itemId);
            }
        });
    }


    @OnClick({R.id.tv_good_detail_buy, R.id.iv_good_detai_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_good_detail_buy:
                Intent intent = new Intent(GoodDetailActivity.this, OrderDefActivity.class);
                intent.putExtra("item", item);
                invoke(false, intent);
                break;
            case R.id.iv_good_detai_back:
                finish();
                break;
        }
    }

    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            ImageLoader.getInstance().displayImage(context, data, imageView);
        }
    }

}
