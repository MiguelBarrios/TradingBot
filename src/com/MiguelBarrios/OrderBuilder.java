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

    public static String limitOrder(String instruction, String symbol, int quantity, double price)
    {
        return String.format(limitOrderTemplate, LIMIT,price, instruction, quantity, symbol);
    }

    public static String marketOrder(String instruction, String symbol, int quantity)
    {
        return String.format(marketOrderTemplate, MARKET, instruction , quantity, symbol);
    }
}
