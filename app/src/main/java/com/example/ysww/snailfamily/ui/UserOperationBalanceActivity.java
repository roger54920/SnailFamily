package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.MyCccountGrandTotalBean;
import com.example.ysww.snailfamily.bean.MyCccountMessageBean;
import com.example.ysww.snailfamily.custom.RadioGroup;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.MyCccountOperationView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.MyCccountOperationPresenter;
import com.example.ysww.snailfamily.utils.AcquisitionTimeUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.MoveLocationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 我的账户
 */
public class UserOperationBalanceActivity extends AutoLayoutActivity implements MyCccountOperationView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.recharge_btn)
    Button rechargeBtn;
    @InjectView(R.id.recharge_detail)
    RadioButton rechargeDetail;
    @InjectView(R.id.consume_detail)
    RadioButton consumeDetail;
    @InjectView(R.id.group)
    RadioGroup group;
    @InjectView(R.id.account_balance)
    TextView accountBalance;
    @InjectView(R.id.grand_total_rv)
    RecyclerView grandTotalRv;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.no_content)
    TextView noContent;
    @InjectView(R.id.withdraw_btn)
    Button withdrawBtn;
    private MyCccountOperationPresenter myCccountOperationPresenter = new MyCccountOperationPresenter();//我的账户信息
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private int pageno = 1; //分页
    private int pagesize = 3;//每页条数
    private CommonAdapter<MyCccountGrandTotalBean.PageBean.ListBean> commonAdapter;
    private List<MyCccountGrandTotalBean.PageBean.ListBean> grandTotalList;
    private LinearLayoutManager linearLayoutManager;//RV显示方式
    private String requestType = "recharge";//刷新请求类型
    private String displayCredit;//账户余额
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operation_balance);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }

    private void initViews() {
        rechargeDetail.setChecked(true);
        requestGetAccountResult();
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestRechargeDetailListResult();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.recharge_detail:
                        pageno = 1;
                        requestType = "recharge";
                        noContent.setText("暂无累计充值信息");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestRechargeDetailListResult();
                        break;
                    case R.id.consume_detail:
                        pageno = 1;
                        requestType = "consume";
                        noContent.setText("暂无累计消费信息");
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestConsumeDetailListResult();
                        break;
                }
            }
        });
        //添加头部
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);
        refreshLayout.setHeaderHeight(50);
        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);
        linearLayoutManager = new LinearLayoutManager(this);
        grandTotalRv.setLayoutManager(linearLayoutManager);
    }

    /**
     * 初始化账户信息
     */
    private void requestGetAccountResult() {
        new OkHttpResolve(this);
        myCccountOperationPresenter.attach(this);
        myCccountOperationPresenter.getAccountResult(this);
    }

    /**
     * 初始化充值记录
     */
    private void requestRechargeDetailListResult() {
        JSONObject json = new JSONObject();
        try {
            JSONObject page = new JSONObject();
            page.put("pageNo", pageno);
            page.put("pageSize", pagesize);
            json.put("pageInfo", page);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new OkHttpResolve(this);
        myCccountOperationPresenter.attach(this);
        myCccountOperationPresenter.postRechargeDetailListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 初始化消费记录
     */
    private void requestConsumeDetailListResult() {
        JSONObject json = new JSONObject();
        try {
            JSONObject page = new JSONObject();
            page.put("pageNo", pageno);
            page.put("pageSize", pagesize);
            json.put("pageInfo", page);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new OkHttpResolve(this);
        myCccountOperationPresenter.attach(this);
        myCccountOperationPresenter.postConsumeDetailListResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        grandTotalRv.setLayoutManager(new LinearLayoutManager(this));
        commonAdapter = new CommonAdapter<MyCccountGrandTotalBean.PageBean.ListBean>(this, R.layout.grand_total_item, grandTotalList) {
            @Override
            protected void convert(ViewHolder holder, MyCccountGrandTotalBean.PageBean.ListBean listBean, int position) {
                try {
                    String time = AcquisitionTimeUtil.longToString(listBean.getTransactionTime(), "yyyy-MM-dd HH:mm:ss");
                    holder.setText(R.id.datetime, time + (listBean.getTransactionChannel()));
                    holder.setText(R.id.money, listBean.getTransactionAmount() + "元");
                    if (position == grandTotalList.size()-1) {
                        holder.setVisible(R.id.line, false);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        grandTotalRv.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                refreshRV();
                SkipIntentUtil.rvRefreshTimeout(UserOperationBalanceActivity.this, refreshLayout, 0);

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                refreshRV();
                SkipIntentUtil.rvRefreshTimeout(UserOperationBalanceActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    /**
     * 刷新列表
     */
    private void refreshRV() {
        switch (requestType) {
            case "recharge":
                requestRechargeDetailListResult();
                break;
            case "consume":
                requestConsumeDetailListResult();
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.return_arrows, R.id.recharge_btn,R.id.withdraw_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                returnHomeMe();
                break;
            case R.id.recharge_btn:
                skipBalanceRecharge("recharge");
                break;
            case R.id.withdraw_btn:
                skipBalanceRecharge("withdraw");
                break;
        }
    }

    /**
     * 跳转到充值页面
     */
    private void skipBalanceRecharge(String source){
        intent = new Intent(this,UserOperationBalanceRechargeActivity.class);
        intent.putExtra("source",source);
        if(!TextUtils.isEmpty(displayCredit) && source.equals("withdraw")){
            intent.putExtra("displayCredit",displayCredit);
        }
        startActivity(intent);
    }

    /**
     * 返回首页用户页面
     */
    private void returnHomeMe(){
        intent = new Intent(this,BottomNavigationMenuActivity.class);
        intent.putExtra("source","fragment_me");
        startActivity(intent);
        finish();
    }
    @Override
    public void onGetAccountFinish(Object o) {
        MyCccountMessageBean cccountMessageBean = (MyCccountMessageBean) o;
        MyCccountMessageBean.AccountBean account = cccountMessageBean.getAccount();
        if (account != null) {
            displayCredit = account.getAmount()+"";
            accountBalance.setText("账户余额：" + account.getAmount() + "元");
            rechargeDetail.setText("累计充值：" + account.getIncome() + "元");
            consumeDetail.setText("累计消费：" + account.getPay() + "元");
        }
    }

    @Override
    public void onGetRechargeDetailListFinish(Object o) {
        setRVFinish((MyCccountGrandTotalBean) o);
    }

    @Override
    public void onGetConsumeDetailListFinish(Object o) {
        setRVFinish((MyCccountGrandTotalBean) o);
    }

    /**
     * 请求完成设置列表参数
     *
     * @param myCccountGrandTotalBean
     */
    private void setRVFinish(MyCccountGrandTotalBean myCccountGrandTotalBean) {
        if (myCccountGrandTotalBean != null) {
            if (grandTotalList == null) {
                grandTotalList = new ArrayList<>();
            }
            List<MyCccountGrandTotalBean.PageBean.ListBean> list = myCccountGrandTotalBean.getPage().getList();
            if (myCccountGrandTotalBean.getPage().getPageNo() == 1) {
                if (myCccountGrandTotalBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    grandTotalList.clear();
                    grandTotalList = list;
                    setUpParameters();
                    if (myCccountGrandTotalBean.getPage().getCount() > 3) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    noContent.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }
            } else {
                if (list != null) {
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, grandTotalRv, grandTotalList.size());
                    for (int i = 0; i < list.size(); i++) {
                        grandTotalList.add(list.get(i));
                    }
                    setUpParameters();
                    if (list.size() == 3) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);
                }
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
           returnHomeMe();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        myCccountOperationPresenter.dettach();
    }
}
