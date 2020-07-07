package com.MiguelBarrios;

public class Mover
{
    private double change;

    private String direction;

    private double last;

    private String symbol;

    private int totalVolume;

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
