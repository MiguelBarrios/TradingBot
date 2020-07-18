package com.MiguelBarrios;

public class OrderBuilder
{
    public static String MARKET = "MARKET";
    public static String LIMIT = "LIMIT";

    public static String limitOrderTemplate = "{\n" +
            "  \"orderType\": \"%s\",\n" +
            "  \"session\": \"NORMAL\",\n" +
            "  \"price\": \"%.2f\",\n" +
            "  \"duration\": \"DAY\",\n" +
            "  \"orderStrategyType\": \"SINGLE\",\n" +
            "  \"orderLegCollection\": [\n" +
            "    {\n" +
            "      \"instruction\": \"%s\",\n" +
            "      \"quantity\": %d,\n" +
            "      \"instrument\": {\n" +
            "        \"symbol\": \"%s\",\n" +
            "        \"assetType\": \"EQUITY\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static String marketOrderTemplate = "{\n" +
            "  \"orderType\": \"%s\",\n" +
            "  \"session\": \"NORMAL\",\n" +
            "  \"duration\": \"DAY\",\n" +
            "  \"orderStrategyType\": \"SINGLE\",\n" +
            "  \"orderLegCollection\": [\n" +
            "    {\n" +
            "      \"instruction\": \"%s\",\n" +
            "      \"quantity\": %d,\n" +
            "      \"instrument\": {\n" +
            "        \"symbol\": \"%s\",\n" +
            "        \"assetType\": \"EQUITY\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static String trailingOrderTemplate = "{\n" +
            "  \"orderType\": \"TRAILING_STOP\",\n" +
            "  \"session\": \"NORMAL\",\n" +
            "  \"stopPriceLinkBasis\": \"%s\",\n" +
            "  \"stopPriceLinkType\": \"VALUE\",\n" +
            "  \"stopPriceOffset\": %.2f,\n" +
            "  \"duration\": \"DAY\",\n" +
            "  \"orderStrategyType\": \"SINGLE\",\n" +
            "  \"orderLegCollection\": [\n" +
            "    {\n" +
            "      \"instruction\": \"%s\",\n" +
            "      \"quantity\": %d,\n" +
            "      \"instrument\": {\n" +
            "        \"symbol\": \"%s\",\n" +
            "        \"assetType\": \"EQUITY\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static String buySellTrailingTemplate = "{\n" +
            "  \"orderType\": \"TRAILING_STOP\",\n" +
            "  \"session\": \"NORMAL\",\n" +
            "  \"stopPriceLinkBasis\": \"ASK\",\n" +
            "  \"stopPriceLinkType\": \"VALUE\",\n" +
            "  \"stopPriceOffset\": %.2f,\n" +
            "  \"duration\": \"DAY\",\n" +
            "  \"orderStrategyType\": \"TRIGGER\",\n" +
            "  \"orderLegCollection\": [\n" +
            "    {\n" +
            "      \"instruction\": \"BUY\",\n" +
            "      \"quantity\": %d,\n" +
            "      \"instrument\": {\n" +
            "        \"symbol\": \"%s\",\n" +
            "        \"assetType\": \"EQUITY\"\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"childOrderStrategies\": [\n" +
            "    {\n" +
            "      \"orderType\": \"TRAILING_STOP\",\n" +
            "      \"session\": \"NORMAL\",\n" +
            "      \"stopPriceLinkBasis\": \"BID\",\n" +
            "      \"stopPriceLinkType\": \"VALUE\",\n" +
            "      \"stopPriceOffset\": %.2f,\n" +
            "      \"duration\": \"DAY\",\n" +
            "      \"orderStrategyType\": \"SINGLE\",\n" +
            "      \"orderLegCollection\": [\n" +
            "        {\n" +
            "          \"instruction\": \"SELL\",\n" +
            "          \"quantity\": %d,\n" +
            "          \"instrument\": {\n" +
            "            \"symbol\": \"%s\",\n" +
            "            \"assetType\": \"EQUITY\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    public static String trailingBuySellOrder(String symbol, int quantity, double buyTrailingPrice, double sellTrailingPrice)
    {
        return String.format(buySellTrailingTemplate, buyTrailingPrice, quantity, symbol, sellTrailingPrice, quantity, symbol);
    }


    public static String trailingOrder(String instruction, String symbol, int quantity, double stopPriceOffset)
    {
        String stopPriceLinkBasis = (instruction.equals("Buy")) ? "ASK" : "BID";

        return String.format(trailingOrderTemplate, stopPriceLinkBasis, stopPriceOffset, instruction, quantity, symbol);
    }

    public static String limitOrder(String instruction, String symbol, int quantity, double price)
    {
        return String.format(limitOrderTemplate, LIMIT,price, instruction, quantity, symbol);
    }

    public static String marketOrder(String instruction, String symbol, int quantity)
    {
        return String.format(marketOrderTemplate, MARKET, instruction , quantity, symbol);
    }
}
