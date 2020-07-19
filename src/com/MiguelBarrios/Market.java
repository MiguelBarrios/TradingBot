package com.MiguelBarrios;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Market
{
    public static final SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private final Calendar preMarketOpen;

    private final Calendar regularMarketOpen;

    private final Calendar regularMarketClose;

    private final Calendar extendedHoursClose;

    public Market(Date preMarketOpen, Date regularMarketOpen, Date regularMarketClose, Date extendedHoursClose)
    {
        this.preMarketOpen = Calendar.getInstance();
        this.regularMarketOpen = Calendar.getInstance();
        this.regularMarketClose = Calendar.getInstance();
        this.extendedHoursClose = Calendar.getInstance();

        this.preMarketOpen.setTime(preMarketOpen);
        this.regularMarketOpen.setTime(regularMarketOpen);
        this.regularMarketClose.setTime(regularMarketClose);
        this.extendedHoursClose.setTime(extendedHoursClose);
    }

    //if we want to trade from start of pre market to after hours market
    public boolean isOpen()
    {
        return isOpen(false, false);
    }

    public boolean isOpen(boolean pre, boolean afterHours)
    {
        Date cur = new Date(System.currentTimeMillis());

        if(!pre && !afterHours)
        {
            return (cur.after(regularMarketOpen.getTime()) && cur.before(regularMarketClose.getTime()));
        }
        else if(pre && afterHours)
        {
            return (cur.after(preMarketOpen.getTime()) && cur.before(extendedHoursClose.getTime()));
        }
        else if(pre)
        {
            return (cur.after(preMarketOpen.getTime()) && cur.before(regularMarketClose.getTime()));
        }
        else
        {
            return (cur.after(regularMarketOpen.getTime()) && cur.before(extendedHoursClose.getTime()));
        }
    }

    public static Market waitForMarketToOpen() throws InterruptedException
    {
        Market market = TDARequest.getMarketHours();
        //Weekend
        while(market == null)
        {
            int hourOfDay = ZonedDateTime.now().getHour();
            System.out.println("Waiting for " + (24 - hourOfDay) + " hrs");
            TimeUnit.HOURS.sleep(24 - hourOfDay);
            market = TDARequest.getMarketHours();
        }

        //past market hours need next day
        if(market.preMarketOpen.getTime().getHours() > 16)
        {
            int hourOfDay = ZonedDateTime.now().getHour();
            System.out.println("Waiting for " + (24 - hourOfDay) + " hrs");
            TimeUnit.HOURS.sleep(24 - hourOfDay);
            market = TDARequest.getMarketHours();
        }

        long open = market.regularMarketOpen.getTime().getTime();

        Date cur = new Date();
        cur.setYear(ZonedDateTime.now().getYear());

        long waitTime = open - cur.getTime();
        int minuts = (int)TimeUnit.MILLISECONDS.toMinutes(waitTime) % 60;
        int hour = (int)TimeUnit.MILLISECONDS.toHours(waitTime);

        System.out.println("Waiting for %d hrs %d min");
        TimeUnit.MILLISECONDS.sleep(waitTime);
        return market;
    }

    @Override
    public String toString()
    {
        return String.format("Market Hours: %s\nPreMarket open: %s\nRegularMarketOpen: %s\nRegularMarketClose: %s\nExtended Market Close: %s",
                dateFormat.format(regularMarketOpen.getTime()),
                timeFormat.format(preMarketOpen.getTime()),
                timeFormat.format(regularMarketOpen.getTime()),
                timeFormat.format(regularMarketClose.getTime()),
                timeFormat.format(extendedHoursClose.getTime()));
    }
}
