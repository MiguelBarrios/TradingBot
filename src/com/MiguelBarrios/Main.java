package com.MiguelBarrios;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static int NUM_SHARES = 1;

    public static void main(String[] args) throws InterruptedException
    {



        /*
        TDARequest.refreshAuthToken();
        TDARequest.setSimulation(true);
        Log log = new Log("/Users/miguelbarrios/Documents/Projects/Logs/TopMovers");

        HashSet<String> encountered = new HashSet<>();
        ArrayList<Mover> trendingUp =  TDARequest.allTopMovers("up");


        //Find which stocks we have not encountered
        ArrayList<String> notEncountered = new ArrayList<>();
        for(Mover mover : trendingUp)
        {
            String symbol = mover.getSymbol();
            if(encountered.add(symbol))
            {
                notEncountered.add(symbol);
            }
        }

        //Buy Equity we have not encountered
        for(String tickerSymbol : notEncountered)
        {
            Trade trade = TDARequest.placeOrder(tickerSymbol, OrderType.BUY, NUM_SHARES);
            if(trade == null)
            {
                System.out.println("Order not placed");
            }
            else
            {
                log.saveTrade(trade);
            }
        }


         */

    }

}


