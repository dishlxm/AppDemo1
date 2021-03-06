package com.example.appdemo;

import java.util.Calendar;

public class DateUtils {
    //get current year
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    //get current month
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    //get current day
    public static int getCurrentDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //get current day of week
    public static int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    //get current hour
    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);//二十四小时制
    }

    //get current minute
    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    //get current second
    public static int getSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    //get current month format
    public static int[][] getDayOfMonthFormat(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int days[][] = new int[6][7];

        int daysOfFirstWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysOfMonth = getDaysOfMonth(year, month);
        int daysOfLastMonth = getDaysOfLastMonth(year, month);

        int dayNum = 1;
        int nextMonthDayNum = 1;

        for(int i=0; i<days.length; i++){
            for(int j=0; j<days[i].length; j++){
                if(i == 0 && j < daysOfFirstWeek - 1){
                    days[i][j] = daysOfLastMonth - daysOfFirstWeek + 2 + j;
                }
                else if(dayNum <= daysOfMonth){
                    days[i][j] = dayNum++;
                }
                else {
                    days[i][j] = nextMonthDayNum++;
                }
            }
        }

        return days;
    }

    public static int getDaysOfLastMonth(int year, int month) {
        int lastDaysOfMonth = 0;
        if (month == 1) {
            lastDaysOfMonth = getDaysOfMonth(year - 1, 12);
        } else {
            lastDaysOfMonth = getDaysOfMonth(year, month - 1);
        }
        return lastDaysOfMonth;
    }

    public static int getDaysOfMonth(int year, int month){
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if(isLeap(year)){
                    return 29;
                }else{
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }

    public static boolean isLeap(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }

}
