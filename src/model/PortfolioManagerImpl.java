package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the interface PortfolioManager. It contains the implementation of methods
 * that allow to add a new portfolio, get an existing portfolio and list all the existing
 * portfolios.
 */
public class PortfolioManagerImpl implements PortfolioManager {
  Map<String, Portfolio> portfolios;
  Map<Integer, String> portfolioIndexing;
  int index;

  /**
   * Constructs the object of the PortfolioManagerImpl class.
   */
  public PortfolioManagerImpl() {
    portfolios = new LinkedHashMap<>();
    portfolioIndexing = new LinkedHashMap<>();
    index = 1;
  }

  @Override
  public void createPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio Already Exists");
    } else {
      portfolioIndexing.put(index, name);
      portfolios.put(name, new PortfolioImpl(name));
      index++;
    }
  }

  @Override
  public Portfolio getPortfolio(String name) {
    if (portfolios.containsKey(name)) {
      return portfolios.get(name);
    } else {
      throw new IllegalArgumentException("No Portfolio with that name");
    }
  }

  @Override
  public Portfolio getPortfolio(int portIndex) {
    if (portfolioIndexing.containsKey(portIndex)) {
      return portfolios.get(portfolioIndexing.get(portIndex));
    } else {
      throw new IllegalArgumentException("Wrong Index Input");
    }
  }

  @Override
  public List<String> listAllPortfolios() {
    List<String> things = new ArrayList<>();

    for(int i:portfolioIndexing.keySet()){
      things.add(portfolioIndexing.get(i));
    }
    /*StringBuilder toRet = new StringBuilder();
    portfolioIndexing.forEach((k, v) -> toRet.append(k + ". " + v + "\n"));
    String[] toPrint = toRet.toString().split("\n");
    StringBuilder n = new StringBuilder();
    for (int i = 0; i < toPrint.length; i++) {
      if (!(toPrint[i].trim().equals(""))) {
        n.append(toPrint[i].trim());
      }
      if (i != toPrint.length - 1) {
        n.append("\n");
      }
    }*/
    return things;
  }
}
