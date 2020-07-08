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
}