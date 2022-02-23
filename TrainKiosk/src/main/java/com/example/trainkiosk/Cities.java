package com.example.trainkiosk;

public enum Cities {
    NEW_YORK("New York", 220), BOSTON("Boston", 200),
    LOS_ANGELES("Los Angeles", 75), CHICAGO("Chicago", 125),
    HOUSTON("Houston", 120), PHOENIX("Phoenix", 55),
    PHILADELPHIA("Philadelphia", 140), DALLAS("Dallas", 120),
    SAN_DIEGO("San Diego", 60), DETROIT("Detroit", 150),
    DENVER("Denver", 50), SALT_LAKE_CITY("Salt Lake City", 0);

    private String name;
    private long price;

    Cities(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
