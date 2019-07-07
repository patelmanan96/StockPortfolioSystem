package model;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * This is the class that contains all the data related to a Stock.
 */
public class Information implements Comparable{

  private Calendar buyingDate;
  private double price;
  private double quantity;
  private String companyName;
  private String companyCountry;
  private String type;
  private String symbol;
  private NumberFormat formatter;
  private Mode addedFrom;
  private double commission;

  /**
   * Constructs the objects of the class Information and sets the default currency to USD.
   */
  public Information() {
    formatter = NumberFormat.getCurrencyInstance(Locale.US);
    addedFrom = Mode.ONLINE;
  }

  /**
   * Setter for Mode of purchase.
   *
   * @param addedFrom Mode of purchase.
   */
  public void setAddedFrom(Mode addedFrom) {
    this.addedFrom = addedFrom;
  }

  /**
   * Getter for Mode of purchase.
   *
   * @return Mode of purchase.
   */
  public Mode getAddedFrom() {
    return addedFrom;
  }

  /**
   * Setter for Buying Date.
   *
   * @param d buying Date.
   */
  public void setBuyingDate(Calendar d) {
    this.buyingDate = d;
  }

  /**
   * Setter for price of Stock.
   *
   * @param price Price of Stock.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Setter for number of Stocks.
   *
   * @param quantity Number of Stocks.
   */
  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  /**
   * Setter for company name of Stock.
   *
   * @param companyName Company name.
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  /**
   * Setter for country of the company.
   *
   * @param companyCountry Country of the company.
   */
  public void setCompanyCountry(String companyCountry) {
    this.companyCountry = companyCountry;
  }

  /**
   * Setter for type of stock Mutual Fund, Equity, ETF,etc.
   *
   * @param type Type of stock.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Getter for date of purchase.
   *
   * @return Date of purchase.
   */
  public Calendar getBuyingDate() {
    return buyingDate;
  }

  /**
   * Getter for price of Stock.
   *
   * @return Price of Stock.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Getter for number of shares bought.
   *
   * @return Number of shares bought.
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Getter for company's country of registration.
   *
   * @return Company's country.
   */
  public String getCompanyCountry() {
    return companyCountry;
  }

  /**
   * Getter for company's name.
   *
   * @return Company's name.
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * Getter for type of stock Mutual Fund, Equity, ETF,etc.
   *
   * @return Type of stock.
   */
  public String getType() {
    return type;
  }

  /**
   * Setter for the company ticker of the stock.
   *
   * @param symbol Company ticker.
   */
  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  /**
   * Getter for the company ticker of the stock.
   *
   * @return Company ticker.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Getter for the total amount invested in a particular stock.
   *
   * @return Amount invested in a particular stock.
   */
  public double getTotalInvested() {
    return price * quantity;
  }

  public double getCommission() {
    return commission;
  }

  public void setCommission(double commission) {
    this.commission = commission;

  }

  @Override
  public String toString() {
    return String.format(companyName + ", " + companyCountry + "\n" + "Company Ticker : "
            + symbol + "\n"
            + "Date of Buying : " + buyingDate.getTime() + "\n" + "Price of Buying : "
            + formatter.format(price) + "\n" + "Quantity Bought : " +
            String.format("%.2f", quantity)
            + "\n"
            + "Total Invested : "
            + formatter.format(price * quantity)
            + "\n" + "Commission Charged : " + formatter.format(commission) + "\n" +
            "Type : " + this.type);
  }

  // NEW
  @Override
  public int compareTo(Object o) {
    if(o instanceof Information){
      Information incoming = (Information)o;
      return this.getBuyingDate().compareTo(incoming.getBuyingDate());
    }
    else
    {
      return -1;
    }
  }
}
