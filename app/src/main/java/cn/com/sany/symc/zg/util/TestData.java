package cn.com.sany.symc.zg.util;

import android.util.Log;

import java.math.BigDecimal;

public class TestData {

    public static void main(String[] args)
    {
        float b57 = (float) 32.34*0.0000f/100 - 20;

        Log.i("======222=========",dataTranInfo(b57));
}
    /**
     * 保留两位小数  四舍五入
     * @param num
     * @return
     */
    public static String dataTranInfo(float num){
        BigDecimal b = new BigDecimal(num);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        String for_num = String.format("%.2f",f1);

        return for_num;
    }

}
