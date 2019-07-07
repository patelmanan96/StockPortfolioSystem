package model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class implements the interface PortfolioCommissionStrategy and extends the class
 * PortfolioImpl. It contains the implementations of the methods which facilitate purchase of stocks
 * using the Dollar Cost Averaging Strategy, set commission for each trade and also change the
 * weights of the existing stocks. composition and the total value invested till a date.
 */
public class PCSImpl extends PortfolioImpl implements PortfolioCommissionStrategy {

  boolean percentSet = false;
  HashMap<String, Double> companyToPercent;

  /**
   * Constucts the object of the class PCSImpl initializing the name of the Portfolio..
   *
   * @param namePort The name of the Portfolio.
   */
  public PCSImpl(String namePort) {
    super(namePort);
    companyToPercent = new HashMap<>();
  }

  /**
   * This is a private helper method that calculates the quantity of the stock to be purchased from
   * the amount that is given to it.
   *
   * @param ticker     The company Ticker.
   * @param totalToInv The total amount to be invested.
   * @param a          The date at which the investment is to be done.
   * @return The quantity of the stock to be purchased.
   */
  private double quantityFromPrice(String ticker, double totalToInv, GregorianCalendar a) {
    double quantity = 0;
    int startPoint = 1;
    boolean inside = Boolean.FALSE;
    String[] outside = dataCache.get(ticker).toString().split("\n");
    for (int i = startPoint; i < outside.length; i++) {
      String[] arrForEval = outside[i].split(",");
      String[] date = arrForEval[0].split("-");
      if (Integer.parseInt(date[0]) == a.get(Calendar.YEAR)
              && Integer.parseInt(date[1]) == a.get(Calendar.MONTH) + 1
              && Integer.parseInt(date[2]) == a.get(Calendar.DAY_OF_MONTH)) {
        double price = Double.parseDouble(arrForEval[4]);
        quantity = totalToInv / price;
        inside = Boolean.TRUE;
        break;
      }
    }
    if (!(inside)) {
      throw new IllegalArgumentException("Incorrect Date Entered");
    }

    return quantity;
  }

  /**
   * This helper method which buys stocks into the portfolio from the provided amount, ticker and
   * the date.
   *
   * @param ticker The company ticker.
   * @param amount Amount to purchase the stock that day.
   * @param c      Date when to purchase.
   */
  private void buyOnline(String ticker, double amount, GregorianCalendar c) {
    Information set = null;
    String[] outside = super.companyInfoCache.get(ticker).toString().split("\n");
    for (int j = 1; j < outside.length; j++) {
      String temp = outside[j];
      String[] tempArr = temp.split(",");
      if (Double.parseDouble(tempArr[tempArr.length - 1]) == 1.0) {
        try {
          set = offlineOnlineResolver((GregorianCalendar) c.clone(), tempArr, ticker,
                  quantityFromPrice(ticker, amount, (GregorianCalendar) c.clone())
                  , 0, Mode.ONLINE);
        } catch (Exception a) {
          throw new IllegalArgumentException("Incorrect Entry");
        }
        break;
      } else {
        throw new IllegalArgumentException("Ambiguous Ticker Entry");
      }
    }
    if (set == null) {
      throw new IllegalArgumentException("Incorrect Entry");
    }
    super.stockAndInfo.add(set);
  }

  @Override
  public void divideInPortfolio(double amount, GregorianCalendar c) {
    if ((percentSet)) {
      for (String t : companyToPercent.keySet()) {
        double amountToSplit = amount * (companyToPercent.get(t) / 100);
        buyOnline(t, amountToSplit - commission, c);
      }
    } else {
      int toDivide = super.companyRecord.size();
      Iterator<String> travel = super.companyRecord.iterator();
      double equalAmount = amount / toDivide;
      int count = 0;
      while (travel.hasNext()) {
        String temp = travel.next();
        buyOnline(temp, equalAmount - commission, c);
        count++;
      }
    }
  }

  @Override
  public void setCommission(double value) {
    if (value < 0) {
      throw new IllegalArgumentException("Negative Commission");
    }
    super.commission = value;
  }

  @Override
  public void setPercentages(HashMap<String, Double> tickToPercent) {
    System.out.println(companyRecord);
    if (companyRecord.size() != tickToPercent.size()) {
      throw new IllegalArgumentException("Specify Companies Correctly");
    }
    for (String k : companyRecord) {
      if (tickToPercent.containsKey(k) == Boolean.FALSE) {
        throw new IllegalArgumentException("Specify Companies Correctly");
      }
    }
    double perCheck = 0;
    for (String pF : tickToPercent.keySet()) {
      perCheck += tickToPercent.get(pF);
    }
    if (perCheck != 100) {
      throw new IllegalArgumentException("Total should be 100");
    }
    companyToPercent = new HashMap<>(tickToPercent);
    percentSet = Boolean.TRUE;
  }

  @Override
  public void buyLoopDate(double amount, GregorianCalendar start, int days) {
    GregorianCalendar startingDate = start;
    int interval = days;
    resolver(amount, start, (GregorianCalendar) Calendar.getInstance(), days);
  }

  @Override
  public void buyLoopDate(double amount, GregorianCalendar start, GregorianCalendar end, int days) {
    resolver(amount, start, end, days);
  }

  /**
   * This helper method resolves dates when the market is not open and increments accordingly.
   * @param amount The amount to invest.
   * @param start The start date.
   * @param end The end date.
   * @param days The recurring date.
   */
  private void resolver(double amount, GregorianCalendar start, GregorianCalendar end, int days) {
    while (start.compareTo(end) < 1) {
      while (true) {
        try {
          divideInPortfolio(amount, start);
          break;
        } catch (Exception a) {
          start.add(Calendar.DATE, 1);
        }
      }
      start.add(Calendar.DATE, days);
    }
  }

}
