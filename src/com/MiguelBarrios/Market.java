package com.MiguelBarrios;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Market
{
    public static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    //String formattedTime = output.format(d);


    public long startTrading;

    public long stopTrading;

    public Market(Date startTrading, Date stopTrading)
    {
        this.startTrading = startTrading.getTime();
        this.stopTrading = stopTrading.getTime();
        System.out.println("Trading session");
        System.out.println(startTrading + " -> " + stopTrading);

    }

    public boolean isOpen()
    {
        Date cur = new Date();
        cur.setYear(2020);
        long now = cur.getTime();
    	return (now > startTrading) && (now < stopTrading);
    }

    public void waitForMarketToOpen() throws InterruptedException
    {
        Date now = new Date();
        now.setYear(2020);
        long waitTime = startTrading - now.getTime();

        if(waitTime > 0)
        {
            System.out.println("Waiting for:  " + waitTime);
            TimeUnit.MILLISECONDS.sleep(waitTime);
        }

        Clock clock = new Clock(this);
        clock.start();
    }

    @Override
    public String toString()
    {
    	return "";
    }


}
