package com.MiguelBarrios;

public abstract class TradingStrategy
{

    public abstract void cycle();

    public  abstract void closeAllPositions();

    public abstract void update();


}
