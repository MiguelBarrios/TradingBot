package com.MiguelBarrios;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Market
{
    public static final SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private static Calendar preMarketOpen;

    private static Calendar regularMarketOpen;

    private static Calendar regularMarketClose;

    private static Calendar extendedHoursClose;

    private static boolean tradeExtendedHours = false;

    public Market(Date preMarketOpen, Date regularMarketOpen, Date regularMarketClose, Date extendedHoursClose)
    {
        Market.preMarketOpen = Calendar.getInstance();
        Market.regularMarketOpen = Calendar.getInstance();
        Market.regularMarketClose = Calendar.getInstance();
        Market.extendedHoursClose = Calendar.getInstance();

        Market.preMarketOpen.setTime(preMarketOpen);
        Market.regularMarketOpen.setTime(regularMarketOpen);
        Market.regularMarketClose.setTime(regularMarketClose);
        Market.extendedHoursClose.setTime(extendedHoursClose);
    }

    public static void setTradeExtendedHours()
    {
        tradeExtendedHours = true;
    }

    public static boolean tradeExtendedHours()
    {
        return tradeExtendedHours;
    }

    public static boolean isOpen(boolean pre, boolean afterHours)
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

    public static void waitForMarketToOpen() throws InterruptedException
    {
        Calendar now = Calendar.getInstance();

        //Regular market open as 8:30 -> 3:00
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        //Starting program the day before
        int remainingHours;
        int remainingMinutes;
        if(hourOfDay > 16)
        {
            remainingHours = 8 + (23 - hourOfDay);
        }
        else  // Starting program the day of
        {
            remainingHours = 8 - (23 - hourOfDay);
        }

        remainingMinutes = 30 + (60 - minute);
        int waitTime = (60 * remainingHours) + remainingMinutes;
        int hours = waitTime / 60;
        int minutes = waitTime % 60;

        TimeUnit.MINUTES.sleep(waitTime);
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
