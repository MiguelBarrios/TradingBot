package com.MiguelBarrios;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Request
{

    //TODO: FIND A WAY TO AUTOMATE THIS
    // this will take use to a authentication page on td- ameritrade
    // Then once we authenticate it will redirect us to a error page
    // decode the infor that comes after code=
    // then we can put that into the code section on https://developer.tdameritrade.com/authentication/apis/post/token-0
    // this will give us the access_token
    public static String auth = "https://auth.tdameritrade.com/auth?response_type=code&redirect_uri=https://localhost&client_id=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP%40AMER.OAUTHAP";

    private String redirect_uri =  "https://localhost";

    private static String apiKey = "9TTXMCZ89BDCA4KP807HELK5RZMYBZAP";

    private static String marketHours = "https://api.tdameritrade.com/v1/marketdata/EQUITY/hours?apikey=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP&date=";

    private static String movers = "https://api.tdameritrade.com/v1/marketdata/%s/movers?apikey=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP&direction=%s&change=percent";

    private static String SMP = "$SPX.X";

    private static String DOW = "$DJI";

    private static String NASDAQ = "$COMPX";

    private static String UP = "up";

    private static String DOWN = "down";

    public static String getAuthorizationToken()
    {
    	System.out.println(auth);
    	return "";
    }

    public static Market marketHours()
    {
        int month = ZonedDateTime.now().getDayOfMonth();
        int day = ZonedDateTime.now().getMonthValue();
        int year = ZonedDateTime.now().getYear();

        String request =  String.format("%s%4d-%02d-%02d", marketHours, year, day, month);

        String response = Client.sendRequest(request);

        Market hours = Parser.parseMarketHours(response);

        return hours;
    }

    public static String[] topMovers(Exchange exchange, String direction)
    {
        String request = String.format(movers, exchange, direction);
        String response = Client.sendRequest(request);

        return Parser.parseMovers(response);
    }


    //TODO: need to test if there is a limit to the number of stocks that can be returned
    public static ArrayList<Quote> getQuotes(String[] arr)
    {
        String request = "https://api.tdameritrade.com/v1/marketdata/quotes?apikey=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP&symbol=";

        String out = "";
        int count = 1;
        for(String ticker : arr)
        {
            out += ticker + "%2C";
        }

        request = request + out.substring(0,out.length() - 3);

        String response = Client.sendRequest(request);

        return Parser.parseQuotes(response,arr);
    }

    public static Quote quote(String symbol)
    {
        String[] arr = {symbol};

        return getQuotes(arr).get(0);

    }

    public static Trade placeOrder(String symbol, OrderType type, int numShares)
    {
        if(type == OrderType.BUY)
        {
            double price = buy(symbol, numShares);
            return new Trade(OrderType.BUY, numShares,price, symbol);
        }
        else if(type == OrderType.SELL)
        {
            double price = sell(symbol,numShares);
            return new Trade(OrderType.SELL, numShares, price, symbol);
        }

        return null;
    }

    public static double sell(String symbol, int numShares)
    {
        //TODO: once testing is complete implement actual buy
        //Will just get quote for now
        return quote(symbol).getAskPrice();
    }

    public static double buy(String symbol, int numShares)
    {
        //TODO: once testing is complete implement actual buy
        //Will just get quote for now
        return quote(symbol).getAskPrice();
    }
}