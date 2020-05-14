package com.MiguelBarrios;

import java.io.IOException;
import java.time.ZonedDateTime;

public class Request
{
    private static String apiKey = "9TTXMCZ89BDCA4KP807HELK5RZMYBZAP";

    private static String marketHours = "https://api.tdameritrade.com/v1/marketdata/EQUITY/hours?apikey=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP&date=";

    private static String topMovers = "https://api.tdameritrade.com/v1/marketdata/%s/movers?apikey=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP&direction=up&change=percent";

    //Used to get the hours the markets are open
    public static String marketHours()
    {
        int month = ZonedDateTime.now().getDayOfMonth();
        int day = ZonedDateTime.now().getMonthValue();
        int year = ZonedDateTime.now().getYear();

        return String.format("%s%4d-%02d-%02d", marketHours, year, day, month);
    }

    public static String topMoversNasdaq()
    {
        return String.format(topMovers, "$COMPX");
    }

    public static String topMoversDow()
    {
        return String.format(topMovers, "$DJI");
    }

    public static String topMoversSMP()
    {
        return String.format(topMovers, "$SPX.X");
    }


    //TODO: need to test if there is a limit to the number of stocks that can be returned
    public static String quotes(String[] arr)
    {
        String request = "https://api.tdameritrade.com/v1/marketdata/quotes?apikey=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP&symbol=";

        String out = "";
        int count = 1;
        for(String ticker : arr)
        {
            out += ticker + "%2C";
        }

        return request + out.substring(0,out.length() - 3);
    }
}