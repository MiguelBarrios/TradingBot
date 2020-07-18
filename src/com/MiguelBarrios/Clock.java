
package com.MiguelBarrios;
import java.util.concurrent.TimeUnit;

public class Clock extends Thread
{
    public Market market;

    public Clock(Market market)
    {
        this.market = market;
    }

    public void run()
    {
        System.out.println("Clock thread started");
        //Refresh authentication token whenever its about to expire
        while(market.isOpen()) {
            TDARequest.refreshAuthToken();
            System.out.println("AuthToken Refreshed");
            try {
                TimeUnit.MINUTES.sleep(28);
            }
            catch (Exception e) {
                TDARequest.refreshAuthToken();
            }
        }
    }
}