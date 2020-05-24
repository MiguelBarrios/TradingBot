package com.MiguelBarrios;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Market
{
	public static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    //String formattedTime = output.format(d);

    private Calendar preMarketOpen;

    private Calendar regularMarketOpen;

    private Calendar regularMarketClose;

    private Calendar extendedHoursClose;

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

    /**
     * Set the following true if we want to trade in the
     * Default is for regular market hours
     * @param pre pre market
     * @param afterHours post market
     * @return
     */
    public boolean isOpen(boolean pre, boolean afterHours)
    {
        Date cur = new Date(System.currentTimeMillis());

        //TODO: instead see if we can set hours to EST in constructor
        cur.setHours(cur.getHours() + 1);

        //TODO: simplify logic
        if(!pre && !afterHours)
        {
            return (cur.after(regularMarketOpen.getTime()) && cur.before(regularMarketClose.getTime()));
        }
        else if(pre && afterHours)
        {
            return (cur.after(preMarketOpen.getTime()) && cur.before(extendedHoursClose.getTime()));
        }
        else if(pre && !afterHours)
        {
            return (cur.after(preMarketOpen.getTime()) && cur.before(regularMarketClose.getTime()));
        }
        else if(!pre && afterHours)
        {
            return (cur.after(regularMarketOpen.getTime()) && cur.before(extendedHoursClose.getTime()));
        }

        return false;
    }

    @Override
    public String toString()
    {
    	return String.format("Date: %s\n%s Pre Market Open\n%s Regular Market Open\n%s Regular Market Close\n%s Post Market Close", 
    		date.format(regularMarketOpen.getTime()),
    		time.format(preMarketOpen.getTime()),
    		time.format(regularMarketOpen.getTime()),
    		time.format(regularMarketClose.getTime()),
    		time.format(extendedHoursClose.getTime()));
    }

}
