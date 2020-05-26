package com.MiguelBarrios;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.net.URL;
import java.net.HttpURLConnection;




public class Client
{
    private static HttpClient client = HttpClient.newHttpClient();

    public static String sendRequest(String requestType)
    {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestType))
                    .build();

            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return response;
        }
        catch (Exception e)
        {
            System.out.println("Error: Request failed");
            return null;
        }
    }

    public static String sendRequestGet(String urlString)
    {
        try{
            URL url = new URL(urlString);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            //Add header
            String auth = String.format("Bearer %s", Config.getAuthToken());
            httpURLConnection.setRequestProperty("Authorization", auth);

            String line = "";
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine()) != null)
            {
                response.append(line);
            }

            bufferedReader.close();

            return response.toString();
        }
        catch (Exception e)
        {
            System.out.println("Error getting request");
        }

        return null;
    }

    public static String sendRequestPost(String urlString, String post_data)
    {
        //TODO: check to see if HttpsURLConnection can be sent multiple times
        try{
            URL url = new URL(urlString);

            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(post_data.getBytes());
            outputStream.flush();
            outputStream.close();

            String line = "";
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine()) != null)
            {
                response.append(line);
            }

            bufferedReader.close();

            return response.toString();
        }
        catch (Exception e)
        {
            System.out.println("Error getting request");
        }

        return null;
    }



    //WORKS!!!!!!!!!!!!
    //Remove is place order works
    public static String buyStock()
    {
        String urlString = "https://api.tdameritrade.com/v1/accounts/" + Config.accountID + "/orders";

        try{
            URL url = new URL(urlString);

            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            //Add header
            String auth = String.format("Bearer %s", Config.getAuthToken());
            httpURLConnection.setRequestProperty("Authorization", auth);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);

            String jsonString = "{\n" +
                    "  \"orderType\": \"MARKET\",\n" +
                    "  \"session\": \"NORMAL\",\n" +
                    "  \"duration\": \"DAY\",\n" +
                    "  \"orderStrategyType\": \"SINGLE\",\n" +
                    "  \"orderLegCollection\": [\n" +
                    "    {\n" +
                    "      \"instruction\": \"Buy\",\n" +
                    "      \"quantity\": 1,\n" +
                    "      \"instrument\": {\n" +
                    "        \"symbol\": \"MPLX\",\n" +
                    "        \"assetType\": \"EQUITY\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            try(OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = jsonString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            String line = "";
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine()) != null)
            {
                response.append(line);
            }

            bufferedReader.close();

            String out = response.toString();

            System.out.println(out);
            return out;


        }
        catch (Exception e)
        {

        }
        return null;
    }


    public static String placeOrder(String post_data)
    {
        String urlString = "https://api.tdameritrade.com/v1/accounts/" + Config.accountID + "/orders";

        try{
            URL url = new URL(urlString);

            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            //Add header
            String auth = String.format("Bearer %s", Config.getAuthToken());
            httpURLConnection.setRequestProperty("Authorization", auth);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);

            try(OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = post_data.getBytes("utf-8");
                os.write(input, 0, input.length);
            }


            String line = "";
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine()) != null)
            {
                response.append(line);
            }

            bufferedReader.close();

            return response.toString();
        }
        catch (Exception e)
        {
            System.out.println("Error Client: placeOrder()");
        }

        return null;
    }

}























