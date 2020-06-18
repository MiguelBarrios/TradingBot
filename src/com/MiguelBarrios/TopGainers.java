package com.MiguelBarrios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TopGainers extends TradingStrategy
{
    public static String symbolsFilePath = "/Users/miguelbarrios/Documents/Projects/WebScrapers/stockSymbolsWebScraper/symbols.txt";

    private Trader trader;

    public static ArrayList<String> symbols;

    public static ConcurrentHashMap<String, Quote> minQuote;

    public static ConcurrentHashMap<String, Quote> currentQuotes;

    public static final double SPREAD_FACTOR = 0.025;

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
        try
        {
            //Initial quotes
            System.out.print("Getting initial Quotes -> ");
            ArrayList<Quote> quoteMins = TDARequest.getQuotes(symbols);
            trader.saveQuotes(quoteMins);

            for(Quote quote : quoteMins)
                minQuote.put(quote.getSymbol(), quote);

            ArrayList<Quote> updatedQuotesList = new ArrayList<>();

            while(Market.getInstance().isOpen(false, false))
            {
                System.out.println("Waiting 30 sec");
                Util.pause(30);

                //Get updates for all quotes
                System.out.print("Getting quote updates -> ");
                updatedQuotesList = TDARequest.getQuotes(symbols);
                //Todo:fix  we are not updating the active orders, because we removed them from min quotes
                for(Quote currentQuote: updatedQuotesList)
                {
                    String symbol = currentQuote.getSymbol();

                    if(trader.hasOpenPosition(symbol))
                    {
                        trader.updatePosition(currentQuote);
                    }
                    else if(minQuote.containsKey(symbol))
                    {
                        double lowestPrice = minQuote.get(symbol).getAskPrice();
                        double updatedPrice = currentQuote.getAskPrice();
                        double change = Util.percentChange(lowestPrice, updatedPrice);

                        //TODO: update to allow purchase if price peoplle are willing to pay is greater than sell
                        if(change >= 0.05 && spread(currentQuote) <= SPREAD_FACTOR)
                        {
                            int purchased = trader.buyPosition(currentQuote);
                            if(purchased == 1 || purchased == -2) {
                                minQuote.remove(symbol);
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

                trader.saveQuotes(updatedQuotesList);
            }

            trader.closeAllOpenPositions();


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private double spread(Quote quote)
    {
        return Math.abs(Util.percentChange(quote.getAskPrice(),quote.getBidprice()));
    }
}
