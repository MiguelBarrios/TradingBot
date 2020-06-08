package com.MiguelBarrios;

public enum Strategy
{
    TOPMOVERS("TOPMOVERS"),
    STOCKFOLLOWER("STOCKFOLLOWER");

    private String exchange;

    private Strategy(String type) {
        this.exchange = type;
    }

    @Override
    public String toString()
    {
        return exchange;
    }
}