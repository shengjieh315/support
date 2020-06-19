package com.fire.support.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.MeasureSpec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.socks.library.KLog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * bitmap工具类
 */
public class BitmapHelper {

    private static BitmapHelper util;


    synchronized public static BitmapHelper getInstance() {

        if (util == null) {
            util = new BitmapHelper();

        }
        return util;

    }

    private BitmapHelper() {
        super();

    }

    /**
     * 将bitmap转为base64
     *
     * @param bitmap
     * @param imgFormat
     * @return
     */
    @SuppressLint("NewApi")
    public String bitmapToBase64(Bitmap bitmap, String imgFormat) {

        if (null == bitmap) {

            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
            if (imgFormat.equalsIgnoreCase("png"))
                compressFormat = Bitmap.CompressFormat.PNG;
            bitmap.compress(compressFormat, 85, out);
            out.flush();
            out.close();
            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将图片转为base64
     *
     * @param imgPath
     * @param imgFormat
     * @return
     */
    @SuppressLint("NewApi")
    public String urlToBase64(String imgPath, String imgFormat) {
        Bitmap bitmap = null;
        if (imgPath != null && imgPath.length() > 0) {
            bitmap = readBitmap(imgPath);
        }
        return bitmapToBase64(bitmap, imgFormat);

    }

    private Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到圆角的bitmap
     *
     * @param bitmap
     * @param corner 以长或宽的比例为半径，2表示二分之一，8表示八分之一
     * @return
     */
    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int corner) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / corner;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / corner;
            float clip = (width - height) / corner;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(
                PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 设置Bitmap圆角
     *
     * @param image          传入的Bitmap
     * @param outerRadiusRat 圆角半径
     * @return 返回处理后的Bitmap
     */
    public Bitmap getRoundedBitmap(Bitmap image, int outerRadiusRat) {
        int x = image.getWidth();
        int y = image.getHeight();
        // 根据源文件新建一个darwable对象
        Drawable imageDrawable = new BitmapDrawable(image);

        // 新建一个新的输出图片
        Bitmap output = Bitmap.createBitmap(x, y, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, x, y);

        // 产生一个红色的圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

        // 将源图片绘制到这个圆角矩形上
        // 详解见http://lipeng88213.iteye.com/blog/1189452
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, x, y);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();

        return output;
    }

    /**
     * 图片压缩
     *
     * @param image
     * @return
     */
    public Bitmap getCompressBitmap(Bitmap image, int minSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > minSize) {
            options -= 10;
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     * 得到本地缩略图
     *
     * @param srcPath
     * @return
     */
    public Bitmap getCompressBitmapByLocal(String srcPath, int ww, int hh) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return getCompressBitmap(bitmap, 100);
    }


    /**
     * 得到本地缩略图
     *
     * @param srcPath
     * @return
     */
    public Bitmap getBitmapByLocal(String srcPath, int ww, int hh) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;


        int be1 = 1;
        if (w > ww && ww > 0) {
            be1 = w / ww;

        }
        int be2 = 1;
        if (h > hh && hh > 0) {
            be2 = h / hh;

        }

        newOpts.inJustDecodeBounds = false;
        newOpts.inSampleSize = Math.max(be1, be2);

        return BitmapFactory.decodeFile(srcPath, newOpts);

    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public Bitmap getZoomBitmap(Bitmap bitmap, int w, int h) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidht, scaleHeight);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        }
        return newbmp;
    }

    /**
     * 得到网络图片
     *
     * @param imgUrl
     * @return
     */
    public Bitmap getHttpBitmap(String imgUrl) {

        Bitmap bitmap = null;
        if (!PhoneHelper.getInstance().isNetworkAvailable()) {

            return null;
        }

        InputStream is = getHttpPicInputStream(imgUrl);
        if (is != null) {

            bitmap = BitmapFactory.decodeStream(is);

            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return bitmap;

    }

    /**
     * 得到网络图片的输入流
     *
     * @param url
     * @return
     */
    public InputStream getHttpPicInputStream(String url) {
        InputStream is = null;
        URL url1 = null;
        try {
            url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return is;
    }

    /**
     * Bitmap转化为drawable
     *
     * @param bitmap
     * @return
     */
    @SuppressWarnings("deprecation")
    public Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    public Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                    : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }


    /**
     * 根据网络状态获取本地图片缩略图
     *
     * @param picPath
     * @return
     */
    public Bitmap getBitmapByNetWork(String picPath) {
        int networkType = PhoneHelper.getInstance().getNetType();

        Bitmap tagBm = null;
        ByteArrayOutputStream decodeBitmap = null;
        int size = 150;
        switch (networkType) {
            case 1:
                size = 200;
                break;
            case 2:
                size = 100;
                break;
            case 3:
            case 4:
                size = 150;
                break;

        }
        decodeBitmap = getBitmapByteArray(picPath, size);

        if (decodeBitmap != null) {
            byte[] array = decodeBitmap.toByteArray();
            try {
                tagBm = BitmapFactory.decodeByteArray(array, 0, array.length);
            } catch (OutOfMemoryError oError) {
                oError.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return tagBm;

    }


    public Bitmap getBitmapByDefault(String picPath) {

        Bitmap tagBm = null;
        ByteArrayOutputStream decodeBitmap = null;
        decodeBitmap = getBitmapByteArray(picPath, 100);

        if (decodeBitmap != null) {
            byte[] array = decodeBitmap.toByteArray();
            try {
                tagBm = BitmapFactory.decodeByteArray(array, 0, array.length);
            } catch (OutOfMemoryError oError) {
                oError.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return tagBm;
    }

    /**
     * 将本地图片转为输出流
     *
     * @param path
     * @param size
     * @return
     */
    public ByteArrayOutputStream getBitmapByteArray(String path, int size) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, 1024 * 800);

        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        opts.inPreferredConfig = Config.RGB_565;
        opts.inTempStorage = new byte[100 * 1024];
        FileInputStream is = null;
        Bitmap bmp = null;

        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(path);
            bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);

            baos = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            int options = 100;
            while (baos.toByteArray().length / 1024 > size && options > 4) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 2;// 每次都减少10
            }
            bmp.recycle();
            return baos;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError oError) {
            oError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (baos != null) {
                    baos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }
        return baos;
    }


    /**
     * 为了得到恰当的inSampleSize，Android提供了一种动态计算的方法
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private int computeSampleSize(BitmapFactory.Options options,
                                  int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private int computeInitialSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 通过view得到bitmap
     *
     * @param view
     * @param w
     * @param h
     * @return
     */
    public Bitmap loadBitmapFromView(View view, int w, int h) {
        if (view == null) {
            return null;
        }

        if (view.getMeasuredHeight() <= 0) {
            view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
            // 这个方法也非常重要，设置布局的尺寸和位置
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }

        // 生成bitmap
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Config.ARGB_8888);
        // 利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap);
        // 把view中的内容绘制在画布上
        view.draw(canvas);

        return bitmap;
    }


    public long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available() / 1024;

                return size;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


        return size;
    }


    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    private Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        try {

            String status = Environment.getExternalStorageState();
            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
            long time = System.currentTimeMillis();
            String imageName = timeFormatter.format(new Date(time));
            // ContentValues是我们希望这条记录被创建时包含的数据信息
            ContentValues values = new ContentValues(3);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
            values.put(MediaStore.Images.Media.DATE_TAKEN, time);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            if (status.equals(Environment.MEDIA_MOUNTED)) {
                // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
                imageFilePath = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values);
            } else {
                imageFilePath = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFilePath;
    }


    public Bitmap getGreyImage(Bitmap bmp) {
        if (bmp != null) {
            int width, height;
            Paint paint = new Paint();
            height = bmp.getHeight();
            width = bmp.getWidth();
            Bitmap bm = Bitmap.createBitmap(width, height,
                    Config.RGB_565);
            Canvas c = new Canvas(bm);
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            paint.setColorFilter(f);
            c.drawBitmap(bmp, 0, 0, paint);
            return bm;
        } else {
            return bmp;
        }
    }


    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     *
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    public Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {

            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }


    public Bitmap mergeBitmap_QR(Bitmap topBitmap, Bitmap bottomBitmap, String comicName) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {

            return null;
        }

        int width = topBitmap.getWidth();

        Bitmap tempBitmapT = topBitmap;


        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
        }

//        else if (bottomBitmap.getWidth() != width) {
//            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * width), false);
//        }

        Bitmap reallyBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width / 2, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * (width / 2)), false);


        int height = tempBitmapT.getHeight() + width;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, reallyBitmapB.getWidth(), reallyBitmapB.getHeight());

        Rect bottomRectT = new Rect(width / 4, tempBitmapT.getHeight() + width / 4, width / 4 * 3, tempBitmapT.getHeight() + width / 4 * 3);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(PhoneHelper.getInstance().dp2Px(30));

//        float w = paint.measureText(comicName,0,comicName.length());


        canvas.drawText(comicName, width / 2, tempBitmapT.getHeight() + width / 8, paint);

        canvas.drawBitmap(reallyBitmapB, bottomRect, bottomRectT, null);

        return bitmap;
    }


    /**
     * 解析二维码图片工具类
     */
    public Result[] analyzeQrBitmap(Bitmap mBitmap) {


        try {
            Result[] qr = decodeQr(mBitmap);

            if (qr != null && qr.length > 0) {

                return qr;
            }

            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();

            Bitmap newBitmp1 = Bitmap.createBitmap(mBitmap, 0, 0, width, height / 2, null, false);
            qr = decodeQr(newBitmp1);

            if (qr != null && qr.length > 0) {

                return qr;
            }

            Bitmap newBitmp = Bitmap.createBitmap(mBitmap, 0, height / 2, width, height / 2, null, false);
            qr = decodeQr(newBitmp);


            if (qr != null && qr.length > 0) {

                return qr;
            }


            width = newBitmp.getWidth();
            height = newBitmp.getHeight();


            Bitmap newnewBitmp = Bitmap.createBitmap(newBitmp, 0, height / 2, width, height / 2, null, false);
            qr = decodeQr(newnewBitmp);


            try {
                if (newBitmp1 != null && !newBitmp1.isRecycled()) {
                    newBitmp1.recycle();
                    newBitmp1 = null;
                }
                if (newBitmp != null && !newBitmp.isRecycled()) {
                    newBitmp.recycle();
                    newBitmp = null;
                }
                if (newnewBitmp != null && !newnewBitmp.isRecycled()) {
                    newnewBitmp.recycle();
                    newnewBitmp = null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            return qr;
        } catch (Throwable e) {
            e.printStackTrace();
            KLog.e(e);
        }

        return null;


    }


    private static Result[] decodeQr(Bitmap mBitmap) {

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        HashMap<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        QRCodeMultiReader multiFormatReader = new QRCodeMultiReader();
        try {

            int[] pixels = new int[width * height];
            mBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(
                    width, height, pixels);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    rgbLuminanceSource));


            Result[] result = multiFormatReader.decodeMultiple(binaryBitmap, hints);

            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            KLog.e(e);

        }
        return null;
    }


    private static Result[] decodeyuvQr(Bitmap mBitmap) {


        HashMap<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        QRCodeMultiReader multiFormatReader = new QRCodeMultiReader();
        try {
            yuvs = null;

            int width = mBitmap.getWidth();
            int height = mBitmap.getHeight();
            byte[] data = getYUV420sp(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap);

            int[] pixels = new int[width * height];
            mBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            PlanarYUVLuminanceSource rgbLuminanceSource =
                    new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    rgbLuminanceSource));


            Result[] result = multiFormatReader.decodeMultiple(binaryBitmap, hints);

            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            KLog.e(e);

        }
        return null;
    }

    private static byte[] yuvs;

    /**
     * YUV420sp
     *
     * @param inputWidth
     * @param inputHeight
     * @param scaled
     * @return
     */
    public static byte[] getYUV420sp(int inputWidth, int inputHeight, Bitmap scaled) {
        int[] argb = new int[inputWidth * inputHeight];

        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);

        /**
         * 需要转换成偶数的像素点，否则编码YUV420的时候有可能导致分配的空间大小不够而溢出。
         */
        int requiredWidth = inputWidth % 2 == 0 ? inputWidth : inputWidth + 1;
        int requiredHeight = inputHeight % 2 == 0 ? inputHeight : inputHeight + 1;

        int byteLength = requiredWidth * requiredHeight * 3 / 2;
        if (yuvs == null || yuvs.length < byteLength) {
            yuvs = new byte[byteLength];
        } else {
            Arrays.fill(yuvs, (byte) 0);
        }

        encodeYUV420SP(yuvs, argb, inputWidth, inputHeight);

        scaled.recycle();

        return yuvs;
    }

    /**
     * RGB转YUV420sp
     *
     * @param yuv420sp inputWidth * inputHeight * 3 / 2
     * @param argb     inputWidth * inputHeight
     * @param width
     * @param height
     */
    private static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        // 帧图片的像素大小
        final int frameSize = width * height;
        // ---YUV数据---
        int Y, U, V;
        // Y的index从0开始
        int yIndex = 0;
        // UV的index从frameSize开始
        int uvIndex = frameSize;

        // ---颜色数据---
        // int a, R, G, B;
        int R, G, B;
        //
        int argbIndex = 0;
        //

        // ---循环所有像素点，RGB转YUV---
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {

                // a is not used obviously
                // a = (argb[argbIndex] & 0xff000000) >> 24;
                R = (argb[argbIndex] & 0xff0000) >> 16;
                G = (argb[argbIndex] & 0xff00) >> 8;
                B = (argb[argbIndex] & 0xff);
                //
                argbIndex++;

                // well known RGB to YUV algorithm
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                //
                Y = Math.max(0, Math.min(Y, 255));
                U = Math.max(0, Math.min(U, 255));
                V = Math.max(0, Math.min(V, 255));

                // NV21 has a plane of Y and interleaved planes of VU each sampled by a factor of 2
                // meaning for every 4 Y pixels there are 1 V and 1 U. Note the sampling is every other
                // pixel AND every other scanline.
                // ---Y---
                yuv420sp[yIndex++] = (byte) Y;
                // ---UV---
                if ((j % 2 == 0) && (i % 2 == 0)) {
                    //
                    yuv420sp[uvIndex++] = (byte) V;
                    //
                    yuv420sp[uvIndex++] = (byte) U;
                }
            }
        }
    }
}
