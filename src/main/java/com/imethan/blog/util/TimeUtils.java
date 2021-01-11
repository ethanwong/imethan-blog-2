package com.imethan.blog.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @Name TimeUtils
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 18:22
 */
public class TimeUtils {

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss
     */
    public static String DATETIME_FORMAT_01 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式yyyyMMdd
     */
    public static String DATETIME_FORMAT_02 = "yyyyMMdd";

    public static Date stringToDate(String dateTimeString, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeString);
        return dateTime.toDate();
    }

    public static String dateToString(Date date, String format) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    public static Date stringToDate(String dateTimeString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATETIME_FORMAT_01);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeString);
        return dateTime.toDate();
    }

    public static String dateToString(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(DATETIME_FORMAT_01);
    }

    public static void main(String[] args) {
        System.out.println(TimeUtils.dateToString(new Date()));
    }

}

