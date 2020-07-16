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

	public static void placeLimitOrder(String symbol, OrderType type, int numShares, double price)
	{
		if(!simulation) {
			String order = OrderBuilder.limitOrder(type.toString(), symbol, numShares, price);
			String response =  Client.placeOrder(order);
			System.out.println(response);
		}
	}

	public static Trade placeMarketOrder(String symbol, OrderType type, int numShares)
	{
		if(!simulation) {
			String order = OrderBuilder.marketOrder(type.toString(), symbol, numShares);
			String response =  Client.placeOrder(order);
			System.out.println(response);
		}

		//TODO: find a way to check the price if simulation is off, then everthing below is unnesesary
		Quote quote = getQuote(symbol);
		if(quote != null) {
			double price = (type == OrderType.SELL) ? quote.getBidprice() : quote.getAskPrice();
			return new Trade(type, numShares, price, symbol);
		}

		return null;
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



	//Complete un for request graeter than 500
	public static ArrayList<Quote> getQuotes(ArrayList<String> arr)
	{
		//TODO: modify arr becuase it is cycling through all the symbols when parsing the resonses

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

	/**
	 *
	 * @param exchange
	 * @param direction
	 * @param change can be "percent" or "value"
	 * @return
	 */
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








}