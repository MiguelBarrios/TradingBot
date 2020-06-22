package com.MiguelBarrios;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TopGainers extends TradingStrategy
{
    public static String symbolsFilePath = "/Users/miguelbarrios/Documents/Projects/WebScrapers/stockSymbolsWebScraper/symbols.txt";

    public static ConcurrentHashMap<String, Quote> unpurchasedStocks;

    public static ArrayList<String> symbols;

    public static int NUM_SYMBOLS;

    private Trader trader;

    public TopGainers() throws IOException
    {
        this(10000, 0, 500, 1,0, true);
    }

    public TopGainers(double initial_funds, double min_price, double max_price, int position_size, int minVolume, boolean simulation) throws IOException
    {
        symbols = Util.readInData(symbolsFilePath);
        NUM_SYMBOLS = symbols.size();
        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation,minVolume,0.025,"TopGainers" ,Strategy.TOPGAINERS);
        unpurchasedStocks = new ConcurrentHashMap<>();
    }

    public void run()
    {
        try
        {
            //Initial quotes
            ArrayList<Quote> initialQuotes = TDARequest.getQuotes(symbols);
            trader.saveQuotes(initialQuotes);

            for(Quote quote : initialQuotes)
                unpurchasedStocks.putIfAbsent(quote.getSymbol(),quote);

            while(Market.getInstance().isOpen())
            {
                System.out.println("Waiting 30 sec");   Util.pause(30);

                //Get updates for all quotes
                ArrayList<Quote> updatedQuotesList = TDARequest.getQuotes(symbols);
                for(Quote currentQuote: updatedQuotesList)
                {
                    String symbol = currentQuote.getSymbol();

                    if(trader.hasOpenPosition(symbol))
                    {
                        trader.updatePosition(currentQuote);
                    }
                    else if(unpurchasedStocks.containsKey(symbol))
                    {
                        double lowestPrice = unpurchasedStocks.get(symbol).getAskPrice();
                        double updatedPrice = currentQuote.getAskPrice();
                        double change = Util.percentChange(lowestPrice, updatedPrice);

                        //TODO: update to allow purchase if price peoplle are willing to pay is greater than sell
                        if(change >= 0.05 && trader.isEligibleForPurchase(currentQuote))
                        {
                            int purchased = trader.buyPosition(currentQuote);
                            if(purchased == 1 || purchased == -2) {
                                unpurchasedStocks.remove(symbol);
                            }
                        }
                        else if(change < 0)
                        {
                            if(updatedPrice < lowestPrice) {
                                unpurchasedStocks.put(symbol, currentQuote);
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

}
