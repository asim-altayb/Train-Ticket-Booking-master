package com.example.fashkl.im;

public class ticket_info {

    static String from_station;
    static String to_station;
    static String Price;
    static String class_trip;
    static String Date;
    static String time;
    static String chair_no;
    static String trip_type;

    public ticket_info(String from_station, String to_station, String price, String class_trip,
                       String date, String time, String chair_no, String trip_type) {
        this.from_station = from_station;
        this.to_station = to_station;
        this.Price = price;
        this.class_trip = class_trip;
        this.Date = date;
        this.time = time;
        this.chair_no = chair_no;
        this.trip_type=trip_type;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public static String getFrom_station() {
        return from_station;
    }

    public void setFrom_station(String from_station) {
        this.from_station = from_station;
    }

    public static String getTo_station() {
        return to_station;
    }

    public void setTo_station(String to_station) {
        this.to_station = to_station;
    }

    public static String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public static String getClass_trip() {
        return class_trip;
    }

    public void setClass_trip(String class_trip) {
        this.class_trip = class_trip;
    }

    public static String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public static String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static String getChair_no() {
        return chair_no;
    }

    public void setChair_no(String chair_no) {
        this.chair_no = chair_no;
    }
}
