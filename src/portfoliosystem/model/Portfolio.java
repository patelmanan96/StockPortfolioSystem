package portfoliosystem.model;

import java.util.Calendar;
import java.util.List;

/**
 * This is the interface that represents a Portfolio. A Portfolio consists of multiple stocks.It
 * provides various methods to buy stocks, portfoliosystem.view its Cost basis, its composition and
 * its total value.
 */
public interface Portfolio {
  /**
   * Buy a stock using online API data, explicitly giving the price. The price given should be
   * between the lowest and the highest price of the stock for the day entered. The stock can be
   * bought only on days for which the Stock Market is open.
   *
   * @param a             The date for which the stock is to be bought as a Calender object.
   * @param companyTicker The ticker of the Company who's stock is to be bought.
   * @param quantity      The number of stocks to be bought.
   * @param price         The price at which the stock is to be bought. The price should be between
   *                      the lowest and the highest price of the stock for the day entered
   * @throws IllegalArgumentException If any Illegal Argument is passed.
   */
  void buyOnlineWithPrice(Calendar a, String companyTicker, double quantity, double price)
          throws IllegalArgumentException;

  /**
   * Buy a stock using online API data without explicitly giving the price. The stock can be bought
   * only on days for which the Stock Market is open. The buying price would be considered to be the
   * closing price of that stock on that day.
   *
   * @param a             The date for which the stock is to be bought as a Calender object.
   * @param companyTicker The ticker of the Company who's stock is to be bought.
   * @param quantity      The number of stocks to be bought.
   * @throws IllegalArgumentException If any Illegal Argument is passed.
   */
  void buyOnline(Calendar a, String companyTicker, double quantity) throws IllegalArgumentException;

  /**
   * Buy a stock using offline file data which is previously fed into the application, without
   * explicitly giving the price. The stock can be bought only on days for which the Stock Market is
   * open. The buying price would be considered to be the closing price of that stock on that day.
   *
   * @param a             The date for which the stock is to be bought as a Calender object.
   * @param companyTicker The ticker of the Company who's stock is to be bought.
   * @param quantity      The number of stocks to be bought.
   * @throws IllegalArgumentException If any Illegal Argument is passed.
   */
  void buyOffline(Calendar a, String companyTicker, double quantity)
          throws IllegalArgumentException;

  /**
   * Buy a stock using offline file data which is previously fed into the application with
   * explicitly giving the price. The price given should be between the lowest and the highest price
   * of the stock for the day entered. The stock can be bought only on days for which the Stock
   * Market is open.
   *
   * @param a             The date for which the stock is to be bought as a Calender object.
   * @param companyTicker The ticker of the Company who's stock is to be bought.
   * @param quantity      The number of stocks to be bought.
   * @param price         The price at which the stock is to be bought. The price should be between
   *                      the lowest and the highest price of the stock for the day entered
   * @throws IllegalArgumentException If any Illegal Argument is passed.
   */
  void buyOfflineWithPrice(Calendar a, String companyTicker, double quantity, double price)
          throws IllegalArgumentException;

  /**
   * Sets the directory where offline files containing the stock data are stored.
   *
   * @param directoryPath The path where the files containing the stock data are stored.
   */
  void setOfflineDirectory(String directoryPath);

  /**
   * Gets the total amount that is invested until the date provided.
   *
   * @param d The date until which the total invested amount is to be displayed.
   * @return The total value of the investments made until the date passed.
   * @throws IllegalArgumentException If an invalid date is passed.
   */
  double getTotalValue(Calendar d) throws IllegalArgumentException;

  /**
   * Gets the Cost basis of a Portfolio.
   *
   * @param d The date until which the cost basis is to be displayed.
   * @return The cost basis of teh Portfolio.
   * @throws IllegalArgumentException If an invalid date is passed.
   */
  double getCostBasis(Calendar d) throws IllegalArgumentException;

  /**
   * Returns the contents ie. each purchase of the stock of a Portfolio as a List in a sorted
   * order where old bought stocks appear before the newly bought stocks.
   *
   * @param d The date until which the composition is to be displayed.
   * @return List of all stocks in the Portfolio.
   */
  List getComposition(Calendar d);
}