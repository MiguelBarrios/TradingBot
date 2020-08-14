package com.MiguelBarrios;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static double TRAIL_BUY = 0.003;

    public static double TRAIL_SELL = 0.01;

    public static double MAX_PRICE = 15;

    public static int QUANTITY = 1;

    public static void main(String[] args) throws InterruptedException
    {
        Market.waitForMarketToOpen();

        //will determine if actual orders are placed
        TDARequest.setSimulation(true);
        TDARequest.refreshAuthToken();
        Clock clock = new Clock();
        clock.start();
        Account account = new Account();

        //For this strategy we will only purchase each equity once
        HashMap<String, Mover> movers = new HashMap<>();

        while (Market.isOpen(true, false))
        {
            ArrayList<Mover> topMovers = TDARequest.getAllMovers();
            for (Mover mover : topMovers)
            {
                String tickerSymbol = mover.getSymbol();
                if(!movers.containsKey(tickerSymbol))
                {
                    //TODO: find out what values maximize returns
                    double currentPrice = mover.getLast();
                    double buyTrail = currentPrice * TRAIL_BUY;
                    double trailValue = currentPrice * TRAIL_SELL;

                    //TODO: once margin requirements are met remove last condition
                    double buyPrice = currentPrice + trailValue;
                    if(account.hasAvailableFunds(buyPrice) && buyPrice < MAX_PRICE && mover.getDirection().equalsIgnoreCase("up"))
                    {
                        boolean orderPlaced = TDARequest.placeBuySellOrder(tickerSymbol, QUANTITY, buyTrail, trailValue);
                        if(orderPlaced)
                            System.out.println(String.format("Order placed: symbol = %s, quantity = %d, @ %f", tickerSymbol, QUANTITY, currentPrice + buyTrail));
                    }

                    movers.put(tickerSymbol, mover);
                }
            }

            //Prices don't fluctuate that much every second
            TimeUnit.SECONDS.sleep(5);
        }
    }
}



