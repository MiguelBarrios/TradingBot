package com.MiguelBarrios;
import java.util.ArrayList;

public class AccountSummary
{
    public ArrayList<Position> activePositions;

    public double initialBalance;

    public double availableFunds;

    public double buyingPower;

    public double accountValue;

    public AccountSummary(double initialBalance, double availableFunds, double buyingPower, double accountValue, ArrayList<Position> activePositions)
    {
        this.initialBalance = initialBalance;
        this.availableFunds = availableFunds;
        this.buyingPower = buyingPower;
        this.accountValue = accountValue;
        this.activePositions = activePositions;
    }

}