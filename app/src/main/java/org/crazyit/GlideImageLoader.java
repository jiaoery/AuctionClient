package org.crazyit;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;

import org.crazyit.auction.client.R;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * 配置gallery的加载框架为glide
 * Created by cqjix on 2017/3/9.
 */

public class GlideImageLoader implements ImageLoader {

    public static int TAG=0X12312;

    /**
     * 重载galleryfinal的图片加载框架（默认为imageloader）
     * @param activity
     * @param path
     * @param imageView
     * @param defaultDrawable
     * @param width
     * @param height
     */
    @Override
    public void displayImage(final Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        Glide.with(activity)
                .load("file://"+path)
                .placeholder(defaultDrawable)
                .override(width,height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .centerCrop()
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void setRequest(Request request) {
                        imageView.setTag(TAG,request);
                    }

                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(TAG);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {

    }
}
