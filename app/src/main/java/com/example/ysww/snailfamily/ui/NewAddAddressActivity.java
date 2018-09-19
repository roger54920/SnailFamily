package com.example.ysww.snailfamily.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.NewAddressPlotNameBean;
import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.example.ysww.snailfamily.bean.NewAddressSaveBean;
import com.example.ysww.snailfamily.bean.NewAddressTabletBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AllRegionView;
import com.example.ysww.snailfamily.mvp.NewAddAddressView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AllRegionPresenter;
import com.example.ysww.snailfamily.presenter.NewAddAddressPresenter;
import com.example.ysww.snailfamily.utils.CacheUtils;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.example.ysww.snailfamily.utils.TelNumMatch;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ysww.snailfamily.R.id.add_address_btn;


/**
 * 新增地址
 */
public class NewAddAddressActivity extends AutoLayoutActivity implements NewAddAddressView, AllRegionView {
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.address_change_btn)
    Button addressChangeBtn;
    @InjectView(R.id.region_name)
    TextView regionName;
    @InjectView(R.id.region)
    RelativeLayout region;
    @InjectView(R.id.plot_name_name)
    TextView plotNameName;
    @InjectView(R.id.plot_name)
    RelativeLayout plotName;
    @InjectView(R.id.tablet_name)
    TextView tabletName;
    @InjectView(R.id.tablet)
    RelativeLayout tablet;
    @InjectView(R.id.snail_electron_tx)
    TextView snailElectronTx;
    @InjectView(R.id.se_number)
    TextView seNumber;
    @InjectView(R.id.sd)
    TextView sd;
    @InjectView(R.id.manual_customization_btn)
    Button manualCustomizationBtn;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.snail_address)
    RelativeLayout snailAddress;
    @InjectView(R.id.input_name)
    EditText inputName;
    @InjectView(R.id.input_phone)
    EditText inputPhone;
    @InjectView(R.id.input_region)
    TextView inputRegion;
    @InjectView(R.id.input_address)
    EditText inputAddress;
    @InjectView(R.id.common_address)
    LinearLayout commonAddress;
    @InjectView(add_address_btn)
    Button addAddressBtn;

    private Intent intent;
    private String user_id;//用户ID
    private String source;//来源于那个页面
    private String mobile;//手机号
    private String compile;//编辑地址
    private String ifDefault;//是否默认地址
    private String compileId;//编辑的Id

    private NewAddAddressPresenter newAddAddressPresenter = new NewAddAddressPresenter();
    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();
    //全国地区选择
    private List<NewAddressRegionBean.AreaBean> provinceAllBeanList;//全省
    private List<NewAddressRegionBean.AreaBean> options1AllItems = new ArrayList<>();
    private List<List<String>> options2AllItems = new ArrayList<>();
    private List<List<List<String>>> options3AllItems = new ArrayList<>();
    //蜗牛地区选择
    private List<NewAddressRegionBean.AreaBean> areaBeanList;//省
    private List<NewAddressRegionBean.AreaBean> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();

    //小区选择
    private List<NewAddressPlotNameBean.CommunityBean> plotNameoptions1Items = new ArrayList<>();
    private List<NewAddressPlotNameBean.CommunityBean> communityList;

    //门牌号选择
    private List<NewAddressTabletBean.HouseBean> tabletoptions1Items = new ArrayList<>();
    private List<List<String>> tabletoptions2Items = new ArrayList<>();
    private List<List<List<String>>> tabletoptions3Items = new ArrayList<>();
    private List<NewAddressTabletBean.HouseBean> houseList;//楼

    private NewAddressSaveBean newAddressSaveBean = new NewAddressSaveBean();//选择的地址ID
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载


    private OptionsPickerView pvOptions;

    private String[] allRegionOptions = new String[3];//全国地区
    private String[] regionOptions = new String[3];//地区
    private String plotOptions;//小区名
    private String[] houseOptions = new String[3];//单元
    private boolean isWhetherRequest = false;

    private AsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_add_address);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);

        initViews();

    }

    private void initViews() {
        task = new AsyncTask<String, Void, List<NewAddressRegionBean.AreaBean>>() {
            @Override
            protected List<NewAddressRegionBean.AreaBean> doInBackground(String... params) {
                List<NewAddressRegionBean.AreaBean> analysisJson = CacheUtils.analysisJson(NewAddAddressActivity.this);
                if (analysisJson != null) {
                    provinceAllBeanList = analysisJson;
                }
                return provinceAllBeanList;
            }
        }.execute();
        intent = getIntent();
        if (intent != null) {
            user_id = intent.getStringExtra("user_id");
            source = intent.getStringExtra("source");
            mobile = intent.getStringExtra("mobile");
            compile = intent.getStringExtra("compile");
        }
        //编辑地址
        if (compile != null) {
            title.setText("编辑地址");
            addressChangeBtn.setVisibility(View.GONE);
            addAddressBtn.setText("修改");
            compileId = intent.getStringExtra("id");
            ifDefault = intent.getStringExtra("ifDefault");
            switch (compile) {
                case "1":
                    addressChangeBtn.setText("普通地址");
                    snailAddress.setVisibility(View.VISIBLE);
                    commonAddress.setVisibility(View.GONE);
                    String ifUserParcelNo = intent.getStringExtra("ifUserParcelNo");
                    mobile = intent.getStringExtra("phone");
                    seNumber.setText(intent.getStringExtra("parcelNo"));
                    if (ifUserParcelNo.equals("1")) {
                        checkbox.setChecked(true);
                    } else {
                        checkbox.setChecked(false);
                    }
                    regionOptions = intent.getStringExtra("region").split(",");
                    plotOptions = intent.getStringExtra("plot");
                    houseOptions = intent.getStringExtra("house").split(",");
                    regionName.setText(regionOptions[0] + " " + regionOptions[1] + " " + regionOptions[2]);
                    plotNameName.setText(plotOptions);
                    tabletName.setText(houseOptions[0] + " " + houseOptions[1] + " " + houseOptions[2]);
                    if (newAddressSaveBean != null) {
                        newAddressSaveBean.setProvincialCode(intent.getStringExtra("provincialCode"));
                        newAddressSaveBean.setCityCode(intent.getStringExtra("cityCode"));
                        newAddressSaveBean.setAreaCode(intent.getStringExtra("areaCode"));
                        newAddressSaveBean.setCellCode(intent.getStringExtra("cellCode"));
                        newAddressSaveBean.setHouseNumber(intent.getStringExtra("houseNumber"));
                        newAddressSaveBean.setUnit(intent.getStringExtra("unit"));
                        newAddressSaveBean.setRoom(intent.getStringExtra("room"));
                    }
                    break;
                case "2":
                    addressChangeBtn.setText("蜗牛地址");
                    snailAddress.setVisibility(View.GONE);
                    commonAddress.setVisibility(View.VISIBLE);

                    inputName.setText(intent.getStringExtra("name"));
                    inputPhone.setText(intent.getStringExtra("phone"));
                    inputAddress.setText(intent.getStringExtra("place"));
                    allRegionOptions = intent.getStringExtra("allRegion").split(",");
                    inputRegion.setText(allRegionOptions[0] + " " + allRegionOptions[1] + " " + allRegionOptions[2]);
                    if (newAddressSaveBean != null) {
                        newAddressSaveBean.setProvincialCode(intent.getStringExtra("provincialCode"));
                        newAddressSaveBean.setCityCode(intent.getStringExtra("cityCode"));
                        newAddressSaveBean.setAreaCode(intent.getStringExtra("areaCode"));
                    }
                    break;
            }
        } else {
            if (addressChangeBtn.getText().equals("普通地址")) {
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestDataSnail();
            }
        }
    }

    /**
     * 全国地区初始化请求数据
     */

    private void requestDataAllRegion() {
        new OkHttpResolve(this);
        allRegionPresenter.attach(this);
        allRegionPresenter.postAllRegionJsonResult("{\"id\":\" \"}", this, lazyLoadProgressDialog);
    }

    /**
     * 地区初始化请求数据
     */
    private void requestDataRegion() {
        new OkHttpResolve(this);
        newAddAddressPresenter.attach(this);
        newAddAddressPresenter.postRegionJsonResult("{\"id\":\" \"}", this, lazyLoadProgressDialog);
    }

    /**
     * 小区名初始化请求数据
     */
    private void requestDataPlotName(String json) {
        new OkHttpResolve(this);
        newAddAddressPresenter.attach(this);
        newAddAddressPresenter.postPlotNameJsonResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 门牌号名初始化请求数据
     */
    private void requestDataTablet(String json) {
        new OkHttpResolve(this);
        newAddAddressPresenter.attach(this);
        newAddAddressPresenter.postTabletJsonResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 新增地址
     */
    private void requestDataSave(String json) {
        new OkHttpResolve(this);
        newAddAddressPresenter.attach(this);
        newAddAddressPresenter.postSaveJsonResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 得到电子包裹箱号
     **/
    private void requestDataSnail() {
        new OkHttpResolve(this);
        newAddAddressPresenter.attach(this);
        newAddAddressPresenter.getSnailElectronResult(this, lazyLoadProgressDialog);
    }

    private void showPickerView(final TextView textName, final String typeName) {// 弹出选择器
        pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (typeName.equals("地区选择")) {
                    newAddressSaveBean = new NewAddressSaveBean();
                    //返回的分别是三个级别的选中位置
                    String tx = options1AllItems.get(options1).getPickerViewText() + " " +
                            options2AllItems.get(options1).get(options2) + " " +
                            options3AllItems.get(options1).get(options2).get(options3);
                    textName.setTextColor(Color.parseColor("#333333"));
                    textName.setText(tx);
                    if (provinceAllBeanList != null) {
                        for (int i = 0; i < provinceAllBeanList.size(); i++) {
                            if (provinceAllBeanList.get(i).getName().equals(options1AllItems.get(options1).getPickerViewText())) {
                                newAddressSaveBean.setProvincialCode(provinceAllBeanList.get(i).getId());
                                allRegionOptions[0] = provinceAllBeanList.get(i).getName();
                            }
                            for (int j = 0; j < provinceAllBeanList.get(i).getNodes().size(); j++) {
                                if (provinceAllBeanList.get(i).getNodes().get(j).getName().equals(options2AllItems.get(options1).get(options2))) {
                                    newAddressSaveBean.setCityCode(provinceAllBeanList.get(i).getNodes().get(j).getId());
                                    allRegionOptions[1] = provinceAllBeanList.get(i).getNodes().get(j).getName();
                                }
                                if (provinceAllBeanList.get(i).getNodes().get(j).getNodes() != null) {
                                    for (int k = 0; k < provinceAllBeanList.get(i).getNodes().get(j).getNodes().size(); k++) {
                                        if (provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName().equals(options3AllItems.get(options1).get(options2).get(options3))) {
                                            newAddressSaveBean.setAreaCode(provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getId());
                                            allRegionOptions[2] = provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName();
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (typeName.equals("蜗牛地区选择")) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText() + " " +
                            options2Items.get(options1).get(options2) + " " +
                            options3Items.get(options1).get(options2).get(options3);
                    if (!tx.equals(regionName.getText())) {
                        if (!plotNameName.getText().equals("请选择") || !tabletName.getText().equals("请选择")) {
                            plotNameName.setText("请选择");
                            tabletName.setText("请选择");
                        }
                    }
                    textName.setTextColor(Color.parseColor("#333333"));
                    textName.setText(tx);
                    if (areaBeanList != null) {
                        for (int i = 0; i < areaBeanList.size(); i++) {
                            if (areaBeanList.get(i).getName().equals(options1Items.get(options1).getPickerViewText())) {
                                newAddressSaveBean.setProvincialCode(areaBeanList.get(i).getId());
                                regionOptions[0] = areaBeanList.get(i).getName();
                            }
                            for (int j = 0; j < areaBeanList.get(i).getNodes().size(); j++) {
                                if (areaBeanList.get(i).getNodes().get(j).getName().equals(options2Items.get(options1).get(options2))) {
                                    newAddressSaveBean.setCityCode(areaBeanList.get(i).getNodes().get(j).getId());
                                    regionOptions[1] = areaBeanList.get(i).getNodes().get(j).getName();
                                }
                                for (int k = 0; k < areaBeanList.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (areaBeanList.get(i).getNodes().get(j).getNodes().get(k).getName().equals(options3Items.get(options1).get(options2).get(options3))) {
                                        newAddressSaveBean.setAreaCode(areaBeanList.get(i).getNodes().get(j).getNodes().get(k).getId());
                                        regionOptions[2] = areaBeanList.get(i).getNodes().get(j).getNodes().get(k).getName();
                                        if (isWhetherRequest == false) {
                                            isWhetherRequest = true;
                                        } else {
                                            isWhetherRequest = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (typeName.equals("小区名选择")) {
                    //返回的分别是三个级别的选中位置
                    String tx = plotNameoptions1Items.get(options1).getPickerViewText();
                    if (!tx.equals(plotNameName.getText())) {
                        if (!tabletName.getText().equals("请选择")) {
                            tabletName.setText("请选择");
                        }
                    }
                    textName.setTextColor(Color.parseColor("#333333"));
                    textName.setText(tx);
                    newAddressSaveBean.setCellCode(plotNameoptions1Items.get(options1).getId());
                    plotOptions = plotNameoptions1Items.get(options1).getName();
                } else if (typeName.equals("门牌号选择")) {
                    //返回的分别是三个级别的选中位置
                    String tx = tabletoptions1Items.get(options1).getPickerViewText() + " " +
                            tabletoptions2Items.get(options1).get(options2) + " " +
                            tabletoptions3Items.get(options1).get(options2).get(options3);
                    textName.setTextColor(Color.parseColor("#333333"));
                    textName.setText(tx);
                    if (houseList != null) {
                        for (int i = 0; i < houseList.size(); i++) {
                            if (houseList.get(i).getName().equals(tabletoptions1Items.get(options1).getPickerViewText())) {
                                newAddressSaveBean.setHouseNumber(houseList.get(i).getId());
                                houseOptions[0] = houseList.get(i).getName();
                            }
                            if (houseList.get(i).getNodes() != null) {
                                for (int j = 0; j < houseList.get(i).getNodes().size(); j++) {
                                    if (houseList.get(i).getNodes().get(j).getName().equals(tabletoptions2Items.get(options1).get(options2))) {
                                        newAddressSaveBean.setUnit(houseList.get(i).getNodes().get(j).getId());
                                        houseOptions[1] = houseList.get(i).getNodes().get(j).getName();
                                    }
                                    if (houseList.get(i).getNodes().get(j).getNodes() != null) {
                                        for (int k = 0; k < houseList.get(i).getNodes().get(j).getNodes().size(); k++) {
                                            if (houseList.get(i).getNodes().get(j).getNodes().get(k).getName().equals(tabletoptions3Items.get(options1).get(options2).get(options3))) {
                                                newAddressSaveBean.setRoom(houseList.get(i).getNodes().get(j).getNodes().get(k).getId());
                                                houseOptions[2] = houseList.get(i).getNodes().get(j).getNodes().get(k).getName();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

                .setTitleText(typeName)
                .setTitleSize(18)
                .setTitleColor(Color.parseColor("#333333"))
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(Color.parseColor("#6fd1c8"))
                .setSubmitColor(Color.parseColor("#6fd1c8"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
                .setOutSideCancelable(false)// default is true
                .build();
        if (typeName.equals("地区选择")) {
            pvOptions.setPicker(options1AllItems, options2AllItems, options3AllItems);//三级选择器
            if (allRegionOptions != null && allRegionOptions.length > 0) {
                for (int i = 0; i < options1AllItems.size(); i++) {
                    if (options1AllItems.get(i).getName().equals(allRegionOptions[0])) {
                        for (int j = 0; j < options1AllItems.get(i).getNodes().size(); j++) {
                            if (options1AllItems.get(i).getNodes().get(j).getName().equals(allRegionOptions[1])) {
                                for (int k = 0; k < options1AllItems.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (options1AllItems.get(i).getNodes().get(j).getNodes().get(k).getName().equals(allRegionOptions[2])) {
                                        pvOptions.setSelectOptions(i, j, k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (typeName.equals("蜗牛地区选择")) {
            pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
            if (regionOptions != null && regionOptions.length > 0) {
                for (int i = 0; i < options1Items.size(); i++) {
                    if (options1Items.get(i).getName().equals(regionOptions[0])) {
                        for (int j = 0; j < options1Items.get(i).getNodes().size(); j++) {
                            if (options1Items.get(i).getNodes().get(j).getName().equals(regionOptions[1])) {
                                for (int k = 0; k < options1Items.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (options1Items.get(i).getNodes().get(j).getNodes().get(k).getName().equals(regionOptions[2])) {
                                        pvOptions.setSelectOptions(i, j, k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (typeName.equals("小区名选择")) {
            pvOptions.setPicker(plotNameoptions1Items);//一级选择器
            if (plotOptions != null) {
                for (int i = 0; i < plotNameoptions1Items.size(); i++) {
                    if (plotNameoptions1Items.get(i).getName().equals(plotOptions)) {
                        pvOptions.setSelectOptions(i);
                    }
                }
            }
        } else if (typeName.equals("门牌号选择")) {
            pvOptions.setPicker(tabletoptions1Items, tabletoptions2Items, tabletoptions3Items);//三级选择器
            if (houseOptions != null && houseOptions.length > 0) {
                for (int i = 0; i < tabletoptions1Items.size(); i++) {
                    if (tabletoptions1Items.get(i).getName().equals(houseOptions[0])) {
                        for (int j = 0; j < tabletoptions1Items.get(i).getNodes().size(); j++) {
                            if (tabletoptions1Items.get(i).getNodes().get(j).getName().equals(houseOptions[1])) {
                                for (int k = 0; k < tabletoptions1Items.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (tabletoptions1Items.get(i).getNodes().get(j).getNodes().get(k).getName().equals(houseOptions[2])) {
                                        pvOptions.setSelectOptions(i, j, k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        pvOptions.show();

    }

    @OnClick({R.id.region, R.id.plot_name, R.id.tablet, R.id.manual_customization_btn, R.id.checkbox, R.id.return_arrows, R.id.add_address_btn, R.id.address_change_btn, R.id.input_region})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.region:
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                requestDataRegion();
                break;
            case R.id.plot_name:
                if (!regionName.getText().toString().equals("请选择")) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestDataPlotName("{\"areaCode\":\"" + newAddressSaveBean.getAreaCode() + "\"}");
                } else {
                    SkipIntentUtil.toastShow(this, "请选择蜗牛地区！");
                }
                break;
            case R.id.tablet:
                if (!regionName.getText().toString().equals("请选择")) {
                    if (!plotNameName.getText().toString().equals("请选择")) {
                        lazyLoadProgressDialog.show();
                        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                        requestDataTablet("{\"cellCode\":\"" + newAddressSaveBean.getCellCode() + "\"}");
                    } else {
                        SkipIntentUtil.toastShow(this, "请选择小区名！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请选择蜗牛地区！");
                }
                break;
            case R.id.manual_customization_btn:
                intent = new Intent(NewAddAddressActivity.this, ElectronParcelNumberActivity.class);
                startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                break;
            case R.id.return_arrows:
                intent = new Intent();
                setResult(Constants.SUBSCRIPT_ZER0, intent);
                finish();
                break;
            case add_address_btn:
                if (addressChangeBtn.getText().equals("普通地址")) {
                    if (!regionName.getText().toString().equals("请选择")) {
                        if (!plotNameName.getText().toString().equals("请选择")) {
                            if (!tabletName.getText().toString().equals("请选择")) {
                                String parcel = seNumber.getText().toString().trim();
                                if (!TextUtils.isEmpty(parcel)) {

                                    lazyLoadProgressDialog.show();
                                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                    HashMap<String, Object> saveParams = new HashMap<>();
                                    compile = "1";
                                    saveParams.put("type", compile);
                                    if (checkbox.isChecked() == true) {
                                        saveParams.put("ifUserParcelNo", "1");
                                    } else {
                                        saveParams.put("ifUserParcelNo", "0");
                                    }

                                    if (source.equals("personal_message")) {
                                        ifDefault = "1";
                                        saveParams.put("ifDefault", ifDefault);
                                    } else if (source.equals("common_site_address")) {
                                        ifDefault = intent.getStringExtra("ifDefault");
                                        saveParams.put("ifDefault", ifDefault);
                                    } else {
                                        ifDefault = "0";
                                        saveParams.put("ifDefault", ifDefault);
                                    }


                                    if (compileId != null) {
                                        saveParams.put("id", compileId);
                                    }
                                    if (newAddressSaveBean != null) {
                                        saveParams.put("provincialCode", newAddressSaveBean.getProvincialCode());
                                        saveParams.put("cityCode", newAddressSaveBean.getCityCode());
                                        saveParams.put("areaCode", newAddressSaveBean.getAreaCode());
                                        saveParams.put("cellCode", newAddressSaveBean.getCellCode());
                                        saveParams.put("houseNumber", newAddressSaveBean.getHouseNumber());
                                        saveParams.put("unit", newAddressSaveBean.getUnit());
                                        saveParams.put("room", newAddressSaveBean.getRoom());
                                        if (mobile != null) {
                                            saveParams.put("phone", mobile);
                                        } else {
                                            saveParams.put("phone", "");
                                        }

                                        saveParams.put("parcelNo", parcel);
                                        try {
                                            saveParams.put("owner", new JSONObject().put("id", user_id));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        JSONObject saveJsonObject = new JSONObject(saveParams);
                                        requestDataSave(saveJsonObject.toString());
                                    }
                                } else {
                                    SkipIntentUtil.toastShow(this, "电子包裹箱不能为空，请重新添加！");
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请选择门牌号！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请选择小区名！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请选择地区！");
                    }
                } else {
                    String inputname = inputName.getText().toString();
                    String inputphone = inputPhone.getText().toString();
                    String inputaddress = inputAddress.getText().toString();
                    String inputregion = inputRegion.getText().toString();
                    if (!TextUtils.isEmpty(inputname)) {
                        if (TelNumMatch.isPhone(inputphone) == true) {
                            if (!TextUtils.isEmpty(inputregion)) {
                                if (!inputaddress.equals("")) {
                                    lazyLoadProgressDialog.show();
                                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                    HashMap<String, Object> commonParams = new HashMap<>();
                                    compile = "2";
                                    commonParams.put("type", compile);
                                    commonParams.put("name", inputname);
                                    commonParams.put("phone", inputphone);
                                    commonParams.put("place", inputaddress);
                                    if (newAddressSaveBean != null) {
                                        commonParams.put("provincialCode", newAddressSaveBean.getProvincialCode());
                                        commonParams.put("cityCode", newAddressSaveBean.getCityCode());
                                        commonParams.put("areaCode", newAddressSaveBean.getAreaCode());
                                    }
                                    if (source.equals("personal_message")) {
                                        ifDefault = "1";
                                        commonParams.put("ifDefault", ifDefault);
                                    } else if (source.equals("common_site_address")) {
                                        ifDefault = intent.getStringExtra("ifDefault");
                                        commonParams.put("ifDefault", ifDefault);
                                    } else {
                                        ifDefault = "0";
                                        commonParams.put("ifDefault", ifDefault);
                                    }
                                    if (compileId != null) {
                                        commonParams.put("id", compileId);
                                    }
                                    try {
                                        commonParams.put("owner", new JSONObject().put("id", user_id));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject commonJsonObject = new JSONObject(commonParams);
                                    requestDataSave(commonJsonObject.toString());

                                } else {
                                    SkipIntentUtil.toastShow(this, "请输入详细地址！");
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请输入选择地区！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请正确输入手机号！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请输入姓名！");
                    }
                }

                break;
            case R.id.address_change_btn:
                if (addressChangeBtn.getText().equals("普通地址")) {
                    addressChangeBtn.setText("蜗牛地址");
                    snailAddress.setVisibility(View.INVISIBLE);
                    commonAddress.setVisibility(View.VISIBLE);
                } else if (addressChangeBtn.getText().equals("蜗牛地址")) {
                    addressChangeBtn.setText("普通地址");
                    snailAddress.setVisibility(View.VISIBLE);
                    commonAddress.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.input_region:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (provinceAllBeanList == null || provinceAllBeanList.size() == 0) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad0neMinute(lazyLoadProgressDialog);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (provinceAllBeanList == null || provinceAllBeanList.size() == 0) {
                                    requestDataAllRegion();
                                } else {
                                    LazyLoadUtil.SetLazyLadResult(lazyLoadProgressDialog);
                                    initJsonDataRegion(provinceAllBeanList, "all");
                                    showPickerView(inputRegion, "地区选择");
                                }
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    initJsonDataRegion(provinceAllBeanList, "all");
                    showPickerView(inputRegion, "地区选择");
                }

                break;
        }
    }

    /**
     * 页面来源返回的参数  为 startActivityForResult
     *
     * @param source
     */
    private void sourceResultReturn(String source) {
        intent = new Intent();
        intent.putExtra("source", source);
        setResult(Constants.SUBSCRIPT_ZER0, intent);
        finish();
    }

    @Override
    public void onAllRegionListFinish(Object o) {
        final NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) o;
        if (newAddressRegionBean != null) {
            provinceAllBeanList = newAddressRegionBean.getArea();
            if (provinceAllBeanList != null) {
                new AsyncTask<String, Void, NewAddressRegionBean>() {
                    @Override
                    protected NewAddressRegionBean doInBackground(String... params) {
                        CacheUtils.writeJson(NewAddAddressActivity.this, newAddressRegionBean, "nationalregions.txt", false,this);
                        return null;
                    }
                }.execute();

                initJsonDataRegion(provinceAllBeanList, "all");
                showPickerView(inputRegion, "地区选择");
            }
        }
    }

    @Override
    public void onRegionListFinish(Object o) {
        NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) o;
        if (newAddressRegionBean != null) {
            areaBeanList = newAddressRegionBean.getArea();
            if (areaBeanList != null) {
                initJsonDataRegion(areaBeanList, "snail");
                showPickerView(regionName, "蜗牛地区选择");
            }
        }
    }

    @Override
    public void onPlotNameListFinish(Object o) {
        NewAddressPlotNameBean newAddressPlotNameBean = (NewAddressPlotNameBean) o;
        if (newAddressPlotNameBean != null) {
            communityList = newAddressPlotNameBean.getCommunity();
            if (communityList != null) {
                plotNameoptions1Items = communityList;
                showPickerView(plotNameName, "小区名选择");

            }
        }
    }

    @Override
    public void onTabletListFinish(Object o) {
        NewAddressTabletBean newAddressTabletBean = (NewAddressTabletBean) o;
        if (newAddressTabletBean != null) {
            houseList = newAddressTabletBean.getHouse();
            if (houseList != null) {
                initJsonDataTablet(houseList);
                showPickerView(tabletName, "门牌号选择");

            }
        }
    }

    @Override
    public void onSaveFinish(Object o) {
        NewAddressPlotNameBean newAddressSaveBean = (NewAddressPlotNameBean) o;
        if (newAddressSaveBean != null) {
            source = getIntent().getStringExtra("source");
            if (source != null) {
                switch (source) {
                    case "send_contacts":
                        sourceResultReturn("send_contacts");
                        break;
                    case "send_address":
                        sourceResultReturn("send_address");
                        break;
                    case "fragment_me":
                        sourceResultReturn("fragment_me");
                        break;
                    case "personal_message":
                        sourceResultReturn("new_add_address");
                        break;
                    case "common_site_address":
//                        compileSourceResult();
                        sourceResultReturn("common_site_address");
                        break;
                    case "common_site_add_address":
                        sourceResultReturn("common_site_add_address");
                        break;
                }
            }
        }
    }

    @Override
    public void onSnailElectronFinish(Object o) {
        NewAddressSaveBean snailElectron = (NewAddressSaveBean) o;
        if (snailElectron != null) {
            snailElectronTx.setText("系统为您分配的蜗牛电子包裹箱为：");
            seNumber.setText(snailElectron.getParcelNo());
        }

    }

    /**
     * 门牌号数据解析
     *
     * @param house
     */
    private void initJsonDataTablet(List<NewAddressTabletBean.HouseBean> house) {
        /**
         * 添加门牌号数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        tabletoptions1Items = house;

        for (int i = 0; i < house.size(); i++) {//遍历楼
            List<String> HouseList = new ArrayList<>();//该楼列表（第二级）
            List<List<String>> Province_HouseList = new ArrayList<>();//该楼的所有列表（第三级）

            if (house.get(i).getNodes() != null) {
                for (int c = 0; c < house.get(i).getNodes().size(); c++) {//遍历该楼的所有
                    String HouseName = house.get(i).getNodes().get(c).getName();
                    HouseList.add(HouseName);//添加楼

                    List<String> City_AreaList = new ArrayList<>();//该楼的所有列表

                    //如果无数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (house.get(i).getNodes().get(c).getNodes() == null
                            || house.get(i).getNodes().get(c).getNodes().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        for (int d = 0; d < house.get(i).getNodes().get(c).getNodes().size(); d++) {//该楼对应所有数据
                            String AreaName = house.get(i).getNodes().get(c).getNodes().get(d).getName();
                            City_AreaList.add(AreaName);//添加该楼所有数据
                        }
                    }
                    Province_HouseList.add(City_AreaList);//添加该楼所有数据
                }
            }
            /**
             * 添加单元数据
             */
            tabletoptions2Items.add(HouseList);

            /**
             * 添加门牌号数据
             */
            tabletoptions3Items.add(Province_HouseList);
        }
    }

    private void initJsonDataRegion(List<NewAddressRegionBean.AreaBean> area, String type) {
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        if (type.equals("all")) {
            options1AllItems = area;
        } else {
            options1Items = area;
        }
        for (int i = 0; i < area.size(); i++) {//遍历省份
            List<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            if (area.get(i).getNodes() != null) {
                for (int c = 0; c < area.get(i).getNodes().size(); c++) {//遍历该省份的所有城市
                    String CityName = area.get(i).getNodes().get(c).getName();
                    CityList.add(CityName);//添加城市

                    List<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (area.get(i).getNodes().get(c).getNodes() == null
                            || area.get(i).getNodes().get(c).getNodes().size() == 0) {
                        City_AreaList.add("");
                    } else {
                        for (int d = 0; d < area.get(i).getNodes().get(c).getNodes().size(); d++) {//该城市对应地区所有数据
                            String AreaName = area.get(i).getNodes().get(c).getNodes().get(d).getName();
                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                        }

                    }
                    Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                }
            }
            /**
             * 添加城市数据
             */
            if (type.equals("all")) {
                options2AllItems.add(CityList);
            } else {
                options2Items.add(CityList);
            }

            /**
             * 添加地区数据
             */
            if (type.equals("all")) {
                options3AllItems.add(Province_AreaList);
            } else {
                options3Items.add(Province_AreaList);
            }

        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String electronNumber = data.getStringExtra("electronNumber");
        if (electronNumber != null) {
            snailElectronTx.setText("您私人定制的蜗牛电子包裹箱为：");
            seNumber.setText(electronNumber);
        }
    }

    /**
     * 编辑来源页面
     */
    private void compileSourceResult() {
        String compile_source = getIntent().getStringExtra("compile_source");
        if (compile_source != null) {
            switch (compile_source) {
                case "send_contacts":
                    sourceResultReturn("send_contacts");
                    break;
                case "send_address":
                    sourceResultReturn("send_address");
                    break;
                case "fragment_me":
                    sourceResultReturn("fragment_me");
                    break;
                case "common_site_address":
                    sourceResultReturn("common_site_address");
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            intent = new Intent();
            setResult(Constants.SUBSCRIPT_ZER0, intent);
            finish();
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
        newAddAddressPresenter.dettach();
        allRegionPresenter.dettach();
        SkipIntentUtil.closeAsyncTask(task);
    }
}
