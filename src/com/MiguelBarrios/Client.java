package com.MiguelBarrios;

import javax.swing.text.Utilities;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client
{
    private HttpClient client;

    public Client()
    {
        client = HttpClient.newHttpClient();
    }

    public String sendRequest(String requestType)
    {
        try{
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(requestType)).build();
            String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return response;
        }
        catch (Exception e)
        {
            System.out.println("Error: Request failed");
            return null;
        }

    }
}
