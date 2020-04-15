package com.me.store.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {
    //默认格式
    public static final String STANDARD_PATTERN="yyyy-MM-dd HH:mm:ss";
    private DateTimeUtil(){}

    /**
     * 字符串转换日期
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date strToDate(String dateStr,String pattern){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(pattern);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }

    /**
     * 日期转换字符串
     * @param date
     * @param pattern
     * @return
     */
    public static  String dateToStr(Date date,String pattern){
        if(date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(pattern);
    }

    public static Date strToDate(String dateStr){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(STANDARD_PATTERN);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateStr);
        return dateTime.toDate();
    }
    public static  String dateToStr(Date date){
        if(date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(STANDARD_PATTERN);
    }
}
