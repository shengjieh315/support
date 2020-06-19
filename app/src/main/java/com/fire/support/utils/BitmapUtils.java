package com.fire.support.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class BitmapUtils {

    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 1280f;//这里设置高度为800f
        float ww = 720f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 400) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static String imageResize(Context context, String path, int limitWidth, int limitHeight, File... aimFile) {
        String newPath = "";
        if (null != aimFile && aimFile.length > 0)
            newPath = aimFile[0].getAbsolutePath();
        if (TextUtils.isEmpty(newPath)) {
            File file = new File(context.getExternalCacheDir(), "temp" + System.currentTimeMillis() + ".jpg");
            newPath = file.getAbsolutePath();
        }
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, onlyBoundsOptions);
        onlyBoundsOptions.inJustDecodeBounds = false;
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        float scale = 1;

        if (limitWidth > 0 && originalWidth > limitWidth) {
            DecimalFormat df = new DecimalFormat("0.00");
            scale = Float.valueOf(df.format(originalWidth / (float) limitWidth));
        }


        if (limitHeight > 0 && originalHeight > limitHeight) {
            DecimalFormat df = new DecimalFormat("0.00");
            float temp = Float.valueOf(df.format(originalHeight / (float) limitHeight));
            if (scale < temp)
                scale = temp;
        }
        if (scale <= 1)
            return path;
        Bitmap bitmap = BitmapFactory.decodeFile(path, onlyBoundsOptions);
        int desWidth = (int) (originalWidth / scale);
        int desHeight = (int) (originalHeight / scale);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            FileOutputStream fos = new FileOutputStream(newPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return path;
        }
        return newPath;
    }

    /**
     * 为图片添加水印
     */
    public static String imageWatermark(Context context, String path, String watermark) {
        if (TextUtils.isEmpty(watermark))
            return path;
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, onlyBoundsOptions);

        String outMimeType = onlyBoundsOptions.outMimeType;
        if ("image/gif".equals(outMimeType))
            return path;

        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        float scale = 1;

        if (originalWidth > 2500) {
            DecimalFormat df = new DecimalFormat("0.00");
            scale = Float.valueOf(df.format(originalWidth / (float) 2500));
        }

        if (originalHeight > 2500) {
            DecimalFormat df = new DecimalFormat("0.00");
            float temp = Float.valueOf(df.format(originalHeight / (float) 2500));
            if (scale < temp)
                scale = temp;
        }
        if (scale > 1) {
            onlyBoundsOptions.inSampleSize = (int) scale;
        }

        onlyBoundsOptions.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, onlyBoundsOptions);

        int degree = readPictureDegree(path);
        bitmap = rotateBitmap(bitmap, degree);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int screenWidth = 1080; //使用固定的宽度标准
        if (w < screenWidth / 4)
            return path;
        Bitmap ret = bitmap.copy(bitmap.getConfig(), true);
        Paint paint = new Paint();
        int textSize = PhoneHelper.getInstance().dp2Px(10);
        int padding = PhoneHelper.getInstance().dp2Px(14);
        textSize = textSize * w / screenWidth;
        if (h < textSize)
            return path;
        padding = padding * w / screenWidth;
        if (textSize < 2) textSize = 2;
        if (padding < 2) padding = 2;
        paint.setTextSize(textSize);
        paint.setShadowLayer(PhoneHelper.getInstance().dp2Px(2), 0, 0, Color.BLACK);
        Rect bounds = new Rect();
        paint.getTextBounds(watermark, 0, watermark.length(), bounds);
        Canvas canvas = new Canvas(ret);
        paint.setColor(Color.WHITE);
        int x = w - bounds.width() - padding;
        int y = h - padding;
        canvas.drawText(watermark, x, y, paint);
        canvas.save();
        canvas.restore();
        File file = new File(context.getExternalCacheDir(), "temp" + System.currentTimeMillis() + ".jpg");
        String newPath = file.getAbsolutePath();

        try {
            FileOutputStream fos = new FileOutputStream(newPath);
            ret.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return path;
        } finally {
            if (!bitmap.isRecycled())
                bitmap.recycle();
            if (!ret.isRecycled())
                ret.recycle();
        }

        return newPath;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 拍摄图片的完整路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        return degree;
    }
}
