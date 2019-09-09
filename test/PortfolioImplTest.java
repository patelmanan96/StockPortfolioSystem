import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import portfoliosystem.model.Information;
import portfoliosystem.model.Portfolio;
import portfoliosystem.model.PortfolioImpl;

import static junit.framework.TestCase.assertEquals;

public class PortfolioImplTest {
  Portfolio p = new PortfolioImpl("Savings");
  Calendar c1 = new GregorianCalendar();
  Calendar c = new GregorianCalendar();
  String dhaval;

  @Before
  public void setUp() {
    Calendar c = new GregorianCalendar();
    c.set(2007, Calendar.DECEMBER, 3, 16, 0, 0);
    p.setOfflineDirectory("/Users/mananpatel/Desktop/PDP/FinalSubmissionAssignment8/src" +
                           "/offlineData");
    c1.set(2012, Calendar.AUGUST, 13, 16, 0, 0);
    c.set(1998, Calendar.DECEMBER, 15, 16, 0, 0);
    dhaval = "/Users/mananpatel/Desktop/PDP/FinalSubmissionAssignment8/src/offlineData";
  }


  @Test
  public void checkSetDataOfImpl() {
    Information i = new Information();
    Calendar c = new GregorianCalendar();
    c.set(2017, Calendar.DECEMBER, 15, 16, 0, 0);
    i.setSymbol("AAPL");
    i.setBuyingDate(c);
    i.setCompanyCountry("USA");
    i.setCompanyName("Apple");
    i.setPrice(100);
    i.setQuantity(500);
    i.setType("EQUITY");
    String result = "Apple, USA\n" +
            "Company Ticker : AAPL\n" +
            "Date of Buying : Fri Dec 15 16:00:00 EST 2017\n" +
            "Price of Buying : $100.00\n" +
            "Quantity Bought : 500.00\n" +
            "Total Invested : $50,000.00\n" +
            "Commission Charged : $0.00\n" +
            "Type : EQUITY";
    assertEquals(i.toString(), result);
  }


  @Test
  public void getCompositionSuccessfully() {
    //Portfolio p = new PortfolioImpl("Savings");
    p.setOfflineDirectory(dhaval);
    p.buyOffline(c1, "AAPL", 100);
    String result = "Savings Portfolio\n" +
            "\n" +
            "Total Investment as on Mon Aug 13 16:00:00 EDT 2012 : $83,925.00\n" +
            "Investment Valuation as on Mon Aug 13 16:00:00 EDT 2012 : $129,039.00\n" +
            "\n" +
            "Apple Inc., United States\n" +
            "Company Ticker : AAPL\n" +
            "Date of Buying : Tue Dec 15 16:00:00 EST 1998\n" +
            "Price of Buying : $178.86\n" +
            "Quantity Bought : 100.0\n" +
            "Total Invested : $17,886.00\n" +
            "Type : Equity\n" +
            "\n" +
            "Microsoft, United States\n" +
            "Company Ticker : MSFT\n" +
            "Date of Buying : Mon Aug 13 16:00:00 EDT 2012\n" +
            "Price of Buying : $30.39\n" +
            "Quantity Bought : 100.0\n" +
            "Total Invested : $3,039.00\n" +
            "Type : Equity\n" +
            "\n" +
            "Apple Inc., United States\n" +
            "Company Ticker : AAPL\n" +
            "Date of Buying : Mon Aug 13 16:00:00 EDT 2012\n" +
            "Price of Buying : $630.00\n" +
            "Quantity Bought : 100.0\n" +
            "Total Invested : $63,000.00\n" +
            "Type : Equity\r\n";

    assertEquals(p.getComposition(c1), result);
  }

  @Test
  public void getValueSuccessfully() {
    assertEquals(p.getTotalValue(c1), 66039.0);
  }

  @Test
  public void getCostBasisSuccessfully() {
    assertEquals(p.getCostBasis(c1), 20925.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyWithIncorrectPriceOffline() {
    p.buyOfflineWithPrice(c, "AAPL", 100, 982908);
    assertEquals(p.getComposition(c1), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyWithIncorrectPriceOnline() {
    p.buyOnlineWithPrice(c, "AAPL", 100, 893849);
    assertEquals(p.getComposition(c1), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyOnAHolidayOffline() {
    c.set(2017, Calendar.DECEMBER, 25, 16, 0, 0);
    p.buyOfflineWithPrice(c, "AAPL", 100, 74);
    assertEquals(p.getComposition(c1), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyOnAHolidayOnline() {
    c.set(2017, Calendar.DECEMBER, 25, 16, 0, 0);
    p.buyOnlineWithPrice(c, "AAPL", 100, 74);
    assertEquals(p.getComposition(c1), "");
  }

  @Test
  public void successfulBuyOnlineWithPrice() {
    Portfolio p = new PortfolioImpl("Savings");
    Calendar c = new GregorianCalendar(2013, Calendar.JANUARY, 4);
    Calendar c1 = new GregorianCalendar(2015, Calendar.JANUARY, 16);
    p.buyOnlineWithPrice(c, "AMZN", 300, 258);
    assertEquals(p.getTotalValue(c1), 87222.0);
  }

  @Test
  public void successfulBuyOfflineWithPrice() {
    Portfolio p = new PortfolioImpl("Savings");
    Calendar c = new GregorianCalendar(2013, Calendar.JANUARY, 4);
    Calendar c1 = new GregorianCalendar(2015, Calendar.JANUARY, 16);
    p.setOfflineDirectory(dhaval);
    p.buyOfflineWithPrice(c, "AMZN", 300, 258);
    assertEquals(p.getTotalValue(c1), 87222.0);
  }

  @Test
  public void successfulBuyOfflineWithoutPrice() {
    Portfolio p = new PortfolioImpl("Savings");
    Calendar c = new GregorianCalendar(2013, Calendar.JANUARY, 4);
    Calendar c1 = new GregorianCalendar(2015, Calendar.JANUARY, 16);
    p.setOfflineDirectory(dhaval);
    p.buyOffline(c, "AMZN", 300);
    assertEquals(p.getTotalValue(c1), 87222.0);
  }

  @Test
  public void buyOnlineAndOfflineWithAndWithoutPrice() {
    Portfolio p = new PortfolioImpl("Savings");
    p.setOfflineDirectory(dhaval);
    Calendar c = new GregorianCalendar();
    c.set(1998, Calendar.DECEMBER, 15, 16, 0, 0);
    p.buyOffline(c, "AAPL", 100);
    p.buyOnline(c, "MSFT", 100);
    p.buyOfflineWithPrice(c, "BAC", 100, 56.9);
    c.set(2005, Calendar.FEBRUARY, 23, 16, 0, 0);
    p.buyOnlineWithPrice(c, "GOOGL", 100, 193.5);
    Calendar c2 = new GregorianCalendar(2005, 0, 19);
    p.buyOffline(c2, "VTCIX", 100);
    p.buyOffline(c2, "AMZN", 100);
    p.buyOffline(c2, "JNJ", 100);
    Calendar c1 = new GregorianCalendar();
    c1.set(2018, Calendar.JANUARY, 10, 16, 0, 0);
    Calendar c3 = new GregorianCalendar();
    c3.set(2018, Calendar.NOVEMBER, 1, 16, 0, 0);
    assertEquals(p.getTotalValue(c3), 331813.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCSVFileDataBuyOffline() {
    Portfolio p = new PortfolioImpl("Savings");
    p.setOfflineDirectory(dhaval);
    Calendar c = new GregorianCalendar();
    c.set(1998, Calendar.DECEMBER, 15, 16, 0, 0);
    p.buyOffline(c, "DFUSX", 100);
    assertEquals(p.getComposition(c1), "");

  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTickerBuyOffline() {
    p.buyOffline(c, "ACBGFFSC", 43);
    assertEquals(p.getComposition(c1), "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTickerButOnline() {
    p.buyOnline(c, "ACBGFFSC", 3434);
    assertEquals(p.getComposition(c1), "");
  }
}