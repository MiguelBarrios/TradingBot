package com.MiguelBarrios;

public class TopLosers extends TradingStrategy
{

    @Override
    public void run()
    {
        for(int i = 0; i < 5; ++i)
        {
            System.out.println("Top loser Strategy running: Trial " + i);
            Util.pause(5);
        }
    }
}
