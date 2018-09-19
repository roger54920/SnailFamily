package com.example.ysww.snailfamily.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.CommonSiteBean;
import com.example.ysww.snailfamily.bean.SendSaveBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.SystemPromptDialog;
import com.example.ysww.snailfamily.mvp.CommonSiteView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.CommonSitePresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.MoveLocationUtil;
import com.example.ysww.snailfamily.utils.NetUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 常用地址
 */
public class CommonSiteActivity extends BaseActivity implements CommonSiteView {

    @InjectView(R.id.common_rv)
    RecyclerView commonRv;
    @InjectView(R.id.add_address_btn)
    Button addAddressBtn;
    @InjectView(R.id.refresh)
    TwinklingRefreshLayout refreshLayout;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.no_content)
    TextView noContent;

    private Intent intent;

    private List<CommonSiteBean.PageBean.ListBean> commonSiteBeanList;//寄件列表数据
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private int pageno = 1; //分页
    private int pagesize = 10;//每页条数

    private CommonAdapter<CommonSiteBean.PageBean.ListBean> commonAdapter;
    private CommonSitePresenter commonSitePresenter = new CommonSitePresenter();//常用地址

    private String[] split;//地址分割
    private LinearLayoutManager linearLayoutManager;
    private String source;//页面来源
    private String item_id;//选中到寄件页面的Item的Id

    private SendSaveBean.SendSaveParameter sendSaveParameter;  //寄件保存的参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_site);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        } else {
            Constants.NETCONNECT =true;
        }
        initViews();
    }

    /**
     * 初始化数据
     */
    private void initViews() {

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        getCommonSiteResult();
        //添加头部
        SinaRefreshView headerView = new SinaRefreshView(this);
        headerView.setArrowResource(R.drawable.arrow);
        headerView.setTextColor(0xff745D5C);
        refreshLayout.setHeaderView(headerView);

        //添加底部
        refreshLayout.setOverScrollBottomShow(false);
        refreshLayout.setAutoLoadMore(true);

        linearLayoutManager = new LinearLayoutManager(this);
        commonRv.setLayoutManager(linearLayoutManager);
    }


    /**
     * 得到常用地址
     */
    private void getCommonSiteResult() {
        JSONObject json = new JSONObject();
        try {
            json.put("pageInfo", new JSONObject().put("pageNo", pageno).put("pageSize", pagesize));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(this);
        commonSitePresenter.attach(this);
        commonSitePresenter.getJsonCommonSiteResult(json.toString(), this, lazyLoadProgressDialog);
    }

    /**
     * 设置默认地址
     */
    private void setDefaultCommonSiteResult(String id) {
        new OkHttpResolve(this);
        commonSitePresenter.attach(this);
        commonSitePresenter.setDefaultCommonSiteResult("{\"id\":\"" + id + "\"}", this, lazyLoadProgressDialog);
    }

    /**
     * 删除常用地址
     */
    private void deleteCommonSiteResult(String id) {
        new OkHttpResolve(this);
        commonSitePresenter.attach(this);
        commonSitePresenter.deleteCommonSiteResult("{\"id\":\"" + id + "\"}", this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.add_address_btn, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_address_btn:
                source = getIntent().getStringExtra("source");
                if (source != null) {
                    intent = new Intent(CommonSiteActivity.this, NewAddAddressActivity.class);
                    switch (source) {
                        case "send_contacts":
                            intent.putExtra("source", "send_contacts");
                            startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                            break;
                        case "send_address":
                            intent.putExtra("source", "send_address");
                            startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                            break;
                        case "fragment_me":
                            intent.putExtra("source", "fragment_me");
                            startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                            break;
                        default:
                            intent.putExtra("source", "common_site_add_address");
                            startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                            break;
                    }
                }
                break;
            case R.id.return_arrows:
                resultSource();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getStringExtra("source") != null) {
            pageno = 1;
            lazyLoadProgressDialog.show();
            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
            getCommonSiteResult();
            source = data.getStringExtra("source");
        }
    }

    /**
     * 返回指定页面
     */
    private void resultSource() {
        intent = new Intent();
        source = getIntent().getStringExtra("source");
        if (source != null) {
            switch (source) {
                case "send_contacts":
                    setResult(Constants.SUBSCRIPT_ZER0, intent);
                    finish();
                    break;
                case "send_address":
                    setResult(Constants.SUBSCRIPT_ONE, intent);
                    finish();
                    break;
                case "fragment_me":
                    intent = new Intent(this, BottomNavigationMenuActivity.class);
                    intent.putExtra("source", "fragment_me");
                    startActivity(intent);
                    finish();
                    break;
                case "placeOrder":
                    setResult(Constants.SUBSCRIPT_ONE, intent);
                    finish();
                    break;

            }
        }
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters() {
        commonAdapter = new CommonAdapter<CommonSiteBean.PageBean.ListBean>(this, R.layout.common_site_item, commonSiteBeanList) {
            @Override
            protected void convert(final ViewHolder holder, final CommonSiteBean.PageBean.ListBean commonSiteBean, final int position) {
                source = getIntent().getStringExtra("hide");
                if (getIntent().getStringExtra("hide") != null) {
                    holder.setVisible(R.id.buttom, false);
                }
                holder.setText(R.id.name, commonSiteBean.getName());
                holder.setText(R.id.ipone, commonSiteBean.getPhone());
                String remarks = commonSiteBean.getRemarks();
                remarks = remarks.replace(",", "");
                holder.setText(R.id.address, remarks);
                final String ifDefault = commonSiteBean.getIfDefault();
                if (ifDefault != null) {
                    if (ifDefault.equals("1")) {
                        holder.setChecked(R.id.radio, true).setText(R.id.radio, "默认地址");
                    } else {
                        holder.setChecked(R.id.radio, false).setText(R.id.radio, "设为默认");
                    }
                }
                holder.setOnClickListener(R.id.radio, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        setDefaultCommonSiteResult(commonSiteBean.getId());

                        for (int i = 0; i < commonSiteBeanList.size(); i++) {
                            commonSiteBeanList.get(i).setIfDefault("0");
                        }
                        commonSiteBeanList.get(position).setIfDefault("1");
                        commonAdapter.notifyDataSetChanged();
                    }
                });
                holder.setOnClickListener(R.id.compile, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(CommonSiteActivity.this, NewAddAddressActivity.class);
                        String type = commonSiteBean.getType();
                        intent.putExtra("compile", type);
                        intent.putExtra("source", "common_site_address");
                        intent.putExtra("id", commonSiteBean.getId());
                        intent.putExtra("ifDefault", ifDefault);
                        String remark = commonSiteBean.getRemarks();
                        if (remark != null) {
                            split = remark.split(",");
                        }
                        if (type.equals("1")) {
                            intent.putExtra("ifUserParcelNo", commonSiteBean.getIfUserParcelNo());
                            intent.putExtra("provincialCode", commonSiteBean.getProvincialCode());
                            intent.putExtra("cityCode", commonSiteBean.getCityCode());
                            intent.putExtra("areaCode", commonSiteBean.getAreaCode());
                            intent.putExtra("cellCode", commonSiteBean.getCellCode());
                            intent.putExtra("houseNumber", commonSiteBean.getHouseNumber());
                            intent.putExtra("unit", commonSiteBean.getUnit());
                            intent.putExtra("room", commonSiteBean.getRoom());
                            intent.putExtra("phone", commonSiteBean.getPhone());
                            intent.putExtra("parcelNo", commonSiteBean.getParcelNo());
                            if (split.length == 7) {
                                intent.putExtra("region", split[0] + "," + split[1] + "," + split[2]);
                                intent.putExtra("plot", split[3]);
                                intent.putExtra("house", split[4] + "," + split[5] + "," + split[6]);
                            } else if (split.length == 6) {
                                intent.putExtra("region", split[0] + "," + split[0] + "," + split[1]);
                                intent.putExtra("plot", split[2]);
                                intent.putExtra("house", split[3] + "," + split[4] + "," + split[5]);
                            }
                        } else {
                            intent.putExtra("name", commonSiteBean.getName());
                            intent.putExtra("phone", commonSiteBean.getPhone());
                            intent.putExtra("place", commonSiteBean.getPlace());
                            intent.putExtra("provincialCode", commonSiteBean.getProvincialCode());
                            intent.putExtra("cityCode", commonSiteBean.getCityCode());
                            intent.putExtra("areaCode", commonSiteBean.getAreaCode());
                            if (split.length == 4) {
                                intent.putExtra("allRegion", split[0] + "," + split[1] + "," + split[2]);
                                intent.putExtra("place", split[3]);
                            } else {
                                intent.putExtra("allRegion", split[0] + "," + split[0] + "," + split[1]);
                                intent.putExtra("place", split[2]);
                            }
                        }

                        startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                    }
                });
                holder.setOnClickListener(R.id.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDelete(commonSiteBean.getId());
                    }
                });
            }
        };
        commonRv.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                CommonSiteBean.PageBean.ListBean listBean = commonSiteBeanList.get(position);
                source = getIntent().getStringExtra("source");
                if (source != null) {
                    intent = new Intent();
                    switch (source) {
                        case "send_contacts":
                            intent.putExtra("source_return", "send_contacts_return");
                            resultSendCasesAffirm(listBean, intent);
                            setResult(Constants.SUBSCRIPT_ZER0, intent);
                            finish();
                            break;
                        case "send_address":
                            item_id = getIntent().getStringExtra("item_id");
                            if (item_id != null && !item_id.equals(listBean.getId())) {
                                intent.putExtra("source_return", "send_address_return");
                                resultSendCasesAffirm(listBean, intent);
                                setResult(Constants.SUBSCRIPT_ONE, intent);
                                finish();
                            } else {
                                SkipIntentUtil.toastShow(CommonSiteActivity.this, "寄件人和收件人不能相同！");
                            }
                            break;
                        case "placeOrder":
                            resultSendCasesAffirm(listBean, intent);
                            intent.putExtra("receiverId",listBean.getId());
                            setResult(Constants.SUBSCRIPT_ZER0, intent);
                            finish();
                            break;
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                pageno = 1;
                getCommonSiteResult();
                SkipIntentUtil.rvRefreshTimeout(CommonSiteActivity.this, refreshLayout, 0);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                pageno++;
                getCommonSiteResult();
                SkipIntentUtil.rvRefreshTimeout(CommonSiteActivity.this, refreshLayout, 0);
            }
        });
        SkipIntentUtil.rvRefreshSuccess(commonAdapter, refreshLayout);
    }

    /**
     * 返回给寄件页面数据
     */
    private void resultSendCasesAffirm(CommonSiteBean.PageBean.ListBean listBean, Intent intent) {
        intent.putExtra("name", listBean.getName());
        intent.putExtra("moblie", listBean.getPhone());
        intent.putExtra("item_id", listBean.getId());
        intent.putExtra("receiverprovincode", listBean.getProvincialCode());
        intent.putExtra("receivercitycode", listBean.getCityCode());
        intent.putExtra("receiverexpareacode", listBean.getAreaCode());
        String remarks = listBean.getRemarks();
        split = remarks.split(",");
        if (split.length == 7) {
            intent.putExtra("provincename", split[0]);
            intent.putExtra("cityname", split[1]);
            intent.putExtra("expareaname", split[2]);
            intent.putExtra("address", split[3] + split[4] + split[5] + split[6]);
        } else if (split.length == 6) {
            intent.putExtra("provincename", split[0]);
            intent.putExtra("cityname", split[0]);
            intent.putExtra("expareaname", split[1]);
            intent.putExtra("address", split[2] + split[3] + split[4] + split[5]);
        } else if (split.length == 4) {
            intent.putExtra("provincename", split[0]);
            intent.putExtra("cityname", split[1]);
            intent.putExtra("expareaname", split[2]);
            intent.putExtra("address", split[3]);
        } else if (split.length == 3) {
            intent.putExtra("provincename", split[0]);
            intent.putExtra("cityname", split[0]);
            intent.putExtra("expareaname", split[1]);
            intent.putExtra("address", split[2]);
        }
    }

    //点击删除按钮
    private void onClickDelete(final String id) {
        SystemPromptDialog.Builder builder = new SystemPromptDialog.Builder(this, "确定要删除地址吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                deleteCommonSiteResult(id);
            }
        });

        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().setCanceledOnTouchOutside(true);  //用户选择取消或者是点击屏幕空白部分时让dialog消失。
        builder.create().show();
    }

    @Override
    public void onCommonSiteListFinish(Object o) {
        CommonSiteBean commonSiteBean = (CommonSiteBean) o;
        if (commonSiteBean != null) {
            if (commonSiteBeanList == null) {
                commonSiteBeanList = new ArrayList<>();
            }
            List<CommonSiteBean.PageBean.ListBean> list = commonSiteBean.getPage().getList();
            if (commonSiteBean.getPage().getPageNo() == 1) {
                if (commonSiteBean.getPage().getCount() > 0 || list != null) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    noContent.setVisibility(View.GONE);
                    commonSiteBeanList.clear();
                    commonSiteBeanList = list;
                    setUpParameters();
                    if (commonSiteBean.getPage().getCount() > 10) {
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
                    MoveLocationUtil.MoveToPosition(linearLayoutManager, commonRv, commonSiteBeanList.size());
                    for (int i = 0; i < list.size(); i++) {
                        commonSiteBeanList.add(list.get(i));
                    }
                    setUpParameters();
                    if (list.size() == 10) {
                        refreshLayout.setEnableLoadmore(true);
                        refreshLayout.setAutoLoadMore(true);
                    } else {
                        refreshLayout.setEnableLoadmore(false);
                        refreshLayout.setAutoLoadMore(false);
                    }
                } else {
                    SkipIntentUtil.toastShow(CommonSiteActivity.this, "数据加载完成！");
                    refreshLayout.setEnableLoadmore(false);
                    refreshLayout.setAutoLoadMore(false);
                }
            }
        }
    }

    @Override
    public void onSetDefaultCommonSiteFinish(Object o) {
        CommonSiteBean commonSiteBean = (CommonSiteBean) o;
        if (commonSiteBean != null) {
        }
    }

    @Override
    public void onDeleteCommonSiteFinish(Object o) {
        CommonSiteBean commonSiteBean = (CommonSiteBean) o;
        if (commonSiteBean != null) {
            pageno = 1;
            getCommonSiteResult();
            commonAdapter.notifyDataSetChanged();

        }
    }
    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
            SkipIntentUtil.rvRefreshTimeout(CommonSiteActivity.this, refreshLayout, 1);
            Constants.NETCONNECT = false;
        } else {
            if (Constants.NETCONNECT == false) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                getCommonSiteResult();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            resultSource();
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
        commonSitePresenter.dettach();
    }
}
