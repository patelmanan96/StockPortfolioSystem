package portfoliosystem.view;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.*;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class MainFrame extends JFrame implements GView {
  private String portName;
  private String currentDir;
  private HashMap<String,Integer> intToStrat;

  UtilDateModel model = new UtilDateModel();
  UtilDateModel model1 = new UtilDateModel();

  Properties p;
  Properties p2;
  JLabel ticker;
  JTextField tickerEntry;
  JLabel quantity;
  JTextField quantityEntry;
  JLabel price;
  JTextField priceEntry;
  JLabel comission;
  JTextField commissionEntry;
  JLabel date;
  JButton submitBuyData;
  JLabel priceOffL;
  JLabel quantityOffL;
  JLabel tickerOffL;
  JLabel commissionOffL;
  JTextField priceOffT;
  JTextField quantityOffT;
  JTextField tickerOffT;
  JTextField commissionOffT;
  JButton submitBuyOffline;
  JButton setDirectory;
  JButton DCAMain;
  JButton OTSplit;
  JButton purchaseStocks;
  JButton changeWeights;
  JLabel strStartDateL;
  JLabel strEndDateL;
  JLabel dcaFreqencyL;
  JTextField dcaFreqencyT;
  JDatePanelImpl datePanel;
  JDatePanelImpl datePanel2;
  JButton companySelect;
  JLabel fileOpenDisplay;
  JButton invest;
  JLabel amountL;
  JTextField amountT;
  JLabel updatedWeightL;
  JTextField updatedWeightT;
  JButton backToOTInvest;
  JLabel warning;
  JButton backToBuy;

  JLabel portNameCurrent;

  JButton retrieve;
  JButton reset;

  JPanel left = new JPanel();
  JPanel right = new JPanel();
  JButton createPort;
  JButton operatePort;
  JButton viewPort;
  JButton exit;

  JButton savedStrategies;
  JButton createStats;

  JList<String> portfolios;

  JComboBox<String> portList;
  JComboBox<String> companyList;
  JButton portFolioSelectButt;

  JButton submitPortName;
  JTextField enterName;

  JButton buy;
  JButton totalInvested;
  JButton totalInvestmentVal;
  JButton view;
  JButton back;

  JButton buyOnline;
  JButton buyOffline;
  JButton strategicBuy;
  JButton backToPortOpts;

  JButton buyOfflineSubmit;

  JButton oneTimeSplit;

  JButton dcaBuy;

  JButton showTotalInvested;
  JButton viewCompositionButton;
  JButton totalValuation;

  JButton savedStrategySubmit;

  //JScrollPane scroll = new JScrollPane();

  public MainFrame() {
    super("PORTFOLIO MANAGEMENT SYSTEM");

    setSize(800, 700);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.setLayout(new GridLayout(0, 2));

    //right = new JPanel();

    left.setSize(200, 400);
    left.setLayout(new GridLayout(6, 1));

    initializeComponents();

    left.add(retrieve);
    left.add(reset);
    left.add(createPort);
    left.add(operatePort);
    left.add(viewPort);
    left.add(exit);
    left.setVisible(true);
    right.setVisible(true);
    this.add(left);

    this.add(right);

    //this.add(right);
    //initializeComponents();

    this.setVisible(true);

    //pack();

    /*for(int i=0;i<Integer.MAX_VALUE;i++){
      System.out.println(i);
    }*/
  }

  private void initializeComponents() {
    retrieve = new JButton("RETRIEVE DATA");
    reset = new JButton("RESET DATA");
    createPort = new JButton("CREATE PORTFOLIO");
    //createPort.addActionListener(this);
    operatePort = new JButton("OPERATE ON PORTFOLIO");
    viewPort = new JButton("VIEW PORTFOLIOS");
    exit = new JButton("EXIT");
    enterName = new JTextField("portName", 20);
    portList = new JComboBox<>();
    portFolioSelectButt = new JButton("CONFIRM SELECTION");
    buy = new JButton("BUY");
    totalInvested = new JButton("TOTAL INVESTED");
    totalInvestmentVal = new JButton("TOTAL INVESTMENT VALUATION");
    view = new JButton("VIEW");
    back = new JButton("BACK");
    buyOnline = new JButton("BUY ONLINE");
    buyOffline = new JButton("BUY OFFLINE");
    strategicBuy = new JButton("STRATEGIC BUY");
    backToPortOpts = new JButton("BACK TO PORTFOLIO OPTIONS");
    // Options For BUY ONLINE
    ticker = new JLabel("Ticker   : ");
    tickerEntry = new JTextField(10);
    quantityEntry = new JTextField(10);
    priceEntry = new JTextField(10);
    commissionEntry = new JTextField(10);
    quantity = new JLabel("Quantity : ");
    price = new JLabel("Price : ");
    comission = new JLabel("Commission : ");
    date = new JLabel("Date : ");
    submitBuyData = new JButton("SUBMIT BUY");

    // Options for BUY OFFLINE
    tickerOffL = new JLabel("Ticker  :");
    tickerOffT = new JTextField(10);
    priceOffL = new JLabel("Price : ");
    priceOffT = new JTextField(10);
    quantityOffL = new JLabel("Quantity : ");
    quantityOffT = new JTextField(10);
    commissionOffL = new JLabel("Commission");
    commissionOffT = new JTextField(10);
    submitBuyOffline = new JButton("BUY OFFLINE");
    setDirectory = new JButton("SET DIRECTORY");
    fileOpenDisplay = new JLabel();
    buyOfflineSubmit = new JButton("BUY OFFLINE SUBMIT");

    // Menu Options for Strategic Buy
    DCAMain = new JButton("DOLLAR COST AVERAGING");
    OTSplit = new JButton("ONE TIME INVESTMENT");
    strStartDateL = new JLabel("PURCHASE START DATE");
    strEndDateL = new JLabel("PURCHASE END DATE");

    //Menu for One Time Investment
    invest = new JButton("INVEST");

    // For One time Investment
    amountL = new JLabel("Amount : ");
    amountT = new JTextField(10);


    // for Changing weights
    companyList = new JComboBox<>();
    companySelect = new JButton("CHANGE WEIGHT");
    updatedWeightL = new JLabel("Updated Weight : ");
    updatedWeightT = new JTextField(10);
    backToOTInvest = new JButton("BACK TO DCA INVESTMENT");
    warning = new JLabel("*****Sum of Weights Should be Equal to 100****");


    p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");

    p2 = new Properties();
    p2.put("text.today", "Today");
    p2.put("text.month", "Month");
    p2.put("text.year", "Year");

    datePanel = new JDatePanelImpl(model, p);
    datePanel2 = new JDatePanelImpl(model1, p2);
    //System.out.println(enterName);
    //this.right.add(enterName);
    //portfolios = new JList<>();
    submitPortName = new JButton("Submit Portfolio Name");

    // main menu for Strategic Buy
    purchaseStocks = new JButton("PURCHASE DATES");
    changeWeights = new JButton("CHANGE WEIGHTS OF COMPANIES");
    backToBuy = new JButton("BACK TO BUY OPTIONS");

    // menu for DCA
    dcaFreqencyL = new JLabel("Frequency : ");
    dcaFreqencyT = new JTextField(10);

    oneTimeSplit = new JButton("ONE TIME BUY");
    dcaBuy = new JButton("DCA BUY");

    savedStrategies = new JButton("SAVED STRATEGIES");
    createStats =  new JButton("CREATE STRATEGIES");

    showTotalInvested = new JButton("SHOW COST BASIS OF PORTFOLIO");
    viewCompositionButton = new JButton("SHOW PORTFOLIO COMPOSITION");
    totalValuation = new JButton("SHOW STOCK VALUATION");

    savedStrategySubmit = new JButton("APPLY STRATEGY");
  }

  @Override
  public String getSubmitNameData() {
    return this.enterName.getText();
  }

  @Override
  public void setPanel(GView panel) {
    //this.left = panel;
  }

  @Override
  public void createInputPortNamePanel() {
    this.right.removeAll();
    //this.right.setLayout();
    this.right.add(enterName);
    this.right.add(submitPortName);
    this.right.setVisible(true);
    this.add(right);
    this.right.revalidate();
    this.right.repaint();
    this.revalidate();
    this.repaint();
  }

  @Override
  public void showPortFolioList(List<String> toShow) {
    String[] arr = new String[toShow.size()];
    int count = 0;
    this.right.removeAll();
    for (String toAdd : toShow) {
      arr[count] = toAdd;
      count++;
    }
    portfolios = new JList<>(arr);
    portfolios.setPreferredSize(new Dimension(400, 400));
    this.right.add(portfolios);
    //portfolios.add(arr);
    //this.right.add(portfolios);
    rightReform();
  }

  private void rightReform() {
    this.right.revalidate();
    this.right.repaint();
    this.revalidate();
    this.repaint();
    //this.getContentPane().remove(scroll);
  }

  @Override
  public void operateOnPortfolios(List<String> listOfPort) {
    this.right.removeAll();
    portList = new JComboBox<>();
    for (String toAdd : listOfPort) {
      portList.addItem(toAdd);
    }
    portList.setPreferredSize(new Dimension(200, 200));
    portFolioSelectButt.setPreferredSize(new Dimension(200, 30));
    this.right.add(portList);
    this.right.add(portFolioSelectButt);
    rightReform();
  }

  @Override
  public String getSelectedPortfolio() {
    return (String) portList.getSelectedItem();
  }

  @Override
  public void displayPortfolioSubMenu() {
    this.right.removeAll();
    //this.right.setLayout(new GridLayout(5,1));
    //this.right.setLayout();

    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(250, 40));
    buy.setPreferredSize(new Dimension(250, 40));
    totalInvested.setPreferredSize(new Dimension(250, 40));
    totalInvestmentVal.setPreferredSize(new Dimension(250, 40));
    view.setPreferredSize(new Dimension(250, 40));
    back.setPreferredSize(new Dimension(250, 40));
    this.right.add(portNameCurrent);
    this.right.add(buy);
    this.right.add(totalInvested);
    this.right.add(totalInvestmentVal);
    this.right.add(view);
    this.right.add(back);
    rightReform();
    //this.right.setLayout(new FlowLayout());
  }

  @Override
  public void showBuyOptions() {
    this.right.removeAll();
    //this.right.setLayout(new GridLayout(5,1));
    //this.right.setLayout();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(250, 40));
    buyOnline.setPreferredSize(new Dimension(250, 40));
    buyOffline.setPreferredSize(new Dimension(250, 40));
    strategicBuy.setPreferredSize(new Dimension(250, 40));
    backToPortOpts.setPreferredSize(new Dimension(250, 40));
    this.right.add(portNameCurrent);
    this.right.add(buyOnline);
    this.right.add(buyOffline);
    this.right.add(strategicBuy);
    this.right.add(backToPortOpts);
    rightReform();
  }


  @Override
  public String[] getBuyDataComposition() {
    String[] toSend = new String[6];
    String month = Integer.toString(datePanel.getModel().getMonth());
    String day = Integer.toString(datePanel.getModel().getDay());
    String year = Integer.toString(datePanel.getModel().getYear());
    toSend[0] = month + "-" + day + "-" + year;
    toSend[1] = tickerEntry.getText();
    toSend[2] = quantityEntry.getText();
    toSend[3] = priceEntry.getText();
    toSend[4] = commissionEntry.getText();
    toSend[5] = this.currentDir;
    return toSend;
  }

  @Override
  public void costBasis() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    date.setPreferredSize(new Dimension(300, 40));
    this.right.add(date);
    this.right.add(datePanel);
    this.right.add(showTotalInvested);
    this.rightReform();
  }

  @Override
  public void viewComposition() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    date.setPreferredSize(new Dimension(300, 40));
    this.right.add(date);
    this.right.add(datePanel);
    this.right.add(viewCompositionButton);
    this.rightReform();
  }

  @Override
  public void investedValuationAsOfToday() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    date.setPreferredSize(new Dimension(300, 40));
    this.right.add(date);
    this.right.add(datePanel);
    this.right.add(totalValuation);
    this.rightReform();
  }

  @Override
  public GregorianCalendar getTheEnteredDate() {
    int month = datePanel.getModel().getMonth();
    int day = datePanel.getModel().getDay();
    int year = datePanel.getModel().getYear();
    GregorianCalendar c = new GregorianCalendar(year,month,day);
    System.out.println(c.getTime());
    return (GregorianCalendar) c.clone();
  }

  @Override
  public void showComposition(List<Object> composition) {
    //this.right.removeAll();
    /*portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);*/
    //String[] arr = new String[composition.size()];
    //int count = 0;
    //this.right.removeAll();
    JTextArea sTextArea = new JTextArea();
    /*JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Scrollable text area"));
    //JTextArea comp = new JTextArea();*/
    //restorePane();
    //JPanel pn= new JPanel();
    //pn.setSize();
    //pn.setVisible(true);
    //pn.setLayout(); //Do not do this, I'm just using this as an example
    //scroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
    sTextArea.setPreferredSize(new Dimension(400,90000));
    //JScrollPane s = new JScrollPane(pn, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    for (Object toAdd : composition) {
      sTextArea.append(toAdd.toString());
      sTextArea.append("\n\n");
    }
    JScrollPane scroll = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    sTextArea.setWrapStyleWord(true);
    scroll.setPreferredSize( new Dimension( 500, 500 ) );
    JOptionPane.showMessageDialog(null, scroll, "Composition",
            JOptionPane.YES_NO_OPTION);
  }

  @Override
  public String[] dcaBuyData() {
    String[] toSend = new String[5];
    String month = Integer.toString(datePanel.getModel().getMonth());
    String day = Integer.toString(datePanel.getModel().getDay());
    String year = Integer.toString(datePanel.getModel().getYear());
    toSend[0] = month + "-" + day + "-" + year;

    String month1 = Integer.toString(datePanel2.getModel().getMonth());
    String day1 = Integer.toString(datePanel2.getModel().getDay());
    String year1 = Integer.toString(datePanel2.getModel().getYear());
    toSend[1] = month1 + "-" + day1 + "-" + year1;

    toSend[2] = amountT.getText();

    toSend[3] = commissionEntry.getText();

    toSend[4] = dcaFreqencyT.getText();

    return toSend;
  }

  @Override
  public String[] getCompanyWeights() {
    return this.enterName.getText().split(",");
  }

  @Override
  public String[] oneTimeInvestData() {
    String[] things = new String[3];
    things[0] = amountT.getText();
    things[1] = commissionEntry.getText();
    String month1 = Integer.toString(datePanel.getModel().getMonth());
    String day1 = Integer.toString(datePanel.getModel().getDay());
    String year1 = Integer.toString(datePanel.getModel().getYear());
    things[2] = month1 + "-" + day1 + "-" + year1;

    return things;
  }

  @Override
  public void strategyList(List<String> strats) {
    this.right.removeAll();
    portList = new JComboBox<>();
    for (String toAdd : strats) {
      portList.addItem(toAdd);
    }
    portList.setPreferredSize(new Dimension(300, 200));
    //portList.getMaximumSize();
    this.right.add(portList);
    savedStrategySubmit.setPreferredSize(new Dimension(200, 30));
    this.right.add(savedStrategySubmit);
    rightReform();
  }

  @Override
  public int getStrategy() {
    return portList.getSelectedIndex();
  }

  @Override
  public void stockBuyModule() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    ticker.setPreferredSize(new Dimension(200, 20));
    this.right.add(ticker);
    tickerEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(tickerEntry);
    quantity.setPreferredSize(new Dimension(200, 20));
    this.right.add(quantity);
    quantityEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(quantityEntry);
    price.setPreferredSize(new Dimension(200, 20));
    this.right.add(price);
    priceEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(priceEntry);
    comission.setPreferredSize(new Dimension(200, 20));
    this.right.add(comission);
    commissionEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(commissionEntry);
    date.setPreferredSize(new Dimension(100, 20));
    this.right.add(date);
    //datePanel.setPreferredSize(new Dimension(300,20));
    this.right.add(datePanel);
    submitBuyData.setPreferredSize(new Dimension(200, 20));
    this.right.add(submitBuyData);
    this.right.add(backToBuy);
    rightReform();

  }

  @Override
  public void stockOfflineBuyModule() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    setDirectory.setPreferredSize(new Dimension(300, 40));
    this.right.add(setDirectory);
    this.right.add(fileOpenDisplay);
    ticker.setPreferredSize(new Dimension(200, 20));
    this.right.add(ticker);
    tickerEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(tickerEntry);
    quantity.setPreferredSize(new Dimension(200, 20));
    this.right.add(quantity);
    quantityEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(quantityEntry);
    price.setPreferredSize(new Dimension(200, 20));
    this.right.add(price);
    priceEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(priceEntry);
    comission.setPreferredSize(new Dimension(200, 20));
    this.right.add(comission);
    commissionEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(commissionEntry);
    date.setPreferredSize(new Dimension(100, 20));
    this.right.add(date);
    //datePanel.setPreferredSize(new Dimension(300,20));
    this.right.add(datePanel);
    submitBuyData.setPreferredSize(new Dimension(200, 20));
    this.right.add(buyOfflineSubmit);
    this.right.add(backToBuy);
    rightReform();

  }

  @Override
  public void setDirectory() {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    chooser.setAcceptAllFileFilterUsed(false);
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      fileOpenDisplay.setText(chooser.getCurrentDirectory().toString());
      // set the directory here in the model.
      this.currentDir = chooser.getCurrentDirectory().toString();
    } else {
      System.out.println("No Selection ");
    }
  }

  @Override
  public void strategiesSub() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    savedStrategies.setPreferredSize(new Dimension(250, 40));
    this.right.add(savedStrategies);
    createStats.setPreferredSize(new Dimension(250, 40));
    this.right.add(createStats);
    rightReform();
  }

  @Override
  public void stockStrategicBuyModule() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    DCAMain.setPreferredSize(new Dimension(250, 40));
    this.right.add(DCAMain);
    OTSplit.setPreferredSize(new Dimension(250, 40));
    this.right.add(OTSplit);
    backToBuy.setPreferredSize(new Dimension(250, 40));
    this.right.add(backToBuy);
    rightReform();
  }

  @Override
  public void dcaBuyModule() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    amountL.setPreferredSize(new Dimension(200, 20));
    this.right.add(amountL);
    amountT.setPreferredSize(new Dimension(300, 20));
    this.right.add(amountT);
    comission.setPreferredSize(new Dimension(200, 20));
    this.right.add(comission);
    commissionEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(commissionEntry);
    this.right.add(strStartDateL);
    this.right.add(datePanel);
    this.right.add(strEndDateL);
    //datePanel.setPreferredSize(new Dimension(300,20));
    this.right.add(datePanel2);
    dcaFreqencyL.setPreferredSize(new Dimension(200, 20));
    this.right.add(dcaFreqencyL);
    dcaFreqencyT.setPreferredSize(new Dimension(300, 20));
    this.right.add(dcaFreqencyT);
    dcaBuy.setPreferredSize(new Dimension(200, 20));
    this.right.add(dcaBuy);
    this.right.add(backToBuy);
    rightReform();
  }

  @Override
  public void otSplitMainMenu() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    invest.setPreferredSize(new Dimension(250, 40));
    this.right.add(invest);
    changeWeights.setPreferredSize(new Dimension(250, 40));
    this.right.add(changeWeights);
    backToBuy.setPreferredSize(new Dimension(250, 40));
    this.right.add(backToBuy);
    rightReform();
  }

  @Override
  public void otSplitPurchaseBuyModule() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    amountL.setPreferredSize(new Dimension(200, 20));
    this.right.add(amountL);
    amountT.setPreferredSize(new Dimension(300, 20));
    this.right.add(amountT);
    comission.setPreferredSize(new Dimension(200, 20));
    this.right.add(comission);
    commissionEntry.setPreferredSize(new Dimension(300, 20));
    this.right.add(commissionEntry);
    date.setPreferredSize(new Dimension(300, 20));
    this.right.add(date);
    this.right.add(datePanel);
    oneTimeSplit.setPreferredSize(new Dimension(200, 20));
    this.right.add(oneTimeSplit);
    this.right.add(backToBuy);
    rightReform();
  }

  @Override
  public void changeWeights(List<String> tick) {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);



    String[] arr = new String[tick.size()];
    int count = 0;
    //this.right.removeAll();
    for (String toAdd : tick) {
      arr[count] = toAdd;
      count++;
    }
    portfolios = new JList<>(arr);
    portfolios.setPreferredSize(new Dimension(400, 400));
    this.right.add(portfolios);





    JLabel fashion = new JLabel("Enter Weights in comma seperated fashion.");
    fashion.setPreferredSize(new Dimension(400, 30));
    this.right.add(fashion);
    enterName.setPreferredSize(new Dimension(400, 30));
    this.right.add(enterName);
    companySelect.setPreferredSize(new Dimension(400, 30));
    this.right.add(companySelect);
    backToOTInvest.setPreferredSize(new Dimension(400, 30));
    this.right.add(backToOTInvest);
    rightReform();
  }

  @Override
  public void dcaMainMenu() {
    this.right.removeAll();
    portNameCurrent = new JLabel(this.portName);
    portNameCurrent.setPreferredSize(new Dimension(300, 40));
    this.right.add(portNameCurrent);
    purchaseStocks.setPreferredSize(new Dimension(200, 40));
    this.right.add(purchaseStocks);
    changeWeights.setPreferredSize(new Dimension(200, 40));
    this.right.add(changeWeights);
    backToBuy.setPreferredSize(new Dimension(200, 40));
    this.right.add(backToBuy);
    rightReform();
  }

  @Override
  public void showDatePicker() {
    this.right.removeAll();
    this.right.add(datePanel);
    rightReform();
  }

  @Override
  public void currentPort(String portName) {
    this.portName = portName + " Portfolio";
  }

  @Override
  public void setActionListener(ActionListener a) {
    createPort.addActionListener(a);
    operatePort.addActionListener(a);
    viewPort.addActionListener(a);
    exit.addActionListener(a);
    submitPortName.addActionListener(a);
    retrieve.addActionListener(a);
    reset.addActionListener(a);
    portList.addActionListener(a);
    portFolioSelectButt.addActionListener(a);
    buy.addActionListener(a);
    totalInvested.addActionListener(a);
    totalInvestmentVal.addActionListener(a);
    view.addActionListener(a);
    back.addActionListener(a);
    buyOnline.addActionListener(a);
    buyOffline.addActionListener(a);
    strategicBuy.addActionListener(a);
    DCAMain.addActionListener(a);
    OTSplit.addActionListener(a);
    invest.addActionListener(a);
    backToPortOpts.addActionListener(a);
    datePanel.addActionListener(a);
    submitBuyData.addActionListener(a);
    purchaseStocks.addActionListener(a);
    changeWeights.addActionListener(a);
    setDirectory.addActionListener(a);
    backToOTInvest.addActionListener(a);
    backToBuy.addActionListener(a);
    buyOfflineSubmit.addActionListener(a);
    oneTimeSplit.addActionListener(a);
    dcaBuy.addActionListener(a);
    savedStrategies.addActionListener(a);
    createStats.addActionListener(a);
    viewCompositionButton.addActionListener(a);
    totalValuation.addActionListener(a);
    showTotalInvested.addActionListener(a);
    companySelect.addActionListener(a);
    savedStrategySubmit.addActionListener(a);
  }

  @Override
  public void displayMessage(String display) {
    JOptionPane.showMessageDialog(this, display);
  }
}