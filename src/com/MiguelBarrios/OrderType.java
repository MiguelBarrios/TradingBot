package com.MiguelBarrios;

public enum OrderType
{
    BUY("Buy"),
    SELL("Sell"),
    HOLD("HOLD"),
    SHORT("SHORT");

    private String type;

    private OrderType(String type) {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return type;
    }
}

