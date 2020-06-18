package com.MiguelBarrios;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Trader
{
    private final Strategy strategy;

    public final double initial_funds;

    public final double min_price;

    public final double max_price;

    public final int position_size;

    public final boolean simulation;

    private ConcurrentHashMap<String, Order> activePositions;

    private HashSet<String> closedPositions = new HashSet<>();

    private HashSet<String> previouslyEncountered = new HashSet<>();

    public double remaining_funds;

    private Log log;

    public void saveQuotes(ArrayList<Quote> quotes)
    {
        log.saveQuotes(quotes);
    }

    public Trader(double initial_funds, double min_price, double max_price, int position_size, boolean simulation, String directory, Strategy strategy)
    {
        this.initial_funds = initial_funds;
        this.remaining_funds = initial_funds;
        this.min_price = min_price;
        this.max_price = max_price;
        this.position_size = position_size;
        this.simulation = simulation;
        this.strategy = strategy;

        activePositions = new ConcurrentHashMap<>();
        closedPositions = new HashSet<>();
        previouslyEncountered = new HashSet<>();

        this.log = new Log(directory);
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

    public void closePosition(Order order)
    {
        Trade trade = TDARequest.placeOrder(order.getSymbol(), OrderType.SELL, order.positionSize());
        log.saveTrade(trade);
        order.close(trade);
        activePositions.remove(order.getSymbol());
        closedPositions.add(order.getSymbol());
    }

    //TODO: if closing all at one add pause cause it is resulting in a too many request resonse code: 429
    public void closeAllOpenPositions()
    {
        if(activePositions.size() == 0)
            return;

        for (Order cur : activePositions.values()) {
            closePosition(cur);
            Util.pause(1);
        }
    }

    public int buyAdditionalShares(Order order)
    {
        String symbol = order.getSymbol();
        Trade trade = TDARequest.placeOrder(symbol, OrderType.BUY,position_size);
        if(trade == null)
            return -1;

        log.saveTrade(trade);

        order.additionalShareBought(trade);

        return 1;
    }

    /**
     * @param quote current quote
     * @return -2: does not meet requirements
     *         -1: Error occurred placing order
     *          1: Order place successfully
     */
    public int buyPosition(Quote quote)
    {
        String symbol = quote.getSymbol();
        if(!isEligible(quote)){
            return -2;
        }

        Trade trade = TDARequest.placeOrder(symbol, OrderType.BUY,position_size);
        if(trade == null) {
            return -1;
        }

        log.saveTrade(trade);

        Order order = null;
        if(strategy == Strategy.TOPMOVERS){
            order = new Order(trade, new TopMoversStats(trade));
        }
        else if(strategy == Strategy.TOPGAINERS){
            order = new Order(trade, new TopGainersStats(trade));
        }

        //add order to currently held orders
        activePositions.put(symbol, order);
        remaining_funds -= order.boughtFor();
        return 1;
    }

    public boolean isEligible(Quote quote)
    {
        String symbol = quote.getSymbol();
        double price = quote.getAskPrice();

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

    public boolean hasOpenPosition(String symbol)
    {
        return activePositions.contains(symbol);
    }

    public void updatePosition(Quote quote){
        Order order = getOrder(quote.getSymbol());
        OrderType orderType = order.update(quote.getAskPrice());

        if(orderType == OrderType.SELL)
        {
            closePosition(order);
        }
        else if(orderType == OrderType.BUY)
        {
            buyAdditionalShares(order);
        }

    }


    public int num_open_positions()
    {
        return activePositions.size();
    }

    public boolean hasEncountered(String symbol)
    {
        return !previouslyEncountered.add(symbol);
    }

    public void updateAllActivePositions()
    {

    }


}
