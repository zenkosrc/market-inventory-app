package com.zenkosrc.marketinventory.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getTimeFromMilliseconds(long milliseconds){

        Date date = new Date(milliseconds);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.format(date);
    }

    public static String getDateFromMilliseconds(long milliseconds){

        Date date = new Date(milliseconds);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }



}
