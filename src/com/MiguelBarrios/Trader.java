package com.MiguelBarrios;

import java.util.*;

public class Trader
{
    private HashMap<String, Order> activePositions = new HashMap<>();

    private HashSet<String> closedPositions = new HashSet<>();

    private HashSet<String> previouslyEncountered = new HashSet<>();

    public double initial_funds;

    public double remaining_funds;

    public double min_price;

    public double max_price;

    public int position_size;

    public boolean simulation;

    public Trader(double initial_funds, double min_price, double max_price, int position_size, boolean simulation)
    {
        this.initial_funds = initial_funds;
        this.remaining_funds = initial_funds;
        this.min_price = min_price;
        this.max_price = max_price;
        this.position_size = position_size;
        this.simulation = simulation;

        activePositions = new HashMap<>();
        closedPositions = new HashSet<>();
        previouslyEncountered = new HashSet<>();
    }

    public Order getOrder(String symbol)
    {
        return activePositions.get(symbol);
    }

    public ArrayList<Quote> getActivePositionsQuotes()
    {
        if(activePositions.size() == 0)
            return new ArrayList<>();

        ArrayList<String> symbols = new ArrayList<>(activePositions.size());
        symbols.addAll(activePositions.keySet());

        return TDARequest.getQuotes(symbols);
    }

    public void update_open_positions()
    {
        ArrayList<Quote> quotes =  getActivePositionsQuotes();
        for (Quote quote :  quotes) {
            Order order = getOrder(quote.symbol);
            OrderType recommendation = order.update(quote.bidprice);

            String color = (order.change()) ? Color.GREEN : Color.RED;
            System.out.print(color + order.status() + ",");

            if (recommendation == OrderType.SELL) {
                closePosition(order);
            }
        }
        System.out.println(Color.RESET);
    }

    public void closePosition(Order order)
    {
        Trade trade = TDARequest.placeOrder(order.getSymbol(), OrderType.SELL, order.positionSize());
        order.close(trade);
        activePositions.remove(order.getSymbol());
        closedPositions.add(order.getSymbol());
    }

    public void closeAllOpenPositions()
    {
        if(activePositions.size() == 0)
            return;

        for (Order cur : activePositions.values())
        {
            closePosition(cur);
        }

    }

    public void buyPosition(String symbol)
    {
        Trade trade = TDARequest.placeOrder(symbol, OrderType.BUY,position_size);
        Order order = new Order(trade);

        //add order to currently held orders
        activePositions.put(symbol, order);
        remaining_funds -= order.boughtFor();
    }

    public boolean isEligible(Quote quote)
    {
        String symbol = quote.symbol;
        double price = quote.askPrice;

        //check to see if price is less then the max you are willing to pay for it
        if(price > max_price || price < min_price) {
            return false;
        }

        //Check to see if we already hold the stock, or have already held the stock
        if(activePositions.containsKey(symbol) || closedPositions.contains(symbol)) {
            return false;
        }

        //Check to see if we have enough money to complete the initialPurchase, with and increase of 1% to cover market price
        double fundsNeeded = ((1.0 / price) + price) * position_size;
        return (fundsNeeded <= remaining_funds);
    }

    public int num_open_positions()
    {
        return activePositions.size();
    }

    public boolean hasEncountered(String symbol)
    {
        return !previouslyEncountered.add(symbol);
    }


}
