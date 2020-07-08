package com.MiguelBarrios;

import java.util.HashMap;

public class AccountSummary
{
    public HashMap<String,Trade> activePositions;

    public double initialBalance;

    public double availableFunds;

    public double buyingPower;

    public double accountValue;

    public AccountSummary(double initialBalance, double availableFunds, double buyingPower, double accountValue, HashMap<String, Trade> activePositions)
    {
        this.initialBalance = initialBalance;
        this.availableFunds = availableFunds;
        this.buyingPower = buyingPower;
        this.accountValue = accountValue;
        this.activePositions = activePositions;
    }

    @Override
    public String toString()
    {
        return String.format("AvailableFunds = %f BuyingPower = %f AccountValue = %f initialBalance = %f NumPositions = %d\n%s", availableFunds, buyingPower, accountValue, initialBalance, activePositions.size(), positionsString());
    }

    public String positionsString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Positions{");
        for(String key : activePositions.keySet())
        {
            Trade trade = activePositions.get(key);
            sb.append(trade.toString()).append(", ");
        }
        sb.append("}");

        return sb.toString();
    }
}