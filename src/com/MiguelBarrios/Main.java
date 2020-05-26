package com.MiguelBarrios;

import com.twilio.Twilio;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/*
 If you are buying a stock you are going to get the ask price.

 If you are selling a stock, you are going to get the bid price,

 */


public class Main
{
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN

    public static final String RESET = "\033[0m";  // Text Reset




    public static void main(String[] args)
    {


        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);

        //-------------------------------------------------------------------------------------------------------------
        Account account = new Account(5000,0, 1000, 1, true);

        //Get authentication token
        String authToken = TDARequest.refreshAuthToken();
        Config.updateAuthToken(authToken);


        //All stocks previously encountered
        //For this trial a stock will only be purchased once
        HashSet<String> previouslyEncountered = new HashSet<>();

        Market market = TDARequest.marketHours();

        if(market == null)
        {
            System.out.println("Market is closed");
            System.exit(-1);
        }

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < 2; ++i)
        //while(market.isOpen(false, false))
        {

            ArrayList<String> trendingUp = new ArrayList<>(20);

            //get symbols that we have not see before
            String[] movers = TDARequest.allTopMovers("up");

            if(movers != null)
            {
                for(String symbol : movers)
                {
                    if(!previouslyEncountered.contains(symbol))
                    {
                        trendingUp.add(symbol);
                        previouslyEncountered.add(symbol);
                    }
                }
            }


            //Get quotes and initialPurchase all newly encountered movers trending up
            ArrayList<Quote> quotesMovers = TDARequest.getQuotes(trendingUp);

            if(quotesMovers != null)
            {
                for(Quote quote : quotesMovers)
                {
                    if(account.isEligible(quote))
                    {
                        account.initialPurchase(quote.getSymbol());
                    }
                }
            }

            if(account.numberActivePosition() == 0 )
            {
                System.out.println("No Holdings");
                pause(10);
            }
            else
            {
                //Check the status of all active positions
                for(int j = 0; j < 5; ++j)
                {

                    ArrayList<Quote> quotes =  account.getActivePositionsQuotes();

                    for (Quote quote :  quotes)
                    {
                        Order order = account.getOrder(quote.getSymbol());
                        OrderType recommendation = order.update(quote.getBidprice());

                        if(order.change())
                        {
                            System.out.print(GREEN + order.status() + ",");
                        }
                        else
                        {
                            System.out.print(RED + order.status() + ",");
                        }

                        if (recommendation == OrderType.SELL) {
                            Order completedOrder = account.closePosition(order);

                        /*
                        Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(Config.myPhoneNum), //To
                                new com.twilio.type.PhoneNumber(Config.sendPhoneNum), //From
                                completedOrder.smsFormat()) //Message
                                .create();
                                */
                        }
                    }


                    pause(1);

                    //TODO: print in color
                    //format text so that they all take up the same amount of space and alighne
                    System.out.println(RESET);
                }

            }


            //Check to see if we need to refresh auth Token
            long timePassed = System.currentTimeMillis() - startTime;
            if(timePassed > 1500000)
            {
                startTime = System.currentTimeMillis();
                Config.updateAuthToken(TDARequest.refreshAuthToken());
                System.out.println("Auth Token Refreshed");
            }

        }



        System.out.println("Closing all open Positions");


        //TODO: works figure out how to do it with iterator, without getting concurrent modification exception
        ArrayList<Quote> quotes =  account.getActivePositionsQuotes();
        for(Quote quote : quotes) {
            Order order = account.getOrder(quote.getSymbol());
            account.closePosition(order);
        }


        account.printSummary();
    }


    public static void pause(long time)
    {
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}


