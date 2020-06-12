package com.MiguelBarrios;
import javax.net.ssl.HttpsURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class Client
{
    private static final HttpClient client = HttpClient.newHttpClient();

    public static String sendRequest(String requestType)
    {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestType))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }
        catch (Exception e)
        {
            System.out.println("Error: Sending request");
            return "";
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

            return readResponse(httpURLConnection);
        }
        catch (Exception e) {
            System.out.println("Error sendingRequestGet");
            return "";
        }
    }

    public static String sendRequestPost(String urlString, String post_data)
    {
        try{
            URL url = new URL(urlString);

            HttpsURLConnection httpURLConnection = (HttpsURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(post_data.getBytes());
            outputStream.flush();
            outputStream.close();

            return readResponse(httpURLConnection);
        }
        catch (Exception e)
        {
            System.out.println("Error getting request");
            return "";
        }
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

            return readResponse(httpURLConnection);
        }
        catch (Exception e)
        {
            System.out.println("Error Client: placeOrder()");
            return "";
        }
    }

    private static String readResponse(HttpsURLConnection httpURLConnection)
    {
        try
        {
            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder response = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null)
                response.append(line);

            bufferedReader.close();
            return response.toString();
        }
        catch (Exception e)
        {
            return "";
        }
    }
}