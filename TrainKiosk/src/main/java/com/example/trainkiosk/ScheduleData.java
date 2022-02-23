package com.example.trainkiosk;

public class ScheduleData {
    private String destination;
    private String date;
    private String departure;
    private int qty;
    private long price;

    public ScheduleData(String destination, String date, String departure, int qty, long price) {
        this.destination = destination;
        this.date = date;
        this.departure = departure;
        this.qty = qty;
        this.price = price;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
