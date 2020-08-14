package com.MiguelBarrios;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<Mover> parseMovers(String response)
    {
        ArrayList<Mover> movers;
        try{
            JSONArray arr = new JSONArray(response);
            movers = new ArrayList<>(arr.length());

            for(int i = 0; i < arr.length(); ++i)
            {
                JSONObject mover = arr.getJSONObject(i);
                double change = mover.getDouble("change");
                String direction = mover.getString("direction");
                double last = mover.getDouble("last");
                String symbol = mover.getString("symbol");
                int totalVolume = mover.getInt("totalVolume");
                movers.add(new Mover(change, direction, last, symbol, totalVolume));
            }

            return movers;

        }catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
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
            e.printStackTrace();
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
                if(quote != null)
                    quotes.add(quote);
            }
        }
        catch(Exception e)
        {
            //do nothing, sometimes request fails to get all values
        }

        return quotes;
    }

    public static AccountSummary parseAccountInfo(String responseString)
    {
        JSONObject json = new JSONObject(responseString);
        JSONObject securitiesAccount = json.getJSONObject("securitiesAccount");
        JSONObject initialBalances = securitiesAccount.getJSONObject("initialBalances");
        JSONObject currentBalances = securitiesAccount.getJSONObject("currentBalances");
        JSONObject projectedBalances = securitiesAccount.getJSONObject("projectedBalances");

        double accountValue = initialBalances.getDouble("accountValue");
        double availableFunds = projectedBalances.getDouble("cashAvailableForTrading");
        double buyingPower =  -1;

        //Get active Positions
        JSONArray activePositions = securitiesAccount.getJSONArray("positions");
        ArrayList<Position> positions = new ArrayList<>();
        for(int i = 0;  i < activePositions.length(); ++i)
        {
            JSONObject str = activePositions.getJSONObject(i);

            double currentDayProfitLoss = str.getDouble("currentDayProfitLoss");
            int longQuantity = str.getInt("longQuantity");
            double currentDayProfitLossPercentage = str.getDouble("currentDayProfitLossPercentage");
            int settledLongQuantity = str.getInt("settledLongQuantity");
            double marketValue = str.getDouble("marketValue");

            JSONObject instrument = str.getJSONObject("instrument");
            String symbol = instrument.getString("symbol");
            String cusip = instrument.getString("cusip");
            double averagePrice = str.getDouble("averagePrice");

            int settledShortQuantity = str.getInt("settledShortQuantity");
            int shortQuantity = str.getInt("shortQuantity");

            Position position = new Position(symbol, cusip, currentDayProfitLoss,currentDayProfitLossPercentage, longQuantity, settledLongQuantity, settledShortQuantity, shortQuantity, marketValue, averagePrice);
            positions.add(position);
        }
        return new AccountSummary(accountValue, availableFunds, buyingPower, accountValue, positions);
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

            timeZoneOffset(preMarketOpen);
            timeZoneOffset(regularMarketOpen);
            timeZoneOffset(regularMarketClose);
            timeZoneOffset(postMarketClose);
            return new Market(preMarketOpen, regularMarketOpen, regularMarketClose, postMarketClose);

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static void timeZoneOffset(Date date)
    {
        date.setHours(date.getHours() - 1);
    }


}

