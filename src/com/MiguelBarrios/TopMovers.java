package com.MiguelBarrios;

import java.util.ArrayList;

public class TopMovers extends TradingStrategy
{
    private Trader trader;

    public TopMovers(double initial_funds, double min_price, double max_price, int position_size, boolean simulation) {
        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation,1000,.2,"TopMovers" ,Strategy.TOPMOVERS);
    }

    public void cycle() {
        ArrayList<String> trendingUp = new ArrayList<>(20);

        //get symbols that we have not see before
        ArrayList<Mover> movers = TDARequest.allTopMovers("up");

        if(movers.size() != 0) {
            for(Mover symbol : movers) {
                if(!trader.hasEncountered(symbol.getSymbol())) {
                    trendingUp.add(symbol.getSymbol());
                }

            }
        }

        if(trendingUp.size() != 0) {
            //Get quotes and initialPurchase all newly encountered movers trending up
            ArrayList<Quote> quotesMovers = TDARequest.getQuotes(trendingUp);

            if(quotesMovers.size() != 0) {
                for(Quote quote : quotesMovers) {
                    if(trader.isEligibleForPurchase(quote)) {
                        trader.buyPosition(quote);
                    }
                }
            }
        }

        update_open_positions();
    }

    public void closeAllPositions()
    {
        trader.closeAllOpenPositions();
    }

    public void update()
    {

    }


    public void run()
    {

    }

    public void update_open_positions()
    {
        ArrayList<Quote> quotes =  trader.getActivePositionsQuotes();
        for (Quote quote :  quotes) {
            if(quote != null)
            {
                Order order = trader.getOrder(quote.getSymbol());
                OrderType recommendation = order.update(quote.getBidprice());

                String color = (order.change()) ? Color.GREEN : Color.RED;
                System.out.print(color + order.status() + ",");

                if (recommendation == OrderType.SELL) {
                    trader.closePosition(order);
                }
            }

        }
        System.out.println(Color.RESET);
    }
}
