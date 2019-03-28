package com.fit.ah.twitter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyDateFormatter {
    public static String FormatDate(Date datetime) {
        String string = "";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date now = calendar.getTime();
        long diff = (now.getTime()) - datetime.getTime();

        int minutes = (int) (diff/60000);

        if(minutes == 0)
            string = "now";
        else if(minutes == 1)
            string = "1m";
        else if(minutes<60)
            string = minutes+"m";
        else if(minutes<1440)
            string = minutes/60+"h";
        else if(minutes<14400)
            string = minutes/1440+"d";
        else{
            DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            string = df.format(datetime);
        }
        return string;
    }

    public static String FormatShortDate(Date datetime) {
        String string = "";
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        Date now = calendar.getTime();
        long diff = (now.getTime()) - datetime.getTime();

        int minutes = (int) (diff/60000);

        if(minutes == 0)
            string = "now";
        else if(minutes == 1)
            string = "1m";
        else if(minutes<60)
            string = minutes+"m";
        else if(minutes<1440)
            string = minutes/60+"h";
        else if(minutes<14400)
            string = minutes/1440+"d";
        else{
            DateFormat df = new SimpleDateFormat("dd/mm/yy");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            string = df.format(datetime);
        }
        return string;
    }

    public static boolean IsLongDate(Date datetime) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        Date now = calendar.getTime();
        long diff = (now.getTime()) - datetime.getTime();

        int minutes = (int) (diff/60000);

        if(minutes>=14400)
            return true;
        else
            return false;
    }
}
