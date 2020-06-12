package com.MiguelBarrios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Util
{
    public static void pause(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readInData(String pathToCvs) throws IOException
    {
        ArrayList<String> symbols = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCvs));

        String row = "";
        while ((row = csvReader.readLine()) != null) {
            symbols.add(row);
        }

        return symbols;
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
