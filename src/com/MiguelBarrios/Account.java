package com.MiguelBarrios;
import java.util.ArrayList;

public class Account
{
    private ArrayList<Position> activePositions;

    private double initialBalance;

    private double availableFunds;

    private double buyingPower;

    private double accountValue;

    public Account()
    {
        updateAccountInfo();
        new Log("TopMovers");
    }

    public void updateAccountInfo()
    {
        AccountSummary summary = TDARequest.getAccountInfo();
        this.initialBalance = summary.initialBalance;
        this.availableFunds = summary.availableFunds;
        this.buyingPower = summary.buyingPower;
        this.accountValue = summary.accountValue;
        activePositions = summary.activePositions;
    }

    public ArrayList<Position> getActivePositions()
    {
        return activePositions;
    }

    public double getInitialBalance()
    {
        return initialBalance;
    }

    public double getAvailableFunds()
    {
        return availableFunds;
    }

    public double getBuyingPower()
    {
        return buyingPower;
    }

    public double getAccountValue()
    {
        return accountValue;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Account{, initialBalance=").append(initialBalance);
        sb.append(", availableFunds=").append(availableFunds);
        sb.append(", buyingPower=").append(buyingPower);
        sb.append(", accountValue=").append(accountValue).append("}\n");

        for(Position pos : activePositions)
        {
            sb.append(pos.toString()).append("\n");
        }

        return sb.toString();
    }
}
