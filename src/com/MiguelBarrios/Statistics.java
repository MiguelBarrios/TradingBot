package com.MiguelBarrios;

public class Statistics
{
    private double maxChange;

    private double minChange;

    private boolean shortPosition;

    public Statistics(boolean isShortPosition)
    {
        maxChange = 0;
        minChange = 0;
        shortPosition = isShortPosition;
    }

    public OrderType update(double change)
    {
        //TODO: implement
        OrderType orderType = OrderType.HOLD;
        if(shortPosition)
        {
           // double changeFromMin = (minChange + change);
        }
        else
        {
            /*
            double changeFromMax =  (maxChange - change);
            if(changeFromMax < -1)
            {
                orderType = OrderType.SELL;
            }

             */
        }


        if(change > maxChange)
            minChange = change;

        if(change < minChange)
            minChange = change;


        return orderType;

    }
}
