package com.MiguelBarrios;

public class Order
{
     private Trade bought;

     private Trade sold;

     private Statistics stats;

    public Order(Trade bought, Statistics stats)
    {
        this.bought = bought;
        this.stats = stats;
        sold = null;
    }

    public String status()
    {
        return String.format("%4s: %+.4f",bought.getSymbol(), stats.getPercentChange());
    }

    public boolean change()
    {
        return stats.getPercentChange() >= 0;
    }

    public OrderType update(double price)
    {
        return stats.update(price);
    }

    public String[] cvsFormat()
    {
        if(!bought.getSymbol().equalsIgnoreCase(sold.getSymbol()))
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

    public Order close(Trade trade)
    {
        this.sold = trade;
        return this;
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

}
