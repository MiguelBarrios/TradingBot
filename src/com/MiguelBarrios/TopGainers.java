package com.MiguelBarrios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TopGainers extends TradingStrategy
{
    public static String symbolsFilePath = "/Users/miguelbarrios/Documents/Projects/WebScrapers/Stocks/symbols.txt";

    private Trader trader;

    public ArrayList<String> symbols;



    public TopGainers(double initial_funds, double min_price, double max_price, int position_size, boolean simulation) throws IOException
    {
        symbols = Util.readInData(symbolsFilePath);

        this.trader = new Trader(initial_funds,min_price,max_price,position_size,simulation,"TopGainers" ,Strategy.TOPGAINERS);


    }

    public void run()
    {
        HashMap<String, Quote> initial = new HashMap<>();

        ArrayList<Quote> quotes = TDARequest.getQuotes(symbols);

        for(Quote quote :quotes ) {
            initial.put(quote.symbol, quote);
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
