package com.MiguelBarrios;

import java.util.ArrayList;
import java.util.Arrays;

public class TDARequest
{
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
	public static ArrayList<Quote> getQuotes(String[] arr)
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

/*
OkHttpClient client = new OkHttpClient().newBuilder()
  .build();
MediaType mediaType = MediaType.parse("application/json");
RequestBody body = RequestBody.create(mediaType, "{\n  \"orderType\": \"MARKET\",\n  \"session\": \"NORMAL\",\n  \"duration\": \"DAY\",\n  \"orderStrategyType\": \"SINGLE\",\n  \"orderLegCollection\": [\n    {\n      \"instruction\": \"Buy\",\n      \"quantity\": 1,\n      \"instrument\": {\n        \"symbol\": \"ET\",\n        \"assetType\": \"EQUITY\"\n      }\n    }\n  ]\n}");
Request request = new Request.Builder()
  .url("https://api.tdameritrade.com/v1/accounts/496820767/orders")
  .method("POST", body)
  .addHeader("Authorization", "Bearer euLlUipASvTqKg6PUCYY2Sx8wKAbppuruUmUUrniP6tqm22mJ6qyjY0bmPPGhm4t3OKhoywMPJAfoVHu/CBRf1sBnq4Cn49HYUhOpck+kteguMk/4A67XAzcRu13P/d5v72brY8KqEqheIucJYMMlPo2YatVyt6GzXRnE6fjqANDmCQ1hHJfFbfDJbg4GumPieAMgaTrfawb4vgHaZpwvwm284/+eGEf3fUIbuc2jabCMKzsWhSp94jUUttREQx7XDY8GqZ/TZiiJCjgAWj4JsBcKc78CIbmArrIcHwA1pMZ0pEwfQNz9hmKie4kpMqB/7ZQGViiy5XkM4kHAyJVUwnsXCbUjHfJ0nya9NrSlIw+7J4FQkdazMuY1Ig1A50Xu8VKUXN9A+xeR6mVAs5DId0lXgUMncAw6RjkqHNfkeltU8k9Ut2dkQrfF0q8hKZ9HI0Rljd9j7u2qf29vEfE4/0sbEQ7RXj//NfTvVrLgbKmhrqZmrhuJj9XCwedFJeBFzLH2J2ExSpy71SoxrMcXog530FsI/Ydgd/dKPBEI5CqLZYKb100MQuG4LYrgoVi/JHHvl2g3KWE6gudF/Mrh1KhZbHpZevqBevz4u1v9CA2UCVUi2lU0uRDNjmQOSE9DNe+heJhmWRNFNlDaxeIalNHX7qGlY5h226B1ryVR5gDQfOZu+9VTPsSFe9xs1QqNZzIsLhumIzm9VHRPVFE03w4iMHCpDA9RBDNpUsI8rrM7XmpOIVT8UrUptmDZ0wOY61f/noPmCSPvutFaD0iaTZer6jjFwhhHvcI+F5cvmXSeefPNYbcdldxxxDs1k5f7N6wtoZ4/9Wu07vZ+KPOrTM06IoHFzjufqTSZH4PqiJX8qZ0Rih2EtIF2Tx3MOvYskHKHAJh0jIpw/9T3tR28NrG9rV+hZTWf461gF8JCv9C111JzWvfJsGBUZXvYuoCYbwbTC9md21my3cPzDymKFO0XPR6/XiiV2Pqr83R9vEWPz2WQJmQ2IxmLE/J9u62QsGCWTiZRHbWoDEZuyIHB5F3/8tXkH0jknZs2v8CxH2x5WzDNI+t42rAkBzmUHuoJhgS3xcCWDIWwpATKEvpil0Me3KBpolLK2jzlP/97TJVdRCQGPAHDA==212FD3x19z9sWBHDJACbC00B75E")
  .addHeader("Content-Type", "application/json")
  .build();
Response response = client.newCall(request).execute();

 */

