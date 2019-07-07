package model;

import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * This is the Interface that represents the Dollar Cost Averaging Investment Strategy. It provides
 * methods to set the commission, weights of stocks and purchase stocks using the Dollar cost
 * Averaging Strategy.
 */
public interface PortfolioCommissionStrategy extends Portfolio {
  /**
   * This method takes the amount and the date on which the stocks are to be purchased and divides
   * the amount equally in the stocks existing in the Portfolio.
   *
   * @param amount The amount to be invested.
   * @param c      The date on which the amount is to be invested.
   */
  void divideInPortfolio(double amount, GregorianCalendar c);

  /**
   * Set the value commission for executing a trade.
   *
   * @param value The commission value as an Integer.
   */
  void setCommission(double value);

  /**
   * Change the percentages/weights of the stocks existing in the Portfolio.
   *
   * @param tickToPercent A map of the Company and its corresponding weight/percentage.
   */
  void setPercentages(HashMap<String, Double> tickToPercent);

  /**
   * Buy the stocks using the Dollar Cost Averaging strategy. If no end date is given, then the
   * current date will be considered the end date.
   *
   * @param amount The amount to be invested.
   * @param start  The date from which the investment should begin.
   * @param days   The number days after which the stocks should be purchased again.
   */
  void buyLoopDate(double amount, GregorianCalendar start, int days);

  /**
   * Buy the stocks using the Dollar Cost Averaging strategy.
   *
   * @param amount The amount to be invested.
   * @param start  The date from which the investment should begin.
   * @param end    The date at which the investment should end.
   * @param days   The number days after which the stocks should be purchased again.
   */
  void buyLoopDate(double amount, GregorianCalendar start, GregorianCalendar end, int days);
}
