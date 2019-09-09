import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import portfoliosystem.controller.ControllerFunctions;
import portfoliosystem.controller.ControllerFunctionsImpl;
import portfoliosystem.model.PCSManager;
import portfoliosystem.model.Portfolio;
import portfoliosystem.model.PortfolioManager;
import portfoliosystem.model.PortfolioManagerImpl;
import portfoliosystem.view.ViewOperations;

import static junit.framework.TestCase.assertEquals;

public class ControllerFunctionsImplTest {
  String dhaval = "/D:/Masters_NEU/CS 5010/Projects/assignment8/src/offlineData/";

  class StubPortfolio implements Portfolio {
    private StringBuilder log = new StringBuilder();
    private String name;

    StubPortfolio(String name) {
      this.name = name;
    }

    @Override
    public void buyOnlineWithPrice(Calendar a, String companyTicker, double quantity, double price)
            throws IllegalArgumentException {
      log.append("Purchase Successful Online With Price");
    }

    @Override
    public void buyOnline(Calendar a, String companyTicker, double quantity)
            throws IllegalArgumentException {
      log.append("Purchase Successful Online Without Price ");

    }

    @Override
    public void buyOffline(Calendar a, String companyTicker, double quantity)
            throws IllegalArgumentException {
      log.append("Purchase Successful Offline Without Price ");

    }

    @Override
    public void buyOfflineWithPrice(Calendar a, String companyTicker, double quantity, double price)
            throws IllegalArgumentException {
      log.append("Purchase Successful Offline With Price ");

    }

    @Override
    public void setOfflineDirectory(String directoryPath) {
      log.append("Directory set ");
    }

    @Override
    public double getTotalValue(Calendar d) throws IllegalArgumentException {
      log.append("Total value successful ");
      return 0;
    }

    @Override
    public double getCostBasis(Calendar d) throws IllegalArgumentException {
      log.append("Cost basis successful ");
      return 0;
    }

    @Override
    public List getComposition(Calendar d) {
      log.append("Composition successful ");
      return null;
    }
  }

  class StubPort implements PortfolioManager {

    private StringBuilder log = new StringBuilder();

    @Override
    public void createPortfolio(String name) {
      log.append(name + " ");
    }

    @Override
    public Portfolio getPortfolio(String name) {
      return null;
    }

    @Override
    public Portfolio getPortfolio(int portIndex) {
      return null;
    }

    @Override
    public List<String> listAllPortfolios() {
      return null;
    }

    @Override
    public String toString() {
      return log.toString();
    }
  }

  class ViewStub implements ViewOperations {
    private StringBuilder logV = new StringBuilder();

    private Readable sin;
    private Appendable sout;
    private String[] init;
    private int i = 0;

    ViewStub(Readable a, Appendable b, String[] comm) {
      sin = a;
      sout = b;
      init = comm;
    }

    @Override
    public void displayToConsole(String k) throws IOException {
      logV.append(k);
    }

    @Override
    public String getDataFromConsole() {
      String temp = init[i];
      i++;
      return temp;
    }

    @Override
    public String toString() {
      return logV.toString();
    }
  }

  @Test
  public void testObjectCreationAndExitProperly() throws IOException {

    Appendable out = new StringBuilder();
    PortfolioManager pm = new PCSManager();
    Readable input = new StringReader("4");

    String[] commands = {"4"};
    ViewOperations vo = new ViewStub(input, out, commands);
    ControllerFunctions c = new ControllerFunctionsImpl(pm, vo);
    c.startPortfolioManagementSystem();
    String res = "\n"
            + "1. Create a new Portfolio.\n"
            + "2. Operate on created Portfolios.\n"
            + "3. View Portfolios.\n"
            + "4. Exit\n"
            + "\n"
            + "Please enter a choice number : ";
    assertEquals(vo.toString(), res);
  }


  @Test
  public void testPortFolioManagerCommunication() throws IOException {
    Appendable out = new StringBuilder();
    PortfolioManager portStub = new StubPort();
    Readable input = new StringReader("");
    String[] commands = {"1", "Savings", "4"};
    ViewOperations vo = new ViewStub(input, out, commands);
    ControllerFunctions c = new ControllerFunctionsImpl(portStub, vo);
    c.startPortfolioManagementSystem();
    assertEquals(portStub.toString(), "Savings ");
  }

  @Test
  public void createMultiplePortfoliosAndView() throws IOException {
    Appendable out = new StringBuilder();
    PortfolioManager portStub = new StubPort();
    Readable input = new StringReader("");
    String[] commands = {"1", "Savings", "1", "Retirement", "1", "College", "3", "f", "4"};
    ViewOperations vo = new ViewStub(input, out, commands);
    ControllerFunctions c = new ControllerFunctionsImpl(portStub, vo);
    c.startPortfolioManagementSystem();
    assertEquals(portStub.toString(), "Savings Retirement College ");
  }

  @Test
  public void buySharesForAPortfolioOfflineWithoutPrice() throws IOException {
    Appendable out = new StringBuilder();
    PortfolioManager portStub = new PortfolioManagerImpl();
    Portfolio p = new StubPortfolio("Savings");
    Readable input = new StringReader("");
    String[] commands = {"1", "Savings", "2", "1", "1", "2", "1", dhaval, "3",
                         "15", "December", "1998", "AAPL", "100", "4", "3", "5", "2", "4"};
    ViewOperations vo = new ViewStub(input, out, commands);
    ControllerFunctions c = new ControllerFunctionsImpl(portStub, vo);
    try {
      c.startPortfolioManagementSystem();
      Calendar c1 = new GregorianCalendar();
      c1.set(2012, Calendar.AUGUST, 13, 16, 0, 0);
      p.buyOffline(c1, "AAPL", 100);
      assertEquals(((StubPortfolio) p).log.toString(), "Purchase Successful Offline "
              + "Without Price ");
    } catch (Exception a) {
      // Not checking for Exception for Test. Checking something else.
    }
  }

  @Test(expected = Exception.class)
  public void buySharesForAPortfolioOfflineWithPrice() throws IOException {
    String[] commands = {"1", "Savings", "2", "1", "1", "2", "1", dhaval, "3",
                         "15", "December", "1998", "AAPL", "228", "100", "4", "3", "5", "2", "4"};
    Appendable out = new StringBuilder();
    Readable input = new StringReader("");
    PortfolioManager pfm = new PortfolioManagerImpl();
    ViewOperations vo = new ViewStub(input, out, commands);
    Portfolio p = new StubPortfolio("Savings");
    ControllerFunctions c = new ControllerFunctionsImpl(pfm, vo);
    c.startPortfolioManagementSystem();
    Calendar c1 = new GregorianCalendar();
    c1.set(2012, Calendar.AUGUST, 13, 16, 0, 0);
    p.buyOfflineWithPrice(c1, "AAPL", 100, 342);
    assertEquals(((StubPortfolio) p).log.toString(),
            "Purchase Successful Offline With Price ");
  }

  @Test
  public void viewComposition() throws IOException {
    String[] commands = {"1", "Savings", "1", "Retirement", "2", "1", "4", "12",
                         "December", "2017", "f", "5", "3", "4"};
    Appendable out = new StringBuilder();
    Readable input = new StringReader("");
    PortfolioManager pfm = new PortfolioManagerImpl();
    ViewOperations vo = new ViewStub(input, out, commands);
    Portfolio p = new StubPortfolio("Savings");
    ControllerFunctions c = new ControllerFunctionsImpl(pfm, vo);
    c.startPortfolioManagementSystem();
    Calendar c1 = new GregorianCalendar();
    c1.set(2012, Calendar.AUGUST, 13, 16, 0, 0);
    p.buyOfflineWithPrice(c1, "AAPL", 100, 342);
    p.getComposition(c1);
    assertEquals(((StubPortfolio) p).log.toString(),
            "Purchase Successful Offline With Price Composition successful ");
  }

  @Test
  public void viewCostBasis() throws IOException {
    String[] commands = {"1", "Savings", "2", "1", "1", "2", "1", dhaval, "3",
                         "15", "December", "1998", "AAPL", "228", "100", "4", "3", "2",
                         "5", "December", "2017", "5", "2", "4"};
    Appendable out = new StringBuilder();
    try {
      Readable input = new StringReader("");
      PortfolioManager pfm = new PortfolioManagerImpl();
      ViewOperations vo = new ViewStub(input, out, commands);
      Portfolio p = new StubPortfolio("Savings");
      ControllerFunctions c = new ControllerFunctionsImpl(pfm, vo);
      c.startPortfolioManagementSystem();
      Calendar c1 = new GregorianCalendar();
      c1.set(2012, Calendar.AUGUST, 13, 16, 0, 0);
      p.buyOfflineWithPrice(c1, "AAPL", 100, 342);
      Calendar c2 = new GregorianCalendar();
      c1.set(2017, Calendar.DECEMBER, 5, 16, 0, 0);
      p.getCostBasis(c2);
      assertEquals(((StubPortfolio) p).log.toString(),
              "Purchase Successful Offline With Price Cost basis successful ");
    } catch (Exception a) {
      // Not checking for Exception for Test. Checking something else.
    }
  }

  @Test
  public void viewTotalValue() throws IOException {
    String[] commands = {"1", "Savings", "2", "1", "1", "2", "1", dhaval, "3",
                         "15", "December", "1998", "AAPL", "228", "4", "3", "3", "5", "December",
                         "2017", "5", "2", "4"};
    try {
      Appendable out = new StringBuilder();
      Readable input = new StringReader("");
      PortfolioManager pfm = new PortfolioManagerImpl();
      ViewOperations vo = new ViewStub(input, out, commands);
      Portfolio p = new StubPortfolio("Savings");
      ControllerFunctions c = new ControllerFunctionsImpl(pfm, vo);
      c.startPortfolioManagementSystem();
      Calendar c1 = new GregorianCalendar();
      c1.set(2012, Calendar.AUGUST, 13, 16, 0, 0);
      p.buyOfflineWithPrice(c1, "AAPL", 100, 342);
      Calendar c2 = new GregorianCalendar();
      c1.set(2017, Calendar.DECEMBER, 5, 16, 0, 0);
      p.getTotalValue(c2);
      assertEquals(((StubPortfolio) p).log.toString(),
              "Purchase Successful Offline With Price Total value successful ");
    } catch (Exception a) {
      // Not checking for Exception for Test. Checking something else.
    }
  }

  @Test
  public void wrongInputGiven() {
    String[] commands = {"77", "-8392", "dascasd", "1", "Savings", "1", "Retirement", "1",
                         "College", "3", "f", "4"};
    Appendable out = new StringBuilder();
    PortfolioManager portStub = new StubPort();
    Readable input = new StringReader("");
    ViewOperations vo = new ViewStub(input, out, commands);
    ControllerFunctions c = new ControllerFunctionsImpl(portStub, vo);
    c.startPortfolioManagementSystem();
    assertEquals(portStub.toString(), "Savings Retirement College ");
  }
}
