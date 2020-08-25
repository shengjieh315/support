package com.fire.support;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {
        double amount = 2.11321112;
        double currentDiscount = getIntelligentPayDiscount(amount);
        double price = 8;
        double f = formatDouble(price * currentDiscount, 0);
        System.out.println("折后：" + f);

    }

    /**
     * 保留小数点后scale位
     *
     * @param d
     * @param scale
     * @return
     */
    public static double formatDouble(double d, int scale) {
        double d1 = d * 100;
        double d2 = formatDoubleHalfUp(d1, 8);
        double d3 = Math.floor(d2);
        return d3 / 100;
    }

    /**
     * 小数点后scale位四舍五入
     *
     * @param d
     * @param scale
     * @return
     */
    public static double formatDoubleHalfUp(double d, int scale) {
        BigDecimal bg = new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP);
        return bg.doubleValue();
    }

    /**
     * 获取折扣
     *
     * @param d
     * @return
     */
    public static double getIntelligentPayDiscount(double d) {
        return d * 10 / 100;
    }

}