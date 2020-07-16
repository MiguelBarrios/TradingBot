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

    private static File quotesFile;
    private static  File tradesFile;
    private static File moversFile;
    private static File resultsFile;

    public Log(String directory)
    {
        //Initilize files and directories
        quotesFile = mkdir(directory + "/Quotes");
        String[] quotesHeader = {"SYMBOL", "BID_PRICE", "BID_SIZE", "ASK_PRICE", "ASK_SIZE", "NET_CHANGE", "VOLUME", "SHORTABLE", "VOLITILITY", "CHANGE", "TIME"};
        addHeader(quotesFile, quotesHeader);
        
        moversFile =  mkdir(directory + "/Movers");
        String[] moversHeader = {"Symbol", "lastPrice", "Direction", "Change", "Volume", "Time"};
        addHeader(moversFile, moversHeader);

        resultsFile =  mkdir(directory + "/Results");
        String[] resultsHeader = {"Symbol", "ShortPosition" ,"BuyPrice", "SellPrice", "profit"};
        addHeader(resultsFile, resultsHeader);
    }

    private File mkdir(String directory)
    {
        Date cur = new Date();

        //Make directory if DNE
        String directoryPath = mainDirectory + directory;
        (new File(directoryPath)).mkdirs();

        return new File(directoryPath + "/" + yearMonthDay.format(cur) + ".csv");
    }

    public static void addHeader(File file, String[] header)
    {
        try{
            // Ad header to Trades File
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);

            writer.writeNext(header);
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: File not created");
        }

    }

    public static void saveTrade(Trade trade)
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

    public static void saveQuotes(ArrayList<Quote> quotes)
    {
        try {
            FileWriter outputFile = new FileWriter(quotesFile, true);
            CSVWriter writer = new CSVWriter(outputFile);

            for(Quote quote : quotes) {
                writer.writeNext(quote.csvFormat());
            }

            writer.close();
        }
        catch (Exception e) {
            //DO NOTHING
        }

    }

    public static void saveMover(Mover mover)
    {
        try {
            FileWriter outputFile = new FileWriter(moversFile, true);
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeNext(mover.csvFormat());

            writer.close();
        }
        catch (Exception e) {
            //DO NOTHING
        }
    }

    public static void saveResults(ArrayList<Results> results)
    {
        try {
            FileWriter outputFile = new FileWriter(resultsFile, true);
            CSVWriter writer = new CSVWriter(outputFile);

            for(Results cur : results)
            {
                writer.writeNext(cur.csvFormat());
            }

            writer.close();
        }
        catch (Exception e) {
            //DO NOTHING
        }
    }

}
