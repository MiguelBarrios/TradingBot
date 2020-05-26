package com.MiguelBarrios;

import jdk.swing.interop.SwingInterOpUtils;

public class Order
{
     private Trade bought;

     private Trade sold;

     private Stats stats;

    public Order(Trade bought)
    {
        this.bought = bought;
        this.stats = new Stats(bought.getPrice(), bought.getOrderType());
        sold = null;
    }

    public String status()
    {
        //TODO: MAKE MORE READABLE
        double profit = stats.getCurrentPrice() - stats.getInitialPrice();
        return String.format("\n%s: %.3f %.3f",bought.getSymbol(), profit, stats.getPercentChange());
    }

    public OrderType update()
    {
        Quote quote = TDARequest.getQuote(bought.getSymbol());
        return stats.update(quote.getAskPrice());
    }

    public OrderType update(double price)
    {
        return stats.update(price);
    }

    public String smsFormat()
    {
        double profit = sold.getTotalPrice() - bought.getTotalPrice();
        return String.format("%s %.2f %.2f %.2f", bought.getSymbol(), bought.getTotalPrice(), sold.getTotalPrice(), profit);
    }

    public String[] cvsFormat()
    {
        if(bought.getSymbol() != sold.getSymbol())
        {
            System.out.println("ERROR in Orders modual:\nMISSMATCHED trades ");
            System.out.println(bought);
            System.out.println(sold + "\n\n");
        }

        double profit = sold.getTotalPrice() - bought.getTotalPrice();
        String[] arr = {
                bought.getSymbol(),
                String.valueOf(bought.getTotalPrice()),
                String.valueOf(sold.getTotalPrice()),
                String.valueOf(profit),
                String.valueOf(stats.getPercentChange()),
                String.valueOf(stats.getAbsoluteMax()),
                String.valueOf(stats.getAbsoluteMin())
        };
        return arr;
    }

    public void close(Trade trade)
    {
        this.sold = trade;
        Log.saveOrder(this);
    }

    public String getSymbol()
    {
        return bought.getSymbol();
    }

    public int positionSize()
    {
        return bought.getNumberOfShares();
    }

    public double boughtFor()
    {
        return bought.getTotalPrice();
    }

    public double soldFor()
    {
        return sold.getTotalPrice();
    }

    public double getPercentChange()
    {
        return stats.getPercentChange();
    }

}
