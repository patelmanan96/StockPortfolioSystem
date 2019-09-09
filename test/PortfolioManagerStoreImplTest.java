import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import portfoliosystem.model.Information;
import portfoliosystem.model.PortfolioManagerExtended;
import portfoliosystem.model.PortfolioManagerStoreImpl;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PortfolioManagerStoreImplTest {
  PortfolioManagerExtended pme;

  @Test
  public void basicPersistanceRoot() {
    File f = new File("Portfolios");
    assertFalse(f.exists());

    pme = new PortfolioManagerStoreImpl();

    assertTrue(f.exists());
  }

  @Test
  public void structureChecks() {
    File f = new File("Portfolios", "Savings");

    assertFalse(f.exists());

    pme = new PortfolioManagerStoreImpl();

    pme.reset();

    pme.createPortfolio("Savings");

    assertTrue(f.exists());

    File further = new File(f.getPath(), "OtherInfo");
    File further1 = new File(f.getPath(), "StockData");
    File further2 = new File(f.getPath(), "StockData");

    assertTrue(further.exists());
    assertTrue(further1.exists());
    assertTrue(further2.exists());

    File caches = new File(further, "CompanyDataCache");
    File caches1 = new File(further, "CompanyInfoCache");

    assertTrue(caches.exists());
    assertTrue(caches1.exists());
  }

  @Test
  public void testFilesOutput() throws IOException {
    pme = new PortfolioManagerStoreImpl();

    pme.reset();

    pme.createPortfolio("Savings");

    pme.getPortfolio("Savings")
            .buyOnline(new GregorianCalendar(2017, Calendar.JANUARY, 10),
                    "AAPL", 10);

    String expectedOut = "Apple Inc.\n" +
            "United States\n" +
            "AAPL\n" +
            "Equity\n" +
            "119.11\n" +
            "10.0\n" +
            "0.0\n" +
            "online\n" +
            "0-10-2017";

    File f = new File(new File("Portfolios", "Savings").getPath(), "StockData");

    StringBuilder sb = new StringBuilder();
    BufferedReader bw = null;
    for (String k : f.list()) {
      bw = new BufferedReader(new FileReader(new File(f, k)));
    }
    String line;
    while ((line = bw.readLine()) != null) {
      sb.append(line);
      String secondline = bw.readLine();
      if (secondline != null) {
        sb.append("\n");
        sb.append(secondline);
        sb.append("\n");
      }
    }
    assertEquals(sb.toString(), expectedOut);
  }

  @Test
  public void testUserFileInput() throws IOException {
    pme = new PortfolioManagerStoreImpl();

    pme.reset();

    pme.createPortfolio("Savings");

    File f = new File(new File("Portfolios", "Savings").getPath(), "StockData");

    File createNew = new File(f, "useInp.txt");

    String input = "Apple Inc.\n" +
            "United States\n" +
            "AAPL\n" +
            "Equity\n" +
            "119.11\n" +
            "10.0\n" +
            "0.0\n" +
            "online\n" +
            "0-10-2017";

    BufferedWriter bw = new BufferedWriter(new FileWriter(createNew));

    bw.write(input);

    bw.flush();

    bw.close();

    pme = new PortfolioManagerStoreImpl();

    pme.retrieve();

    String expected = "Apple Inc., United States\n" +
            "Company Ticker : AAPL\n" +
            "Date of Buying : Tue Jan 10 00:00:00 EST 2017\n" +
            "Price of Buying : $119.11\n" +
            "Quantity Bought : 10.00\n" +
            "Total Invested : $1,191.10\n" +
            "Commission Charged : $0.00\n" +
            "Type : Equity";

    List<Information> toC = pme.getPortfolio("Savings")
            .getComposition(new GregorianCalendar(2017, Calendar.JANUARY, 11));

    assertEquals(expected, toC.get(0).toString());
  }

}
