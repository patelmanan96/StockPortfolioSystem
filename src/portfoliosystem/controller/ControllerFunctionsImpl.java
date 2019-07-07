package portfoliosystem.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import portfoliosystem.model.Information;
import portfoliosystem.model.Mode;
import portfoliosystem.model.PortfolioCommissionStrategy;
import portfoliosystem.model.PortfolioManager;
import portfoliosystem.view.ViewOperations;

/**
 * This is the class that implements the interface ControllerFunctions. It contains the
 * implementation of the method that calls the model's and view's as and when necessary.
 */
public class ControllerFunctionsImpl implements ControllerFunctions {
  private final PortfolioManager pm;
  private final ViewOperations vo;
  private Map<String, Integer> months;
  private List<String> companies = new ArrayList<>();
  private Map<String, Integer> weights = new HashMap<>();
  private NumberFormat formatter;

  /**
   * Constructs the object of the Controller class.
   *
   * @param p The object of the model.
   * @param v The object of the view.
   */
  public ControllerFunctionsImpl(PortfolioManager p, ViewOperations v) {
    this.pm = p;
    this.vo = v;
    months = new HashMap<>();
    months.put("january", 0);
    months.put("february", 1);
    months.put("march", 2);
    months.put("april", 3);
    months.put("may", 4);
    months.put("june", 5);
    months.put("july", 6);
    months.put("august", 7);
    months.put("september", 8);
    months.put("october", 9);
    months.put("november", 10);
    months.put("december", 11);
    formatter = NumberFormat.getCurrencyInstance(Locale.US);
  }

  /**
   * This is a private helper method that displays the Landing menu of the Portfolios.
   *
   * @return String containing the options available for a Portfolio.
   */
  private String firstPage() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n1. Create a new Portfolio.\n");
    sb.append("2. Operate on created Portfolios.\n");
    sb.append("3. View Portfolios.\n");
    sb.append("4. Exit\n");
    return sb.toString();
  }

  /**
   * This is a private helper method that displays the String before taking the input from the
   * user.
   *
   * @return The message as a String.
   */
  private String choiceAsk() {
    return "\nPlease enter a choice number : ";
  }

  /**
   * This is a private helper method that displays the String before entering the name of the
   * Portfolio.
   *
   * @return The message as a String.
   */
  private String portfolioCreateStrting() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nEnter the name of Portfolio : ");
    return sb.toString();
  }

  /**
   * This is a private helper method that displays the message when the user enters an Incorrect
   * choice.
   *
   * @return The message as a String.
   */
  private String incorrectSring() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nIncorrect Choice Please Enter Again : ");
    return sb.toString();
  }

  /**
   * This is a private helper method that displays the menu of the options available for each of the
   * Portfolios.
   *
   * @return The message as a String.
   */
  private String buyTotalCostComposition() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n1. Buy");
    sb.append("\n2. Total Invested");
    sb.append("\n3. Total Investment Valuation");
    sb.append("\n4. View");
    sb.append("\n5. Go Back");
    return sb.toString();
  }

  @Override
  public void startPortfolioManagementSystem() {
    try {
      main:
      {
        while (true) {
          vo.displayToConsole(firstPage());
          vo.displayToConsole(choiceAsk());
          String choice = vo.getDataFromConsole();


          while (true) {
            switch (choice) {
              case "1":
                createPortfolioOp();
                break;
              case "2":
                portOptions();
                break;
              case "3":
                viewPortfolios();
                vo.displayToConsole("\n\nPress any key to return....");
                vo.getDataFromConsole();
                break;
              case "4":
                break main;
              default:
                vo.displayToConsole(incorrectSring());
                choice = vo.getDataFromConsole();
                continue;
            }
            break;
          }

        }
      }

    } catch (IOException a) {
      throw new IllegalArgumentException("Unable to Read/Write please provide correct streams");
    }
  }

  /**
   * This is a private helper method that takes the input for creating a new Portfolio and calls the
   * model's method to create a new Portfolio.
   *
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void createPortfolioOp() throws IOException {
    while (true) {
      try {
        vo.displayToConsole(portfolioCreateStrting());
        String portName = vo.getDataFromConsole();
        pm.createPortfolio(portName);
        break;
      } catch (IllegalArgumentException a) {
        vo.displayToConsole("\n" + a.getMessage());
      }
    }
  }

  /**
   * This is a private helper method that calls the model's method to list all the portfolios and
   * displays the list of the portfolios entered by the user.
   *
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void viewPortfolios() throws IOException {
    vo.displayToConsole("\nList of Portfolios" + "\n\n");
    vo.displayToConsole(portfolioNumbering(pm.listAllPortfolios()));
  }

  private String portfolioNumbering(List<String> port){
    //System.out.println("P DISP : "+port);
    if(port==null){
      return "";
    }
    StringBuilder sb= new StringBuilder();
    int count=1;
    for(String k:port){
      sb.append(count+". "+k);
      if(count != port.size()){
        sb.append("\n");
      }
      count++;
    }
    return sb.toString();
  }

  /**
   * This is a private helper method that displays the options that are available for a Portfolio.
   *
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void portOptions() throws IOException {
    main:
    {
      while (true) {
        viewPortfolios();
        vo.displayToConsole("\n");
        vo.displayToConsole((portfolioNumbering(pm.listAllPortfolios()).split("\n").length + 1) + ". Main Menu");
        vo.displayToConsole("\n");
        int index;
        vo.displayToConsole("\nSelect a portfolio, Enter the number : ");
        String k = vo.getDataFromConsole();
        try {
          index = Integer.parseInt(k);
        } catch (Exception a) {
          continue;
        }
        if (index <= 0 || index > portfolioNumbering(pm.listAllPortfolios()).split("\n").length + 1) {
          continue;
        }
        if (index == portfolioNumbering(pm.listAllPortfolios()).split("\n").length + 1) {
          break main;
        }
        createdPortOperations(index);
      }
    }
  }

  /**
   * This is private helper method that calls the model's methods to display the Cost basis and the
   * Composition of a particular Portfolio and gives it to the view to display it to the console.
   *
   * @param index The index of the portfolio name.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void createdPortOperations(int index) throws IOException {
    main:
    {
      while (true) {
        vo.displayToConsole(buyTotalCostComposition());
        vo.displayToConsole("\n");
        vo.displayToConsole(choiceAsk());
        String choice = vo.getDataFromConsole();
        switch (choice) {
          case "1":
            buyDisplay(index);
            break;
          case "2":
            vo.displayToConsole("\n");
            Calendar c = setDateFromInput();
            try {
              vo.displayToConsole("Total Invested as on " + c.getTime() + " is : "
                      + "" + formatter.format(pm.getPortfolio(index).getCostBasis(c)));
            } catch (IllegalArgumentException a) {
              vo.displayToConsole(a.getMessage());
              continue;
            }
            break;
          case "3":
            vo.displayToConsole("\n");
            Calendar c1 = setDateFromInput();
            try {
              vo.displayToConsole("Total Investment valuation on " + c1.getTime() + " is : "
                      + "" + formatter.format(pm.getPortfolio(index).getTotalValue(c1)));
            } catch (IllegalArgumentException a) {
              vo.displayToConsole(a.getMessage());
              continue;
            }
            break;
          case "4":
            viewStockDisplay(index);
            break;
          case "5":
            break main;
          default:
            break;
        }
      }

    }
  }

  /**
   * This is a private helper method that displays the menu to buy the stock Online or Offline.
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void buyDisplay(int index) throws IOException {
    main:
    {
      while (true) {
        vo.displayToConsole("\nBuy Options\n");
        vo.displayToConsole("\n1. Buy Online.");
        vo.displayToConsole("\n2. Buy Offline.");
        vo.displayToConsole("\n3. Strategic Buy Online");
        vo.displayToConsole("\n4. Go Back");
        vo.displayToConsole("\n\n" + choiceAsk());
        String inp = vo.getDataFromConsole();
        if (simpleNumberValidation(inp)) {
          int i = Integer.parseInt(inp);
          if (i < 0 || i > 5) {
            continue;
          }
          switch (i) {
            case 1:
              buyOnline(index);
              break;
            case 2:
              buyOffline(index);
              break;
            case 3:
              investmentStrategies(index);
              break;
            case 4:
              break main;
            default:
              break;
          }
        }
      }
    }
  }

  /**
   * This is a private helper method that displays the menu for the strategies present under the
   * option "Strategic Buy Online".
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void investmentStrategies(int index) throws IOException {
    main:
    {
      while (true) {
        vo.displayToConsole("\n1. Dollar Cost Averaging");
        vo.displayToConsole("\n2. One Time Split Invest");
        vo.displayToConsole("\n3. Go back");
        vo.displayToConsole(choiceAsk());
        String inp = vo.getDataFromConsole();
        switch (inp) {
          case "1":
            strategicDCABuyMain(index);
            break;
          case "2":
            somethingSimple(index);
            break;
          case "3":
            break main;
          default:
            break;
        }
      }
    }
  }

  /**
   * This is a private helper method that displays the menu for the strategies present under the
   * option "One Time Split Invest".
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void somethingSimple(int index) throws IOException {
    main:
    {
      while (true) {
        vo.displayToConsole("\n1. Change Weight of Companies if you wish");
        vo.displayToConsole("\n2. Invest (if weights not changed, equal split of weights)");
        vo.displayToConsole("\n3. Go back");
        vo.displayToConsole(choiceAsk());
        String inp = vo.getDataFromConsole();
        switch (inp) {
          case "1":
            try {
              changeWeights(index);
              break;
            } catch (Exception a) {
              vo.displayToConsole("Incorrect Entry");
            }
            break;
          case "2":
            invest(index);
            break;
          case "3":
            break main;
          default:
            break;
        }
      }
    }
  }

  /**
   * This is a private helper method takes the amount and the commission for facilitating the
   * purchase of the stocks under the DCA Strategy.
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void invest(int index) throws IOException {
    double amount = 0;
    double commissionD;
    while (true) {
      vo.displayToConsole("\nEnter amount for purchase: \t");
      String amt = vo.getDataFromConsole();
      if (simpleNumberValidation(amt)) {
        amount = Integer.parseInt(amt);
        if (amount < 0) {
          continue;
        }
      }
      vo.displayToConsole("Enter Commission : ");
      String commission = vo.getDataFromConsole();
      try {
        commissionD = Double.parseDouble(commission);
      } catch (NumberFormatException a) {
        continue;
      }
      PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
      try {
        pcs.setCommission(commissionD);
      } catch (IllegalArgumentException a) {
        pcs.setCommission(0);
        vo.displayToConsole(a.getMessage());
      }
      break;
    }
    Calendar c = setDateFromInput();
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
    try {
      pcs.divideInPortfolio(amount, (GregorianCalendar) c);
    } catch (Exception a) {
      pcs.setCommission(0);
      vo.displayToConsole(a.getMessage());
    }
  }

  /**
   * This is a private helper method that displays the menu for the strategies present under the
   * option "Dollar Cost Averaging".
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void strategicDCABuyMain(int index) throws IOException {
    double commissionD;
    main:
    {
      while (true) {
        vo.displayToConsole("\n1. Purchase Dates");
        vo.displayToConsole("\n2. Change Weight of Companies");
        vo.displayToConsole("\n3. Enter Commission");
        vo.displayToConsole("\n4. Go back");
        vo.displayToConsole(choiceAsk());
        String inp = vo.getDataFromConsole();
        switch (inp) {
          case "1":
            purchaseDates(index);
            break;
          case "2":
              try {
                changeWeights(index);
              } catch (Exception a) {
                vo.displayToConsole("Incorrect Entry");
                break;
              }
            break;
          case "3":
            vo.displayToConsole("Enter Commission : ");
            String commission = vo.getDataFromConsole();
            try {
              commissionD = Double.parseDouble(commission);
            } catch (NumberFormatException a) {
              vo.displayToConsole("Incorrect Comission Entered");
              continue;
            }
            PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
            try {
              pcs.setCommission(commissionD);
            } catch (IllegalArgumentException a) {
              pcs.setCommission(0);
              vo.displayToConsole(a.getMessage());
            }
            break;
          case "4":
            break main;
          default:
            break;
        }
      }
    }
  }

  /**
   * This is a private helper method that takes the start and the end dates(if any) for a recurring
   * purchase under the Dollar Cost Averaging Strategy.
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void purchaseDates(int index) throws IOException {
    main:
    {

      vo.displayToConsole("\nEnter purchase Start Date");
      Calendar start = setDateFromInput();
      vo.displayToConsole("\nDo you want to give an End Date");
      vo.displayToConsole("\n1. Yes");
      vo.displayToConsole("\n2. No");
      vo.displayToConsole(choiceAsk());
      String inp = vo.getDataFromConsole();
      if (simpleNumberValidation(inp)) {
        int i = Integer.parseInt(inp);
        if (i == 1) {
          vo.displayToConsole("\nEnter purchase End Date");
          Calendar end = setDateFromInput();
          freqAndAmount(index, start, end);
        } else if (i == 2) {
          freqAndAmount(index, start, null);
        } else {
          purchaseDates(index);
        }

      }
    }
  }

  /**
   * This is a private helper method that takes the frequency of the purchase and the amount that is
   * to be invested.
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @param start The start date of the Strategy.
   * @param end   The end date of the Strategy.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void freqAndAmount(int index, Calendar start, Calendar end) throws IOException {
    int amount = 0;
    int frequency = 0;
    while (true) {
      vo.displayToConsole("\n Enter Frequency of purchase: \t");
      String freq = vo.getDataFromConsole();
      if (simpleNumberValidation(freq)) {
        frequency = Integer.parseInt(freq);
      } else {
        continue;
      }
      while (true) {
        vo.displayToConsole("\n Enter amount for purchase: \t");
        String amt = vo.getDataFromConsole();
        if (simpleNumberValidation(amt)) {
          amount = Integer.parseInt(amt);
          if (amount < 0) {
            continue;
          }
          break;
        }
      }
      try {
        PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
        if (end == null) {
          pcs.buyLoopDate(amount, (GregorianCalendar) start, frequency);
        } else {
          pcs.buyLoopDate(amount, (GregorianCalendar) start, (GregorianCalendar) end, frequency);
        }
      } catch (Exception a) {
        vo.displayToConsole("\n" + a.getMessage());
        freqAndAmount(index, start, end);
      }
      break;
    }
  }

  /**
   * This is a private helper method that facilitates to change the weights of the existing
   * companies in the Portfolio.
   *
   * @param index The index of the portfolio name in which the stock is to be bought.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void changeWeights(int index) throws Exception {
    Set<String> addCompanies = new HashSet<>();
    PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
    List<Information> toTraverse = pcs.getComposition(Calendar.getInstance());
    for (Information a : toTraverse) {
      addCompanies.add(a.getSymbol());
    }
    HashMap<String, Double> toRet = new HashMap<>();
    vo.displayToConsole("\nSpecify Percent, Make sure the total is 100%\n");
    for (String k : addCompanies) {
      while (true) {
        double temp;
          vo.displayToConsole(k + " : ");
          String inpV = vo.getDataFromConsole();
          if(simpleNumberDoubleValidation(inpV) == Boolean.FALSE){
            continue;
          }
          temp = Double.parseDouble(inpV);
          if (temp <= 0 || temp > 100) {
            //vo.displayToConsole("\n");
            //throw new IllegalArgumentException();
            continue;
          }
        toRet.put(k, temp);
        break;
      }
    }
    double totalCheck = 0;
    for (String l : toRet.keySet()) {
      totalCheck += toRet.get(l);
    }
    if (totalCheck != 100) {
      throw new IllegalArgumentException("Percent Total not 100");
      // continue back to taking inputs
    }
    pcs.setPercentages(toRet);
  }

  /**
   * This is a private helper method that asks asks if the stock to be bought Online is to be bought
   * with the user explicitly mentioning the price or without explicitly mentioning the price.
   *
   * @param index The index of the portfolio name.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void buyOnline(int index) throws IOException {
    main:
    {
      while (true) {
        vo.displayToConsole("\nOnline Buy Options\n");
        vo.displayToConsole("\n1. With Price.");
        vo.displayToConsole("\n2. Without Price.");
        vo.displayToConsole("\n3. Go Back.");
        vo.displayToConsole(choiceAsk());
        String inp = vo.getDataFromConsole();
        switch (inp) {
          case "1":
            withPrice(index, Mode.ONLINE);
            break;
          case "2":
            withoutPrice(index, Mode.ONLINE);
            break;
          case "3":
            break main;
          default:
            break;
        }
      }
    }
  }

  /**
   * This is a private helper method that calls the method of the model to buy the stock in a
   * particular portfolio by explicitly giving the price.
   *
   * @param index    The index of the portfolio name.
   * @param callFrom The mode of the purchase - Offline or Online.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void withPrice(int index, Mode callFrom) throws IOException {
    main:
    {
      while (true) {
        double quantityD;
        double priceD;
        double commissionD;
        Calendar c = setDateFromInput();
        vo.displayToConsole("\n\nEnter Ticker : ");
        String tick = vo.getDataFromConsole();
        vo.displayToConsole("\nEnter Quantity : ");
        String quantity = vo.getDataFromConsole();
        try {
          quantityD = Double.parseDouble(quantity);
        } catch (NumberFormatException a) {
          continue;
        }
        vo.displayToConsole("\nEnter Price : ");
        String price = vo.getDataFromConsole();
        try {
          priceD = Double.parseDouble(price);
        } catch (NumberFormatException a) {
          continue;
        }
        vo.displayToConsole("\nEnter Commission : ");
        String commission = vo.getDataFromConsole();
        try {
          commissionD = Double.parseDouble(commission);
        } catch (NumberFormatException a) {
          continue;
        }
        PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
        if (callFrom == Mode.ONLINE) {
          try {
            pcs.setCommission(commissionD);
            pcs.buyOnlineWithPrice(c, tick, quantityD, priceD);
            break;
          } catch (IllegalArgumentException a) {
            pcs.setCommission(0);
            vo.displayToConsole(a.getMessage());
          }
        } else {
          try {
            pcs.setCommission(commissionD);
            pm.getPortfolio(index).buyOfflineWithPrice(c, tick, quantityD, priceD);
            break;
          } catch (IllegalArgumentException a) {
            pcs.setCommission(0);
            vo.displayToConsole(a.getMessage());
          }
        }
      }
    }
  }

  /**
   * This is a private helper method that calls the method of the model to buy the stock in a
   * particular portfolio without explicitly giving the price.
   *
   * @param index    The index of the portfolio name.
   * @param callFrom The mode of the purchase - Offline or Online.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void withoutPrice(int index, Mode callFrom) throws IOException {
    main:
    {
      while (true) {
        double quantityD;
        double commissionD;
        Calendar c = setDateFromInput();
        vo.displayToConsole("\n\nEnter Ticker : ");
        String tick = vo.getDataFromConsole();
        vo.displayToConsole("\nEnter Quantity : ");
        String quantity = vo.getDataFromConsole();
        try {
          quantityD = Double.parseDouble(quantity);
        } catch (NumberFormatException a) {
          continue;
        }
        vo.displayToConsole("\nEnter Commission : ");
        String commission = vo.getDataFromConsole();
        try {
          commissionD = Double.parseDouble(commission);
        } catch (NumberFormatException a) {
          continue;
        }
        PortfolioCommissionStrategy pcs = (PortfolioCommissionStrategy) pm.getPortfolio(index);
        if (callFrom == Mode.ONLINE) {
          try {
            pcs.setCommission(commissionD);
            pm.getPortfolio(index).buyOnline(c, tick, quantityD);
            break;
          } catch (IllegalArgumentException a) {
            pcs.setCommission(0);
            vo.displayToConsole(a.getMessage());
            break;
          }
        } else {
          try {
            pcs.setCommission(commissionD);
            pm.getPortfolio(index).buyOffline(c, tick, quantityD);
            break;
          } catch (IllegalArgumentException a) {
            pcs.setCommission(0);
            vo.displayToConsole(a.getMessage());
            break;
          }
        }
      }
    }
  }

  /**
   * This is a private helper method that sets the directory to facilitate buying of offline stocks.
   * This directory contains the files that contain the historical data of the Stocks.
   *
   * @param index The index of the portfolio name.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void setDirectory(int index) throws IOException {
    vo.displayToConsole("\nPlease Enter the Directory to be Used for Offline : ");
    String dir = vo.getDataFromConsole();
    pm.getPortfolio(index).setOfflineDirectory(dir);
  }

  /**
   * This is a private helper method that asks asks if the stock to be bought Offline is to be
   * bought with the user explicitly mentioning the price or without explicitly mentioning the
   * price.
   *
   * @param index The index of the portfolio name.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void buyOffline(int index) throws IOException {
    main:
    {
      while (true) {
        vo.displayToConsole("\nOffline Buy Options\n");
        vo.displayToConsole("\n1. Set Directory.");
        vo.displayToConsole("\n2. With Price.");
        vo.displayToConsole("\n3. Without Price.");
        vo.displayToConsole("\n4. Go Back.");
        vo.displayToConsole("\n" + choiceAsk());
        String inp = vo.getDataFromConsole();
        switch (inp) {
          case "1":
            setDirectory(index);
            continue;
          case "2":
            withPrice(index, Mode.OFFLINE);
            continue;
          case "3":
            withoutPrice(index, Mode.OFFLINE);
            continue;
          case "4":
            break main;
          default:
            break;
        }
      }
    }
  }

  /**
   * This is a private helper method that calls the model's method to display the composition of a
   * Portfolio.
   *
   * @param index The index of the portfolio name.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private void viewStockDisplay(int index) throws IOException {
    main:
    {
      while (true) {
        Calendar c = setDateFromInput();
        vo.displayToConsole("\nCOMPOSITION \n\n");
        try {
          vo.displayToConsole(compositionFormatter(pm.getPortfolio(index).getComposition(c),
                  c, index));
        } catch (IllegalArgumentException a) {
          vo.displayToConsole(a.getMessage());
          break main;
        }
        vo.displayToConsole("\nPress any key to continue...");
        String v = vo.getDataFromConsole();
        break;
      }
    }
  }

  private String compositionFormatter(List toFormat, Calendar d, int index) {
    if (toFormat == null) {
      throw new IllegalArgumentException("No Stocks Added");
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Total Investment as on " + d.getTime() + " : " + formatter.format(
            pm.getPortfolio(index).getCostBasis(d)));
    sb.append(System.lineSeparator());
    sb.append("Investment Valuation as on " + d.getTime() + " : " + formatter.format(
            pm.getPortfolio(index).getTotalValue(d)));
    sb.append(System.lineSeparator());
    for (Object in : toFormat) {
      sb.append("\n");
      sb.append(in.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * This is a private helper method that checks whether the input is a number or not and returns a
   * boolean value accordingly.
   *
   * @param probableNumber The String value of the input.
   * @return A boolean value based on whether the input is a number or not.
   */
  private boolean simpleNumberValidation(String probableNumber) {
    try {
      int k = Integer.parseInt(probableNumber);
      return Boolean.TRUE;
    } catch (NumberFormatException a) {
      return Boolean.FALSE;
    }
  }

  private boolean simpleNumberDoubleValidation(String probableNumber) {
    try {
      int k = Integer.parseInt(probableNumber);
      return Boolean.TRUE;
    } catch (NumberFormatException a) {
      return Boolean.FALSE;
    }
  }

  /**
   * This is a private helper method that checks whether the inputted month is valid or not.
   *
   * @param month The String value of the month.
   * @return A boolean value based on whether the month entered is valid or not.
   */
  private boolean correctMonth(String month) {
    for (String k : months.keySet()) {
      if (month.equalsIgnoreCase(k)) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }

  /**
   * This is a private helper method that constructs a Calender object.
   *
   * @return A Calender object.
   * @throws IOException If not able to take input from view or give output to view.
   */
  private GregorianCalendar setDateFromInput() throws IOException {
    GregorianCalendar enteredDate;
    while (true) {
      int d = 0;
      String month;
      int year = 0;
      vo.displayToConsole("\nEnter the Date\n\n");
      while (true) {
        vo.displayToConsole("Enter Day : ");
        String day = vo.getDataFromConsole();
        if (simpleNumberValidation(day)) {
          d = Integer.parseInt(day);
          if (d < 0 || d > 31) {
            continue;
          }
        } else {
          continue;
        }
        break;
      }
      while (true) {
        vo.displayToConsole("Enter Month in String: ");
        month = vo.getDataFromConsole();
        if (!(correctMonth(month))) {
          continue;
        }
        break;
      }

      while (true) {
        vo.displayToConsole("Enter Year : ");
        String temp = vo.getDataFromConsole();
        if (simpleNumberValidation(temp)) {
          year = Integer.parseInt(temp);
          if (year < 1900 || year > 2018) {
            continue;
          }
        }
        break;
      }
      Calendar checkToday = Calendar.getInstance();
      enteredDate = new GregorianCalendar(year, months.get(month.toLowerCase()), d);

      if (enteredDate.compareTo(checkToday) == 1) {
        continue;
      }
      break;
    }
    return enteredDate;
  }
}