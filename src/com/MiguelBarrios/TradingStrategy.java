package com.MiguelBarrios;

public abstract class TradingStrategy extends Thread
{
    public abstract void cycle();

    public  abstract void closeAllPositions();

    public abstract void update();

    public abstract void run();

}
