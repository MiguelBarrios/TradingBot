package com.MiguelBarrios;

import java.util.HashMap;

public class Account
{
    private HashMap<String,Trade> activePositions;

    private double initialBalance;

    private double availableFunds;

    private double buyingPower;

    private double accountValue;

    public Account()
    {
        AccountSummary summary = TDARequest.getAccountInfo();
        this.initialBalance = summary.initialBalance;
        this.availableFunds = summary.availableFunds;
        this.buyingPower = summary.buyingPower;
        this.accountValue = summary.accountValue;
        activePositions = summary.activePositions;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Account{, initialBalance=").append(initialBalance);
        sb.append(", availableFunds=").append(availableFunds);
        sb.append(", buyingPower=").append(buyingPower);
        sb.append(", accountValue=").append(accountValue).append("}\n");

        for(String key : activePositions.keySet())
            sb.append(key).append(", ");

        return sb.toString();
    }
}
