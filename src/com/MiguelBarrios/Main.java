package com.MiguelBarrios;

import java.lang.reflect.Array;
import java.time.Year;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{

    public static void main(String[] args) throws InterruptedException
    {
        TDARequest.setSimulation(true);

        Date startTrading = new Date(2020, Calendar.JULY, 9, 8, 30);
        Date stopTrading = new Date(2020, Calendar.JULY, 9, 14, 55);
        Market market = new Market(startTrading, stopTrading);
        market.waitForMarketToOpen();

        Account account = new Account();

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
        ArrayList<String> symbols = new ArrayList<>(movers.keySet());
        ArrayList<Quote> quotes = TDARequest.getQuotes(symbols);
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


