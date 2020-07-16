package com.MiguelBarrios;

import jdk.swing.interop.SwingInterOpUtils;

import java.lang.reflect.Array;
import java.time.Year;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static String split = "########################################\n########################################";

    public static void main(String[] args) throws InterruptedException
    {

        TDARequest.setSimulation(true);
        Date startTrading = new Date(2020, Calendar.JULY, 15, 8, 30);
        Date stopTrading = new Date(2020, Calendar.JULY, 15, 14, 55);
        Market market = new Market(startTrading, stopTrading);
        market.waitForMarketToOpen();
        TimeUnit.SECONDS.sleep(10);
        Account account = new Account();
        new Log("TopMovers");
        //For this strategy we will only purchase each equity once
        HashMap<String, Mover> movers = new HashMap<>();

        while (market.isOpen())
        {
            System.out.println("Getting top movers");
            ArrayList<Mover> topMovers = TDARequest.allTopMovers("up", "percent");
            topMovers.addAll(TDARequest.allTopMovers("up", "value"));
            topMovers.addAll(TDARequest.allTopMovers("down", "percent"));
            topMovers.addAll(TDARequest.allTopMovers("down", "value"));

            for (Mover mover : topMovers)
            {
                if(!movers.containsKey(mover.getSymbol()))
                {
                    movers.put(mover.getSymbol(), mover);
                    Log.saveMover(mover);
                }
            }


            //Buy Logic
            /*
            for(Mover mover : topGainers) //Purchase stocks that we have not encountered
            {
                String tickerSymbol = mover.getSymbol();
                if(previouslyEncountered.add(tickerSymbol))
                {
                    Trade trade = TDARequest.placeOrder(tickerSymbol, OrderType.BUY, NUM_SHARES);
                    if(trade == null) {
                        System.out.println("Order not placed");
                    }
                    else {
                        log.saveTrade(trade);
                    }

                    log.saveMover(mover);
                }
            }

            //Get updated account info
            account.updateAccountInfo();
            System.out.println(account);
            ArrayList<Position> positions = account.getActivePositions();

            for(Position position : positions)
            {
                double change = position.getcurrentDayProfitLossPercentage();
                if(change < -1)
                {
                    System.out.println("sell: " + position.getSymbol());
                }
            }
            */

            //TODO: implement sell logic buy using account position profitlosschange
            //Inorder to avoid 429 error
            TimeUnit.SECONDS.sleep(5);
        }

        //---------------- For testing only ------------------------------------
        //Get quotes for all stocks that would have been purchased
        //TODO: why is it adding each symbol twice?
        System.out.println(split);
        System.out.println("Mover size: " + movers.size());
        System.out.println("Movers keyset size: " + movers.keySet().size());
        ArrayList<String> symbols = new ArrayList<>(movers.size());
        symbols.addAll(movers.keySet());

        System.out.println("Symbols size: " + symbols.size());


        ArrayList<Quote> quotes = TDARequest.getQuotes(symbols);
        System.out.println("quotes.size() = " + quotes.size());
        System.out.println(split);
        Log.saveQuotes(quotes);

        //Results  if we close position the same day
        ArrayList<Results> list = new ArrayList<>();
        for(Quote quote : quotes)
        {
            String symbol = quote.getSymbol();
            Results results = new Results(movers.get(symbol), quote);
            list.add(results);
            System.out.println(results);
        }

        System.out.println(Results.getTotalProfits());
        Log.saveResults(list);
    }


}


