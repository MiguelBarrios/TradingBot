package com.MiguelBarrios;

public enum Exchange
{
    NASDAQ("$COMPX"),
    DOW("$DJI"),
    SMP("$SPX.X");

    private final String exchange;

    private Exchange(String type) {
        this.exchange = type;
    }

    @Override
    public String toString()
    {
        return exchange;
    }
}
