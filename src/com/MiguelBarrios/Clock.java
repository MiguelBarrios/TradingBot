
package com.MiguelBarrios;
import java.util.concurrent.TimeUnit;

public class Clock extends Thread
{
    public void run()
    {
        //Refresh authentication token whenever its about to expire
        while(Market.isOpen(Market.tradeExtendedHours(), false)) {
            TDARequest.refreshAuthToken();
            try {
                TimeUnit.MINUTES.sleep(28);
            }
            catch (Exception e) {
                TDARequest.refreshAuthToken();
            }
        }
    }
}