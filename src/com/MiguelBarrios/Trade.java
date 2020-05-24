package com.MiguelBarrios;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Trade
{
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private String symbol;

    private OrderType orderType;

    private int numberOfShares;

    private double price;

    private double totalPrice;

    private Date time;

    public String[] cvsFormat()
    {
        String[] arr = {
                orderType.toString(),
                symbol,
                String.valueOf(numberOfShares),
                String.valueOf(price),
                String.valueOf(totalPrice),
                time.toString()
                        };

        return arr;
    }


    public Trade(OrderType type, int numberOfShares, double price, String symbol)
    {
        this.orderType = type;
        this.numberOfShares = numberOfShares;
        this.symbol = symbol;
        this.price = price;
        totalPrice = price * numberOfShares;
        time = new Date();
    }

    public String getSymbol()
    {
        return symbol;
    }

    public OrderType getOrderType()
    {
        return orderType;
    }

    public int getNumberOfShares()
    {
        return numberOfShares;
    }

    public double getPrice()
    {
        return price;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public String getTime()
    {
        return timeFormat.format(time);
    }

    @Override
    public String toString()
    {
        return  orderType +
                " " +numberOfShares +
                " " + symbol  +
                " price = " + price +
                ", totalPrice = " + totalPrice +
                ", time = " + time;
    }

}
