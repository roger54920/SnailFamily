package com.example.ysww.snailfamily.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.SendSaveBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.SendSaveView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.SendSavePresenter;
import com.example.ysww.snailfamily.utils.IDCardValidate;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ysww.snailfamily.R.id.send_contacts;

/**
 * 寄件 填写地址 我要寄件
 */
public class SendCasesAffirmActivity extends AutoLayoutActivity implements SendSaveView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.confirmation_message_btn)
    Button confirmationMessageBtn;
    @InjectView(R.id.snail_address)
    RelativeLayout snailAddress;
    @InjectView(send_contacts)
    TextView sendContacts;
    @InjectView(R.id.send_address)
    TextView sendAddress;
    @InjectView(R.id.send_stdmode)
    RelativeLayout sendStdmode;
    @InjectView(R.id.send_drop_way)
    RelativeLayout sendDropWay;
    @InjectView(R.id.send_carrier)
    RelativeLayout sendCarrier;
    @InjectView(R.id.card_no)
    EditText cardNo;
    @InjectView(R.id.checkbox)
    CheckBox checkbox;
    @InjectView(R.id.sender_message)
    RelativeLayout senderMessage;
    @InjectView(R.id.recipients_message)
    RelativeLayout recipientsMessage;
    @InjectView(R.id.freight_base)
    TextView freightBase;
    @InjectView(R.id.goodsType)
    TextView goodsType;
    @InjectView(R.id.delMethods)
    TextView delMethods;
    @InjectView(R.id.cacompany)
    TextView cacompany;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.provincialCode)
    TextView provincialCode;
    @InjectView(R.id.nearby_snail)
    TextView nearbySnail;
    @InjectView(R.id.sender_detailed)
    TextView senderDetailed;
    @InjectView(R.id.accept_detailed)
    TextView acceptDetailed;
    @InjectView(R.id.sender_message_detailed)
    RelativeLayout senderMessageDetailed;
    @InjectView(R.id.accpet_message_detailed)
    RelativeLayout accpetMessageDetailed;

    //附近蜗牛站地址
    private List<SendSaveBean.WorksStrBean> worksStrItems = new ArrayList<>();
    private List<SendSaveBean.WorksStrBean> worksStrList;


    //投递方式
    private List<SendSaveBean.DelMethodsListBean> delMethodsListItems = new ArrayList<>();
    private List<SendSaveBean.DelMethodsListBean> delMethodsList;

    //物品类型
    private List<SendSaveBean.GoodsTypeListBean> goodsTypeListItems = new ArrayList<>();
    private List<SendSaveBean.GoodsTypeListBean> goodsTypeList;

    //承运公司
    private List<SendSaveBean.CacompanyBean> cacompanyItems = new ArrayList<>();
    private List<SendSaveBean.CacompanyBean> cacompanyList;

    //运费标准
    private List<SendSaveBean.WeightpriceBean> weightprice;

    //寄件保存的参数
    private SendSaveBean.SendSaveParameter sendSaveParameter = new SendSaveBean.SendSaveParameter();

    private SendSavePresenter sendSavePresenter = new SendSavePresenter();//新建寄件

    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载

    private Intent intent;

    private String receiverId;//寄件人Id

    private String worksStrOptions;//附近蜗牛站地址
    private String delMethodsOptions;//投递方式
    private String goodsTypeOptions;//物品类型
    private String cacompanyOptions;//承运公司

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cases_affirm);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);

        lazyLoadProgressDialog.show();
        LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
        getSendResult();
    }

    @OnClick({R.id.provincialCode, R.id.return_arrows, R.id.checkbox, R.id.confirmation_message_btn, R.id.snail_address, send_contacts, R.id.send_address, R.id.send_stdmode, R.id.send_drop_way, R.id.send_carrier, R.id.sender_message, R.id.recipients_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.provincialCode:
                if (!TextUtils.isEmpty(phone.getText())) {
                    intent = new Intent(this, SendNewSiteActivity.class);
                    String isAddress = sendSaveParameter.getIsAddress();
                    if (!TextUtils.isEmpty(isAddress)) {
                        intent.putExtra("name", sendSaveParameter.getReceivername());
                        intent.putExtra("moblie", sendSaveParameter.getReceivermoblie());
                        intent.putExtra("provincename", sendSaveParameter.getReceiverprovincename());
                        intent.putExtra("cityname", sendSaveParameter.getReceivercityname());
                        intent.putExtra("expareaname", sendSaveParameter.getReceiverexpareaname());
                        intent.putExtra("address", sendSaveParameter.getReceiveraddress());
                        intent.putExtra("receiverprovincode", sendSaveParameter.getReceiverprovincode());
                        intent.putExtra("receivercitycode", sendSaveParameter.getReceivercitycode());
                        intent.putExtra("receiverexpareacode", sendSaveParameter.getReceiverexpareacode());
                        intent.putExtra("isAddress", isAddress);
                    }
                    startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                } else {
                    SkipIntentUtil.toastShow(this, "请添加寄件人！");
                }
                break;
            case R.id.return_arrows:
                finish();
                break;
            case R.id.checkbox:
                if (checkbox.isChecked() == true) {
                    confirmationMessageBtn.setEnabled(true);
                } else {
                    confirmationMessageBtn.setEnabled(false);
                }
                break;
            case R.id.confirmation_message_btn:
                String card =cardNo.getText().toString();
                if (!TextUtils.isEmpty(nearbySnail.getText().toString())) {
                    if (!TextUtils.isEmpty(phone.getText().toString())) {
                        if (!TextUtils.isEmpty(provincialCode.getText().toString())) {
                            if (!TextUtils.isEmpty(goodsType.getText().toString())) {
                                if (!TextUtils.isEmpty(delMethods.getText().toString())) {
                                    if (!TextUtils.isEmpty(cacompany.getText().toString())) {
                                        if (!TextUtils.isEmpty(card)) {
                                            String validate_effective = IDCardValidate.validate_effective(card, true);
                                            if (validate_effective.equals(card)) {
                                                lazyLoadProgressDialog.show();
                                                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                                                HashMap<String, String> params = new HashMap<>();
                                                if (sendSaveParameter != null) {
                                                    params.put("worksId", sendSaveParameter.getWorksId());
                                                    params.put("receivername", sendSaveParameter.getReceivername());
                                                    params.put("receivermoblie", sendSaveParameter.getReceivermoblie());
                                                    params.put("receiverprovincename", sendSaveParameter.getReceiverprovincename());
                                                    params.put("receivercityname", sendSaveParameter.getReceivercityname());
                                                    params.put("receiverexpareaname", sendSaveParameter.getReceiverexpareaname());
                                                    params.put("receiveraddress", sendSaveParameter.getReceiveraddress());
                                                    params.put("sendername", sendSaveParameter.getSendername());
                                                    params.put("sendermoblie", sendSaveParameter.getSendermoblie());
                                                    params.put("senderprovincename", sendSaveParameter.getSenderprovincename());
                                                    params.put("sendercityname", sendSaveParameter.getSendercityname());
                                                    params.put("senderexpareaname", sendSaveParameter.getSenderexpareaname());
                                                    params.put("senderaddress", sendSaveParameter.getSenderaddress());
                                                    params.put("goodsname", sendSaveParameter.getGoodsname());
                                                    params.put("isnotice", sendSaveParameter.getIsnotice());
                                                    params.put("shippercode", sendSaveParameter.getShippercode());
                                                    params.put("card", validate_effective);
                                                    params.put("receiverprovincode", sendSaveParameter.getReceiverprovincode());
                                                    params.put("receivercitycode", sendSaveParameter.getReceivercitycode());
                                                    params.put("receiverexpareacode", sendSaveParameter.getReceiverexpareacode());
                                                    params.put("isAddress", sendSaveParameter.getIsAddress());
                                                    params.put("ifDefault", "0");
                                                    JSONObject jsonObject = new JSONObject(params);
                                                    postSendSaveResult(jsonObject.toString());
                                                }
                                            } else {
                                                SkipIntentUtil.toastShow(this, validate_effective);
                                            }
                                        } else {
                                            SkipIntentUtil.toastShow(this, "请输入身份证号码！");
                                        }
                                    } else {
                                        SkipIntentUtil.toastShow(this, "请选择承运公司！");
                                    }
                                } else {
                                    SkipIntentUtil.toastShow(this, "请选择投递方式！");
                                }
                            } else {
                                SkipIntentUtil.toastShow(this, "请选择物品类型！");
                            }
                        } else {
                            SkipIntentUtil.toastShow(this, "请添加收件人！");
                        }
                    } else {
                        SkipIntentUtil.toastShow(this, "请选择寄件人！");
                    }
                } else {
                    SkipIntentUtil.toastShow(this, "请选择蜗牛站地址！");
                }
                break;
            case send_contacts:
                intent = new Intent(this, CommonSiteActivity.class);
                intent.putExtra("hide", "layout");
                intent.putExtra("source", "send_contacts");
                intent.putExtra("item_id", receiverId);
                startActivityForResult(intent, Constants.SUBSCRIPT_ZER0);
                break;
            case R.id.send_address:
                if (!TextUtils.isEmpty(phone.getText().toString())) {
                    intent = new Intent(this, CommonSiteActivity.class);
                    intent.putExtra("hide", "layout");
                    intent.putExtra("source", "send_address");
                    intent.putExtra("item_id", receiverId);
                    startActivityForResult(intent, Constants.SUBSCRIPT_ONE);
                } else {
                    SkipIntentUtil.toastShow(this, "请选择寄件人！");
                }

                break;
            case R.id.snail_address:
                if (worksStrItems != null && worksStrItems.size()>0) {
                    ShowPickerView(nearbySnail, "蜗牛站地址", "worksStr");
                } else {
                    SkipIntentUtil.toastShow(this, "请重新申请信息！");
                }
                break;
            case R.id.send_stdmode:
                if (goodsTypeListItems != null  && goodsTypeListItems.size()>0) {
                    ShowPickerView(goodsType, "物品类型", "goodsType");
                } else {
                    SkipIntentUtil.toastShow(this, "请重新申请信息！");
                }
                break;
            case R.id.send_drop_way:
                if (delMethodsListItems != null  && delMethodsListItems.size()>0) {
                    ShowPickerView(delMethods, "投递方式", "delMethods");
                } else {
                    SkipIntentUtil.toastShow(this, "请重新申请信息！");
                }
                break;
            case R.id.send_carrier:
                    if (cacompanyItems != null  && cacompanyItems.size()>0) {
                        ShowPickerView(cacompany, "承运公司", "cacompany");
                    } else {
                        SkipIntentUtil.toastShow(this, "请重新申请信息！");
                    }
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String source = data.getStringExtra("source_return");
        if (source != null) {
            String name = data.getStringExtra("name");
            String moblie = data.getStringExtra("moblie");
            String detailedAddress = data.getStringExtra("provincename") + data.getStringExtra("cityname") + data.getStringExtra("expareaname") + data.getStringExtra("address");
            switch (source) {
                case "send_contacts_return":
                    setSendResultAddress(phone, senderMessageDetailed, senderDetailed, detailedAddress, name, moblie);
                    receiverId = data.getStringExtra("item_id");
                    sendSaveParameter.setSendername(data.getStringExtra("name"));
                    sendSaveParameter.setSendermoblie(data.getStringExtra("moblie"));
                    sendSaveParameter.setSenderprovincename(data.getStringExtra("provincename"));
                    sendSaveParameter.setSendercityname(data.getStringExtra("cityname"));
                    sendSaveParameter.setSenderexpareaname(data.getStringExtra("expareaname"));
                    sendSaveParameter.setSenderaddress(data.getStringExtra("address"));
                    break;
                case "send_address_return":
                    setSendResultAddress(provincialCode, accpetMessageDetailed, acceptDetailed, detailedAddress, name, moblie);
                    sendSaveParameter.setReceivername(data.getStringExtra("name"));
                    sendSaveParameter.setReceivermoblie(data.getStringExtra("moblie"));
                    sendSaveParameter.setReceiverprovincename(data.getStringExtra("provincename"));
                    sendSaveParameter.setReceivercityname(data.getStringExtra("cityname"));
                    sendSaveParameter.setReceiverexpareaname(data.getStringExtra("expareaname"));
                    sendSaveParameter.setReceiveraddress(data.getStringExtra("address"));
                    sendSaveParameter.setReceiverprovincode(data.getStringExtra("receiverprovincode"));
                    sendSaveParameter.setReceivercitycode(data.getStringExtra("receivercitycode"));
                    sendSaveParameter.setReceiverexpareacode(data.getStringExtra("receiverexpareacode"));
                    String isAddress = data.getStringExtra("isAddress");
                    if (isAddress != null) {
                        sendSaveParameter.setIsAddress(isAddress);
                    }
                    break;
            }
        }
    }
    /**
     * 设置寄件的地址信息
     */
    private void setSendResultAddress(TextView phone, RelativeLayout messageDetailed, TextView detailed, String detailedAddress, String name, String moblie) {
        phone.setText(name + "  " + moblie);
        messageDetailed.setVisibility(View.VISIBLE);
        detailed.setText(detailedAddress);

    }
    String tx = ""; //选中的值

    private void ShowPickerView(final TextView textName, final String typeName, final String type) {// 弹出选择器
        OptionsPickerView  pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                switch (type) {
                    case "worksStr"://蜗牛站地址
                        tx = worksStrItems.get(options1).getPickerViewText();
                        for (int i = 0; i < worksStrItems.size(); i++) {
                            if (tx.equals(worksStrItems.get(i).getRemarks().replace(",", ""))) {
                                sendSaveParameter.setWorksId(worksStrItems.get(i).getId());
                                worksStrOptions = worksStrItems.get(i).getRemarks();
                            }
                        }
                        break;
                    case "goodsType"://商品类型
                        tx = goodsTypeListItems.get(options1).getPickerViewText();
                        for (int i = 0; i < goodsTypeListItems.size(); i++) {
                            if (tx.equals(goodsTypeListItems.get(i).getLabel())) {
                                sendSaveParameter.setGoodsname(goodsTypeListItems.get(i).getValue());
                                goodsTypeOptions = tx;
                            }
                        }
                        break;
                    case "delMethods"://投递方式
                        tx = delMethodsListItems.get(options1).getPickerViewText();
                        for (int i = 0; i < delMethodsListItems.size(); i++) {
                            if (tx.equals(delMethodsListItems.get(i).getLabel())) {
                                sendSaveParameter.setIsnotice(delMethodsListItems.get(i).getValue());
                                delMethodsOptions = tx;
                            }
                        }
                        break;
                    case "cacompany"://承运公司
                        tx = cacompanyItems.get(options1).getPickerViewText();
                        for (int i = 0; i < cacompanyItems.size(); i++) {
                            if (tx.equals(cacompanyItems.get(i).getLabel())) {
                                sendSaveParameter.setShippercode(cacompanyItems.get(i).getValue());
                                cacompanyOptions = tx;
                            }
                        }
                        break;
                }
                textName.setTextColor(Color.parseColor("#333333"));
                textName.setText(tx);
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
        switch (type) {
            case "worksStr":
                pvOptions.setPicker(worksStrItems);//一级选择器
                if (worksStrOptions != null) {
                    for (int i = 0; i < worksStrItems.size(); i++) {
                        if (worksStrItems.get(i).getRemarks().equals(worksStrOptions)) {
                            pvOptions.setSelectOptions(i);
                        }
                    }
                }
                break;
            case "goodsType":
                pvOptions.setPicker(goodsTypeListItems);//一级选择器
                if (goodsTypeOptions != null) {
                    for (int i = 0; i < goodsTypeListItems.size(); i++) {
                        if (goodsTypeListItems.get(i).getLabel().equals(goodsTypeOptions)) {
                            pvOptions.setSelectOptions(i);
                        }
                    }
                }
                break;
            case "delMethods":
                pvOptions.setPicker(delMethodsListItems);//一级选择器
                if (delMethodsOptions != null) {
                    for (int i = 0; i < delMethodsListItems.size(); i++) {
                        if (delMethodsListItems.get(i).getLabel().equals(delMethodsOptions)) {
                            pvOptions.setSelectOptions(i);
                        }
                    }
                }
                break;
            case "cacompany":
                pvOptions.setPicker(cacompanyItems);//一级选择器
                if (cacompanyOptions != null) {
                    for (int i = 0; i < cacompanyItems.size(); i++) {
                        if (cacompanyItems.get(i).getLabel().equals(cacompanyOptions)) {
                            pvOptions.setSelectOptions(i);
                        }
                    }
                }
                break;
        }
        pvOptions.show();
    }

    /**
     * 得到寄件相关信息
     */
    private void getSendResult() {
        new OkHttpResolve(this);
        sendSavePresenter.attach(this);
        sendSavePresenter.getJsonSendResult(this, lazyLoadProgressDialog);

    }

    /**
     * 确认寄件信息
     */
    private void postSendSaveResult(String json) {
        new OkHttpResolve(this);
        sendSavePresenter.attach(this);
        sendSavePresenter.postJsonSendSaveResult(json, this, lazyLoadProgressDialog);

    }

    @Override
    public void onGetSendFinish(Object o) {
        SendSaveBean sendSaveBean = (SendSaveBean) o;
        if (sendSaveBean != null) {
            weightprice = sendSaveBean.getWeightprice();
            //运费标准
            if (weightprice != null && weightprice.size() > 0) {
                freightBase.setText("运费标准：首重1公斤" + weightprice.get(0).getLabel() + "元  继重" + weightprice.get(1).getLabel() + "元");
            }
            //附近蜗牛站
            worksStrList = sendSaveBean.getWorksStr();
            if (worksStrList != null && worksStrList.size() > 0) {
                worksStrItems = worksStrList;
            }

            //投递方式
            delMethodsList = sendSaveBean.getDelMethodsList();
            if (delMethodsList != null && delMethodsList.size() > 0) {
                delMethodsListItems = delMethodsList;
            }
            //物品类型
            goodsTypeList = sendSaveBean.getGoodsTypeList();
            if (goodsTypeList != null && goodsTypeList.size() > 0) {
                goodsTypeListItems = goodsTypeList;
            }
            //承运公司
            cacompanyList = sendSaveBean.getCacompany();
            if (cacompanyList != null && cacompanyList.size() > 0) {
                cacompanyItems = cacompanyList;
            }
        }
    }

    @Override
    public void onAffirmSendSaveFinish(Object o) {
        SendSaveBean.ResultSendBean resultSendBean = (SendSaveBean.ResultSendBean) o;
        if (resultSendBean != null) {
            SkipIntentUtil.skipIntent(this,SendCasesDisplayActivity.class);
           finish();
        }
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
        sendSavePresenter.dettach();
    }
}
