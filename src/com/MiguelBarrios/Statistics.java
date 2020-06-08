package com.MiguelBarrios;

public abstract class Statistics
{
    public abstract OrderType update(double price);

    public abstract double getPercentChange();

    public abstract double getAbsoluteMax();

    public abstract double getAbsoluteMin();

    public abstract double getCurrentPrice();

    public abstract double getInitialPrice();
}
