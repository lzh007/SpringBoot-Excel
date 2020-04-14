package com.example.excel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: DateTimeUtil
 * @Author: lzh
 * @Date: 2020/4/14
 */
public class DateTimeUtil {


    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
