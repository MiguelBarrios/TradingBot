package com.MiguelBarrios;

import java.text.SimpleDateFormat;

public class Mover
{
    private double change;

    private String direction;

    private double last;

    private String symbol;

    private int totalVolume;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

    public Mover(double change, String direction, double last, String symbol, int totalVolume)
    {
        this.change = change;
        this.direction = direction;
        this.last = last;
        this.symbol = symbol;
        this.totalVolume = totalVolume;
    }

    public String[] csvFormat()
    {
        //{"Symbol", "lastPrice", "Direction", "Change", "Volume", "Time"};
        long now = System.currentTimeMillis();



        String[] arr = {symbol,
                        String.valueOf(getLast()),
                        direction,
                        String.valueOf(change),
                        String.valueOf(totalVolume),
                        sdf.format(now)};


        return arr;
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
