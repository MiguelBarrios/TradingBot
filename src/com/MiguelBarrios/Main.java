package com.MiguelBarrios;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args) throws IOException
    {




        Market market = TDARequest.marketHours();

        while(market == null) {
            System.out.println("Waiting for market to open");
            Util.pause(60);
            market = TDARequest.marketHours();
        }

        while(!market.isOpen(false, false)) {
            System.out.println("Waiting for main market to open ...");
            Util.pause(20);
        }


        //Get authentication token
        TDARequest.refreshAuthToken();
        long startTime = System.currentTimeMillis();

        TradingStrategy topMovers = new TopMovers(10000,0,200,1,true);

        while(market.isOpen(false, false)) {

            topMovers.cycle();

            Util.pause(5);

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


