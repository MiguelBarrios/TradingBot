package com.MiguelBarrios;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Parser
{
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static String parsAuthToken(String responseString)
    {
        try{
            JSONObject json = new JSONObject(responseString);
            return json.get("access_token").toString();
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }

        return null;
    }

    public static Market parseMarketHours(String responseString)
    {
        try{
            JSONObject json = new JSONObject(responseString);

            JSONObject sessionHours = json.getJSONObject("equity")
                    .getJSONObject("EQ")
                    .getJSONObject("sessionHours");


            JSONObject regularMarket = sessionHours.getJSONArray("regularMarket").getJSONObject(0);
            Date regularMarketOpen = sdf.parse(regularMarket.getString("start").substring(0, 19));
            Date regularMarketClose = sdf.parse(regularMarket.getString("end").substring(0, 19));

            String preMarket = sessionHours.getJSONArray("preMarket").getJSONObject(0).getString("start").substring(0, 19);
            String postMarket = sessionHours.getJSONArray("postMarket").getJSONObject(0).getString("end").substring(0,19);

            Date preMarketOpen = sdf.parse(preMarket);
            Date postMarketClose = sdf.parse(postMarket);

            return new Market(preMarketOpen, regularMarketOpen, regularMarketClose, postMarketClose);

        }catch (Exception e)
        {
            return null;
        }

    }


    public static String[] parseMovers(String response)
    {
        JSONArray arr = new JSONArray(response);
        String[] symbols = new String[arr.length()];

        for(int i = 0; i < arr.length(); ++i)
        {
            JSONObject object = arr.getJSONObject(i);
            double change = object.getDouble("change");
            String symbol = object.getString("symbol");
            symbols[i] = symbol;
        }

        return symbols;
    }

    public static Quote parseQuote(JSONObject response, String symbol)
    {
        try{
            JSONObject obj = response.getJSONObject(symbol);
            double bidprice = obj.getDouble("bidPrice");
            int bidSize = obj.getInt("bidSize");
            double askPrice = obj.getDouble("askPrice");
            int askSize = obj.getInt("askSize");
            double netChange = obj.getDouble("netChange");
            int totalVolume = obj.getInt("totalVolume");
            boolean shortable = obj.getBoolean("shortable");
            double volitility = obj.getDouble("volatility");
            double regularMarketNetChange = obj.getDouble("regularMarketNetChange");

            return new Quote(symbol, bidprice, bidSize, askPrice, askSize, netChange, totalVolume, shortable, volitility, regularMarketNetChange);
        }
        catch (Exception e)
        {
            System.out.println(symbol + " Not Found");
        }
        return null;
    }


    public static ArrayList<Quote> parseQuotes(String quotesString,ArrayList<String> stocks)
    {
        if(stocks.size() == 0)
            return new ArrayList<>();

        ArrayList<Quote> quotes = new ArrayList<>(stocks.size());

        try{
            JSONObject response = new JSONObject(quotesString);
            for(String symbol : stocks)
            {
                Quote quote = parseQuote(response, symbol);
                quotes.add(quote);
            }
        }
        catch(Exception e)
        {
            //do nothing, sometimes request fails to get all values
        }

        return quotes;
    }

}

