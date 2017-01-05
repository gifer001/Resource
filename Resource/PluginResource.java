package com.crypt.lord.plugin;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by caiys on 2017/1/4.
 */
public class PluginResource extends Resources {
    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     */
    public PluginResource(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }

    public static AssetManager getAssetManager(String path) {
        try {
            Class<?> c = Class.forName("android.content.res.AssetManager");
            Method[]  methods = c.getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals("addAssetPath")) {
                    AssetManager assetManager = AssetManager.class.newInstance();
                    m.invoke(assetManager, path);
                    return assetManager;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Resources getPluginResource(AssetManager assetManager, Resources resources) {
        return new PluginResource(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }
}
