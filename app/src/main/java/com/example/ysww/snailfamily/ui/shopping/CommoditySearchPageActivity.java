package com.example.ysww.snailfamily.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.shopping.CommonSearchBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.shopping.CommonSearchView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.shopping.CommonSearchPresenter;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 商品搜索页面
 */
public class CommoditySearchPageActivity extends BaseActivity implements CommonSearchView {
    @InjectView(R.id.input_commodity_name)
    EditText inputCommodityName;
    @InjectView(R.id.hot_search_rv)
    RecyclerView hotSearchRv;
    @InjectView(R.id.history_search_rv)
    RecyclerView historySearchRv;
    @InjectView(R.id.clear_history_record_btn)
    Button clearHistoryRecordBtn;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private CommonAdapter<String> hotSearchAdapter;
    private CommonAdapter<String> historySearchAdapter;
    private List<String> list;
    private CommonSearchPresenter commonSearchPresenter = new CommonSearchPresenter();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_search_page);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);

        initData();
        initView();
    }

    private void initView() {
        requestCommonSearch();

        hotSearchRv.setLayoutManager(new GridLayoutManager(this, 3));
        historySearchRv.setLayoutManager(new GridLayoutManager(this, 3));


        historySearchAdapter = new CommonAdapter<String>(this, R.layout.shopping_search_page_layout, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.search_commodity_type_tv, s);
            }
        };
        historySearchRv.setAdapter(historySearchAdapter);
    }

    /**
     * 初始化 热门搜索
     */
    private void requestCommonSearch() {
        new OkHttpResolve(this);
        commonSearchPresenter.attach(this);
        commonSearchPresenter.postJsonCommonSearchResult("", this, lazyLoadProgressDialog);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("大西瓜" + i);
        }
    }

    @OnClick({R.id.return_arrows, R.id.shopping_seach_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.shopping_seach_btn:
                String dityName = inputCommodityName.getText().toString();
                if(!TextUtils.isEmpty(dityName)){
                    skipCommoditySearchResultList(dityName);
                }else{
                    SkipIntentUtil.skipIntent(this, CommoditySearchResultListActivity.class);
                }
                break;
        }
    }

    @Override
    public void onCommonSearchFinish(Object o) {
        CommonSearchBean commonSearchBean = (CommonSearchBean) o;
        if (commonSearchBean != null) {
            final List<String> commonSearchList = commonSearchBean.getCommonSearchList();
            hotSearchAdapter = new CommonAdapter<String>(this, R.layout.shopping_search_page_layout, commonSearchList) {
                @Override
                protected void convert(ViewHolder holder, String commonSearchBean, int position) {
                    holder.setText(R.id.search_commodity_type_tv, commonSearchBean);
                }
            };
            hotSearchAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    skipCommoditySearchResultList(commonSearchList.get(position));
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            hotSearchRv.setAdapter(hotSearchAdapter);
        }
    }

    /**
     * 跳转搜索详情页面
     * @param search
     */
    private void skipCommoditySearchResultList(String search){
        intent = new Intent(CommoditySearchPageActivity.this, CommoditySearchResultListActivity.class);
        intent.putExtra("search",search);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        commonSearchPresenter.dettach();
    }
}
