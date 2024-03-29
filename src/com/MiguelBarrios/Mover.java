package com.MiguelBarrios;

import java.text.SimpleDateFormat;

public class Mover
{
    private final double change;

    private final String direction;

    private final double last;

    private final String symbol;

    private final int totalVolume;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

    public Mover(double change, String direction, double last, String symbol, int totalVolume)
    {
        this.change = change;
        this.direction = direction;
        this.last = last;
        this.symbol = symbol;
        this.totalVolume = totalVolume;
    }

    public double getChange()
    {
        return change;
    }

    public String getDirection()
    {
        return direction;
    }

    public double getLast()
    {
        return last;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public int getTotalVolume()
    {
        return totalVolume;
    }

    @Override
    public String toString()
    {
        return "Mover{" +
                "change=" + change +
                ", direction='" + direction + '\'' +
                ", last=" + last +
                ", symbol='" + symbol + '\'' +
                ", totalVolume=" + totalVolume +
                '}';
    }
}
