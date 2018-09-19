package com.example.ysww.snailfamily.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ysww.snailfamily.BuildConfig;
import com.example.ysww.snailfamily.R;
import com.example.ysww.snailfamily.bean.LoginBean;
import com.example.ysww.snailfamily.bean.UpLoadImageBean;
import com.example.ysww.snailfamily.custom.GlideCircleTransform;
import com.example.ysww.snailfamily.dialog.ActionSheetDialog;
import com.example.ysww.snailfamily.dialog.LazyLoadProgressDialog;
import com.example.ysww.snailfamily.dialog.SystemPromptDialog;
import com.example.ysww.snailfamily.mvp.LoginView;
import com.example.ysww.snailfamily.mvp.UpLoadImageView;
import com.example.ysww.snailfamily.mvp.UpdatePhotoView;
import com.example.ysww.snailfamily.okgo.OkHttpResolve;
import com.example.ysww.snailfamily.presenter.LoginPresenter;
import com.example.ysww.snailfamily.presenter.UpLoadImagePresenter;
import com.example.ysww.snailfamily.presenter.UpdatePhotoPresenter;
import com.example.ysww.snailfamily.ui.ClipImageActivity;
import com.example.ysww.snailfamily.ui.CommonSiteActivity;
import com.example.ysww.snailfamily.ui.LoginActivity;
import com.example.ysww.snailfamily.ui.UserOperationBalanceActivity;
import com.example.ysww.snailfamily.ui.shopping.MyOrderActivity;
import com.example.ysww.snailfamily.utils.BitmapUtil;
import com.example.ysww.snailfamily.utils.LazyLoadUtil;
import com.example.ysww.snailfamily.utils.SkipIntentUtil;
import com.lzy.okgo.OkGo;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.example.ysww.snailfamily.Constants.UP_LOAD_IMAGE_TOP;

/**
 * Created by me-jie on 2017/4/8.
 * 我的个人中心
 */

public class MeFragment extends Fragment implements LoginView, UpLoadImageView, UpdatePhotoView {
    @InjectView(R.id.head_portrait)
    ImageView headPortrait;
    @InjectView(R.id.isLogin)
    TextView isLogin;
    @InjectView(R.id.personal_address_tv)
    RelativeLayout personalAddressTv;
    @InjectView(R.id.my_account)
    RelativeLayout myAccount;

    private LoginPresenter loginPresenter = new LoginPresenter();//登录
    private UpLoadImagePresenter upLoadImagePresenter = new UpLoadImagePresenter();//上传图片
    private UpdatePhotoPresenter updatePhotoPresenter = new UpdatePhotoPresenter();//更新头像
    private LazyLoadProgressDialog lazyLoadProgressDialog;//延迟加载
    private View view;

    private Intent intent;
    private String mobile;

    SharedPreferences sp; //免登陆
    SharedPreferences.Editor editor;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //调用照相机返回图片文件
    private File tempFile;
    //提交的连接地址
    private String srcPath;
    //本地的地址
    private String cropImagePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.inject(this, view);
        lazyLoadProgressDialog = LazyLoadProgressDialog.createDialog(getActivity());
        initViews();
        return view;
    }

    /**
     * 初始化布局
     */
    private void initViews() {
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sp.edit();
        String photo = sp.getString("photo", null);
        mobile = sp.getString("mobile", null);
        if (photo != null) {
            Glide.with(this).load(UP_LOAD_IMAGE_TOP + photo)
                    .transform(new GlideCircleTransform(getActivity()))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                    .placeholder(headPortrait.getDrawable())//加载成功之前
                    .error(headPortrait.getDrawable())//加载失败
                    .crossFade()
                    .into(headPortrait);
        } else {
            headPortrait.setImageResource(R.drawable.circle_shape);
        }
    }

    @OnClick({R.id.isLogin, R.id.head_portrait, R.id.personal_address_tv, R.id.my_order,R.id.my_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.isLogin:
                onClickWrittenOff();
                break;
            case R.id.head_portrait:
                actionSheetDialog();
                break;
            case R.id.personal_address_tv:
                intent = new Intent(getActivity(), CommonSiteActivity.class);
                intent.putExtra("source", "fragment_me");
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.my_order:
                SkipIntentUtil.skipIntent(getActivity(), MyOrderActivity.class);
                break;
            case R.id.my_account:
                SkipIntentUtil.skipIntent(getActivity(), UserOperationBalanceActivity.class);
                break;
        }
    }

    /**
     * 底部弹窗
     */
    private void actionSheetDialog() {
        final ActionSheetDialog actionSheetDialog = new ActionSheetDialog(getActivity(), new ActionSheetDialog.ActionSheetClickListener() {
            @Override
            public void onClickTakePhoto(ActionSheetDialog actionSheetDialog) {
                //权限判断
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                actionSheetDialog.dismiss();

            }

            @Override
            public void onClickChoosePhoto(ActionSheetDialog actionSheetDialog) {
                //权限判断
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                actionSheetDialog.dismiss();
            }

            @Override
            public void onClickCancel(ActionSheetDialog actionSheetDialog) {
                actionSheetDialog.dismiss();
            }
        }).setActionSheetDialog();
        actionSheetDialog.show();

    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/thesnailfamily/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }

                    cropImagePath = getRealFilePathFromUri(getActivity().getApplicationContext(), uri);
                    srcPath = cropImagePath;
                    lazyLoadProgressDialog.show();
                    LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
                    requestUpLoadImageResult();
//                    Bitmap bitMap = BitmapFactory.decodeFile(srcPath);
//
//                        headPortrait.setImageBitmap(bitMap);
//                    //此处后面可以将bitMap转为二进制上传后台网络

                }
                break;
        }
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    //点击注销按钮
    private void onClickWrittenOff() {
        SystemPromptDialog.Builder builder = new SystemPromptDialog.Builder(getActivity(), "确定要注销吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                editor.remove("login");
                editor.clear();
                editor.commit();
                SkipIntentUtil.skipIntent(getActivity(), LoginActivity.class);
                getActivity().overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
                getActivity().finish();
                requestWriteOff();
                //设置你的操作事项
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

    /**
     * 初始化注销
     */
    private void requestWriteOff() {
        new OkHttpResolve(getActivity());
        loginPresenter.attach(this);
        loginPresenter.userWriteOffResult(getActivity());
    }

    /**
     * 初始化上传图片
     */
    private void requestUpLoadImageResult() {
        new OkHttpResolve(getActivity());
        upLoadImagePresenter.attach(this);
        upLoadImagePresenter.postJsonResult("user", srcPath, getActivity(), lazyLoadProgressDialog);
    }

    /**
     * 初始化更新头像
     */
    private void requestUpdatePhotoResult() {
        new OkHttpResolve(getActivity());
        updatePhotoPresenter.attach(this);
        updatePhotoPresenter.postJsonResult("{\"photo\":\"" + srcPath + "\"}", getActivity(), lazyLoadProgressDialog);
    }

    @Override
    public void onBeanLoginFinish(Object o) {

    }

    @Override
    public void onBeanWriteOffFinish(Object o) {
        LoginBean loginBean = (LoginBean) o;
        if (loginBean != null) {
            Log.e(getTag(), "onBeanWriteOffFinish: 注销成功！");
        }
    }

    @Override
    public void onUploadImageFinish(Object o) {
        UpLoadImageBean upLoadImageBean = (UpLoadImageBean) o;
        if (upLoadImageBean != null) {
            srcPath = upLoadImageBean.getRelativePath();
            lazyLoadProgressDialog.show();
            LazyLoadUtil.SetLazyLad(lazyLoadProgressDialog);
            requestUpdatePhotoResult();
        } else {
            srcPath = null;
        }
    }

    @Override
    public void onUpdatePhotoFinish(Object o) {
        UpLoadImageBean upLoadImageBean = (UpLoadImageBean) o;
        if (upLoadImageBean != null) {
            if (!TextUtils.isEmpty(srcPath)) {
                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                BitmapUtil.saveImageToGallery(bitMap, mobile);
                BitmapUtil.RecursionDeleteFile(new File(cropImagePath));
                Glide.with(this).load(UP_LOAD_IMAGE_TOP + srcPath)
                        .transform(new GlideCircleTransform(getActivity()))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //添加缓存
                        .placeholder(headPortrait.getDrawable())//加载成功之前
                        .error(headPortrait.getDrawable())//加载失败
                        .crossFade()
                        .into(headPortrait);
                editor.putString("photo", srcPath);
                editor.commit();

            }
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
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);
        loginPresenter.dettach();
        upLoadImagePresenter.dettach();
        updatePhotoPresenter.dettach();
    }
}
