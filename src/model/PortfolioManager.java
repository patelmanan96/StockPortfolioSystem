package model;

import java.util.List;

/**
 * This is the interface that represents a collection of Portfolios. It contains the method
 * definitions to create a new Portfolio, get an existing portfolio, and print all the existing
 * portfolios.
 */
public interface PortfolioManager {
  /**
   * Create a new Portfolio.
   *
   * @param name The name of the Portfolio.
   */
  void createPortfolio(String name);

  /**
   * Get an already existing Portfolio by passing the name of the Portfolio.
   *
   * @param name The name of the Portfolio.
   * @return The Portfolio object.
   */
  Portfolio getPortfolio(String name);

  /**
   * Get an already existing Portfolio by passing the index value of the Portfolio.
   *
   * @param portIndex The index of the Portfolio.
   * @return The Portfolio object.
   */
  Portfolio getPortfolio(int portIndex);

  /**
   * Get all the existing Portfolios, returned a String previously now returns a list.
   *
   * @return All the existing Portfolios as a String.
   */
  List<String> listAllPortfolios(); // Returns a List instead of a String.
}
