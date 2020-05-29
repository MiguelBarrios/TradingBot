package com.MiguelBarrios;

public class Quote
{
    public final String symbol;

    public final  double bidprice;

    public final int bidSize;

    public final double askPrice;

    public final int askSize;

    public final double netChange;

    public final boolean shortable;

    public final int totalVolume;

    public final double volitility;

    public final double regularMarketNetChange;

    public final long time;

    public Quote(String symbol, double bidprice, int bidSize, double askPrice, int askSize, double netChange, int totalVolume, boolean shortable, double volitility, double regularMarketNetChange)
    {
        this.symbol = symbol;
        this.bidprice = bidprice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.netChange = netChange;
        this.totalVolume = totalVolume;
        this.shortable = shortable;
        this.volitility = volitility;
        this.regularMarketNetChange = regularMarketNetChange;
        this.time = System.currentTimeMillis();
    }

    public String[] csvFormat()
    {
        String[] arr = {symbol,
                String.valueOf(bidprice),
                String.valueOf(bidSize),
                String.valueOf(askPrice),
                String.valueOf(askSize),
                String.valueOf(netChange),
                String.valueOf(totalVolume),
                String.valueOf(shortable),
                String.valueOf(volitility),
                String.valueOf(regularMarketNetChange),
                String.valueOf(time)
        };

        return arr;
    }


    @Override
    public String toString()
    {
        return
                symbol +
                ", bidprice=" + bidprice +
                ", bidSize=" + bidSize +
                ", askPrice=" + askPrice +
                ", askSize=" + askSize +
                ", netChange=" + netChange +
                ", shortable=" + shortable;
    }
}
