package com.example.anukrit.quiescent.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class ImageUtils {

    private static ImageUtils imageUtils;

    public void setCircleCropImage(Context context, String url, ImageView imageView, final ProgressBar progressBar)
    {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setImage(final Context context, String url, ImageView imageView, final ProgressBar progressBar)
    {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
//                .apply(RequestOptions.placeholderOf())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setImageWithPlaceholder(final Context context, String url, ImageView imageView, int resPlaceholder)
    {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.centerInsideTransform())
                .apply(RequestOptions.placeholderOf(resPlaceholder))
                .into(imageView);
    }

    public static ImageUtils newInstance()
    {
        if (imageUtils == null)
            imageUtils = new ImageUtils();
        return imageUtils;
    }

}
