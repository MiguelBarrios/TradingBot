package com.MiguelBarrios;
import com.twilio.Twilio;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.twilio.rest.api.v2010.account.Message;

public class Main
{
    public static void main(String[] args)
    {
        Twilio.init(Config.ACCOUNT_SID, Config.AUTH_TOKEN);
        Account account = new Account(100000,0, 2000, 1, true);

        //Get authentication token
        TDARequest.refreshAuthToken();

        //All stocks previously encountered
        //For this trial a stock will only be purchased once
        HashSet<String> previouslyEncountered = new HashSet<>();
        Market market = TDARequest.marketHours();

        long startTime = System.currentTimeMillis();
        while(market != null && market.isOpen(false, false)) {
            ArrayList<String> trendingUp = new ArrayList<>(20);

            //get symbols that we have not see before
            String[] movers = TDARequest.allTopMovers("up");

            if(movers.length != 0) {
                for(String symbol : movers) {
                    if(!previouslyEncountered.contains(symbol)) {
                        trendingUp.add(symbol);
                        previouslyEncountered.add(symbol);
                    }
                }
            }

            //Get quotes and initialPurchase all newly encountered movers trending up
            ArrayList<Quote> quotesMovers = TDARequest.getQuotes(trendingUp);

            if(quotesMovers.size() != 0) {
                for(Quote quote : quotesMovers) {
                    if(account.isEligible(quote)) {
                        account.initialPurchase(quote.symbol);
                    }
                }
            }

            for(int j = 0; j < 5 && (account.numberActivePosition() != 0); ++j) {
                ArrayList<Quote> quotes =  account.getActivePositionsQuotes();
                for (Quote quote :  quotes) {
                    Order order = account.getOrder(quote.symbol);
                    OrderType recommendation = order.update(quote.bidprice);

                    String color = (order.change()) ? Color.GREEN : Color.RED;
                    System.out.print(color + order.status() + ",");

                    if (recommendation == OrderType.SELL) {
                        account.closePosition(order);
                    }
                }
                System.out.println(Color.RESET);
                pause(1);
            }

            //Check to see if we need to refresh auth Token
            if((System.currentTimeMillis() - startTime) > 1500000) {
                startTime = System.currentTimeMillis();
                TDARequest.refreshAuthToken();
            }
        }

        //Close all open position
        if(account.numberActivePosition() > 0)
        {
            ArrayList<Quote> quotes =  account.getActivePositionsQuotes();
            for(Quote quote : quotes) {
                Order order = account.getOrder(quote.symbol);
                account.closePosition(order);
            }
        }
    }

    public static void pause(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSMS(String sms)
    {
        try{
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(Config.myPhoneNum), //To
                    new com.twilio.type.PhoneNumber(Config.sendPhoneNum), //From
                    sms) //Message
                    .create();
        }
        catch (Exception e) {
            //do nothing
        }
    }
}


