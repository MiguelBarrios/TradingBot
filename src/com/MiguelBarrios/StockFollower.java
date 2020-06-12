package com.MiguelBarrios;

import java.util.ArrayList;

public class StockFollower extends TradingStrategy
{
    private Trader trader;

    private ArrayList<String> symbols;

    public StockFollower(double initial_funds, double min_price, double max_price, int position_size, boolean simulation, ArrayList<String> symbols) {
        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation, "StockFollower" ,Strategy.TOPGAINERS);
        this.symbols = symbols;

        for(String symbol : symbols) {
            trader.buyPosition(symbol);
        }
    }

    @Override
    public void cycle()
    {
        

    }

    @Override
    public void closeAllPositions()
    {

    }

    @Override
    public void update()
    {

    }
}
