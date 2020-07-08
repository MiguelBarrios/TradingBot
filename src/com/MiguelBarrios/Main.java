package com.MiguelBarrios;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static int NUM_SHARES = 1;

    public static void main(String[] args) throws InterruptedException
    {

        Date open = new Date(2020, Calendar.JULY, 9, 8, 30);
        Date close = new Date(2020, Calendar.JULY, 9, 14, 55);
        Market market = new Market(open, close);


        //market.waitForMarketToOpen();
        TDARequest.refreshAuthToken();
        TDARequest.setSimulation(true);

        Account account = new Account();
        System.out.println(account);


        System.exit(-1);



        Log log = new Log("TopMovers");

        //For this strategy we will only purchase each equity once
        HashSet<String> previouslyEncountered = new HashSet<>();

        while(market.isOpen())
        {
            ArrayList<Mover> topGainers =  TDARequest.allTopMovers("up");

            //Purchase stocks that we have not encountered
            for(Mover mover : topGainers)
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
                }
            }

        }










    }

}


