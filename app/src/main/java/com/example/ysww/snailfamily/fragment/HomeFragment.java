package com.example.ysww.snailfamily.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.adapter.shopping.TodayDealAdapter;
import com.example.ysww.snailfamily.bean.HomeShowDataAdvertisementBean;
import com.example.ysww.snailfamily.bean.HomeShowDataHeadlineBean;
import com.example.ysww.snailfamily.bean.HomeShowDataMessageBean;
import com.example.ysww.snailfamily.bean.SearchCourierNumberBean;
import com.example.ysww.snailfamily.bean.shopping.AddReduceGoodsBean;
import com.example.ysww.snailfamily.bean.shopping.GetCommodityInfo;
import com.example.ysww.snailfamily.bean.shopping.GoodsListByCategoryBean;
import com.example.ysww.snailfamily.custom.FullyLinearLayoutManager;
import com.example.ysww.snailfamily.custom.GlideImageLoader;
import com.example.ysww.snailfamily.custom.MyScrollview;
import com.example.ysww.snailfamily.custom.UPMarqueeView;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.HomeShowDataView;
import com.example.ysww.snailfamily.mvp.SearchCourierNumberView;
import com.example.ysww.snailfamily.mvp.shopping.AddReduceGoodsView;
import com.example.ysww.snailfamily.mvp.shopping.GetCommodityInfoView;
import com.example.ysww.snailfamily.mvp.shopping.GoodsListByCategoryView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.HomeShowDataPresenter;
import com.example.ysww.snailfamily.presenter.SearchCourierNumberPresenter;
import com.example.ysww.snailfamily.presenter.shopping.AddReduceGoodsPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GetCommodityInfoPresenter;
import com.example.ysww.snailfamily.presenter.shopping.GoodsListByCategoryPresenter;
import com.example.ysww.snailfamily.ui.AddresseeActivity;
import com.example.ysww.snailfamily.ui.BottomNavigationMenuActivity;
import com.example.ysww.snailfamily.ui.MipcaActivityCapture;
import com.example.ysww.snailfamily.ui.SendCasesDisplayActivity;
import com.example.ysww.snailfamily.ui.shopping.CommodityDetailsPageActivity;
import com.example.ysww.snailfamily.ui.shopping.LogisticsInformationActivity;
import com.example.ysww.snailfamily.utils.AcquisitionTimeUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.lzy.okgo.OkGo;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ysww.snailfamily.R.id.accept_phone;

/**
 * Created by me-jie on 2017/4/8.
 * 我的主页
 */

public class HomeFragment extends Fragment implements GetCommodityInfoView,HomeShowDataView, SearchCourierNumberView, GoodsListByCategoryView,AddReduceGoodsView {
    @InjectView(R.id.logistics_rv)
    RecyclerView logisticsRv;
    @InjectView(R.id.myScrollview)
    MyScrollview myScrollview;
    @InjectView(R.id.consignee)
    LinearLayout consignee;
    @InjectView(R.id.send)
    LinearLayout send;
    @InjectView(R.id.shopping)
    LinearLayout shoppingll;
    @InjectView(R.id.pay_for_another)
    LinearLayout payForAnother;
    @InjectView(R.id.QRcode)
    ImageView QRcode;
    @InjectView(R.id.message)
    ImageView message;
    @InjectView(R.id.up_marquee_view)
    UPMarqueeView upMarqueeView;
    @InjectView(R.id.title)
    RelativeLayout title;
    @InjectView(R.id.search)
    EditText search;
    @InjectView(R.id.banner)
    Banner banner;
    @InjectView(R.id.deal_rv)
    RecyclerView dealRv;
    private CommonAdapter<HomeShowDataMessageBean.ListBean> commonLogisticsAdapter;
    private TodayDealAdapter todayDealAdapter;

    private HomeShowDataPresenter homeShowDataPresenter = new HomeShowDataPresenter();//蜗牛头条
    private SearchCourierNumberPresenter searchCourierNumberPresenter = new SearchCourierNumberPresenter();//搜索快递单号
    private GoodsListByCategoryPresenter goodsListByCategoryPresenter = new GoodsListByCategoryPresenter();//今日特价
    private AddReduceGoodsPresenter addReduceGoodsPresenter = new AddReduceGoodsPresenter();//添加和删除商品
    private GetCommodityInfoPresenter getCommodityInfoPresenter = new GetCommodityInfoPresenter();//商品详情接口
    private List<View> views = new ArrayList<>();  //头条布局
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private Intent intent;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(getActivity());
        logisticsRv.setFocusable(false);
        dealRv.setFocusable(false);
        myScrollview.smoothScrollTo(0, 0);
        initViews();
        return view;
    }

    /**
     * 初始化页面
     */
    private void initViews() {
        /**
         * 限制只能输入字母和数字，默认弹出数字输入法
         */
        search.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_PHONE;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData(R.string.express_number_only_can_input).toCharArray();
                return data;
            }
        });
        requestHomeShowDataHeadline();
        requestHomeShowDataMessage();
        requestHomeShowDataAdvertisement();
        requesGoodsListByCategory();
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

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
                    String seek = search.getText().toString();
                    if (!TextUtils.isEmpty(seek)) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requesSearchCourierNumber("{\"search\":\"" + seek + "\"}");
                    } else {
                        if (SkipIntentUtil.KeyBoard(search) == true) {
                            imm.showSoftInput(search, InputMethodManager.SHOW_FORCED);
                        }
                        SkipIntentUtil.toastShow(getActivity(), "快递单号不能为空！");
                    }

                    return true;
                }
                return false;
            }
        });
    }

    public String getStringData(int id) {
        return getResources().getString(id);
    }

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView(List<HomeShowDataHeadlineBean.ListBean> data) {
        for (int i = 0; i < data.size(); i = i + 2) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_top_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.top_tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.top_tv2);
            //进行对控件赋值
            tv1.setText(data.get(i).getContent());
            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).getContent());
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }

            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    /**
     * 初始化蜗牛头条
     */
    private void requestHomeShowDataHeadline() {
        new OkHttpResolve(getActivity());
        homeShowDataPresenter.attach(this);
        homeShowDataPresenter.postJsonHomeShowDataHeadlineResult(getActivity());
    }

    /**
     * 初始化信息展示
     */
    private void requestHomeShowDataMessage() {
        new OkHttpResolve(getActivity());
        homeShowDataPresenter.attach(this);
        homeShowDataPresenter.postJsonHomeShowDataMessageResult(getActivity());
    }

    /**
     * 初始化快递单号搜索
     */
    private void requesSearchCourierNumber(String json) {
        new OkHttpResolve(getActivity());
        searchCourierNumberPresenter.attach(this);
        searchCourierNumberPresenter.postJsonSearchCourierNumberResult(json, getActivity(), lazyLoadProgressDialog);
    }

    /**
     * 初始化 用户首页广告图
     */
    private void requestHomeShowDataAdvertisement() {
        new OkHttpResolve(getActivity());
        homeShowDataPresenter.attach(this);
        homeShowDataPresenter.postJsonHomeShowDataAdvertisementResult(getActivity());
    }

    /**
     * 今日特价
     */
    private void requesGoodsListByCategory() {
        JSONObject json = new JSONObject();
        try {
            json.put("categoryId", "0");
            json.put("pageNo", "1");
            json.put("pageSize", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(getActivity());
        goodsListByCategoryPresenter.attach(this);
        goodsListByCategoryPresenter.postJsonGoodsListByCategoryResult(json.toString(), getActivity(), null);
    }
    /**
     * 初始化 添加商品
     */
    private void requestAddGoods(String shopCommodityId) {
        JSONObject json= new JSONObject();
        try {
            json.put("quantity","1");
            json.put("shopCommodityId",shopCommodityId);
            json.put("shopType","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new OkHttpResolve(getActivity());
        addReduceGoodsPresenter.attach(this);
        addReduceGoodsPresenter.postJsonAddGoodsResult(json.toString(),getActivity());
    }
    /**
     * 初始化 查询商品详情
     */
    private void requestGetCommodityInfo(String id) {
        new OkHttpResolve(getActivity());
        getCommodityInfoPresenter.attach(this);
        getCommodityInfoPresenter.postJsonGetCommodityInfoResult("{\"id\":\"" + id + "\"}",getActivity(),lazyLoadProgressDialog);
    }

    /**
     * 设置列表参数
     */
    private void setUpParameters(List<HomeShowDataMessageBean.ListBean> homeShowDataMessageList) {
        commonLogisticsAdapter = new CommonAdapter<HomeShowDataMessageBean.ListBean>(getActivity(), R.layout.fragment_home_item, homeShowDataMessageList) {
            @Override
            protected void convert(ViewHolder holder, HomeShowDataMessageBean.ListBean listBean, int position) {
                holder.setText(R.id.state, listBean.getBusinessStatus());
                holder.setText(R.id.express_type_tv, "承运来源：" + listBean.getExpressParcel().getShipperCode());
                try {
                    holder.setText(R.id.datetime, AcquisitionTimeUtil.longToString(listBean.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.setText(R.id.arrive_date_tv, listBean.getRemarks());
                String type = listBean.getType();
                switch (type) {
                    case "1":
                        holder.setVisible(R.id.lgistic_code, true);
                        holder.setText(R.id.lgistic_code, "快递单号：" + listBean.getExpressParcel().getLgisticCode());
                        holder.setVisible(R.id.accept_rl, false);
                        holder.setVisible(R.id.accept_phone, false);
                        break;
                    case "2":
                        holder.setVisible(R.id.lgistic_code, false);
                        holder.setVisible(R.id.accept_rl, true);
                        holder.setVisible(R.id.accept_phone, true);
                        HomeShowDataMessageBean.ListBean.ExpressParcelBean.ReceiverBean receiver = listBean.getExpressParcel().getReceiver();
                        if (receiver != null) {
                            holder.setText(R.id.sir_name, receiver.getName());
                            holder.setText(accept_phone, receiver.getMobile());
                            String provinceName = receiver.getProvinceName();
                            String cityName = receiver.getCityName();
                            String expAreaName = receiver.getExpAreaName();
                            String address = receiver.getAddress();
                            if (provinceName.equals(cityName)) {
                                holder.setText(R.id.sir_address, cityName + expAreaName + address);
                            } else {
                                holder.setText(R.id.sir_address, provinceName + cityName + expAreaName + address);
                            }
                        }
                        break;
                }
            }
        };
        logisticsRv.setAdapter(commonLogisticsAdapter);
    }

    @OnClick({R.id.consignee, R.id.send, R.id.shopping, R.id.pay_for_another, R.id.QRcode, R.id.message, R.id.logistics_information_more_rl, R.id.today_deal_more_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.consignee:
                skipAddressee("accept");
                break;
            case R.id.send:
                SkipIntentUtil.skipIntent(getActivity(), SendCasesDisplayActivity.class);
                break;
            case R.id.shopping:
                skipFragmentShopping();
                break;
            case R.id.pay_for_another:
                skipAddressee("acceptPay");
                break;
            case R.id.QRcode:
                SkipIntentUtil.skipIntent(getActivity(), MipcaActivityCapture.class);
                break;
            case R.id.message:
                //信息
                break;
            case R.id.logistics_information_more_rl:
                SkipIntentUtil.skipIntent(getActivity(), LogisticsInformationActivity.class);
                break;
            case R.id.today_deal_more_rl:
                skipFragmentShopping();
                break;

        }
    }

    /**
     * 跳转到收件列表 做  收件和代付货款操作
     *
     * @param source
     */
    private void skipAddressee(String source) {
        intent = new Intent(getActivity(), AddresseeActivity.class);
        intent.putExtra("source", source);
        startActivity(intent);
    }

    /**
     * 首页跳转到购物页面
     */
    private void skipFragmentShopping(){
        intent = new Intent(getActivity(), BottomNavigationMenuActivity.class);
        intent.putExtra("source", "shopping");
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onHomeShowDataHeadlineFinish(Object o) {
        HomeShowDataHeadlineBean homeShowDataHeadlineBean = (HomeShowDataHeadlineBean) o;
        if (homeShowDataHeadlineBean.getList() != null) {
            //设置头条
            setView(homeShowDataHeadlineBean.getList());
            upMarqueeView.setViews(views);
        }
    }

    @Override
    public void onHomeShowDataMessageFinish(Object o) {
        HomeShowDataMessageBean homeShowDataMessageBean = (HomeShowDataMessageBean) o;
        if (homeShowDataMessageBean.getList() != null) {
            linearLayoutManager = new FullyLinearLayoutManager(getActivity());
            logisticsRv.setLayoutManager(linearLayoutManager);
            setUpParameters(homeShowDataMessageBean.getList());
        }
    }

    @Override
    public void onHomeShowDataAdvertisementFinish(Object o) {
        HomeShowDataAdvertisementBean homeShowDataAdvertisementBean = (HomeShowDataAdvertisementBean) o;
        if (homeShowDataAdvertisementBean != null) {
            List<HomeShowDataAdvertisementBean.AdvertisementListBean> advertisementList = homeShowDataAdvertisementBean.getAdvertisementList();
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < advertisementList.size(); i++) {
                imagesUrl.add(Constants.UP_LOAD_IMAGE_TOP + advertisementList.get(i).getFilePath());
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(imagesUrl);
            banner.start();
        }
    }

    @Override
    public void onSearchCourierNumberViewFinish(Object o) {
        SkipIntentUtil.searchCourierNumberSkip((SearchCourierNumberBean) o, getActivity(), intent, lazyLoadProgressDialog);
    }

    @Override
    public void onGoodsListByCategoryListFinish(Object o) {
        GoodsListByCategoryBean goodsListByCategoryBean = (GoodsListByCategoryBean) o;
        if (goodsListByCategoryBean != null && goodsListByCategoryBean.getPage().getCount() > 0) {
            linearLayoutManager = new FullyLinearLayoutManager(getActivity());
            dealRv.setLayoutManager(linearLayoutManager);
            todayDealAdapter = new TodayDealAdapter(getActivity(),goodsListByCategoryBean.getList());
            todayDealAdapter.setTodayDealItemOnClick(new TodayDealAdapter.TodayDealItemOnClick() {
                @Override
                public void onItemClick(View view,String shopCommodityId) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestGetCommodityInfo(shopCommodityId);
                }

                @Override
                public void onAddClick(View view,String shopCommodityId) {
                    requestAddGoods(shopCommodityId);
                }
            });
            dealRv.setAdapter(todayDealAdapter);
        }
    }

    @Override
    public void onAddGoodsFinish(Object o) {
        AddReduceGoodsBean addGoods = (AddReduceGoodsBean) o;
        if(addGoods!=null){
            skipFragmentShopping();
        }
    }

    @Override
    public void onReduceGoodsFinish(Object o) {

    }
    @Override
    public void onGetCommodityInfoFinish(Object o) {
        GetCommodityInfo getCommodityInfo = (GetCommodityInfo) o;
        if(getCommodityInfo!=null){
            intent = new Intent(getActivity(), CommodityDetailsPageActivity.class);
            intent.putExtra("GetCommodityInfo", (Serializable) getCommodityInfo.getShopCommodity());
            startActivity(intent);
        }

    }
    @Override
    public void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        homeShowDataPresenter.dettach();
        searchCourierNumberPresenter.dettach();
        goodsListByCategoryPresenter.dettach();
        addReduceGoodsPresenter.dettach();
        getCommodityInfoPresenter.dettach();
    }


}
