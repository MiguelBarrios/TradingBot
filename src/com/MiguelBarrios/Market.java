package com.MiguelBarrios;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Market
{
    public static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    //String formattedTime = output.format(d);


    public long startTrading;

    public long stopTrading;

    public Market(Date startTrading, Date stopTrading)
    {
        this.startTrading = startTrading.getTime();
        this.stopTrading = stopTrading.getTime();
    }

    public boolean isOpen()
    {
        long now = System.currentTimeMillis();
    	return (now > startTrading) && (now < stopTrading);

    }

    public void waitForMarketToOpen() throws InterruptedException
    {
        long now = System.currentTimeMillis();
        long waitTime = startTrading - now;
        System.out.println("Waiting: " + time.format(waitTime));
        TimeUnit.MILLISECONDS.sleep(waitTime);
    }



    @Override
    public String toString()
    {
    	return "";
    }


}
