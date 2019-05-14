package com.example.anukrit.quiescent.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;




public class ImageUtils {

    public static void loadImg(Context context, String imgUrl, ImageView imageView) {
        if (!GeneralUtils.isNull(imgUrl)) {
            Glide.with(context)
                    .load(imgUrl)
                    .into(imageView);
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .thumbnail(0.5f)
//                    .into(imageView);

        }
    }

    /*public static void loadImgCenterCrop(Context context, String imgUrl, ImageView imageView) {
        if (!GeneralUtils.isNull(imgUrl)) {
            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(R.color.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    public static void loadImgWithoutCaching(Context context, String imgUrl, ImageView imageView) {
        if (!GeneralUtils.isNull(imgUrl)) {
        *//*Picasso.with(context)
                .load(imgUrl)
                .fit()
                .into(imageView);*//*

            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(R.color.placeholder)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        }
    }

    public static void loadImg(Context context, Uri imgUrl, ImageView imageView) {
        if (imgUrl != null) {
        *//*Picasso.with(context)
                .load(imgUrl)
                .fit()
                .into(imageView);*//*

            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .into(imageView);
        }
    }

    public static void loadThumbnailWithDimensions(Context context, String imageUrl, AspectRatioImageView imageView, int width, int height) {
        imageView.setAspectRatio((float) height / (float) width);
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.color.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .override(width / 2, height / 2)
                .into(imageView);
    }

    public static void loadImageWithDimensions(Context context, String imageUrl, AspectRatioImageView imageView, int width, int height) {
        imageView.setAspectRatio((float) height / (float) width);
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.color.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .fitCenter()
                .override(width, height)
                .into(imageView);
*//*            String l1 = "Card IMG [" + data.getId() + "] : W = " + data.getWidth() + " H = " + data.getHeight() + " R : " + (float) data.getHeight() / (float) data.getWidth();
            String l2 = "Card IV [" + data.getId() + "] : W = " + cardimage.width + " H = " + cardimage.height + " R : " + (float) cardimage.height / (float) cardimage.width;
            cardtext.setText(l1 + "\n" + l2);*//*
    }

    public static void loadProfileImages(Context context, String imgUrl, ImageView imageView) {
        if (!GeneralUtils.isNull(imgUrl)) {
            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.avatar_8)
                    .dontAnimate()
                    .fitCenter()
                    .into(imageView);
        }
    }

    public static void preLoadImage(Context context, String imgUrl) {
        if (!GeneralUtils.isNull(imgUrl)) {
            GlideApp.with(context)
                    .load(imgUrl).preload();
        }
    }

    public static void loadImageWithThumbnail(Context context, String thumbnailUrl, String imgUrl, ImageView imageView) {
        if (!GeneralUtils.isNull(thumbnailUrl) && !GeneralUtils.isNull(imgUrl)) {

            // setup Glide request without the into() method
            RequestBuilder<Drawable> thumbnailRequest = GlideApp
                    .with(context)
                    .load(thumbnailUrl);

            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(imageView.getDrawable())
                    .fitCenter()
                    .thumbnail(thumbnailRequest)
                    .into(imageView);
        }
    }

    public static void loadImg(Context context, String imgUrl, ImageView imageView, int placeholder) {
        if (!GeneralUtils.isNull(imgUrl)) {
            GlideApp.with(context)
                    .load(imgUrl)
                    .placeholder(placeholder)
                    .fitCenter()
                    .into(imageView);
        *//*Picasso.with(context)
                .load(imgUrl)
                .placeholder(placeholder)
                .fit()
                .into(imageView);*//*
        }
    }

    public static void loadImg(Context context, int image, ImageView imageView) {
        try {
            GlideApp.with(context)
                    .load(image)
                    .fitCenter()
                    .into(imageView);
        } catch (IllegalArgumentException ex){
            // Crash #511
            ErrorUtils.logTryCatch(ex);
        }
    }

    public static void loadImgCenterCrop(Context context, int image, ImageView imageView) {
        GlideApp.with(context)
                .load(image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, final @DrawableRes
            int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (drawable == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap) {
        final int MAX_DIMENSTION = 1024;
        int maxHeight = MAX_DIMENSTION;
        int maxWidth = MAX_DIMENSTION;
        if (bitmap.getWidth() > maxWidth || bitmap.getHeight() > maxHeight) {
            try {
                float scale = Math.min(((float) maxHeight / bitmap.getWidth()), ((float) maxWidth / bitmap.getHeight()));
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                // "RECREATE" THE NEW BITMAP
                Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                bitmap.recycle();
                return resizedBitmap;
            } catch (OutOfMemoryError ex) {
                // Crash #509
                ErrorUtils.logTryCatch(ex);
            }
        }

        return bitmap;
    }*/

}
