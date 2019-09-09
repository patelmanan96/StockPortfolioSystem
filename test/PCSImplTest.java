import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import portfoliosystem.model.PCSImpl;
import portfoliosystem.model.Portfolio;
import portfoliosystem.model.PortfolioCommissionStrategy;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the new Interface PortfolioCommissionStrategy.
 */
public class PCSImplTest {
  Portfolio pi = new PCSImpl("Test");

  @Test
  public void oldPortTest() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2016, Calendar.JANUARY, 20),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2016, Calendar.AUGUST, 22),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2016, Calendar.APRIL, 25),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2016, Calendar.APRIL, 25),
            "MSFT", 100);
    pcs.setCommission(5.0);
    pcs.divideInPortfolio(1000, new GregorianCalendar(2016, Calendar.APRIL,
            25));
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Jan 20 00:00:00 EST 2016\n" +
            "Price of Buying : $198.70\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $19,870.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Aug 22 00:00:00 EDT 2016\n" +
            "Price of Buying : $222.93\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $22,293.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Apr 25 00:00:00 EDT 2016\n" +
            "Price of Buying : $251.82\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $25,182.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Apr 25 00:00:00 EDT 2016\n" +
            "Price of Buying : $52.11\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $5,211.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Apr 25 00:00:00 EDT 2016\n" +
            "Price of Buying : $52.11\n" +
            "Quantity Bought : 15.26\n" +
            "Total Invested : $795.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Apr 25 00:00:00 EDT 2016\n" +
            "Price of Buying : $251.82\n" +
            "Quantity Bought : 0.77\n" +
            "Total Invested : $195.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity]";
    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.NOVEMBER,
            27)).toString(), result);
  }

  @Test
  public void noCommissionFeeOneTimeBuy() {
    Portfolio p = new PCSImpl("Test");
    p.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    p.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 100);
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $9,406.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity]";
    assertEquals(p.getComposition(new GregorianCalendar(2018, Calendar.OCTOBER,
            12)).toString(), result);
    assertEquals(p.getCostBasis(new GregorianCalendar(2018, Calendar.OCTOBER,
            12)), 43691.0, 2);
    assertEquals(p.getTotalValue(new GregorianCalendar(2018, Calendar.OCTOBER,
            12)), 36835.0, 2);

  }

  @Test
  public void CommissionFeeOneTimeBuy() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.setCommission(5);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 100);
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $9,406.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity]";
    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.OCTOBER,
            12)).toString(), result);
    assertEquals(p.getCostBasis(new GregorianCalendar(2018, Calendar.OCTOBER,
            12)), 43701.0, 2);
    assertEquals(p.getTotalValue(new GregorianCalendar(2018, Calendar.OCTOBER,
            12)), 36835.0, 2);

  }

  @Test
  public void noCommissionRecurringBuy() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 100);
    pcs.buyLoopDate(1000, new GregorianCalendar(2017, Calendar.APRIL, 25),
            new GregorianCalendar(2018, Calendar.AUGUST, 29), 30);
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $9,406.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Apr 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $67.92\n" +
            "Quantity Bought : 7.36\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Apr 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $313.79\n" +
            "Quantity Bought : 1.59\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Thu May 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $69.62\n" +
            "Quantity Bought : 7.18\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Thu May 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $316.83\n" +
            "Quantity Bought : 1.58\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jun 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $70.53\n" +
            "Quantity Bought : 7.09\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jun 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $377.49\n" +
            "Quantity Bought : 1.32\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Jul 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $74.05\n" +
            "Quantity Bought : 6.75\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Jul 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $343.85\n" +
            "Quantity Bought : 1.45\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Aug 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $72.82\n" +
            "Quantity Bought : 6.87\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Aug 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $348.05\n" +
            "Quantity Bought : 1.44\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Sep 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $73.26\n" +
            "Quantity Bought : 6.83\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Sep 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $344.99\n" +
            "Quantity Bought : 1.45\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Oct 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $78.63\n" +
            "Quantity Bought : 6.36\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Oct 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $325.84\n" +
            "Quantity Bought : 1.53\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Nov 24 00:00:00 EST 2017\n" +
            "Price of Buying : $83.26\n" +
            "Quantity Bought : 6.01\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Nov 24 00:00:00 EST 2017\n" +
            "Price of Buying : $315.55\n" +
            "Quantity Bought : 1.58\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Dec 26 00:00:00 EST 2017\n" +
            "Price of Buying : $85.40\n" +
            "Quantity Bought : 5.85\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Dec 26 00:00:00 EST 2017\n" +
            "Price of Buying : $317.29\n" +
            "Quantity Bought : 1.58\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Thu Jan 25 00:00:00 EST 2018\n" +
            "Price of Buying : $92.33\n" +
            "Quantity Bought : 5.42\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Thu Jan 25 00:00:00 EST 2018\n" +
            "Price of Buying : $337.64\n" +
            "Quantity Bought : 1.48\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Feb 26 00:00:00 EST 2018\n" +
            "Price of Buying : $95.42\n" +
            "Quantity Bought : 5.24\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Feb 26 00:00:00 EST 2018\n" +
            "Price of Buying : $357.42\n" +
            "Quantity Bought : 1.40\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Mar 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $89.39\n" +
            "Quantity Bought : 5.59\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Mar 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $257.78\n" +
            "Quantity Bought : 1.94\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Apr 27 00:00:00 EDT 2018\n" +
            "Price of Buying : $95.82\n" +
            "Quantity Bought : 5.22\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Apr 27 00:00:00 EDT 2018\n" +
            "Price of Buying : $294.07\n" +
            "Quantity Bought : 1.70\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue May 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $98.01\n" +
            "Quantity Bought : 5.10\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue May 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $283.76\n" +
            "Quantity Bought : 1.76\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Thu Jun 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $98.63\n" +
            "Quantity Bought : 5.07\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Thu Jun 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $349.93\n" +
            "Quantity Bought : 1.43\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jul 30 00:00:00 EDT 2018\n" +
            "Price of Buying : $105.37\n" +
            "Quantity Bought : 4.75\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jul 30 00:00:00 EDT 2018\n" +
            "Price of Buying : $290.17\n" +
            "Quantity Bought : 1.72\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Aug 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $112.02\n" +
            "Quantity Bought : 4.46\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Aug 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $305.01\n" +
            "Quantity Bought : 1.64\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity]";
    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)).toString(), result);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)), 66392.58, 2);
    assertEquals(pcs.getCostBasis(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)), 60691.0, 2);
  }

  @Test
  public void CommissionRecurringBuy() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 100);
    pcs.setCommission(5);
    pcs.buyLoopDate(1000, new GregorianCalendar(2017, Calendar.APRIL, 25),
            new GregorianCalendar(2018, Calendar.AUGUST, 29), 30);
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $9,406.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Apr 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $67.92\n" +
            "Quantity Bought : 7.29\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Apr 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $313.79\n" +
            "Quantity Bought : 1.58\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Thu May 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $69.62\n" +
            "Quantity Bought : 7.11\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Thu May 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $316.83\n" +
            "Quantity Bought : 1.56\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jun 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $70.53\n" +
            "Quantity Bought : 7.02\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jun 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $377.49\n" +
            "Quantity Bought : 1.31\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Jul 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $74.05\n" +
            "Quantity Bought : 6.68\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Jul 26 00:00:00 EDT 2017\n" +
            "Price of Buying : $343.85\n" +
            "Quantity Bought : 1.44\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Aug 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $72.82\n" +
            "Quantity Bought : 6.80\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Aug 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $348.05\n" +
            "Quantity Bought : 1.42\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Sep 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $73.26\n" +
            "Quantity Bought : 6.76\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Sep 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $344.99\n" +
            "Quantity Bought : 1.43\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Oct 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $78.63\n" +
            "Quantity Bought : 6.30\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Oct 25 00:00:00 EDT 2017\n" +
            "Price of Buying : $325.84\n" +
            "Quantity Bought : 1.52\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Nov 24 00:00:00 EST 2017\n" +
            "Price of Buying : $83.26\n" +
            "Quantity Bought : 5.95\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Nov 24 00:00:00 EST 2017\n" +
            "Price of Buying : $315.55\n" +
            "Quantity Bought : 1.57\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Dec 26 00:00:00 EST 2017\n" +
            "Price of Buying : $85.40\n" +
            "Quantity Bought : 5.80\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Dec 26 00:00:00 EST 2017\n" +
            "Price of Buying : $317.29\n" +
            "Quantity Bought : 1.56\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Thu Jan 25 00:00:00 EST 2018\n" +
            "Price of Buying : $92.33\n" +
            "Quantity Bought : 5.36\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Thu Jan 25 00:00:00 EST 2018\n" +
            "Price of Buying : $337.64\n" +
            "Quantity Bought : 1.47\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Feb 26 00:00:00 EST 2018\n" +
            "Price of Buying : $95.42\n" +
            "Quantity Bought : 5.19\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Feb 26 00:00:00 EST 2018\n" +
            "Price of Buying : $357.42\n" +
            "Quantity Bought : 1.38\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Mar 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $89.39\n" +
            "Quantity Bought : 5.54\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Mar 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $257.78\n" +
            "Quantity Bought : 1.92\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Apr 27 00:00:00 EDT 2018\n" +
            "Price of Buying : $95.82\n" +
            "Quantity Bought : 5.17\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Apr 27 00:00:00 EDT 2018\n" +
            "Price of Buying : $294.07\n" +
            "Quantity Bought : 1.68\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue May 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $98.01\n" +
            "Quantity Bought : 5.05\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue May 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $283.76\n" +
            "Quantity Bought : 1.74\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Thu Jun 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $98.63\n" +
            "Quantity Bought : 5.02\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Thu Jun 28 00:00:00 EDT 2018\n" +
            "Price of Buying : $349.93\n" +
            "Quantity Bought : 1.41\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jul 30 00:00:00 EDT 2018\n" +
            "Price of Buying : $105.37\n" +
            "Quantity Bought : 4.70\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jul 30 00:00:00 EDT 2018\n" +
            "Price of Buying : $290.17\n" +
            "Quantity Bought : 1.71\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Wed Aug 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $112.02\n" +
            "Quantity Bought : 4.42\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Wed Aug 29 00:00:00 EDT 2018\n" +
            "Price of Buying : $305.01\n" +
            "Quantity Bought : 1.62\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity]";
    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)).toString(), result);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)), 66187.64, 2);
    assertEquals(pcs.getCostBasis(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)), 60691.0, 2);

  }

  @Test
  public void allDataViewMultipleTimesWithCommission() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 200);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 200);
    pcs.setCommission(5);
    pcs.buyLoopDate(1000, new GregorianCalendar(2017, Calendar.APRIL, 25),
            new GregorianCalendar(2018, Calendar.AUGUST, 29), 30);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.SEPTEMBER,
            28)), 94253.38, 2);
    assertEquals(pcs.getCostBasis(new GregorianCalendar(2018, Calendar.SEPTEMBER,
            28)), 104382.0, 2);

    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)), 109176.41, 2);
    assertEquals(pcs.getCostBasis(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)), 104382.0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeCommissionFee() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.setCommission(-5);

    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 100);
    pcs.buyLoopDate(1000, new GregorianCalendar(2017, Calendar.APRIL, 25),
            new GregorianCalendar(2018, Calendar.AUGUST, 29), 30);

    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)).toString(), "");
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)), "");
    assertEquals(pcs.getCostBasis(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)), "");
  }

  @Test
  public void skipHolidayMoveNextDateRecurringBuy() {
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 2),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 2),
            "MSFT", 100);
    pcs.setCommission(5);
    pcs.buyLoopDate(1000, new GregorianCalendar(2018, Calendar.JANUARY, 2)
            , new GregorianCalendar(2018, Calendar.JANUARY, 31), 5);
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Jan 02 00:00:00 EST 2018\n" +
            "Price of Buying : $320.53\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $32,053.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Jan 02 00:00:00 EST 2018\n" +
            "Price of Buying : $85.95\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $8,595.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Jan 02 00:00:00 EST 2018\n" +
            "Price of Buying : $85.95\n" +
            "Quantity Bought : 5.76\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Jan 02 00:00:00 EST 2018\n" +
            "Price of Buying : $320.53\n" +
            "Quantity Bought : 1.54\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jan 08 00:00:00 EST 2018\n" +
            "Price of Buying : $88.28\n" +
            "Quantity Bought : 5.61\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jan 08 00:00:00 EST 2018\n" +
            "Price of Buying : $336.41\n" +
            "Quantity Bought : 1.47\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Tue Jan 16 00:00:00 EST 2018\n" +
            "Price of Buying : $88.35\n" +
            "Quantity Bought : 5.60\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Tue Jan 16 00:00:00 EST 2018\n" +
            "Price of Buying : $340.06\n" +
            "Quantity Bought : 1.46\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jan 22 00:00:00 EST 2018\n" +
            "Price of Buying : $91.61\n" +
            "Quantity Bought : 5.40\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jan 22 00:00:00 EST 2018\n" +
            "Price of Buying : $351.56\n" +
            "Quantity Bought : 1.41\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Jan 29 00:00:00 EST 2018\n" +
            "Price of Buying : $93.92\n" +
            "Quantity Bought : 5.27\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Mon Jan 29 00:00:00 EST 2018\n" +
            "Price of Buying : $349.53\n" +
            "Quantity Bought : 1.42\n" +
            "Total Invested : $495.00\n" +
            "Commission Charged : $5.00\n" +
            "Type : Equity]";
    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)).toString(), result);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)), 50266.68, 2);
    assertEquals(pcs.getCostBasis(new GregorianCalendar(2018, Calendar.NOVEMBER,
            20)), 45648.0, 2);
  }

  @Test
  public void changeWeightsOfCompanyAndBuy() {
    HashMap<String, Double> test = new HashMap<>();
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "MSFT", 100);
    pcs.divideInPortfolio(1000, new GregorianCalendar(2018, Calendar.JANUARY,
            26));
    test.put("TSLA", 40.0);
    test.put("MSFT", 60.0);
    pcs.setPercentages(test);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.divideInPortfolio(1000, new GregorianCalendar(2018, Calendar.JANUARY,
            26));
    test.put("TSLA", 30.0);
    test.put("MSFT", 70.0);
    pcs.setPercentages(test);
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.divideInPortfolio(1000, new GregorianCalendar(2018, Calendar.JANUARY,
            26));
    String result = "[Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $9,406.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 5.32\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 1.46\n" +
            "Total Invested : $500.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 6.38\n" +
            "Total Invested : $600.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 1.17\n" +
            "Total Invested : $400.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 100.00\n" +
            "Total Invested : $34,285.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Microsoft Corporation, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $94.06\n" +
            "Quantity Bought : 7.44\n" +
            "Total Invested : $700.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity, Tesla Inc., United States\n" +
            "Company Ticker : TSLA\n" +
            "Date of Buying : Fri Jan 26 00:00:00 EST 2018\n" +
            "Price of Buying : $342.85\n" +
            "Quantity Bought : 0.88\n" +
            "Total Invested : $300.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity]";
    assertEquals(pcs.getComposition(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)).toString(), result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void abnormalWeightsOne() {
    HashMap<String, Double> test = new HashMap<>();
    test.put("TSLA", -110.0);
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.setPercentages(test);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            25)), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void abnormalWeightsNegative() {
    Portfolio p = new PCSImpl("Test");
    HashMap<String, Double> test = new HashMap<>();
    test.put("TSLA", -50.0);
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.setPercentages(test);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            25)), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void addOneCompanyAndChangeWeights() {
    HashMap<String, Double> test = new HashMap<>();
    test.put("TSLA", 50.0);
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.buyOnline(new GregorianCalendar(2018, Calendar.JANUARY, 26),
            "TSLA", 100);
    pcs.setPercentages(test);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            28)), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeWeightCompanyNotInPortfolio() {
    HashMap<String, Double> test = new HashMap<>();
    test.put("AAPL", 50.0);
    Portfolio p = new PCSImpl("Test");
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) p;
    pcs.setPercentages(test);
    assertEquals(pcs.getTotalValue(new GregorianCalendar(2018, Calendar.NOVEMBER,
            25)), "");
  }
}
