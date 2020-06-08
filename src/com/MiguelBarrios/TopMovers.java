package com.MiguelBarrios;

import java.util.ArrayList;

public class TopMovers extends TradingStrategy
{
    private Trader trader;

    public TopMovers(double initial_funds, double min_price, double max_price, int position_size, boolean simulation) {
        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation);
    }

    @Override
    public void cycle() {
        ArrayList<String> trendingUp = new ArrayList<>(20);

        //get symbols that we have not see before
        String[] movers = TDARequest.allTopMovers("up");

        if(movers.length != 0) {
            for(String symbol : movers) {
                if(!trader.hasEncountered(symbol)) {
                    trendingUp.add(symbol);
                }

            }
        }

        if(trendingUp.size() != 0) {
            //Get quotes and initialPurchase all newly encountered movers trending up
            ArrayList<Quote> quotesMovers = TDARequest.getQuotes(trendingUp);

            if(quotesMovers.size() != 0) {
                for(Quote quote : quotesMovers) {
                    if(trader.isEligible(quote)) {
                        trader.buyPosition(quote.symbol);
                    }
                }
            }
        }

        trader.update_open_positions();
    }

    @Override
    public void closeAllPositions()
    {
        trader.closeAllOpenPositions();
    }
}
