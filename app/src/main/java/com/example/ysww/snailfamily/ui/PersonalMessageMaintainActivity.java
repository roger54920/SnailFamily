package com.example.ysww.snailfamily.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.mvp.AllRegionView;
import com.example.ysww.snailfamily.mvp.UpdateUserView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.AllRegionPresenter;
import com.example.ysww.snailfamily.presenter.UpdateUserPresenter;
import com.example.ysww.snailfamily.utils.CacheUtils;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 个人信息维护
 */
public class PersonalMessageMaintainActivity extends AutoLayoutActivity implements UpdateUserView, AllRegionView {
    @InjectView(R.id.input_name)
    EditText inputName;
    @InjectView(R.id.common_address_rl)
    RelativeLayout commonAddressRl;
    @InjectView(R.id.btn_finish)
    Button btnFinish;
    @InjectView(R.id.return_arrows)
    ImageView returnArrows;

    private Intent intent; //得到之前用户的intent数据
    private UpdateUserPresenter updateUserPresenter = new UpdateUserPresenter();
    private AllRegionPresenter allRegionPresenter = new AllRegionPresenter();//全国地址
    private String user_id;//用户ID
    private String mobile;//手机号
    private Intent skipIntent;//跳转Intent
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_message_maintain);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(this);
        btnFinish.setClickable(true);
        intent = getIntent();
        if (intent != null) {
            user_id = intent.getStringExtra("user_id");
            mobile = intent.getStringExtra("mobile");
        }

    }

    /**
     * 初始化Json请求数据
     *
     * @param str
     */
    private void updateUserJson(String str) {
        new OkHttpResolve(this);
        updateUserPresenter.attach(this);
        updateUserPresenter.postJsonResult(str, this, lazyLoadProgressDialog);
    }

    /**
     * 全国地区初始化请求数据
     */

    private void requestDataAllRegion() {
        new OkHttpResolve(this);
        allRegionPresenter.attach(this);
        allRegionPresenter.postAllRegionJsonResult("{\"id\":\" \"}", this, lazyLoadProgressDialog);
    }

    @Override
    public void onBeanFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            String status = loginBean.getStatus();
            if (status != null) {
                sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                editor = sp.edit();
                if (loginBean.getUser().getRoleName() != null) {
                    btnFinish.setClickable(false);
                    String roleName = loginBean.getUser().getRoleName();
                    long sessionTimeout = loginBean.getSessionTimeout();
                    if (roleName.equals("consumer")) {
                        editor.putString("roleName", roleName);
                        editor.putLong("timeout", System.currentTimeMillis() + sessionTimeout);
                        editor.putString("photo", loginBean.getUser().getPhoto());
                        editor.putString("mobile",loginBean.getUser().getMobile());
                        editor.commit();

                        SkipIntentUtil.skipIntent(this, BottomNavigationMenuActivity.class);
                        requestDataAllRegion();
                    }
                }
            }
        }
    }

    @OnClick({R.id.common_address_rl, R.id.btn_finish, R.id.return_arrows})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_address_rl:
                skipIntent = new Intent(PersonalMessageMaintainActivity.this, NewAddAddressActivity.class);
                skipIntent.putExtra("user_id", user_id);
                skipIntent.putExtra("source", "personal_message");
                skipIntent.putExtra("mobile", mobile);
                startActivityForResult(skipIntent, 0);
                break;
            case R.id.btn_finish:
                String inputname = inputName.getText().toString();
                if (!TextUtils.isEmpty(inputname)) {
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("name", inputname);
                    params.put("id", user_id);
                    params.put("mobile", mobile);
                    params.put("roleName", "consumer");
                    JSONObject jsonObject = new JSONObject(params);
                    updateUserJson(jsonObject.toString());
                } else {
                    SkipIntentUtil.toastShow(this, "请输入姓名！");
                }

                break;
            case R.id.return_arrows:
                finish();
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Constants.SOURCE = data.getStringExtra("source");
        if (Constants.SOURCE != null && Constants.SOURCE.equals("new_add_address")) {
            commonAddressRl.setVisibility(View.INVISIBLE);
            SkipIntentUtil.toastShow(this, "新增地址成功，点击完成注册！");
        }
    }

    @Override
    public void onAllRegionListFinish(Object o) {
        final NewAddressRegionBean newAddressRegionBean = (NewAddressRegionBean) o;
        if (newAddressRegionBean != null) {
            new AsyncTask<String, Void, NewAddressRegionBean>() {
                @Override
                protected NewAddressRegionBean doInBackground(String... params) {
                    CacheUtils.writeJson(PersonalMessageMaintainActivity.this, newAddressRegionBean, "nationalregions.txt", false,this);
                    return null;
                }
            }.execute();
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
        updateUserPresenter.dettach();
        allRegionPresenter.dettach();
    }
}
