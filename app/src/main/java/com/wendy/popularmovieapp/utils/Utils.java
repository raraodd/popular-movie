package com.wendy.popularmovieapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wendy on 7/7/2017.
 */

public class Utils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        if(noOfColumns < 2) return 2;
        return noOfColumns;
    }

    public static String getYear(String dateString){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = parser.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public static String convertDateToString(String dateString){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMMM yyyy");
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertRuntime(String duration){
        String timeToDisplay = "";
        if (duration == null || duration.isEmpty())
            return "";
        else {
            int runtime = Integer.parseInt(duration);
            int hours = runtime / 60;
            int min = runtime % 60;
            if(min < 10)
                timeToDisplay = hours + "h 0" + min + "min";
            else
                timeToDisplay = hours + "h " + min + "min";

            return timeToDisplay;
        }
    }

}