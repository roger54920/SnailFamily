package com.example.ysww.snailfamily.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysww.snailfamily.Constants;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.fragment.FragmentSwitchTool;
import com.example.ysww.snailfamily.fragment.HomeFragment;
import com.example.ysww.snailfamily.fragment.MeFragment;
import com.example.ysww.snailfamily.fragment.ShappingFragment;
import com.example.ysww.snailfamily.net.BaseActivity;
import com.example.ysww.snailfamily.net.NetBroadcastReceiver;
import com.example.ysww.snailfamily.utils.MyActivityManager;
import com.example.ysww.snailfamily.utils.NetUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.example.ysww.snailfamily.utils.StatusBarUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 底部导航菜单
 */
public class BottomNavigationMenuActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{
    private static final String TAG = "BottomNavigationMenuAct";
    @InjectView(R.id.ivHome)
    ImageView ivHome;
    @InjectView(R.id.tvHome)
    TextView tvHome;
    @InjectView(R.id.llHome)
    LinearLayout llHome;
    @InjectView(R.id.ivShopping)
    ImageView ivShopping;
    @InjectView(R.id.tvShopping)
    TextView tvShopping;
    @InjectView(R.id.llShopping)
    LinearLayout llShopping;
    @InjectView(R.id.ivMe)
    ImageView ivMe;
    @InjectView(R.id.tvMe)
    TextView tvMe;
    @InjectView(R.id.llMe)
    LinearLayout llMe;
    @InjectView(R.id.navigation_bg)
    RelativeLayout navigationBg;
    private FragmentSwitchTool tool;
    private BroadcastReceiver receiver;//获取广播对象
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_menu);
        ButterKnife.inject(this);
        StatusBarUtil.SetStatusBar(this);
        if (checkDeviceHasNavigationBar()==true) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(0, 0, 0, getNavigationBarHeight());
            navigationBg.setLayoutParams(lp);
        }
        receiver = new NetBroadcastReceiver();
        StatusBarUtil.registerBroadrecevicer(this, receiver);
        initFragments();
        //启动时判断网络状态
        Constants.NETCONNECT = this.isNetConnect();
        if (Constants.NETCONNECT == false) {
            lazyLoadProgressDialog = lazyLoadProgressDialog.createDialog(this);
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        }else{
            Constants.NETCONNECT = true;
        }
    }
    public boolean checkDeviceHasNavigationBar() {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(this)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }
    /**
     * 获取底部导航栏的高度
     *
     * @return
     */
    private int getNavigationBarHeight() {
        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 初始化Fragment
     */
    private void initFragments() {
        tool = new FragmentSwitchTool(getFragmentManager(), R.id.flContainer);
        tool.setClickableViews(llHome, llShopping, llMe);
        tool.addSelectedViews(new View[]{ivHome, tvHome}).addSelectedViews(new View[]{ivShopping, tvShopping})
                .addSelectedViews(new View[]{ivMe, ivMe});
        tool.setFragments(HomeFragment.class, ShappingFragment.class, MeFragment.class);
        Constants.SOURCE = getIntent().getStringExtra("source");
        if (Constants.SOURCE != null) {
            switch (Constants.SOURCE) {
                case "fragment_me":
                    tool.changeTag(llMe);
                    break;
                case "shopping":
                    tool.changeTag(llShopping);
                    break;
                default:
                    tool.changeTag(llHome);
                    break;
            }
        } else {
            tool.changeTag(llHome);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        requestCodeCameraPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }

    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeCameraPermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }
    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            SkipIntentUtil.noNetworkPopUpWindows(this, lazyLoadProgressDialog);
        }
    }

    /**
     * Android中的“再按一次返回键退出程序”实现
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出蜗牛快递！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MyActivityManager.getInstance().finishAllActivity();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

}
