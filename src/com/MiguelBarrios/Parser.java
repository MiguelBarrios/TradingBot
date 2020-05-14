package com.MiguelBarrios;

import org.json.JSONObject;


import java.io.IOException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Parser
{
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //String formattedTime = output.format(d);


    public static Date[] parseMarketHours(String responseString)
    {

        try{
            JSONObject json = new JSONObject(responseString);

            JSONObject hours = json.getJSONObject("equity")
                    .getJSONObject("EQ")
                    .getJSONObject("sessionHours")
                    .getJSONArray("regularMarket")
                    .getJSONObject(0);


            String openString = hours.getString("start").substring(0, 19);
            String closeString = hours.getString("end").substring(0, 19);

            Date open = sdf.parse(openString);
            Date close = sdf.parse(closeString);

            Date[] arr = {open, close};

            return arr;


        }catch (Exception e)
        {
            System.out.println("Market is closed");
            return null;
        }

    }

    public static ArrayList<Quote> parseQuotes(String quotesString, String[] stocks)
    {
        ArrayList<Quote> quotes = new ArrayList<>(stocks.length);

        JSONObject response = new JSONObject(quotesString);

        for(String symbol : stocks)
        {
            try{
                JSONObject obj = response.getJSONObject(symbol);
                double bidprice = obj.getDouble("bidPrice");
                int bidSize = obj.getInt("bidSize");
                double askPrice = obj.getDouble("askPrice");
                int askSize = obj.getInt("askSize");
                double netChange = obj.getDouble("netChange");
                boolean shortable = obj.getBoolean("shortable");

                Quote quote = new Quote(symbol, bidprice, bidSize, askPrice, askSize, netChange, shortable);
                quotes.add(quote);
            }
            catch(Exception e)
            {
                //do nothing, somtimes request fails to get all values
            }
        }

        return quotes;
    }
}

