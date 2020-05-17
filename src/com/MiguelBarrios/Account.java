package com.MiguelBarrios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Account
{
    //Funds available for trading
    private double availableFunds;

    //amount made from from closed postions
    private double profit;

    //All currently active postions
    private HashMap<String, Order> positions;

    //closed positions
    private LinkedList<Order> closedPositions;

    public Account(double availableFunds)
    {
        this.availableFunds = availableFunds;
        this.profit = 0;
        positions = new HashMap<>();
        closedPositions = new LinkedList<>();
    }

    public void addNewPosition(Order order)
    {
        positions.put(order.getSymbol(), order);
        availableFunds -= order.boughtFor();
    }

    public void closePosition(Order order)
    {
        Trade trade = Request.placeOrder(order.getSymbol(), OrderType.SELL, order.positionSize());
        order.close(trade);

        positions.remove(order.getSymbol());
        closedPositions.add(order);
        profit += order.soldFor();

    }

    public boolean hasSufficientFunds(Quote quote)
    {
        return true;
    }

    public double availableFunds()
    {
        return availableFunds;
    }

    public boolean hasStake(String symbol)
    {
        return positions.containsKey(symbol);
    }

    public boolean hasOpenPositions()
    {
        return (positions.size() >= 1);
    }

    public Order getOrder(String symbol)
    {
        return null;
    }

    public Set<String> keySet()
    {
        return positions.keySet();
    }
}
