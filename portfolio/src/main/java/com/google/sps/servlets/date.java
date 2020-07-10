package com.google.sps;

public final class date{
    private final String month;
    private final int day;
    private final int year;
    //store hours as 24 hr clock
    private final int hours;
    private final int minutes;
    
    public date(String month, int day, int year, int hours, int minutes ){
        this.month = month;
        this.day = day;
        this.year = year;
        this.hours = hours;
        this.minutes = minutes;
    }
    public String getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public int getHour(){
        return hours;
    }
    public int getMinutes(){
        return minutes;
    }
}