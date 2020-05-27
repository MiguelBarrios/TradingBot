package com.MiguelBarrios;

import java.util.*;

public class Account
{
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String RESET = "\033[0m";  // Text Reset



    //Initial funds
    private double startingFunds;

    //Funds available for trading
    private double availableFunds;

    //Max price willing to pay for individual stocks
    private double maxPrice;

    private double minPrice;

    //Number of shares to be purchased
    private int positionSize;

    //amount made from from closed postions
    private double totalProfit;

    //All currently active postions
    private HashMap<String, Order> activeOrders;

    //closed activeOrders
    private HashSet<String> closedOrders;


    public Account(double availableFunds,double minPrice, double maxPrice, int positionSize, boolean simulation)
    {
        this.startingFunds = availableFunds;
        this.availableFunds = availableFunds;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.positionSize = positionSize;
        this.totalProfit = 0;
        activeOrders = new HashMap<>();
        closedOrders = new HashSet<>();

        //initialize all logs for today
        Log log = new Log();

        TDARequest.simulation = simulation;
    }


    public boolean isEligible(Quote quote)
    {
        String symbol = quote.getSymbol();
        Double price = quote.getAskPrice();

        //check to see if price is less then the max you are willing to pay for it
        if(price > maxPrice || price < minPrice) {
            return false;
        }

        //Check to see if we already hold the stock, or have already held the stock
        if(activeOrders.containsKey(symbol) || closedOrders.contains(symbol)) {
            return false;
        }

        //Check to see if we have enough money to complete the initialPurchase, with and increase of 1% to cover market price
        double fundsNeeded = ((1.0 / price) + price) * positionSize;
        if(fundsNeeded > availableFunds) {
            return false;
        }

        return true;
    }

    public Trade initialPurchase(String symbol)
    {

        Trade trade = TDARequest.placeOrder(symbol, OrderType.BUY,positionSize);
        Order order = new Order(trade);

        //add order to currently held orders
        activeOrders.put(symbol, order);
        availableFunds -= order.boughtFor();

        return trade;

    }

    public Set<String> getKeySet()
    {
        return activeOrders.keySet();
    }

    public void closeAllPositions()
    {
        Set<String> set = getKeySet();

        for(String str : set)
        {
            closePosition(activeOrders.get(str));
            //Order cur = activeOrders.remove(str);
        }

    }

    public String getSummary()
    {
        double profit = ((totalProfit + availableFunds) - startingFunds);
        return String.format("Account Summary\nProfits: %+.2f", profit);
    }


    public Order closePosition(Order order)
    {
        Trade trade = TDARequest.placeOrder(order.getSymbol(), OrderType.SELL, order.positionSize());

        order.close(trade);

        activeOrders.remove(order.getSymbol());
        closedOrders.add(order.getSymbol());
        totalProfit += order.soldFor();

        if(order.soldFor() >= order.boughtFor())
        {
            System.out.print(GREEN + "\n" + trade);
        }
        else
        {
            System.out.print(RED + "\n" + trade);
        }

        System.out.println(RESET);

        return order;
    }

    public double availableFunds()
    {
        return availableFunds;
    }


    public Order getOrder(String symbol)
    {
        return activeOrders.get(symbol);
    }

    public ArrayList<Quote> getActivePositionsQuotes()
    {
        if(activeOrders.size() == 0)
            return new ArrayList<>();


        ArrayList<String> symbols = new ArrayList<>(activeOrders.size());
        for(String key : activeOrders.keySet())
        {
            symbols.add(key);
        }


        return TDARequest.getQuotes(symbols);
    }

    public int numberActivePosition()
    {
        return activeOrders.size();
    }

    public double getAvailableFunds()
    {
        return availableFunds;
    }

    public Set<String> keySet()
    {
        return activeOrders.keySet();
    }
}
