package com.MiguelBarrios;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)
    {
        Account account = new Account(10, 20, 1, true);

        //All stocks previously encountered
        //For this trial a stock will only be purchased once
        HashSet<String> previouslyEncountered = new HashSet<>();

        Market market = TDARequest.marketHours();

        if(market == null)
        {
            System.out.println("Market is closed");
            System.exit(-1);
        }


        //Get authentication token
        String authToken = TDARequest.refreshAuthToken();
        Config.updateAuthToken(authToken);


        System.out.println("Auth Token: \n" + authToken);


        long startTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - startTime;


        while(market.isOpen(false, false))
        {

            ArrayList<String> trendingUp = new ArrayList<>(20);

            //get symbols that we have not see before
            for(String symbol : TDARequest.allTopMovers("up"))
            {
                if(!previouslyEncountered.contains(symbol))
                {
                    trendingUp.add(symbol);
                    previouslyEncountered.add(symbol);
                }
            }


            //Get quotes and initialPurchase all newly encountered movers trending up
            for(Quote quote : TDARequest.getQuotes(trendingUp))
            {
                if(account.isEligible(quote))
                {
                    account.initialPurchase(quote.getSymbol());
                }
            }


            //Check the status of all active positions
            for(int i = 0; i < 5; ++i)
            {

                StringBuilder display = new StringBuilder();
                for (Quote quote :  account.getActivePositionsQuotes())
                {
                    Order order = account.getOrder(quote.getSymbol());
                    OrderType recommendation = order.update(quote.getBidprice());

                    display.append(order.status() + ", ");

                    if (recommendation == OrderType.SELL) {
                        account.closePosition(order);
                    }
                }

                try { TimeUnit.SECONDS.sleep(1); }
                catch (Exception e) { e.printStackTrace(); }

                System.out.println("\n" + display.toString());

            }

            //Check to see if we need to refresh auth Token
            timePassed = startTime - System.currentTimeMillis();
            if(timePassed > 1500000)
            {
                startTime = System.currentTimeMillis();
                Config.updateAuthToken(TDARequest.refreshAuthToken());
                System.out.println("Auth Token Refreshed");
            }

        }


        account.closeAllPositions();
        account.printSummary();
    }

}


