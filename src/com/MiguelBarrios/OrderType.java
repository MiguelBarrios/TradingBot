package com.MiguelBarrios;

public enum OrderType
{
    BUY("Buy"),
    SELL("Sell"),
    HOLD("HOLD"),
    //When wanting to enter and exit a short position
    SELL_SHORT("SELL_SHORT"),
    BUY_TO_COVER("BUY_TO_COVER");

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

