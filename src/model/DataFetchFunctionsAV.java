package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This is the class that makes the API call to AlphaVantage to get the stock data for Online mode
 * and reads the data from the csv file in the offline mode. The data is the daily time series data
 * of the stocks.
 */
public class DataFetchFunctionsAV {
  /**
   * Gets the stock data from the Online API.
   *
   * @param name The company ticker.
   * @return Stock data.
   */
  public static StringBuilder getHistory(String name) {
    String apiKey = "PKM4NVFT2FXVTRSQ";
    String stockSymbol = name;
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }

    return output;
  }

  /**
   * Checks whether a particular ticker is valid before making the API call to fetch the stock data.
   * If valid, fetches the stock data for the inputted ticker.
   *
   * @param name The company ticker.
   * @return The stock data of the company.
   */
  public static StringBuilder checkName(String name) {
    String apiKey = "ESYN9H65CCY6PTU1";
    String stockSymbol = name; //ticker symbol for Google
    URL url = null;

    try {
      url = new URL("https://www.alphavantage.co/query?"
              + "function=SYMBOL_SEARCH"
              + "&keywords=" + stockSymbol
              + "&apikey=" + apiKey
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No search data found for " + stockSymbol);
    }
    return output;
  }

  /**
   * Gets the stock data from the file to facilitate the Offline purchase of Stocks.
   *
   * @param name The company ticker.
   * @return The stock data of the company.
   * @throws FileNotFoundException If the file containing the company's stock data is not present.
   * @throws IOException           If not able to take input from portfoliosystem.view or give
   *                               output to portfoliosystem.view.
   */
  public static StringBuilder fileFetch(String name) throws FileNotFoundException, IOException {

    FileReader f = new FileReader(new File(name));
    BufferedReader br = new BufferedReader(f);
    StringBuilder toRet = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      toRet.append(line);
      toRet.append(System.lineSeparator());
    }
    return toRet;
  }
}