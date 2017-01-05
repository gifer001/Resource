package com.crypt.lord.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.crypt.lord.plugin.PluginResource;
import com.crypt.lord.utils.LogUtils;

import java.io.File;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

/**
 * Created by caiys on 2017/1/4.
 */
public class CryptLordActivityImpl extends Activity {

    private Activity mActivity = null;

    private final String TAG = "CryptLordActivityImpl";

    public void setActivity(
            Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onStart() {
        LogUtils.d(TAG, "onStart...");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate...");

        File file = new File("/mnt/sdcard/.CryptLord/CryptLordHeart.jar");
        int id = -1;

        AssetManager assetManager = PluginResource.getAssetManager(file.getPath());
        Resources resources =  PluginResource.getPluginResource(assetManager, mActivity.getResources());
        DexClassLoader classLoader = new DexClassLoader(file.getAbsolutePath(),
                mActivity.getDir("core", Context.MODE_PRIVATE).getAbsolutePath(),
                null,
                mActivity.getClassLoader());
        try {
            Class<?> loadClass = classLoader.loadClass("com.crypt.lord" + ".R$layout");
            Field[] fields = loadClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("activity_main")) {
                    id = field.getInt(loadClass);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        View v = null;
        if (id != -1) {
            XmlResourceParser layout = resources.getLayout(id);
            v = mActivity.getLayoutInflater().inflate(layout, null);
        }
        mActivity.setTheme(android.R.style.Theme_DeviceDefault);
        if (v != null) {
            mActivity.setContentView(v);

            int btnId = -1;
            try {
                Class<?> loadClass = classLoader.loadClass("com.crypt.lord" + ".R$id");
                Field[] fields = loadClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals("watch_list_button")) {
                        btnId = field.getInt(loadClass);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            View btn = mActivity.findViewById(btnId);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mActivity, "插件点击事件响应。。。", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onRestoreInstanceState...");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onPostCreate...");
    }

    @Override
    protected void onRestart() {
        LogUtils.d(TAG, "onRestart...");
    }

    @Override
    public void onStateNotSaved() {
        LogUtils.d(TAG, "onStateNotSaved...");
    }

    @Override
    protected void onResume() {
        LogUtils.d(TAG, "onResume...");
    }

    @Override
    protected void onPostResume() {
        LogUtils.d(TAG, "onPostResume...");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.d(TAG, "onNewIntent...");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtils.d(TAG, "onSaveInstanceState...");
    }

    @Override
    protected void onPause() {
        LogUtils.d(TAG, "onPause...");
    }

    @Override
    protected void onStop() {
        LogUtils.d(TAG, "onStop...");
    }

    @Override
    protected void onDestroy() {
        LogUtils.d(TAG, "onDestroy...");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.d(TAG, "onKeyDown...");
        return true;
    }
}
