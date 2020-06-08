package com.MiguelBarrios;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Log
{
    public static final SimpleDateFormat yearMonth = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat yearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final String mainDirectory = "/Users/miguelbarrios/Documents/Projects/Logs/";

    private File quotesFile;
    private File tradesFile;
    private File ordersFile;

    public Log(String directory)
    {
        //Initilize files and directories
        tradesFile =  mkdir(directory + "/Trades");
        String[] tradesHeader = {"Trade Type", "Symbol", "amount", "Idv Price", "Total  Price", "TOE"};
        addHeader(tradesFile, tradesHeader);


        ordersFile = mkdir(directory + "/Orders");
        String[] orderHeader = {"Symbol", "Bought Price", "Sold Price","Profit", "Change", "Max", "Min"};
        addHeader(ordersFile, orderHeader);

        quotesFile = mkdir(directory + "/Quotes");
        String[] quotesHeader = {"SYMBOL", "BID_PRICE", "BID_SIZE", "ASK_PRICE", "ASK_SIZE", "NET_CHANGE", "VOLUME", "SHORTABLE", "VOLITILITY", "CHANGE", "TIME"};
        addHeader(quotesFile, quotesHeader);

    }

    private File mkdir(String directory)
    {
        Date cur = new Date();

        //Make directory if DNE
        String directoryPath = mainDirectory + directory + "/" + yearMonth.format(cur);
        (new File(directoryPath)).mkdirs();

        return new File(directoryPath + "/" + yearMonthDay.format(cur) + ".csv");

    }

    public static void addHeader(File file, String[] header)
    {

        try{
            // Ad header to Trades File
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeNext(header);

            writer.close();

        }
        catch (Exception e)
        {
            System.out.println("Error: File not created");
        }

    }

    public void saveOrder(Order order)
    {
        try{
            FileWriter outputfile = new FileWriter(ordersFile, true);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(order.cvsFormat());

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveTrade(Trade trade)
    {
        try{
            FileWriter outputfile = new FileWriter(tradesFile, true);
            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeNext(trade.cvsFormat());

            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveQuotes(ArrayList<Quote> quotes)
    {
        try
        {
            FileWriter outputFile = new FileWriter(quotesFile, true);
            CSVWriter writer = new CSVWriter(outputFile);

            for(Quote quote : quotes)
            {
                writer.writeNext(quote.csvFormat());
            }

            writer.close();
        }
        catch (Exception e)
        {
            //DO NOTHING
        }

    }
}
