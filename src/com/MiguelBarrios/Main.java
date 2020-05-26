package com.MiguelBarrios;

import com.twilio.Twilio;

import java.util.*;
import java.util.concurrent.TimeUnit;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Main
{

    public static void main(String[] args)
    {

        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);

        //-------------------------------------------------------------------------------------------------------------
        Account account = new Account(50, 10, 1, true);

        //Get authentication token
        String authToken = TDARequest.refreshAuthToken();
        Config.updateAuthToken(authToken);
        System.out.println("Auth Token: \n" + authToken);


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
                        Order completedOrder = account.closePosition(order);
                        Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(Config.myPhoneNum), //To
                                new com.twilio.type.PhoneNumber(Config.sendPhoneNum), //From
                                completedOrder.smsFormat()) //Message
                                .create();

                    }
                }

                try { TimeUnit.SECONDS.sleep(1); }
                catch (Exception e) { e.printStackTrace(); }

                System.out.println("\n" + display.toString());

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

        //Close all active orders
        for(String key: account.getKeySet())
        {
            Order order = account.getOrder(key);
            account.closePosition(order);

            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(Config.myPhoneNum), //To
                    new com.twilio.type.PhoneNumber(Config.sendPhoneNum), //From
                    order.smsFormat()) //Message
                    .create();
        }


        account.printSummary();
    }

}


