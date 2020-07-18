package com.MiguelBarrios;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        Market mar = TDARequest.getMarketHours();
        Market.waitForMarketToOpen();
        System.exit(-1);
        //Max price willing to pay per equity
        double MAX_PRICE = 10;
        int QUANTITY = 1;

        //will determine if actual orders are placed
        TDARequest.setSimulation(true);
        Market market = TDARequest.getMarketHours();

        System.out.println(market);

        Account account = new Account();

        //For this strategy we will only purchase each equity once
        HashMap<String, Mover> movers = new HashMap<>();

        int iterations = 0;
        while (market.isOpen())
        {
            ArrayList<Mover> topMovers = TDARequest.getAllMovers();
            for (Mover mover : topMovers)
            {
                String tickerSymbol = mover.getSymbol();
                if(!movers.containsKey(tickerSymbol))
                {
                    //TODO: find out what values maximize returns
                    double currentPrice = mover.getLast();
                    double buyPrice = getTrailValue(currentPrice, .005);
                    double sellPrice = getTrailValue(currentPrice, 0.01);

                    //TODO: once margin requirements are met remove last condition
                    //initial movers are unpredictable, iterations > 240 is so that the initial one  die out
                    if(account.hasAvailableFunds(buyPrice) && buyPrice < MAX_PRICE && iterations > 240 && mover.getDirection().equalsIgnoreCase("up"))
                    {
                        boolean orderPlaced = TDARequest.placeBuySellOrder(tickerSymbol, QUANTITY, buyPrice, sellPrice);
                        if(orderPlaced) {
                            System.out.println("OrderPlaced: " + tickerSymbol);
                        }
                    }

                    movers.put(tickerSymbol, mover);
                }
            }

            ++iterations;

            //Prices don't fluctuate that much every second
            TimeUnit.SECONDS.sleep(5);
        }


        //---------------- For analysis only ------------------------------------
        //Results  if we close position the same day
        ArrayList<Results> list = new ArrayList<>();

        //Get quotes for all stocks that would have been purchased
        ArrayList<Quote> quotes = TDARequest.getQuotes(new ArrayList<>(movers.keySet()));
        for(Quote quote : quotes)
        {
            String symbol = quote.getSymbol();
            list.add(new Results(movers.get(symbol), quote));
        }

        System.out.println(Results.getTotalProfits());

        new Log("TopMovers");
        Log.saveResults(list);
        Log.saveQuotes(quotes);
        Log.saveMovers(movers);
    }

    public static double getTrailValue(double currentPrice, double percentOffset)
    {
        return currentPrice * percentOffset;
    }

}



