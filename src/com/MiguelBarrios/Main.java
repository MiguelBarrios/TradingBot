package com.MiguelBarrios;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args)
    {
        //Get authentication token
        TDARequest.refreshAuthToken();

        TradingStrategy topMovers = new TopMovers(10000,0,300,1,true);






        //For this trial a stock will only be purchased once
        Market market = TDARequest.marketHours();
        long startTime = System.currentTimeMillis();
        while(market != null && market.isOpen(false, false)) {



            //Check to see if we need to refresh auth Token
            if((System.currentTimeMillis() - startTime) > 1500000) {
                startTime = System.currentTimeMillis();
                TDARequest.refreshAuthToken();
            }
        }

        //Close all open position
        topMovers.closeAllPositions();
    }



}


