package model;

/**
 * This class extends the class PortfolioManagerImpl. It creates the object of the new Portfolio
 * which provides the functionality of purchasing stocks using Dollar Cost Averaging Strategy and
 * normal cost split strategies with default preset of equal division of stocks.
 */
public class PCSManager extends PortfolioManagerImpl {
  @Override
  public void createPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio Already Exists");
    } else {
      portfolioIndexing.put(index, name);
      portfolios.put(name, new PCSImpl(name));
      index++;
    }
  }
}
