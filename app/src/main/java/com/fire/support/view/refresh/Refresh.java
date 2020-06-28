package com.fire.support.view.refresh;

public interface Refresh {
    /**
     * 重置
     */
    public void onReset();


    /**
     * 下拉高度大于头部高度
     */
    public void onPrepare();


    /**
     * 放手后
     */
    public void onRelease();

    /**
     * 不足时放手后
     */
    public void onReleaseNoEnough(float currentPercent);

    /**
     * 刷新完成
     */
    public void onComplete();

    /**
     * 下拉高度与头部高度比例
     */
    public void onPositionChange(float currentPercent);


    /**
     * 是下拉还是上拉
     * @param isHeader boolean
     */
    public void setIsHeaderOrFooter(boolean isHeader);
}
