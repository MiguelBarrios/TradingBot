package com.MiguelBarrios;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Market market = Market.waitUntilOpen();

        //Get authentication token
        TDARequest.refreshAuthToken();
        long startTime = System.currentTimeMillis();

        TradingStrategy gainers = new TopGainers();
        gainers.start();

        //Check to see if we need to refresh auth Token
        while(market.isOpen(false, true)) {
            if((System.currentTimeMillis() - startTime) > 1500000) {
                startTime = System.currentTimeMillis();
                TDARequest.refreshAuthToken();
            }
            Util.pause(120);
        }
    }

}


