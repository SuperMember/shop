package com.example.lenovo.taoshop;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.taoshop.bean.common.ItemList;
import com.example.lenovo.taoshop.bean.common.TbComments;
import com.example.lenovo.taoshop.bean.common.TbCommentsResult;
import com.example.lenovo.taoshop.bean.common.UserEntity;
import com.example.lenovo.taoshop.injector.components.DaggerCommentComponent;
import com.example.lenovo.taoshop.injector.modules.CommentModule;
import com.example.lenovo.taoshop.mvp.base.BaseActivity;
import com.example.lenovo.taoshop.mvp.present.CommentPresent;
import com.example.lenovo.taoshop.mvp.view.ICommentView;
import com.example.lenovo.taoshop.utils.ImageLoader;
import com.example.lenovo.taoshop.utils.SharedPreferencesUtil;
import com.example.lenovo.taoshop.widget.DegreeLinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class OrderCommentActivity extends BaseActivity<CommentPresent> implements ICommentView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_good_pic)
    ImageView ivGoodPic;
    @Bind(R.id.tv_intro)
    TextView tvIntro;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.et_comments)
    EditText etComments;//内容
    @Bind(R.id.submit)
    Button submit;//提交
    @Bind(R.id.ll_dec)
    DegreeLinearLayout llDec;
    @Bind(R.id.ll_send)
    DegreeLinearLayout llSend;
    @Bind(R.id.ll_service)
    DegreeLinearLayout llService;
    @Bind(R.id.ll_logistics)
    DegreeLinearLayout llLogistics;

    private UserEntity userEntity;
    private TbComments tbComments;
    private ItemList itemList;
    private ProgressDialog dialog;
    private int Decdegree = 0;
    private int Servicedegree = 0;
    private int Senddegree = 0;
    private int Logisticddegree = 0;
    private long orderId;

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
        //描述相符
        llDec.setOnDegreeClickListener(new DegreeLinearLayout.OnDegreeClickListener() {
            @Override
            public void onClick(int index) {
                Decdegree = index;
            }
        });
        //发货
        llSend.setOnDegreeClickListener(new DegreeLinearLayout.OnDegreeClickListener() {
            @Override
            public void onClick(int index) {
                Senddegree = index;
            }
        });
        //服务
        llService.setOnDegreeClickListener(new DegreeLinearLayout.OnDegreeClickListener() {
            @Override
            public void onClick(int index) {
                Servicedegree = index;
            }
        });
        //物流
        llLogistics.setOnDegreeClickListener(new DegreeLinearLayout.OnDegreeClickListener() {
            @Override
            public void onClick(int index) {
                Logisticddegree = index;
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

        initToolbar("发表评论", true, true, toolbar);
        userEntity = SharedPreferencesUtil.getInstance(this).getInfo();
        itemList = (ItemList) getIntent().getSerializableExtra("item");
        orderId = getIntent().getLongExtra("order", 0);//订单号

        dialog = new ProgressDialog(this);
        dialog.setTitle("提交");
        dialog.setMessage("正在提交中...");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_comment;
    }

    @Override
    protected void updateView() {
        //设置商品信息
        tvIntro.setText(itemList.getTitle());
        tvPrice.setText(itemList.getPrice() + "");
        ImageLoader.getInstance().displayImage(this, itemList.getImage().split(",")[0], ivGoodPic);
    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                tbComments = new TbComments();
                String comment = etComments.getText().toString().trim();
                tbComments.setComments(comment.equals("") ? "该买家没有进行评论" : comment);
                tbComments.setSoldid(itemList.getMuser_id());
                tbComments.setBuyername(userEntity.getUsername());
                tbComments.setItemid(Long.parseLong(itemList.getId()));
                tbComments.setMuserId(userEntity.getId());
                tbComments.setPic(userEntity.getImg());
                tbComments.setGoodname(itemList.getCidname());
                //综合
                if (Decdegree == 0) {
                    showToast("请为描述相符评分");
                    break;
                }
                if (Logisticddegree == 0) {
                    showToast("请为物流服务评分");
                    break;
                }
                if (Senddegree == 0) {
                    showToast("请为发货速度评分");
                    break;
                }
                if (Servicedegree == 0) {
                    showToast("请为服务态度评分");
                    break;
                }
                int count = (Decdegree + Servicedegree + Senddegree + Logisticddegree) / 4;
                tbComments.setDegree(count + "");
                //提交评论
                dialog.show();
                myPresent.submitComments(tbComments, orderId);
                break;
        }

    }

    @Override
    public void show(List<TbCommentsResult> list) {

    }

    @Override
    public void fail(String msg) {
        dialog.dismiss();
        showToast(msg);
    }

    @Override
    public void getMore(List<TbCommentsResult> list) {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void empty() {

    }

    @Override
    public void submit(String msg) {
        dialog.dismiss();
        showToast(msg);
        finish();
    }
}
