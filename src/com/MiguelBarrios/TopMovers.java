package com.MiguelBarrios;

import java.util.ArrayList;

public class TopMovers extends TradingStrategy
{
    private Trader trader;

    public TopMovers(double initial_funds, double min_price, double max_price, int position_size, boolean simulation) {
        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation,"TopMovers" ,Strategy.TOPMOVERS);
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

        update_open_positions();
    }

    @Override
    public void closeAllPositions()
    {
        trader.closeAllOpenPositions();
    }

    @Override
    public void update()
    {

    }

    public void update_open_positions()
    {
        ArrayList<Quote> quotes =  trader.getActivePositionsQuotes();
        for (Quote quote :  quotes) {
            Order order = trader.getOrder(quote.symbol);
            OrderType recommendation = order.update(quote.bidprice);

            String color = (order.change()) ? Color.GREEN : Color.RED;
            System.out.print(color + order.status() + ",");

            if (recommendation == OrderType.SELL) {
                trader.closePosition(order);
            }
        }
        System.out.println(Color.RESET);
    }
}
