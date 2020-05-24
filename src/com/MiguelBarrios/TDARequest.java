package com.MiguelBarrios;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class TDARequest
{
	public static boolean simulation = false;

	public static Trade placeOrder(String symbol, OrderType type, int numShares)
	{
		if(type == OrderType.BUY)
		{
			Trade trade =  buy(symbol, numShares);
			Log.saveTrade(trade);
			return trade;
		}
		else if(type == OrderType.SELL)
		{
			Trade trade = sell(symbol,numShares);
			Log.saveTrade(trade);
			return trade;
		}

		return null;
	}


	public static Trade buy(String symbol, int numShares)
	{
		if(simulation)
		{
			Quote quote = getQuote(symbol);
			return new Trade(OrderType.BUY, numShares, quote.getAskPrice(), symbol);
		}
		else
		{
			//TODO: execute buy order
		}

		return null;
	}

	public static Trade sell(String symbol, int numShares)
	{
		if(simulation)
		{
			Quote quote = getQuote(symbol);
			return new Trade(OrderType.SELL, numShares, quote.getBidprice(), symbol);
		}
		else
		{
			//TODO: EXECUTE SELL ORDER
		}

		return null;
	}




	//Complete
	public static Market marketHours()
	{
		int month = ZonedDateTime.now().getDayOfMonth();
		int day = ZonedDateTime.now().getMonthValue();
		int year = ZonedDateTime.now().getYear();

		String request =  String.format("https://api.tdameritrade.com/v1/marketdata/EQUITY/hours?apikey=%s&date=%4d-%02d-%02d",
				Config.apiKey, year, day, month);

		String response = Client.sendRequest(request);

		Market hours = Parser.parseMarketHours(response);

		return hours;

	}

	//Complete
	public static String getAccountInfo()
	{
		String urlString = String.format("https://api.tdameritrade.com/v1/accounts/%s", Config.accountID);

		return Client.sendRequestGet(urlString);
	}

	//Complete
	public static Quote getQuote(String symbol)
	{
		String urlString = String.format("https://api.tdameritrade.com/v1/marketdata/%s/quotes", symbol);
		String response = Client.sendRequestGet(urlString);
		return Parser.parseQuote(response, symbol);
	}

	//Complete
	public static ArrayList<Quote> getQuotes(ArrayList<String> arr)
	{
		StringBuilder test = new StringBuilder();
		for(String symbol : arr)
		{
			test.append(symbol.trim() + ",");
		}

		String symbols = test.substring(0, test.length() - 1);

		String urlString = "https://api.tdameritrade.com/v1/marketdata/quotes?symbol=" + symbols;

		String response = Client.sendRequestGet(urlString);
		return Parser.parseQuotes(response, arr);
	}

	//Complete
	public static String[] topMovers(Exchange exchange, String direction)
	{
		String url = String.format("https://api.tdameritrade.com/v1/marketdata/%s/movers?direction=%s&change=percent",
				exchange, direction);

		String response = Client.sendRequestGet(url);

		String[] one = Parser.parseMovers(response);
		return one;
	}

	public static String[] allTopMovers(String direction)
	{
		String[] smp = topMovers(Exchange.SMP, direction);
		String[] dow = topMovers(Exchange.DOW, direction);
		String[] nasdaq = topMovers(Exchange.NASDAQ, direction);

		String[] combined = new String[smp.length + dow.length + nasdaq.length];

		int index = 0;
		for(String cur : smp) {
			combined[index] = cur;
			++index;
		}

		for(String cur : dow) {
			combined[index] = cur;
			++index;
		}

		for(String cur : nasdaq) {
			combined[index] = cur;
			++index;
		}

		return combined;
	}

	//Need to update refresh token july 24
	//Complete
	public static String refreshAuthToken()
	{
		String urlString = "https://api.tdameritrade.com/v1/oauth2/token";
		String post_data = "grant_type=refresh_token&refresh_token=" + Config.refreshToknn + "&access_type=&code=&client_id=" + Config.apiKey + "&redirect_uri=";

		String response = Client.sendRequestPost(urlString, post_data);

		String out = Parser.parsAuthToken(response);
		return out;
	}

	public static void placeOrder()
	{

	}
}

