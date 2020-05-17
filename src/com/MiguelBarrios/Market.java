package com.MiguelBarrios;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Market
{
	public static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
    //String formattedTime = output.format(d);

    private Date preMarketOpen;

    private Date regularMarketOpen;

    private Date regularMarketClose;

    private Date extendedHoursClose;

    public Market(Date preMarketOpen, Date regularMarketOpen, Date regularMarketClose, Date extendedHoursClose)
    {
        this.preMarketOpen = preMarketOpen;
        this.regularMarketOpen = regularMarketOpen;
        this.regularMarketClose = regularMarketClose;
        this.extendedHoursClose = extendedHoursClose;
    }

    public boolean isOpen()
    {
    	return isOpen(true, true, true);
    }

    public boolean isOpen(boolean pre, boolean regular, boolean afterHours)
    {
    	return true;
    }

    @Override
    public String toString()
    {
    	return String.format("Date: %s\n%s Pre Market Open\n%s Regular Market Open\n%s Regular Market Close\n%s Post Market Close", 
    		date.format(regularMarketOpen),
    		time.format(preMarketOpen),
    		time.format(regularMarketOpen),
    		time.format(regularMarketClose),
    		time.format(extendedHoursClose));
    }

}
