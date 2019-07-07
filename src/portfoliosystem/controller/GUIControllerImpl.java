package portfoliosystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import portfoliosystem.model.PortfolioCommissionStrategy;
import portfoliosystem.model.PortfolioManager;
import portfoliosystem.model.PortfolioManagerExtended;
import portfoliosystem.model.StrategyAutoSaveRetrieve;
import portfoliosystem.view.GView;

public class GUIControllerImpl implements ControllerFunctions {
  GView gv;
  PortfolioManager pme;
  String currentPort;
  Set<String> addCompanies = new HashSet<>();
  private NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

  public GUIControllerImpl(GView gv, PortfolioManager pme) {
    this.gv = gv;
    this.pme = pme;
  }

  @Override
  public void startPortfolioManagementSystem() throws IllegalArgumentException {
    configureButtonListener();
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonPress = new HashMap<>();

    buttonPress.put("CREATE PORTFOLIO", () -> {
      System.out.println("HERE");
      gv.createInputPortNamePanel();
      System.out.println("CREATED ?");
    });

    buttonPress.put("EXIT", () -> {
      System.exit(0);
    });

    buttonPress.put("Submit Portfolio Name", () -> {

      try {
        pme.createPortfolio(gv.getSubmitNameData());
        //System.out.println("CREATED IN MODEL");
        gv.displayMessage("CREATED");
      }
      catch (IllegalArgumentException a){
        gv.displayMessage(a.getMessage());
      }
    });

    buttonPress.put("RETRIEVE DATA", () -> {
      try {
        PortfolioManagerExtended pmeA = (PortfolioManagerExtended) pme;
        pmeA.retrieve();
      } catch (ClassCastException a) {
        gv.displayMessage("Operation Not Supported by the passed class");
      }
      gv.displayMessage("Retrieve Success");
    });

    buttonPress.put("RESET DATA", () -> {
      try {
        PortfolioManagerExtended pmeA = (PortfolioManagerExtended) pme;
        pmeA.reset();
      } catch (ClassCastException a) {
        gv.displayMessage("Operation Not Supported by the passed class");
      }
      gv.displayMessage("Reset Success");
    });

    buttonPress.put("VIEW PORTFOLIOS", () -> {
      System.out.println("HERE");
      /*List<String> temp = new ArrayList<>();
      temp.add("ONE");
      temp.add("TWO");
      */
      gv.showPortFolioList(pme.listAllPortfolios());
    });

    buttonPress.put("OPERATE ON PORTFOLIO", () -> {
      /*List<String> temp = new ArrayList<>();
      temp.add("ONE");
      temp.add("TWO");*/
      gv.operateOnPortfolios(pme.listAllPortfolios());
    });

    buttonPress.put("CONFIRM SELECTION", () -> {
      gv.currentPort(gv.getSelectedPortfolio());
      this.currentPort = gv.getSelectedPortfolio();
      System.out.println("CURRENT PORT : "+this.currentPort);
      gv.displayPortfolioSubMenu();
    });

    buttonPress.put("BUY", () -> {
      gv.showBuyOptions();
    });

    buttonPress.put("BACK", () -> {
      gv.operateOnPortfolios(pme.listAllPortfolios());
    });

    buttonPress.put("BUY ONLINE", () -> {
      gv.stockBuyModule();
    });

    /*buttonPress.put("BUY OFFLINE", () -> {
    });

    buttonPress.put("STRATEGIC BUY", () -> {
    });

    buttonPress.put("BACK TO PORTFOLIO OPTIONS", () -> {
      gv.displayPortfolioSubMenu();
    });*/

    buttonPress.put("SUBMIT BUY", () -> {
      String[] get = gv.getBuyDataComposition();
      System.out.println("INSIDE SUBMIT BUY ");
      try {
        System.out.println("POINT 0 : "+this.currentPort);
        String[] dateSplit = get[0].split("-");
        GregorianCalendar c = new GregorianCalendar(Integer.parseInt(dateSplit[2]),
                Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));
        // STOP ALL OPERATIONS WHILE BUYING
        System.out.println("POINT 0.1 : "+this.currentPort);
        double quantity = Double.parseDouble(get[2]);
        double price;
        System.out.println("POINT 0.2 : "+this.currentPort);
        if(get[3].equalsIgnoreCase("")){
          System.out.println("POINT 0.3 : "+this.currentPort);
          price = 0;
        }
        else {
          System.out.println("POINT 0.4 : "+this.currentPort);
          price = Double.parseDouble(get[3]);
        }
        System.out.println("POINT 0.5 : "+this.currentPort);
        System.out.println("COMM : "+get[4]);
        double commission = Double.parseDouble(get[4]);
        System.out.println("POINT 1 : "+this.currentPort);
        System.out.println(pme.listAllPortfolios());
        System.out.println("POINT 2 : "+this.currentPort);
        if(quantity<0 || price<0){
          throw new IllegalArgumentException();
        }
        try {
          System.out.println("POINT 3 : "+this.currentPort);
          PortfolioCommissionStrategy pmc = (PortfolioCommissionStrategy)pme.getPortfolio(this.currentPort);
          pmc.setCommission(commission);
          System.out.println("POINT 4 : "+this.currentPort);
        }
        catch (ClassCastException a){
          gv.displayMessage("COMISSION NOT SUPPORTED BY CURRENT CLASS, Buying without commission");
        }
        if(price == 0) {
          pme.getPortfolio(this.currentPort.trim()).buyOnline(c, get[1], quantity);
        }
        else{
          System.out.println("INSISDDE PORT : "+this.currentPort);
          pme.getPortfolio(this.currentPort.trim()).buyOnlineWithPrice(c, get[1], quantity,price);
        }
        gv.displayMessage("Buy Successful");
        gv.showBuyOptions();
      }
      catch (Exception a){
        gv.displayMessage("Buy Failure : "+a.getMessage());
        gv.showBuyOptions();
      }
    });

    // CHANGES


    buttonPress.put("BUY OFFLINE",() -> {gv.stockOfflineBuyModule();});

    buttonPress.put("SET DIRECTORY",()->{gv.setDirectory();});

    buttonPress.put("STRATEGIC BUY",() -> {gv.strategiesSub();});

    buttonPress.put("BACK TO PORTFOLIO OPTIONS",() -> {gv.displayPortfolioSubMenu();});

    buttonPress.put("DOLLAR COST AVERAGING",() ->{gv.dcaMainMenu();});

    buttonPress.put("ONE TIME INVESTMENT",()-> {gv.otSplitMainMenu();});

    buttonPress.put("INVEST",()->{gv.otSplitPurchaseBuyModule();});

    buttonPress.put("BACK TO BUY OPTIONS",()->{gv.showBuyOptions();});

    buttonPress.put("PURCHASE DATES",()-> {gv.dcaBuyModule();});

    buttonPress.put("BACK TO DCA INVESTMENT",()->{gv.stockStrategicBuyModule();});

    buttonPress.put("BUY OFFLINE SUBMIT",() -> {
      String[] get = gv.getBuyDataComposition();
      System.out.println("INSIDE SUBMIT BUY ");
      try {
        System.out.println("POINT 0 : "+this.currentPort);
        String[] dateSplit = get[0].split("-");
        GregorianCalendar c = new GregorianCalendar(Integer.parseInt(dateSplit[2]),
                Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));
        // STOP ALL OPERATIONS WHILE BUYING
        System.out.println("POINT 0.1 : "+this.currentPort);
        double quantity = Double.parseDouble(get[2]);
        double price;
        System.out.println("POINT 0.2 : "+this.currentPort);
        if(get[3].equalsIgnoreCase("")){
          System.out.println("POINT 0.3 : "+this.currentPort);
          price = 0;
        }
        else {
          System.out.println("POINT 0.4 : "+this.currentPort);
          price = Double.parseDouble(get[3]);
        }
        System.out.println("POINT 0.5 : "+this.currentPort);
        System.out.println("COMM : "+get[4]);
        double commission = Double.parseDouble(get[4]);
        System.out.println("POINT 1 : "+this.currentPort);
        System.out.println(pme.listAllPortfolios());
        System.out.println("POINT 2 : "+this.currentPort);
        if(quantity<0 || price<0){
          throw new IllegalArgumentException();
        }
        try {
          System.out.println("POINT 3 : "+this.currentPort);
          PortfolioCommissionStrategy pmc = (PortfolioCommissionStrategy)pme.getPortfolio(this.currentPort);
          pmc.setCommission(commission);
          System.out.println("POINT 4 : "+this.currentPort);
        }
        catch (ClassCastException a){
          gv.displayMessage("COMISSION NOT SUPPORTED BY CURRENT CLASS, Buying without commission");
        }
        String directory = get[5]+"/";
        System.out.println("DIR : "+directory);
        //String directory = "/Users/mananpatel/Desktop/PDP/Assignment10/src/offlineData/";
        pme.getPortfolio(this.currentPort.trim()).setOfflineDirectory(directory);
        if(price == 0) {
          pme.getPortfolio(this.currentPort.trim()).buyOffline(c, get[1], quantity);
        }
        else{
          System.out.println("INSISDDE PORT : "+this.currentPort);

          pme.getPortfolio(this.currentPort.trim()).buyOfflineWithPrice(c, get[1], quantity,price);
        }
        gv.displayMessage("Buy Successful");
        gv.showBuyOptions();
      }
      catch (Exception a){
        gv.displayMessage("Buy Failure : "+a.getMessage());
        gv.showBuyOptions();
      }
    });

    buttonPress.put("SAVED STRATEGIES", () ->{
      StrategyAutoSaveRetrieve sas = (StrategyAutoSaveRetrieve) pme.getPortfolio(this.currentPort);
      gv.strategyList(sas.listAllSavedStrategies());});
    buttonPress.put("CREATE STRATEGIES",() -> {gv.stockStrategicBuyModule();});

    buttonPress.put("ONE TIME BUY",() -> {
      try {
        String[] contents = gv.oneTimeInvestData();

        double amt = Double.parseDouble(contents[0]);
        double com = Double.parseDouble(contents[1]);

        String[] dateSplit = contents[2].split("-");
        GregorianCalendar c = new GregorianCalendar(Integer.parseInt(dateSplit[2]),
                Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));


        StrategyAutoSaveRetrieve sas = (StrategyAutoSaveRetrieve) pme.getPortfolio(this.currentPort);

        sas.divideInPortfolio(amt, (GregorianCalendar) c.clone());
        gv.displayMessage("Success");
      }
      catch (Exception a){
        gv.displayMessage(a.getMessage());
      }
    });

    buttonPress.put("DCA BUY",() -> {
      try {
        System.out.println("INSIDE DCA");
        String[] dcaData = gv.dcaBuyData();
        String[] dateSplit = dcaData[0].split("-");
        GregorianCalendar c = new GregorianCalendar(Integer.parseInt(dateSplit[2]),
                Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));

        System.out.println(c.getTime());

        String[] dateSplit1 = dcaData[1].split("-");
        GregorianCalendar c1 = new GregorianCalendar(Integer.parseInt(dateSplit1[2]),
                Integer.parseInt(dateSplit1[0]), Integer.parseInt(dateSplit1[1]));

        System.out.println(c1.getTime());

        double amt = Double.parseDouble(dcaData[2]);

        System.out.println(amt);

        double commission = Double.parseDouble(dcaData[3]);

        System.out.println(commission);

        int freq = Integer.parseInt(dcaData[4]);

        System.out.println(freq);

        StrategyAutoSaveRetrieve sas = (StrategyAutoSaveRetrieve) pme.getPortfolio(this.currentPort);

        sas.setCommission(commission);

        System.out.println("CHECK 1");

        sas.buyLoopDate(amt, c, c1, freq);

        System.out.println("CHECK 2");

        gv.displayMessage("Success");
      }
      catch (Exception a){
        gv.displayMessage(a.getMessage());
      }
    });

    buttonPress.put("CHANGE WEIGHTS OF COMPANIES",()->{

      System.out.println("C WEIGHTS");
      List<Information> toTraverse = pme.getPortfolio(this.currentPort).getComposition(Calendar.getInstance());
      for (Information a :toTraverse) {
        this.addCompanies.add(a.getSymbol());
      }
      List<String> toPass= new ArrayList<>(this.addCompanies);
      gv.changeWeights(toPass);
    });

    buttonPress.put("CHANGE WEIGHT",()-> {
      try {
        List<Information> toTraverse = pme.getPortfolio(this.currentPort).getComposition(Calendar.getInstance());
        for (Information a : toTraverse) {
          this.addCompanies.add(a.getSymbol());
        }
        String[] weights = gv.getCompanyWeights();
        HashMap<String, Double> compWei = new HashMap<>();
        int count = 0;
        for (String k : this.addCompanies) {
          compWei.put(k, Double.parseDouble(weights[count]));
          count++;
        }
        StrategyAutoSaveRetrieve sasr = (StrategyAutoSaveRetrieve) pme.getPortfolio(this.currentPort);
        sasr.setPercentages(new HashMap<>(compWei));
        gv.displayMessage("Success");
      }
      catch (Exception a){
        gv.displayMessage(a.getMessage());
      }
    });


    buttonPress.put("TOTAL INVESTED", () -> {gv.costBasis();
    });

    buttonPress.put("SHOW COST BASIS OF PORTFOLIO",()->{
      //System.out.println("CALLED COST BASIS");
      GregorianCalendar toPass = gv.getTheEnteredDate();
      //System.out.println(toPass.getTime());
      double amt = pme.getPortfolio(this.currentPort).getCostBasis(toPass);
      //System.out.println(amt);
      gv.displayMessage(formatter.format(amt));
    });
      //;} );

    buttonPress.put("TOTAL INVESTMENT VALUATION", () -> {gv.investedValuationAsOfToday();
    });

    buttonPress.put("SHOW STOCK VALUATION",() -> {
              //System.out.println("CALLED COST BASIS");
              GregorianCalendar toPass = gv.getTheEnteredDate();
              //System.out.println(toPass.getTime());
              double amt = pme.getPortfolio(this.currentPort).getTotalValue(toPass);
              //System.out.println(amt);
              gv.displayMessage(formatter.format(amt));});

    buttonPress.put("SHOW PORTFOLIO COMPOSITION",()->{
      List<Object> l = pme.getPortfolio(this.currentPort).getComposition(gv.getTheEnteredDate());
      gv.showComposition(l);});

    buttonPress.put("VIEW", () -> {gv.viewComposition(); });

    buttonPress.put("APPLY STRATEGY",() -> {
      try {
        StrategyAutoSaveRetrieve sas = (StrategyAutoSaveRetrieve) pme.getPortfolio(this.currentPort);
        sas.applyListedStrategyByIndex(gv.getStrategy());
        gv.displayMessage("Success");
      }
      catch (Exception a){
        gv.displayMessage(a.getMessage());
      }
    });

    // END

    ButtonPressListener bpl = new ButtonPressListener();
    bpl.setActions(buttonPress);
    gv.setActionListener(bpl);
  }
}
