package com.xogrp.tkgz.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by ayu on 1/8/2016 0008.
 * Provide the methods for select a picture from album and crop it.
 */
public abstract class AbstractCropImageFragment extends AbstractTKGZFragment {
    private static final String APP_DIR = Environment.getExternalStorageDirectory().getPath() + "/TKGZ/";
    public static final String IMAGE_PATH = APP_DIR + "icon_cache/";
    public static final String AVATAR_FILE_NAME = "avatar.jpeg";
    public static final String BACKGROUND_FILE_NAME = "background.jpeg";

    public static final int REFRESH_AVATAR = 2;
    public static final int REFRESH_BACKGROUND = 3;
    public static final int SELECT_A_PICTURE_FOR_AVATAR = 6;
    public static final int SELECT_A_PICTURE_FOR_BACKGROUND = 7;

    private static Uri mImageUri;
    private File mAvatar;
    private File mBackGround;


    /**
     * According the Androids API, select the different method.
     *
     * @param activity   The activity.
     * @param uri        The image.
     * @param resultCode Select image for different use.
     */
    protected void selectImage(Activity activity, Uri uri, int resultCode) {
        mImageUri = uri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            selectImageUriBeforeKitkat(activity, resultCode);
        } else {
            selectImageUriAfterKitKat(activity, resultCode);
        }
    }

    /**
     * Use this method to select Image when Android API less than 19.
     *
     * @param activity   The activity.
     * @param resultCode Select image for different use.
     */
    private void selectImageUriBeforeKitkat(Activity activity, int resultCode) {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            activity.startActivityForResult(intent, resultCode);
        }
    }

    /**
     * Use this method to select Image when Android API greater than 19.
     *
     * @param activity   The activity.
     * @param resultCode Select image for different use.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKitKat(Activity activity, int resultCode) {
        if (activity != null) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            activity.startActivityForResult(intent, resultCode);
        }
    }

    /**
     * Crop Image
     *
     * @param activity   The activity receive the resultCode.
     * @param uri        The selected image.
     * @param width      The width for the sheared image.
     * @param height     The height for the sheared image.
     * @param resultCode The different views receive the sheared image.
     */
    public static void cropImageUri(Activity activity, Uri uri, int width, int height, int resultCode) {
        if (activity != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/jpeg");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", width);
            intent.putExtra("aspectY", height);
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
            intent.putExtra("scale", true);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", false);
            intent.putExtra("noFaceDetection", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            activity.startActivityForResult(intent, resultCode);
        }
    }

    protected Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            getLogger().error("AbstractCropImageFragment: decodeUriAsBitmap", e.getMessage());
        }
        return bitmap;
    }

    protected void openOrCreatePicFile() {
        folderIsExist(new File(APP_DIR));
        folderIsExist(new File(IMAGE_PATH));
        mAvatar = new File(IMAGE_PATH, AVATAR_FILE_NAME);
        mBackGround = new File(IMAGE_PATH, BACKGROUND_FILE_NAME);
    }

    protected File getAvatarFile() {
        return mAvatar;
    }

    protected File getBackgroundFile() {
        return mBackGround;
    }
}
