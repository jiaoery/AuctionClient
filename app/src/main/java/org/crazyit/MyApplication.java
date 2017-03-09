package org.crazyit;

import android.app.Application;

import com.bumptech.glide.Glide;

import org.crazyit.auction.client.R;
import org.crazyit.auction.client.util.SharedPrefsUtil;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by cqjix on 2017/3/9.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsUtil.init(getApplicationContext());
        initGalleryFinal();
    }

    private void initGalleryFinal() {
        /*****主题配置****/
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(getResources().getColor(R.color.main_green_26a65b))
                .setFabNornalColor(getResources().getColor(R.color.main_green_deliver))
                .setFabPressedColor(getResources().getColor(R.color.main_green_26a65b))
                .setCheckSelectedColor(getResources().getColor(R.color.main_green_26a65b))
                .build();

        /****功能配置****/
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)//开启相机功能
                .setEnableEdit(true)//开启编辑功能
                .setEnableRotate(true)//开启选择功能
                .setEnablePreview(false)//开启预览功能
                .build();
        /**设置核心配置信息**/
        CoreConfig config = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(config);
    }
}
