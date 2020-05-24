package com.MiguelBarrios;

import org.json.JSONObject;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Request
{

    //TODO: FIND A WAY TO AUTOMATE THIS
    // this will take use to a authentication page on td- ameritrade
    // Then once we authenticate it will redirect us to a error page
    // decode the infor that comes after code=
    // then we can put that into the code section on https://developer.tdameritrade.com/authentication/apis/post/token-0
    // this will give us the access_token
    public static String auth = "https://auth.tdameritrade.com/auth?response_type=code&redirect_uri=https://localhost&client_id=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP%40AMER.OAUTHAP";



    public static String[] getAuthCode()
    {
        String url =  "https://auth.tdameritrade.com/auth?response_type=code&redirect_uri=https%3A%2F%2Flocalhost&client_id=9TTXMCZ89BDCA4KP807HELK5RZMYBZAP%40AMER.OAUTHAP";

        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(url);
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Paste URL");

        String line = input.nextLine().trim();
        String encoded = line.split("code=")[1];

        String code = decode(encoded);

        System.out.println("Code:");
        System.out.println(code);

        System.out.println("\n\nEnter access_token");
        String access_token = input.nextLine().trim();

        System.out.println("\n\n Enter refresh_token");
        String refresh_token = input.nextLine().trim();

        String[] arr = {access_token, refresh_token};

        return arr;
    }

    private static String decode(String url)
    {
        try
        {
            String decode = URLDecoder.decode(url, "UTF-8");
            return decode;
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return null;
    }



}