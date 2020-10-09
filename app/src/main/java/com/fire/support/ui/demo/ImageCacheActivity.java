package com.fire.support.ui.demo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fire.support.R;
import com.fire.support.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageCacheActivity extends BaseActivity {

    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.iv_footer)
    SimpleDraweeView iv_footer;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image_cache);
        ButterKnife.bind(this);

    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        iv_header.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = ((BitmapDrawable) iv_footer.getDrawable()).getBitmap();
                iv_header.setImageBitmap(bm);
            }
        }, 2000);
    }


}
