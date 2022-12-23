package com.hybridsearch.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 */
public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static String getDate(){
        return simpleDateFormat.format(new Date());
    }


}
