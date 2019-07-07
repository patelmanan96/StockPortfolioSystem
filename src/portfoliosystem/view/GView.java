package portfoliosystem.view;

import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.List;

public interface GView {
  //void displayBasicOptions();

  String getSubmitNameData();

  void currentPort(String portName);

  void setPanel(GView panel);

  void createInputPortNamePanel();

  void setActionListener(ActionListener a);

  void displayMessage(String display);

  void showPortFolioList(List<String> toShow);

  void operateOnPortfolios(List<String> listOfPort);

  String getSelectedPortfolio();

  void displayPortfolioSubMenu();

  void showBuyOptions();

  void showDatePicker();

  void stockBuyModule();

  void stockOfflineBuyModule();

  void setDirectory();

  void stockStrategicBuyModule();

  void dcaMainMenu();

  void dcaBuyModule();

  void otSplitPurchaseBuyModule();

  void otSplitMainMenu();

  void strategiesSub();

  void changeWeights(List<String> tick);

  String[] getBuyDataComposition();

  void investedValuationAsOfToday();

  void viewComposition();

  void costBasis();

  GregorianCalendar getTheEnteredDate();

  void showComposition(List<Object> composition);

  String[] dcaBuyData();

  String[] getCompanyWeights();

  String[] oneTimeInvestData();

  void strategyList(List<String> strats);

  int getStrategy();
}