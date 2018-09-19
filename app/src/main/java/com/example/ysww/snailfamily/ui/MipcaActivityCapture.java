package com.example.ysww.snailfamily.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.AddressBean;
import com.example.ysww.snailfamily.bean.SendDetailsBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AScanCodeView;
import com.example.ysww.snailfamily.mvp.SendDisplayView;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.net.NetBroadcastReceiver;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AScanCodePresenter;
import com.example.ysww.snailfamily.presenter.SendDisplayPresenter;
import com.example.ysww.snailfamily.utils.AcquisitionTimeUtil;
import com.example.ysww.snailfamily.utils.FileUtils;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.NetUtil;
import com.example.ysww.snailfamily.utils.RequestOperationUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 用户二维码扫描
 */
public class MipcaActivityCapture extends BaseActivity implements QRCodeView.Delegate, AScanCodeView, SendDisplayView {

    @InjectView(R.id.return_arrows)
    ImageView returnArrows;
    @InjectView(R.id.open_flashlight)
    TextView openFlashlight;
    @InjectView(R.id.close_flashlight)
    TextView closeFlashlight;
    @InjectView(R.id.zxingview)
    ZXingView mQRCodeView;
    private static final String TAG = MipcaActivityCapture.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private Intent intent;
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private AScanCodePresenter aScanCodePresenter = new AScanCodePresenter();  //收件 扫码
    private SendDisplayPresenter sendDisplayPresenter = new SendDisplayPresenter();  //寄件 扫码
    private BroadcastReceiver receiver;//获取广播对象

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mipca_capture);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
        lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        mQRCodeView.startSpot();
        scanQRCodeSuccess(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mQRCodeView.showScanRect();
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = FileUtils.getRealFilePath(this, data.getData());
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
                }

                @Override
                protected void onPostExecute(String result) {
                    scanQRCodeSuccess(result);
                    mQRCodeView.startSpot();
                }
            }.execute();

        }
    }

    /**
     * 扫码成功
     *
     * @param result
     */
    private void scanQRCodeSuccess(String result) {
        if (TextUtils.isEmpty(result)) {
            SkipIntentUtil.toastShow(this, "未发现二维码！");
        } else {
            //启动时判断网络状态
            Constants.NETCONNECT = this.isNetConnect();
            if (Constants.NETCONNECT == false) {
                SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
                Constants.NETCONNECT = false;
            } else {
                Constants.NETCONNECT = true;
                lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
                lazyLoadProgressDialog.show();
                LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                if (result.length() == 32) {
                    postSendScanCodeResult("{\"senderId\":\"" + result + "\"}");
                } else {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("expressParcel", new JSONObject().put("lgisticCode", result));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    postAScanCodeResult(json.toString());
                }
            }

        }
    }

    /**
     * 初始化收件扫码数据
     */
    private void postAScanCodeResult(String json) {
        new OkHttpResolve(this);
        aScanCodePresenter.attach(this);
        aScanCodePresenter.postJsonScanCodeResult(json, this, lazyLoadProgressDialog);
    }

    /**
     * 初始化寄件扫码数据
     */
    private void postSendScanCodeResult(String json) {
        new OkHttpResolve(this);
        sendDisplayPresenter.attach(this);
        sendDisplayPresenter.postJsonSendResult(json, this, lazyLoadProgressDialog);
    }

    @OnClick({R.id.return_arrows, R.id.open_flashlight, R.id.close_flashlight, R.id.choose_qrcde_from_gallery})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.return_arrows:
                finish();
                break;
            case R.id.open_flashlight:
                mQRCodeView.openFlashlight();
                openFlashlight.setVisibility(View.GONE);
                closeFlashlight.setVisibility(View.VISIBLE);
                break;
            case R.id.close_flashlight:
                mQRCodeView.closeFlashlight();
                openFlashlight.setVisibility(View.VISIBLE);
                closeFlashlight.setVisibility(View.GONE);
                break;
            case R.id.choose_qrcde_from_gallery:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
        }

    }

    @Override
    public void onAScanCodeFinish(Object o) {
        AddressBean addressBean = (AddressBean) o;
        if (addressBean != null) {
            String taskDefKey = addressBean.getAct().getTaskDefKey();
            if (taskDefKey != null) {
                switch (taskDefKey) {
                    case "sendImmediatelyChoose":
                        intent = new Intent(this, ParcelParticularsActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "mipca_capture_sendImmediatelyChoose");
                        break;
                    case "sendImmediately":
                        intent = new Intent(this, ParcelParticularsPredictActivity.class);
                        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, "mipca_capture_sendImmediately");
                        try {
                            String time = AcquisitionTimeUtil.longToString(addressBean.getAct().getVars().getMap().getTime(), "yyyy-MM-dd HH:mm:ss");
                            intent.putExtra("time", time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "singExpress":
                        intnetRequiredParameter(addressBean, "mipca_capture_singExpress", ParcelParticularsPredictActivity.class);
                        try {
                            String time = AcquisitionTimeUtil.longToString(addressBean.getAct().getVars().getMap().getTime(), "yyyy-MM-dd HH:mm:ss");
                            intent.putExtra("time", time);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                isAcceptPay(addressBean.getExpressAccept().getType());
                startActivity(intent);
                finish();
            } else {
                //默认页面
                intnetRequiredParameter(addressBean, "mipca_capture", ParcelParticularsActivity.class);
                isAcceptPay(addressBean.getExpressAccept().getType());
                startActivity(intent);
                finish();
            }
        }
    }

    /**
     * 是否是代付
     *
     * @param type
     */
    private void isAcceptPay(String type) {
        if (type.equals("1")) {
            intent.putExtra("home_source", "accept");
        } else if (type.equals("2")) {
            intent.putExtra("home_source", "acceptPay");
        }
    }

    /**
     * 收件跳转必要参数
     *
     * @param addressBean
     * @param source
     */
    private void intnetRequiredParameter(AddressBean addressBean, String source, Class cl) {
        intent = new Intent(this, cl);
        RequestOperationUtil.intentAcceptNecessaryParameters(addressBean, intent, source);
        AddressBean.ExpressAcceptBean expressAccept = addressBean.getExpressAccept();
        intent.putExtra("cellCode", expressAccept.getWorkstation().getCellCode());
        intent.putExtra("workstation_id", expressAccept.getWorkstation().getId());
        intent.putExtra("createDate", expressAccept.getCreateDate());

    }

    @Override
    public void onSendParticularsListFinish(Object o) {

    }

    @Override
    public void onSendParticularsFinish(Object o) {
        SendDetailsBean sendDetailsBean = (SendDetailsBean) o;
        if (sendDetailsBean != null) {
            String taskKey = sendDetailsBean.getMapsender().getTaskKey();
            if (taskKey != null) {
                switch (taskKey) {
                    case "appalyUser":
                        intent = new Intent(this, SendCasesChatActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "mipca_capture_appalyUser");
                        break;
                    case "sendImmediately":
                        intent = new Intent(this, SendSetTimeActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "mipca_capture_sendImmediately");
                        try {
                            String time = AcquisitionTimeUtil.longToString(sendDetailsBean.getMapsender().getTime(), "yyyy-MM-dd HH:mm:ss");
                            intent.putExtra("time", time);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "userPay":
                        intent = new Intent(this, SendCasesChatAliPayActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "mipca_capture_userPay");
                        break;
                    case "0":
                        intent = new Intent(this, SendCasesOrderNumberActivity.class);
                        RequestOperationUtil.intentSendNecessaryParameters(sendDetailsBean, intent, "mipca_capture_0");
                        break;
                }
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
        }
    }
    @Override
    protected void onPause() {
        SkipIntentUtil.toastStop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        aScanCodePresenter.dettach();
        sendDisplayPresenter.dettach();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
}