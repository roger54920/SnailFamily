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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.example.ysww.snailfamily.bean.NewAddressSaveBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AllRegionView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AllRegionPresenter;
import com.example.ysww.snailfamily.utils.CacheUtils;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.example.ysww.snailfamily.utils.TelNumMatch;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 寄件 新增收件人地址
 */
public class SendNewSiteActivity extends AutoLayoutActivity implements AllRegionView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.input_region)
    TextView inputRegion;
    @InjectView(R.id.radio_save_address)
    CheckBox radioSaveAddress;
    @InjectView(R.id.input_name)
    EditText inputName;
    @InjectView(R.id.input_phone)
    EditText inputPhone;
    @InjectView(R.id.input_address)
    EditText inputAddress;

    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();//全国地址
    //全国地区选择
    private List<NewAddressRegionBean.AreaBean> provinceAllBeanList;//省
    private List<NewAddressRegionBean.AreaBean> options1AllItems = new ArrayList<>();
    private List<List<String>> options2AllItems = new ArrayList<>();
    private List<List<List<String>>> options3AllItems = new ArrayList<>();

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private NewAddressSaveBean newAddressSaveBean = new NewAddressSaveBean();//选择的地址ID
    private Intent intent;

    private String[] allRegionOptions = new String[3];//全国地区
    private AsyncTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_site);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        initViews();

    }
    private void initViews(){
        task=new AsyncTask<String, Void, List<NewAddressRegionBean.AreaBean>>() {
            @Override
            protected List<NewAddressRegionBean.AreaBean> doInBackground(String... params) {
                List<NewAddressRegionBean.AreaBean> analysisJson = CacheUtils.analysisJson(SendNewSiteActivity.this);
                if(analysisJson!=null){
                    provinceAllBeanList = analysisJson;
                }
                return provinceAllBeanList;
            }
        }.execute();
        intent = getIntent();
        String isAddress = intent.getStringExtra("isAddress");
        if (isAddress != null) {
            newAddressSaveBean.setReceiverprovincename(intent.getStringExtra("provincename"));
            newAddressSaveBean.setReceivercityname(intent.getStringExtra("cityname"));
            newAddressSaveBean.setReceiverexpareaname(intent.getStringExtra("expareaname"));
            newAddressSaveBean.setProvincialCode(intent.getStringExtra("receiverprovincode"));
            newAddressSaveBean.setCityCode(intent.getStringExtra("receivercitycode"));
            newAddressSaveBean.setAreaCode(intent.getStringExtra("receiverexpareacode"));
            String name = intent.getStringExtra("name");
            String moblie = intent.getStringExtra("moblie");
            String address = intent.getStringExtra("address");
            inputName.setText(name);
            inputPhone.setText(moblie);
            inputAddress.setText(address);
            inputName.setSelection(name.length());
            inputPhone.setSelection(moblie.length());
            inputAddress.setSelection(address.length());
            String receiverprovincename = newAddressSaveBean.getReceiverprovincename();
            String receivercityname = newAddressSaveBean.getReceivercityname();
            String receiverexpareaname = newAddressSaveBean.getReceiverexpareaname();
            inputRegion.setText(receiverprovincename + " " + receivercityname + " " + receiverexpareaname);
            allRegionOptions[0] =receiverprovincename;
            allRegionOptions[1] =receivercityname;
            allRegionOptions[2] =receiverexpareaname;
            if (isAddress.equals("1")) {
                radioSaveAddress.setChecked(true);
            } else {
                radioSaveAddress.setChecked(false);
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

    @OnClick({R.id.return_arrows, R.id.input_region, R.id.radio_save_address, R.id.confirm_recipients_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_arrows:
                intent = new Intent();
                setResult(Constants.SUBSCRIPT_ZER0, intent);
                finish();
                break;
            case R.id.input_region:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                if (provinceAllBeanList == null || provinceAllBeanList.size()==0) {
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
                                    initJsonDataRegion(provinceAllBeanList);
                                    ShowPickerView();
                                }
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    initJsonDataRegion(provinceAllBeanList);
                    ShowPickerView();
                }
                break;
            case R.id.confirm_recipients_btn:
                String inputname = inputName.getText().toString();
                String inputphone = inputPhone.getText().toString();
                String inputaddress = inputAddress.getText().toString();
                String inputregion = inputRegion.getText().toString();
                if (!TextUtils.isEmpty(inputname)) {
                    if (TelNumMatch.isPhone(inputphone) == true) {
                        if (!TextUtils.isEmpty(inputregion)) {
                            if (!inputaddress.equals("")) {
                                intent = new Intent();
                                intent.putExtra("source_return", "send_address_return");
                                intent.putExtra("name", inputname.trim());
                                intent.putExtra("moblie", inputphone.trim());
                                intent.putExtra("provincename", newAddressSaveBean.getReceiverprovincename());
                                intent.putExtra("cityname", newAddressSaveBean.getReceivercityname());
                                intent.putExtra("expareaname", newAddressSaveBean.getReceiverexpareaname());
                                intent.putExtra("address", inputaddress);
                                intent.putExtra("receiverprovincode", newAddressSaveBean.getProvincialCode());
                                intent.putExtra("receivercitycode", newAddressSaveBean.getCityCode());
                                intent.putExtra("receiverexpareacode", newAddressSaveBean.getAreaCode());
                                if (radioSaveAddress.isChecked() == true) {
                                    intent.putExtra("isAddress", "1");
                                } else {
                                    intent.putExtra("isAddress", "0");
                                }
                                setResult(Constants.SUBSCRIPT_ZER0, intent);
                                finish();
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
                break;
        }
    }

    /**
     * 全国地区
     *
     * @param o
     */
    @Override
    public void onAllRegionListFinish(Object o) {
        final NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) o;
        if (newAddressRegionBean != null) {
            provinceAllBeanList = newAddressRegionBean.getArea();
            if (provinceAllBeanList != null) {
                new AsyncTask<String, Void,NewAddressRegionBean>() {
                    @Override
                    protected NewAddressRegionBean doInBackground(String... params) {
                        CacheUtils.writeJson(SendNewSiteActivity.this,newAddressRegionBean,"nationalregions.txt",false,this);
                        return null;
                    }
                }.execute();
                initJsonDataRegion(provinceAllBeanList);
                ShowPickerView();
            }
        }
    }

    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                newAddressSaveBean = new NewAddressSaveBean();
                //返回的分别是三个级别的选中位置
                String tx = options1AllItems.get(options1).getPickerViewText() + " " +
                        options2AllItems.get(options1).get(options2) + " " +
                        options3AllItems.get(options1).get(options2).get(options3);
                inputRegion.setTextColor(Color.parseColor("#333333"));
                inputRegion.setText(tx);
                if (provinceAllBeanList != null) {
                    for (int i = 0; i < provinceAllBeanList.size(); i++) {
                        if (provinceAllBeanList.get(i).getName().equals(options1AllItems.get(options1).getPickerViewText())) {
                            newAddressSaveBean.setProvincialCode(provinceAllBeanList.get(i).getId());
                            newAddressSaveBean.setReceiverprovincename(provinceAllBeanList.get(i).getName());
                            allRegionOptions[0] = provinceAllBeanList.get(i).getName();
                        }
                        for (int j = 0; j < provinceAllBeanList.get(i).getNodes().size(); j++) {
                            if (provinceAllBeanList.get(i).getNodes().get(j).getName().equals(options2AllItems.get(options1).get(options2))) {
                                newAddressSaveBean.setCityCode(provinceAllBeanList.get(i).getNodes().get(j).getId());
                                newAddressSaveBean.setReceivercityname(provinceAllBeanList.get(i).getNodes().get(j).getName());
                                allRegionOptions[1] = provinceAllBeanList.get(i).getNodes().get(j).getName();
                            }
                            if (provinceAllBeanList.get(i).getNodes().get(j).getNodes() != null) {
                                for (int k = 0; k < provinceAllBeanList.get(i).getNodes().get(j).getNodes().size(); k++) {
                                    if (provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName().equals(options3AllItems.get(options1).get(options2).get(options3))) {
                                        newAddressSaveBean.setAreaCode(provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getId());
                                        newAddressSaveBean.setReceiverexpareaname(provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName());
                                        allRegionOptions[2] = provinceAllBeanList.get(i).getNodes().get(j).getNodes().get(k).getName();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

                .setTitleText("地区选择")
                .setTitleSize(20)
                .setTitleColor(Color.parseColor("#333333"))
                .setTitleBgColor(Color.WHITE)
                .setCancelColor(Color.parseColor("#6fd1c8"))
                .setSubmitColor(Color.parseColor("#6fd1c8"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(16)
                .setOutSideCancelable(false)// default is true
                .build();
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
        pvOptions.show();
    }

    private void initJsonDataRegion(List<NewAddressRegionBean.AreaBean> area) {
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1AllItems = area;

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
            options2AllItems.add(CityList);

            /**
             * 添加地区数据
             */
            options3AllItems.add(Province_AreaList);
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
        allRegionPresenter.dettach();
       SkipIntentUtil.closeAsyncTask(task);
    }
}
