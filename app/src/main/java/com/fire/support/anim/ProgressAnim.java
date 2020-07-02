package com.fire.support.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class ProgressAnim extends Animation {

    private Camera camera = new Camera();

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置动画持续时间
        setDuration(2000);
        //设置放大后的动画状态永远保持
        setFillAfter(true);
        //设置加速曲线为线性加速
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        final Matrix matrix = t.getMatrix();
        //保存原有参数
        camera.save();
        //放大
        camera.translate(-0.0f, 0.0f, -20.0f);
        //得到放大后的矩阵
        camera.getMatrix(matrix);
        //恢复参数
        camera.restore();
        matrix.preScale(0 , 0);
        matrix.postScale(1 , 1);

    }
}
