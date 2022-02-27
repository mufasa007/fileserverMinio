package com.activeclub.fileserverminio.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getPreTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyyMMddHHmmssSSS");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
//        System.out.println("现在时间：" + sdf.format(date)); // 输出已经格式化的现在时间（24小时制）
        return sdf.format(date);
    }

    /**
     * 获取最大有效值
     * @return
     */
    public static Date getMaxOverdueTime() {
        return new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 100);
    }

}
