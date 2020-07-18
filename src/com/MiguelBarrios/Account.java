package com.MiguelBarrios;
import java.util.ArrayList;

public class Account
{
    //List of all currently held positions
    private ArrayList<Position> activePositions;

    private double initialBalance;

    private double availableFunds;

    private double buyingPower;

    private double accountValue;

    public Account()
    {
        updateAccountInfo();
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

    public boolean hasAvailableFunds(Double buyPrice)
    {
        return (availableFunds > buyPrice);
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
            sb.append(pos.toString()).append("\n");

        return sb.toString();
    }
}
