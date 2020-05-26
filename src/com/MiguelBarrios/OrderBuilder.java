package com.MiguelBarrios;

public class OrderBuilder
{
    public static String marketOrderTemplate = "{\n" +
            "  \"orderType\": \"MARKET\",\n" +
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

    public static String marketOrder(String instruction, String symbol, int quantity)
    {
        return String.format(marketOrderTemplate,instruction , quantity, symbol);
    }
}
