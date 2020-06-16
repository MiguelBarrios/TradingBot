package com.MiguelBarrios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TopGainers extends TradingStrategy
{
    public static String symbolsFilePath = "/Users/miguelbarrios/Documents/Projects/WebScrapers/Stocks/symbols.txt";

    private Trader trader;

    public static ArrayList<String> symbols;

    public static ConcurrentHashMap<String, Quote> minQuote;

    public static ConcurrentHashMap<String, Quote> currentQuotes;

    public TopGainers() throws IOException
    {
        this(10000, 0, 500, 1, true);
    }

    public TopGainers(double initial_funds, double min_price, double max_price, int position_size, boolean simulation) throws IOException
    {
        System.out.println("Top Gainers thread created");
        symbols = Util.readInData(symbolsFilePath);
        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation,"TopGainers" ,Strategy.TOPGAINERS);
        minQuote = new ConcurrentHashMap<>();
    }

    public void run()
    {
        System.out.println("Thread started");
        try
        {
            //Initial quotes
            System.out.print("Getting initial Quotes -> ");
            ArrayList<Quote> quoteMins = TDARequest.getQuotes(symbols);
            for(Quote quote : quoteMins)
                minQuote.put(quote.getSymbol(), quote);

            ArrayList<Quote> updatedQuotesList = new ArrayList<>();

            while(Market.getInstance().isOpen(false, true))
            {
                System.out.println("Waiting 30 sec");
                Util.pause(30);

                //Get updates for all quotes
                System.out.print("Getting quote updates -> ");
                updatedQuotesList = TDARequest.getQuotes(symbols);

                for(Quote currentQuote: updatedQuotesList)
                {
                    //Check first if we have an open position
                    String symbol = currentQuote.getSymbol();
                    double lowestPrice = minQuote.get(symbol).getAskPrice();
                    double updatedPrice = currentQuote.getAskPrice();
                    double change = Util.percentChange(lowestPrice, updatedPrice);
                    System.out.println(symbol + " " + change);

                    if(trader.hasOpenPosition(symbol))
                    {
                        trader.updatePosition(currentQuote);
                    }
                    else if (change >= 0.05)
                    {
                        int purchased = trader.buyPosition(currentQuote);
                        if(purchased == 1 || purchased == -2) {
                            minQuote.remove(symbol);
                            symbols.remove(symbol);
                        }
                    }
                    else if(change < 0)
                    {
                        if(updatedPrice < lowestPrice) {
                            minQuote.put(symbol, currentQuote);
                        }
                    }

                }

            }

            trader.closeAllOpenPositions();


        }
        catch (Exception e)
        {
            e.printStackTrace();
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
