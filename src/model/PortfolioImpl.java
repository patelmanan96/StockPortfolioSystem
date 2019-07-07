package model;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * This is the class that implements the interface Portfolio. It contains the implementations of the
 * methods which facilitate purchase of stocks in online as well as offline mode, get cost basis,
 * composition and the total value invested till a date.
 */
public class PortfolioImpl implements Portfolio {

  private String offlineDir;
  final List<Information> stockAndInfo;
  Map<String, StringBuilder> dataCache;
  Map<String, StringBuilder> companyInfoCache;
  double commission = 0;
  Set<String> companyRecord;

  /**
   * Constructs the Portfolio object initializing the name of the Portfolio.
   *
   * @param portName The name of the portfolio.
   */
  public PortfolioImpl(String portName) {
    offlineDir = "";
    String name = portName;
    stockAndInfo = new ArrayList<>();
    dataCache = new HashMap<>();
    companyRecord = new HashSet<String>();
    companyInfoCache = new HashMap<>();
    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
  }

  /**
   * This is a private helper method that validates the stock input data before buying and sets the
   * data if the validation is successful.
   *
   * @param a             The date as a Calender object.
   * @param companyTicker The ticker of the company who's stock is to be bought.
   * @param quantity      The quantity of the stock to be bought.
   * @param price         The price at which the stock is to be bought.
   * @param callFrom      Mode of purchase. ie. Online or Offline.
   * @return Information object.
   * @throws IOException If any of the input values are invalid.
   */
  private Information allValidationAndSet(Calendar a, String companyTicker,
                                          double quantity, double price, Mode callFrom)
          throws IOException {
    Information set = null;

    if (callFrom == Mode.ONLINE) {

      StringBuilder hold;
      //System.out.println("MAP : "+companyInfoCache);
      //System.out.println("TICK : "+companyTicker);
      if (companyInfoCache.containsKey(companyTicker)) {
        hold = companyInfoCache.get(companyTicker);
      } else {
        hold = DataFetchFunctionsAV.checkName(companyTicker);
      }
      String[] outside = hold.toString().split("\n");

      for (int j = 1; j < outside.length; j++) {
        String temp = outside[j];
        String[] tempArr = temp.split(",");
        if (Double.parseDouble(tempArr[tempArr.length - 1]) == 1.0) {
          companyInfoCache.put(companyTicker, hold);
          set = offlineOnlineResolver(a, tempArr, companyTicker, quantity, price, callFrom);
          break;
        } else {
          throw new IllegalArgumentException("Ambiguous Ticker Entry");
        }
      }
      if (set == null) {
        throw new IllegalArgumentException("Invalid Ticker Entry");
      }
      return set;
    } else {
      return offlineOnlineResolver(a, null, companyTicker, quantity, price, callFrom);
    }
  }

  /**
   * This is a helper method that is used to parse the Online API data and the Stock data
   * from the .csv file and fetch the correct values from the respective sources.
   *
   * @param a             The date as a Calender object on which the stock is to be bought.
   * @param forOnline     The data necessary to buy the stock.
   * @param companyTicker The ticker of the company who's stock is to be bought.
   * @param quantity      The quantity of the stock to be bought.
   * @param price         The price at which the stock is to be bought.
   * @param offOn         Mode of purchase. ie. Online or Offline.
   * @return The validated data of the Stock.
   * @throws IOException If any input is invalid.
   */
  Information offlineOnlineResolver(Calendar a, String[] forOnline,
                                    String companyTicker, double quantity,
                                    double price, Mode offOn) throws IOException {
    StringBuilder hold;
    String[] outside;
    int startPoint;
    double priceHold = price;
    Boolean inside = Boolean.FALSE;
    String[] companyFeatures = null;

    if (offOn == Mode.OFFLINE) {
      hold = DataFetchFunctionsAV.fileFetch(offlineDir + companyTicker);
      startPoint = 2;
      outside = hold.toString().split("\n");
      companyFeatures = outside[0].split(",");
      if (companyFeatures.length != 3) {
        throw new IllegalArgumentException("File Information not proper");
      }
    } else {
      if (dataCache.containsKey(companyTicker)) {
        hold = dataCache.get(companyTicker);
      } else {
        hold = DataFetchFunctionsAV.getHistory(companyTicker);
      }
      outside = hold.toString().split("\n");
      startPoint = 1;
    }

    for (int i = startPoint; i < outside.length; i++) {
      String[] arrForEval = outside[i].split(",");
      String[] date = arrForEval[0].split("-");
      if (Integer.parseInt(date[0]) == a.get(Calendar.YEAR)
              && Integer.parseInt(date[1]) == a.get(Calendar.MONTH) + 1
              && Integer.parseInt(date[2]) == a.get(Calendar.DAY_OF_MONTH)) {
        if (priceHold == 0) {
          priceHold = Double.parseDouble(arrForEval[4]);
          inside = Boolean.TRUE;
        } else if (priceHold < Double.parseDouble(arrForEval[2])
                && priceHold > Double.parseDouble(arrForEval[3])) {
          inside = Boolean.TRUE;
        } else {
          throw new IllegalArgumentException("Low for the day was : " + arrForEval[3]
                  + " &" + " High for the day was : " + arrForEval[2]);
        }
        break;
      }
    }
    if (!(inside)) {
      throw new IllegalArgumentException("Incorrect Date Entered");
    }
    if (offOn == Mode.OFFLINE) {
      return setAllData(companyTicker, companyFeatures[0], companyFeatures[1], companyFeatures[2],
              a, quantity, priceHold, Mode.OFFLINE);
    } else {
      Information toRet = setAllData(companyTicker, forOnline[1], forOnline[3], forOnline[2],
              a, quantity, priceHold, Mode.ONLINE);
      if (!(dataCache.containsKey(toRet.getSymbol()))) {
        dataCache.put(toRet.getSymbol(), hold);
      }
      return toRet;
    }

  }

  /**
   * This is a private helper method that sets the data that is related to the Stock.
   *
   * @param name           The ticker of the company.
   * @param companyName    The name of the company.
   * @param companyCountry The country of Registration of the company.
   * @param type           The type of Stock.
   * @param a              The calender object on which the stock is bought.
   * @param quantity       The quantity of the stock bought.
   * @param price          The price at which the stock is bought.
   * @param addedF         The mode of purchase. ie. Online or Offline.
   */
  private Information setAllData(String name, String companyName, String companyCountry,
                                 String type, Calendar a, double quantity, double price,
                                 Mode addedF) {
    if(quantity<0){
      throw new IllegalArgumentException("Cannot buy sufficient Quantity");
    }
    else if(price < 0){
      throw new IllegalArgumentException("Cannot buy with given amount");
    } else if (quantity*(price-commission)<0) {
      throw new IllegalArgumentException("Cannot buy given amount");
    }
    Information temp = new Information();
    companyRecord.add(name);
    temp.setSymbol(name);
    temp.setBuyingDate(a);
    temp.setType(type);
    temp.setQuantity(quantity);
    temp.setPrice(price);
    temp.setCommission(commission);
    temp.setCompanyName(companyName);
    temp.setCompanyCountry(companyCountry);
    temp.setAddedFrom(addedF);
    return temp;
  }

  @Override
  public void buyOnlineWithPrice(Calendar a, String companyTicker, double quantity, double price)
          throws IllegalArgumentException {
    if (price <= 0 || quantity <= 0) {
      throw new IllegalArgumentException("Price or Quantity not entered correctly");
    }
    Information currentStock;
    try {
      currentStock = allValidationAndSet(a, companyTicker, quantity, price, Mode.ONLINE);
    } catch (IOException ab) {
      throw new IllegalStateException(ab.getMessage());
    } catch (NumberFormatException ab1) {
      throw new IllegalArgumentException("API calls ran out try after 1 min");
    }
    stockAndInfo.add(currentStock);
  }

  @Override
  public void buyOnline(Calendar a, String companyTicker, double quantity)
          throws IllegalArgumentException {
    Information currentStock = null;
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity can't be negative");
    }
    try {
      currentStock = allValidationAndSet(a, companyTicker, quantity, 0, Mode.ONLINE);
    } catch (IOException ab) {
      throw new IllegalArgumentException("Specify the File path correctly");
    } catch (NumberFormatException ab1) {
      //System.out.println(ab1.getMessage());
      throw new IllegalArgumentException("API calls ran out try after 1 min");
    }
    stockAndInfo.add(currentStock);
  }

  @Override
  public void buyOffline(Calendar a, String companyTicker, double quantity)
          throws IllegalArgumentException {
    Information currentStock = null;
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity can't be negative");
    }
    try {
      currentStock = allValidationAndSet(a, companyTicker, quantity, 0, Mode.OFFLINE);
    } catch (IOException ab) {
      throw new IllegalArgumentException("Please specify the Directory Correctly.");
    }
    stockAndInfo.add(currentStock);
  }

  @Override
  public void buyOfflineWithPrice(Calendar a, String companyTicker, double quantity, double price)
          throws IllegalArgumentException {
    Information currentStock = null;
    if (price <= 0 || quantity <= 0) {
      throw new IllegalArgumentException("Price or Quantity not entered correctly");
    }
    try {
      currentStock = allValidationAndSet(a, companyTicker, quantity, price, Mode.OFFLINE);
    } catch (IOException ab) {
      throw new IllegalArgumentException("Specify the File path correctly");
    }
    stockAndInfo.add(currentStock);
  }

  @Override
  public void setOfflineDirectory(String directoryPath) {
    this.offlineDir = directoryPath;
  }

  @Override
  public double getCostBasis(Calendar d) throws IllegalArgumentException {
    if (stockAndInfo.isEmpty()) {
      return 0;
    }
    double total = 0;
    for (Information in : stockAndInfo) {
      try {
        if (in.getBuyingDate().compareTo(d) < 1) {
          total += in.getTotalInvested() + in.getCommission();
        }
      } catch (Exception a) {
        throw new IllegalArgumentException("Specify the Directory Correctly");
      }
    }
    return total;
  }

  /**
   * This is a private helper method that fetches the price at which the stock is to be bought.
   *
   * @param stockObj The stock data.
   * @param dateforD The date for which the price is to be fetched.
   * @return The price of the stock on the entered date.
   * @throws IOException If file is not found for Offline Mode.
   */
  private double getPrice(Information stockObj, Calendar dateforD) throws IOException {
    double priceHold = 0;
    StringBuilder data = null;
    int startInt = 0;
    if (stockObj.getAddedFrom() == Mode.ONLINE) {
      if(dataCache.containsKey(stockObj.getSymbol())) {
        data = dataCache.get(stockObj.getSymbol());
      }
      else {
        data = DataFetchFunctionsAV.getHistory(stockObj.getSymbol());
        dataCache.put(stockObj.getSymbol(),data);
      }
      startInt = 1;
    } else {
      data = DataFetchFunctionsAV.fileFetch(offlineDir + stockObj.getSymbol());
      startInt = 2;
    }
    boolean inside = Boolean.FALSE;
    String[] outside = data.toString().split("\n");
    for (int i = startInt; i < outside.length; i++) {
      String[] arrForEval = outside[i].split(",");
      String[] date = arrForEval[0].split("-");
      if (Integer.parseInt(date[0]) == dateforD.get(Calendar.YEAR)
              && Integer.parseInt(date[1]) == dateforD.get(Calendar.MONTH) + 1
              && Integer.parseInt(date[2]) == dateforD.get(Calendar.DAY_OF_MONTH)) {
        inside = Boolean.TRUE;
        priceHold = Double.parseDouble(arrForEval[4]);
        break;
      }
    }
    if (!(inside)) {
      throw new IllegalArgumentException("Incorrect Check Date Entered");
    }
    return priceHold;
  }

  @Override
  public double getTotalValue(Calendar d) throws IllegalArgumentException {
    if (stockAndInfo.isEmpty()) {
      return 0;
    }
    double totalApp = 0;
    for (Information in : stockAndInfo) {
      if (in.getBuyingDate().compareTo(d) < 1) {
        try {
          double temp = getPrice(in, d);
          totalApp += temp * in.getQuantity();
        } catch (IOException a) {
          a.printStackTrace();
        }
      }
    }
    return totalApp;
  }

  @Override
  public List getComposition(Calendar d) {
    if (stockAndInfo.isEmpty()) {
      return null;
    }
    List<Information> toRet = new ArrayList<>();
    for (Information in : stockAndInfo) {
      if (in.getBuyingDate().compareTo(d) < 1) {
        toRet.add(in);
      }
    }
    Collections.sort(toRet);
    return toRet;
  }
}