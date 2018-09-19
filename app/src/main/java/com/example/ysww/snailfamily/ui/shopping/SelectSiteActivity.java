package com.example.ysww.snailfamily.ui.shopping;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.WorkstationListBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.ShoppingSelectSiteDialog;
import com.example.ysww.snailfamily.mvp.shopping.WorkstationListView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.WorkstationListPresenter;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 选择站点
 */
public class SelectSiteActivity extends BaseActivity implements WorkstationListView {

    @InjectView(R.id.input_site_name_address)
    EditText inputSiteNameAddress;
    @InjectView(R.id.nearest_me_rv)
    RecyclerView nearestMeRv;
    @InjectView(R.id.other_sites_rv)
    RecyclerView otherSitesRv;
    private CommonAdapter<WorkstationListBean.WorkstationList> nearestMeAdapter;
    private CommonAdapter<String> otherSitesAdapter;
    private WorkstationListPresenter workstationListPresenter = new WorkstationListPresenter();//获取蜗牛站列表
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_site);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
        initViews();
    }
    private void initViews(){
        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        requestWorkstationList();
        inputSiteNameAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“放大镜”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    String seek = inputSiteNameAddress.getText().toString();
                    SkipIntentUtil.toastShow(SelectSiteActivity.this,seek);
//                    if (!TextUtils.isEmpty(seek)) {
//                        lazyLoadProgressDialog.show();
//                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
//                        requesSearchCourierNumber("{\"search\":\"" + seek + "\"}");
//                    } else {
//                        if (SkipIntentUtil.KeyBoard(search) == true) {
//                            imm.showSoftInput(search, InputMethodManager.SHOW_FORCED);
//                        }
//                        SkipIntentUtil.toastShow(getActivity(), "快递单号不能为空！");
//                    }

                    return true;
                }
                return false;
            }
        });

    }
    /**
     * 获取蜗牛站列表
     */
    private void requestWorkstationList() {
        new OkHttpResolve(this);
        workstationListPresenter.attach(this);
        workstationListPresenter.postJsonWorkstationListResult("", this, lazyLoadProgressDialog);
    }
    private void initAdapters(final List<WorkstationListBean.WorkstationList> workstationList){
        nearestMeRv.setLayoutManager(new FullyLinearLayoutManager(this));
        otherSitesRv.setLayoutManager(new FullyLinearLayoutManager(this));

        nearestMeAdapter = new CommonAdapter<WorkstationListBean.WorkstationList>(this,R.layout.shopping_select_site_item,workstationList) {
            @Override
            protected void convert(ViewHolder holder, WorkstationListBean.WorkstationList workstation, int position) {
                holder.setText(R.id.site_name,workstation.getStationName());
                holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
            }
        };
        nearestMeRv.setAdapter(nearestMeAdapter);
        nearestMeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                WorkstationListBean.WorkstationList workstation = workstationList.get(position);
                String remarks = workstation.getRemarks();
               String [] split = remarks.split(",");
                if (split.length == 7) {
                    selectSiteDialog(workstation.getStationName(),split[3]+split[4]+split[5]+split[6],workstation.getId());
                } else if (split.length == 6) {
                    selectSiteDialog(workstation.getStationName(),split[2]+split[3]+split[4]+split[5],workstation.getId());
                } else if (split.length == 4) {
                    selectSiteDialog(workstation.getStationName(),split[3],workstation.getId());
                } else if (split.length == 3) {
                    selectSiteDialog(workstation.getStationName(),split[2],workstation.getId());
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


//        otherSitesAdapter = new CommonAdapter<String>(this,R.layout.shopping_select_site_item,list) {
//            @Override
//            protected void convert(ViewHolder holder, String s, int position) {
//                holder.setBackgroundRes(R.id.ll_content, R.drawable.customer_selector);
//            }
//        };
//        otherSitesRv.setAdapter(otherSitesAdapter);
//        otherSitesAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                selectSiteDialog("墨有点儿","大风景区");
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
    }
    /**
     * 站点选择弹窗
     * @param siteName
     * @param siteDetailsAddress
     */
    private void selectSiteDialog(String siteName, String siteDetailsAddress, final String workstationId){
        ShoppingSelectSiteDialog.Builder builder = new ShoppingSelectSiteDialog.Builder(SelectSiteActivity.this,siteName,siteDetailsAddress);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(SelectSiteActivity.this,ShoppingScanQRCodeActivity.class);
                intent.putExtra("workstationId",workstationId);
                startActivity(intent);
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
    @OnClick(R.id.cancel)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onWorkstationListFinish(Object o) {
        WorkstationListBean workstationListBean = (WorkstationListBean) o;
        if(workstationListBean!=null){
            initAdapters(workstationListBean.getWorkstationList());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        workstationListPresenter.dettach();
    }
}
