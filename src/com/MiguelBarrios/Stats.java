package com.MiguelBarrios;

public class Stats
{
    //Will be used to calculate average asks.
    private double[] askList;

    //Summation of askList
    private double sumAsk;

    // sumAsk / size
    private double averageAsk;

    //  slope = prevAverage - averageAsk;
    //  slope < 0 : increasing
    //  slope > 0 : decreasing
    private double slope;

    //current index in askList and bidList
    private int index;

    private int size;

    public Stats(int size)
    {
        askList = new double[size];
        sumAsk = 0;
        averageAsk = 0;
        index = 0;
        slope = '0';
        this.size = size;
    }


    public void update(double askPrice, double bidPrice)
    {
        //circular array
        if(index >= size)
            index = 0;

        sumAsk -= askList[index];
        sumAsk += askPrice;
        askList[index] = askPrice;

        double prevAverage = averageAsk;

        averageAsk = ((int)(sumAsk / size * 100)) / 100.0;

        slope = prevAverage - averageAsk;

        ++index;
    }

    public double getSumAsk()
    {
        return sumAsk;
    }


    public double getAverageAsk()
    {
        return averageAsk;
    }
    
    public double getSlope()
    {
        return slope;
    }


}
