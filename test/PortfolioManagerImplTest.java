import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import portfoliosystem.model.Portfolio;
import portfoliosystem.model.PortfolioManager;
import portfoliosystem.model.PortfolioManagerImpl;

import static org.junit.Assert.assertEquals;

public class PortfolioManagerImplTest {

  PortfolioManager pm;

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

  @Before
  public void intialize() {
    pm = new PortfolioManagerImpl();
  }

  @Test
  public void createAndListAllPortfolios() {
    pm.createPortfolio("Savings");
    assertEquals(pm.listAllPortfolios().toString(), "[Savings]");
    pm.createPortfolio("Retirement");
    assertEquals(pm.listAllPortfolios().toString(), "[Savings, Retirement]");
    pm.createPortfolio("College");
    assertEquals(pm.listAllPortfolios().toString(), "[Savings, Retirement, College]");
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryToAddPortfolioWithAlreadyExistingPortfolio() {
    pm.createPortfolio("Savings");
    assertEquals(pm.listAllPortfolios().toString(), "[Savings]");
    pm.createPortfolio("Savings");
  }

  @Test
  public void getPortfolioByNameAndIndex() {
    pm.createPortfolio("Savings");
    int a = pm.getPortfolio(1).hashCode();
    int b = pm.getPortfolio("Savings").hashCode();
    assertEquals(a, b);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fetchNonExistingPortfolio() {
    pm.createPortfolio("Savings");
    assertEquals(pm.getPortfolio("Retirement"), "");
  }

  @Test
  public void getPortfolioSpecificObject() {
    pm.createPortfolio("Savings");
    pm.createPortfolio("Retirement");
    Calendar c = new GregorianCalendar(2015, 10, 2);
    pm.getPortfolio("Savings").setOfflineDirectory("/D:/Masters_NEU/CS 5010/Projects/"
            + "assignment8/src/offlineData/");
    pm.getPortfolio("Savings").buyOffline(c, "AAPL", 100);
    pm.getPortfolio("Retirement").setOfflineDirectory("/D:/Masters_NEU/CS 5010/Projects/"
            + "assignment8/src/offlineData/");
    pm.getPortfolio("Retirement").buyOffline(c, "MSFT", 100);
    assertEquals(pm.getPortfolio("Retirement").getTotalValue(c), 5324.0, 0);
    assertEquals(pm.getPortfolio("Savings").getTotalValue(c), 12118.0, 0);
  }
}