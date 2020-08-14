package com.MiguelBarrios;
import org.json.JSONObject;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TDARequest
{
	private static boolean simulation = true;

	public static void setSimulation(boolean simulation)
	{
		TDARequest.simulation = simulation;
	}

	public static boolean placeBuySellOrder(String symbol, int quantity, double buyPrice, double sellPrice)
	{
		if(!simulation) {
			String order = OrderBuilder.trailingBuySellOrder(symbol, quantity, buyPrice, sellPrice);
			int responseCode =  Client.placeOrder(order);
			return (responseCode < 300);
		}

		return true;
	}


	public static void placeTrailingOrder(String symbol, OrderType buySell, int quantity, double stopPriceOffset)
	{
		String out = OrderBuilder.trailingOrder(buySell.toString(), symbol, quantity, stopPriceOffset);
		System.out.println(out);
	}

	public static boolean placeLimitOrder(String symbol, OrderType type, int numShares, double price)
	{
		if(!simulation) {
			String order = OrderBuilder.limitOrder(type.toString(), symbol, numShares, price);
			int responseCode =  Client.placeOrder(order);
			return (responseCode < 300);
		}
		else
		{
			return true;
		}
	}

	public static boolean placeMarketOrder(String symbol, OrderType type, int numShares)
	{
		if(!simulation) {
			String order = OrderBuilder.marketOrder(type.toString(), symbol, numShares);
			int responseCode =  Client.placeOrder(order);
			return (responseCode < 300);
		}

		return true;
	}

	public static AccountSummary getAccountInfo()
	{
		String urlString = String.format("https://api.tdameritrade.com/v1/accounts/%s?fields=positions,orders", Config.accountID);
		String response = Client.sendRequestGet(urlString);
		return Parser.parseAccountInfo(response);
	}

	public static Quote getQuote(String symbol)
	{
		String urlString = String.format("https://api.tdameritrade.com/v1/marketdata/%s/quotes", symbol);
		String response = Client.sendRequestGet(urlString);

		try{
			JSONObject obj = new JSONObject(response);
			return Parser.parseQuote(obj, symbol);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static ArrayList<Quote> getQuotes(ArrayList<String> arr)
	{
		if(arr.size() == 0)
			return new ArrayList<>();

		System.out.print("Building Request -> ");
		ArrayList<String> requests = new ArrayList<>(arr.size() / 500 + 1);
		ArrayList<Quote> quotes = new ArrayList<>();
		StringBuilder symbols = new StringBuilder();

		for(int i = 0, j = 1; i < arr.size(); ++i, ++j)
		{
			if(j <= 500)
			{
				symbols.append(arr.get(i)).append(",");
			}
			else
			{
				j = 1;
				symbols.deleteCharAt(symbols.length() - 1);
				String cur = symbols.toString();
				String request = String.format("https://api.tdameritrade.com/v1/marketdata/quotes?symbol=%s",cur);
				requests.add(request);
				symbols = new StringBuilder();
			}
		}

		if(symbols.length() > 0)
		{
			symbols.deleteCharAt(symbols.length() - 1);
			String request = String.format("https://api.tdameritrade.com/v1/marketdata/quotes?symbol=%s",symbols);
			requests.add(request);
		}


		//Sending Requests
		System.out.print("Sending Requests ->");
		ArrayList<String> responses = new ArrayList<>(arr.size() / 500 + 1);
		for(String request : requests)
		{
			String response = Client.sendRequestGet(request);
			responses.add(response);
		}

		System.out.print("Parsing response -> ");
		for(String response : responses)
		{
			ArrayList<Quote> newQuotes = Parser.parseQuotes(response, arr);
			quotes.addAll(newQuotes);
		}

		System.out.println("Cycle complete");
		return quotes;
	}

	public static ArrayList<Mover> topMovers(Exchange exchange, String direction, String change)
	{
		String url = String.format("https://api.tdameritrade.com/v1/marketdata/%s/movers?direction=%s&change=%s",
				exchange, direction, change);

		String response = Client.sendRequestGet(url);

		return Parser.parseMovers(response);
	}

	public static ArrayList<Mover> allTopMovers(String direction, String change)
	{
		ArrayList<Mover> movers = topMovers(Exchange.SMP, direction, change);
		movers.addAll(topMovers(Exchange.NASDAQ, direction, change));
		return movers;
	}

	public static ArrayList<Mover> getAllMovers()
	{
		ArrayList<Mover> topMovers = TDARequest.allTopMovers("up", "percent");
		topMovers.addAll(TDARequest.allTopMovers("up", "value"));
		topMovers.addAll(TDARequest.allTopMovers("down", "percent"));
		topMovers.addAll(TDARequest.allTopMovers("down", "value"));
		return topMovers;
	}

	//Need to update refresh token july 24
	public static void refreshAuthToken()
	{
		String urlString = "https://api.tdameritrade.com/v1/oauth2/token";

		String post_data = String.format("grant_type=refresh_token&refresh_token=%s&access_type=&code=&client_id=%s&redirect_uri="
							,Config.refreshToknn, Config.apiKey);

		String response = Client.sendRequestPost(urlString, post_data);
		String authToken = Parser.parsAuthToken(response);
		System.out.println(authToken);
		Config.updateAuthToken(authToken);
	}

	public static Market getMarketHours()
	{
		int month = ZonedDateTime.now().getDayOfMonth();
		int day = ZonedDateTime.now().getMonthValue();
		int year = ZonedDateTime.now().getYear();

		String request =  String.format("https://api.tdameritrade.com/v1/marketdata/EQUITY/hours?apikey=%s&date=%4d-%02d-%02d",
				Config.apiKey, year, day, month);

		String response = Client.sendRequest(request);

		return Parser.parseMarketHours(response);
	}
}