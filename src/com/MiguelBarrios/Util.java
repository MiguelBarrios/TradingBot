package com.MiguelBarrios;

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
}
