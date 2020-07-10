package com.MiguelBarrios;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Date;
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
        HashSet<String> previouslyEncountered = new HashSet<>();

        while(true)//market.isOpen())
        {
            System.out.println("Getting top movers");
            ArrayList<Mover> topGainers =  TDARequest.allTopMovers("up", "percent");
            topGainers.addAll(TDARequest.allTopMovers("up", "value"));

            for(Mover mover : topGainers)
            {
                if(previouslyEncountered.add(mover.getSymbol()))
                    Log.saveMover(mover);

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
            TimeUnit.SECONDS.sleep(30);

        }
    }
}


