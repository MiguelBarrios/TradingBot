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

    public static String buyStock()
    {
        String urlString = "https://api.tdameritrade.com/v1/accounts/" + Config.accountID + "/orders";

        try{
            URL url = new URL(urlString);

            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);


            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "  \"orderType\": \"MARKET\",\n" +
                        "  \"session\": \"NORMAL\",\n" +
                        "  \"duration\": \"DAY\",\n" +
                        "  \"orderStrategyType\": \"SINGLE\",\n" +
                        "  \"orderLegCollection\": [\n" +
                        "    {\n" +
                        "      \"instruction\": \"Buy\",\n" +
                        "      \"quantity\": 1,\n" +
                        "      \"instrument\": {\n" +
                        "        \"symbol\": \"ET\",\n" +
                        "        \"assetType\": \"EQUITY\"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}");

            }catch (JSONException err){
                err.printStackTrace();
            }
            String post_data = jsonObject.toString();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(post_data.getBytes());
            outputStream.flush();
            outputStream.close();

            //Add header
            String auth = String.format("Bearer %s", Config.getAuthToken());
            httpURLConnection.setRequestProperty("Authorization", auth);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

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

        }




        return null;
    }

}























