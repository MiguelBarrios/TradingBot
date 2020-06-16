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

	public static Trade placeOrder(String symbol, OrderType type, int numShares)
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

	public static Market marketHours()
	{
		int month = ZonedDateTime.now().getDayOfMonth();
		int day = ZonedDateTime.now().getMonthValue();
		int year = ZonedDateTime.now().getYear();

		String request =  String.format("https://api.tdameritrade.com/v1/marketdata/EQUITY/hours?apikey=%s&date=%4d-%02d-%02d",
				Config.apiKey, year, day, month);

		String response = Client.sendRequest(request);

		return Parser.parseMarketHours(response);
	}

	public static String getAccountInfo()
	{
		String urlString = String.format("https://api.tdameritrade.com/v1/accounts/%s", Config.accountID);
		return Client.sendRequestGet(urlString);
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

	//Complete
	public static ArrayList<Quote> getQuotes(ArrayList<String> arr)
	{
		if(arr.size() == 0)
			return new ArrayList<>();

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
				String urlString = String.format("https://api.tdameritrade.com/v1/marketdata/quotes?symbol=%s",cur);

				System.out.print("Sending Request -> ");
				String response = Client.sendRequestGet(urlString);

				ArrayList<Quote> newQuotes = Parser.parseQuotes(response, arr);
				quotes.addAll(newQuotes);
				symbols = new StringBuilder();
			}
		}

		if(symbols.length() > 0)
		{
			symbols.deleteCharAt(symbols.length() - 1);

			String urlString = String.format("https://api.tdameritrade.com/v1/marketdata/quotes?symbol=%s",symbols);

			System.out.print("Sending Request -> ");
			String response = Client.sendRequestGet(urlString);
			quotes.addAll(Parser.parseQuotes(response, arr));
		}
		System.out.println("Cycle complete");
		return quotes;
	}

	//Complete
	public static String[] topMovers(Exchange exchange, String direction)
	{
		String url = String.format("https://api.tdameritrade.com/v1/marketdata/%s/movers?direction=%s&change=percent",
				exchange, direction);

		String response = Client.sendRequestGet(url);

		return Parser.parseMovers(response);
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