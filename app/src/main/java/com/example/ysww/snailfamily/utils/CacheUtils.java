package com.example.ysww.snailfamily.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.ysww.snailfamily.bean.NewAddressRegionBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by me-jie on 2017/6/13.
 */

public class CacheUtils {
    private static File CacheRoot;

    /**
     * 存储Json文件
     * <p>
     * json字符串
     *
     * @param fileName 存储的文件名
     * @param append   true 增加到文件末，false则覆盖掉原来的文件
     */
    public static void writeJson(Context c, NewAddressRegionBean regionBean, String fileName,
                                 boolean append, AsyncTask task) {
        String json = null;
        Gson gson = new Gson();
        if (regionBean != null) {
            json = gson.toJson(gson.toJson(regionBean)).replace("\\", "");
        }
        if (json != null) {
            CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                    .getExternalCacheDir() : c.getCacheDir();
            FileOutputStream fos = null;
            ObjectOutputStream os = null;
            try {
                File ff = new File(CacheRoot, fileName);
                boolean boo = ff.exists();
                fos = new FileOutputStream(ff, append);
                os = new ObjectOutputStream(fos);
                if (append && boo) {
                    FileChannel fc = fos.getChannel();
                    fc.truncate(fc.position() - 4);

                }

                os.writeObject(json);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {

                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                if (os != null) {

                    try {
                        os.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                SkipIntentUtil.closeAsyncTask(task);

            }
        }
    }

    /**
     * 读取json数据
     *
     * @param c
     * @param fileName
     * @return 返回值为list
     */

    @SuppressWarnings("resource")
    public static String readJson(Context c, String fileName) {
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String result = null;
        File des = new File(CacheRoot, fileName);
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0)
                result = (String) ois.readObject();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        return result;
    }

    /**
     * 二次封装解析数据
     *
     * @param activity
     * @return
     */
    public static List<NewAddressRegionBean.AreaBean> analysisJson(final Activity activity) {
        String strings = CacheUtils.readJson(activity, "nationalregions.txt");
        if (strings != null) {
            strings = strings.substring(1, strings.length() - 1);
            return GsonUtils.getGson(strings, NewAddressRegionBean.class).getArea();
        }
        return null;
    }
}