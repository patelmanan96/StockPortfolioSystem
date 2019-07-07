package portfoliosystem.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// Make an object of this class if you want to check and fetch data from the file structure.
public class PCSStoreFImpl extends PCSImpl implements StrategyAutoSaveRetrieve {

  private Set<String> companiesCached;
  private Set<String> companyDataCached;
  private Map<Integer, Strategy> stratStore;
  private int stratId = 0;

  private File wakeUpDirectory;

  private boolean oneInvoke = Boolean.FALSE;

  // Enter the name of portfolio and specify whether to fetch from the file structure.
  PCSStoreFImpl(String pass) {
    super(new File(pass).getName());
    stratStore = new HashMap<>();
    companiesCached = new HashSet<>();
    companyDataCached = new HashSet<>();
    wakeUpDirectory = new File(pass);
    //System.out.println(wakeUpDirectory.getPath());
    wakeUpIfNeedTo();
  }

  private void wakeUpIfNeedTo() {
    try {
      if (wakeUpDirectory.list().length >= 3) {
        loadDataFromFiles();
      } else if (wakeUpDirectory.list().length == 0) {
        createPortfolioSpecificStructures();
      } else {
        throw new Exception();
      }

    } catch (Exception a) {
      a.printStackTrace();
      throw new IllegalStateException("File Structure Manipulated Externally");
    }
  }

  private void createPortfolioSpecificStructures() {
    new File(wakeUpDirectory, "StockData").mkdir();
    new File(wakeUpDirectory, "Strategies").mkdir();

    File further = new File(wakeUpDirectory, "OtherInfo");
    further.mkdir();

    new File(further, "CompanyInfoCache").mkdir();
    new File(further, "CompanyDataCache").mkdir();
  }

  private void loadDataFromFiles() {
    if (checkConsistencyInPortfolioFolders()) {
      loadStockData();
      loadOtherInfo();
      loadStrategies();
    } else {
      throw new IllegalStateException("File Structure Manipulated Externally");
    }
  }

  private boolean checkConsistencyInPortfolioFolders() {
    List<String> folders = new ArrayList<>();
    folders.add("StockData".toLowerCase());
    folders.add("OtherInfo".toLowerCase());
    folders.add("Strategies".toLowerCase());
    for (String k : wakeUpDirectory.list()) {
      if (k.equalsIgnoreCase(".DS_Store")) {
        continue;
      }
      if (folders.contains(k.toLowerCase()) == Boolean.FALSE) {
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  private void loadStockData() {
    File stock = new File(wakeUpDirectory.getPath(), "StockData");
    for (String stockL : stock.list()) {
      makeInfObjAndAdd(new File(stock, stockL).getPath());
    }
  }

  private void makeInfObjAndAdd(String path) {
    try {
      String[] data = new String[9];
      BufferedReader br = new BufferedReader(new FileReader(path));
      String currentLine;
      int i = 0;
      try {
        while ((currentLine = br.readLine()) != null) {
          data[i] = currentLine.trim();
          i++;
        }
        createInfoObjAndLoad(data);
      } catch (ArrayIndexOutOfBoundsException a) {
        throw new IllegalStateException("File Not Proper");
      }
    } catch (FileNotFoundException a) {
      throw new IllegalStateException("File not Readable");
    } catch (IOException a) {
      throw new IllegalStateException("Unable to read Line");
    }
  }

  private void createInfoObjAndLoad(String[] dataToCreate) {
    HashMap<String, Mode> sm = new HashMap<>();
    sm.put("online", Mode.ONLINE);
    sm.put("offline", Mode.OFFLINE);

    try {
      Information toLoad = new Information();
      toLoad.setCompanyName(dataToCreate[0]);
      toLoad.setCompanyCountry(dataToCreate[1]);
      toLoad.setSymbol(dataToCreate[2]);
      toLoad.setType(dataToCreate[3]);
      toLoad.setPrice(Double.parseDouble(dataToCreate[4]));
      toLoad.setQuantity(Double.parseDouble(dataToCreate[5]));
      toLoad.setCommission(Double.parseDouble(dataToCreate[6]));
      toLoad.setAddedFrom(sm.get(dataToCreate[7].trim().toLowerCase()));
      String[] dateSplit = dataToCreate[8].trim().split("-");
      GregorianCalendar c = new GregorianCalendar(Integer.parseInt(dateSplit[2]),
              Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));
      toLoad.setBuyingDate((GregorianCalendar) c.clone());

      stockAndInfo.add(toLoad);

      addToCompanyRecord(toLoad.getSymbol());
    } catch (Exception a) {
      throw new IllegalStateException("Data in Stocks And Data File Not Proper");
    }
  }

  private void addToCompanyRecord(String sym) {
    super.companyRecord.add(sym);
  }

  private void loadOtherInfo() {
    loadCompanyInfoCache();
    loadCompanyDataCache();
  }

  private void loadCompanyInfoCache() {
    try {
      File compInfo = new
              File(new File(wakeUpDirectory, "OtherInfo").getPath(), "CompanyInfoCache");

      for (String compTicker : compInfo.list()) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bw = new BufferedReader(new FileReader(new File(compInfo, compTicker)));
        String lineC = "";
        while ((lineC = bw.readLine()) != null) {
          String nextLine = bw.readLine();
          if (nextLine == null) {
            sb.append(lineC);
          } else {
            sb.append(lineC + "\n" + nextLine + "\n");
          }
        }
        //System.out.println(compTicker.split("\\.").length);
        //System.out.println(compTicker.split(".")[0].trim());
        // \\. for regex to split file name from filename.txt
        if (compTicker.split("\\.")[0].trim().equalsIgnoreCase("")) {
          continue;
        }
        super.companyInfoCache.put(compTicker.split("\\.")[0].trim(), sb);
        //System.out.println("Data Cache1 Restored");
        companiesCached.add(compTicker);
      }
    } catch (Exception a) {
      throw new IllegalStateException("File Structure not Proper");
    }
  }

  private void loadCompanyDataCache() {
    try {
      File compInfo = new
              File(new File(wakeUpDirectory, "OtherInfo").getPath(), "CompanyDataCache");

      for (String compTicker : compInfo.list()) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bw = new BufferedReader(new FileReader(new File(compInfo, compTicker)));
        String lineC = "";
        while ((lineC = bw.readLine()) != null) {
          String nextLine = bw.readLine();
          if (nextLine == null) {
            sb.append(lineC);
          } else {
            sb.append(lineC + "\n" + nextLine + "\n");
          }
        }
        //System.out.println(sb.toString());
        if (compTicker.split("\\.")[0].trim().equalsIgnoreCase("")) {
          continue;
        }
        // Company name.txt so it considers the name, if manually inputting file provide this
        super.dataCache.put(compTicker.split("\\.")[0].trim(), sb);
        //System.out.println("Data Cache2 Restored");
        companyDataCached.add(compTicker);

      }
    } catch (Exception a) {
      throw new IllegalStateException("File Structure not Proper");
    }
  }

  private void loadStrategies() {
    try {
      File strat = new File(wakeUpDirectory, "Strategies");

      int count = 0;
      for (String strategyFile : strat.list()) {
        int check = Integer.parseInt(strategyFile);
        if(stratStore.containsKey(check) == Boolean.FALSE){
          loadStratFileIntoProgram(new File(strat,strategyFile));
        }
      }
    } catch (Exception a) {
      throw new IllegalStateException("File Structure not Proper");
    }
  }

  private void loadStratFileIntoProgram(File f) {
    HashMap<String, Double> cP = new HashMap<>();
    try {
      BufferedReader bw = new BufferedReader(new FileReader(f));
      String lineC = "";
      // LOAD COMPANY AND THEIR PERCENTS
      String[] compToP = bw.readLine().split(",");
      for (int i = 0; i < compToP.length; i++) {
        String[] compAndP = compToP[i].split("_");
        double temp = Double.parseDouble(compAndP[1]);
        if (temp < 0 || temp > 100) {
          throw new IllegalStateException("File " + f.getName() + " has improper percent data.");
        }
        cP.put(compAndP[0], temp);
      }
      // LOAD AMOUNT
      double amt = Double.parseDouble(bw.readLine());
      if (amt < 0) {
        throw new IllegalStateException("File " + f.getName() + " has improper amount data.");
      }

      // LOAD START DATE
      String[] dateSplit = bw.readLine().trim().split("-");
      GregorianCalendar cStart = new GregorianCalendar(Integer.parseInt(dateSplit[2]),
              Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]));

      // LOAD END DATE
      String end = bw.readLine();
      System.out.println("END READ : "+end);
      GregorianCalendar cEnd;
      if (end.equalsIgnoreCase("null")) {
        cEnd = null;
      } else {
        String[] endD = end.split("-");
        cEnd = new GregorianCalendar(Integer.parseInt(endD[2]),
                Integer.parseInt(endD[0]), Integer.parseInt(endD[1]));
        System.out.println(cEnd.getTime());
      }

      //LOAD RECURRENCE
      String recVal = bw.readLine();
      if (Integer.parseInt(recVal) < 0) {
        throw new IllegalStateException("File " + f.getName() + " has improper recurrence data.");
      }
      int recV = Integer.parseInt(recVal);

      Strategy toC = new Strategy();
      toC.setStartDate((GregorianCalendar)cStart.clone());
      if(cEnd!=null) {
        toC.setEndDate((GregorianCalendar) cEnd.clone());
      }
      else {
        toC.setEndDate(null);
      }
      toC.setToStore(cP);
      toC.setAmount(amt);
      toC.setRecurrence(recV);

      loadStrategyToMap(toC);

    } catch (Exception a) {
      throw new IllegalStateException("File Structure not Proper");
    }
  }

  private void loadStrategyToMap(Strategy st) {
    this.stratStore.put(stratId,st);
    stratId++;
  }

  private void saveStrategy(int ind,Strategy sOb){
    File strat = new File(wakeUpDirectory, "Strategies");
    File fileForThis = new File(strat.getPath(),Integer.toString(ind));
    if(fileForThis.exists()){
      throw new IllegalStateException("File Structure Manipulated for Strategy please Check");
    }
    try {
      fileForThis.createNewFile();
    }
    catch (IOException a){
      throw new IllegalStateException("UNABLE TO SAVE STRATEGY");
    }
    try {
      BufferedWriter bw = new BufferedWriter(new PrintWriter(fileForThis));
      StringBuilder sb = new StringBuilder();
      int count = 1;
      for(String comp:sOb.getToStore().keySet()){
        sb.append(comp+"_"+sOb.getToStore().get(comp));
        if(count != sOb.getToStore().keySet().size()){
          sb.append(",");
        }
        count++;
      }
      sb.append("\n");
      sb.append(sOb.getAmount()+"\n");
      String dateT = sOb.getStartDate().get(Calendar.MONTH) + "-" +
              sOb.getStartDate().get(Calendar.DATE) + "-" + sOb.getStartDate().get(Calendar.YEAR);
      sb.append(dateT+"\n");
      if(sOb.getEndDate() != null) {
        String dateTEnd = sOb.getEndDate().get(Calendar.MONTH) + "-" +
                sOb.getEndDate().get(Calendar.DATE) + "-" + sOb.getEndDate().get(Calendar.YEAR);
        sb.append(dateTEnd + "\n");
      }
      else{
        sb.append("null"+"\n");
      }
      sb.append(sOb.getRecurrence());

      bw.write(sb.toString());
      bw.flush();
      bw.close();
    }
    catch (IOException a){
      throw new IllegalStateException("UNABLE TO WRITE STRATEGY");
    }

  }

  // Develop if the old structres are to be deleted upon manipulation.
  private void deleteOldStructures() {

  }

  private void checkCachingAndAdd(/*String company*/) {
    for (String companiesInfoCache : super.companyInfoCache.keySet()) {
      if (companiesCached.contains(companiesInfoCache) == Boolean.FALSE) {
        cacheCompanyInfo(companiesInfoCache);
        companiesCached.add(companiesInfoCache);
      }
    }
    for (String companiesDataCache : super.dataCache.keySet()) {
      if (companyDataCached.contains(companiesDataCache) == Boolean.FALSE) {
        cacheCompanyData(companiesDataCache);
        companyDataCached.add(companiesDataCache);
      }
    }
  }

  private void cacheCompanyData(String ticker) {
    File dir = new File(new File(wakeUpDirectory, "OtherInfo").getPath(), "CompanyDataCache");
    File dirFileC = new File(dir.getPath(), ticker + ".txt");
    BufferedWriter bw;
    try {
      bw = new BufferedWriter(new PrintWriter(dirFileC));
      bw.write(super.dataCache.get(ticker).toString());
      bw.flush();
      bw.close();
    } catch (IOException a) {
      throw new IllegalStateException("Data Cache Store Failure..");
    }
  }

  private void cacheCompanyInfo(String ticker) {
    File dir = new File(new File(wakeUpDirectory, "OtherInfo").getPath(), "CompanyInfoCache");
    File dirFileC = new File(dir.getPath(), ticker + ".txt");
    BufferedWriter bw;
    try {
      bw = new BufferedWriter(new PrintWriter(dirFileC));
      bw.write(super.companyInfoCache.get(ticker).toString());
      bw.flush();
      bw.close();
    } catch (IOException a) {
      throw new IllegalStateException("Data Cache Store Failure..");
    }
  }

  private String randomAlphaNumeric(int count) {
    final String ams = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int character = (int) (Math.random() * ams.length());
      builder.append(ams.charAt(character));
    }
    return builder.toString();
  }

  private void storeTransactionIntoFile(Information obj) {
    try {
      StringBuilder k = storeFormat(obj);
      String filename;
      while (true) {
        filename = randomAlphaNumeric((5 + (int) (Math.random() * ((10 - 5) + 1)))) + ".txt";
        if (!(checkFileNameConflict(filename,
                new File(wakeUpDirectory, "StockData").getPath()))) {
          break;
        }
      }
      File store = new File(new File(wakeUpDirectory, "StockData").getPath(), filename);

      BufferedWriter bw = new BufferedWriter(new FileWriter(store));

      bw.write(k.toString());
      bw.flush();
      bw.close();
    } catch (Exception a) {
      throw new IllegalStateException("Unable to store stock info to file");
    }
  }

  private boolean checkFileNameConflict(String nameToCheck, String directory) {
    File path = new File(directory);
    for (String files : path.list()) {
      if (files.equalsIgnoreCase(nameToCheck)) {
        return Boolean.TRUE;
      }
    }
    return Boolean.FALSE;
  }

  private StringBuilder storeFormat(Information obj) {
    StringBuilder sb = new StringBuilder();
    sb.append(obj.getCompanyName() + "\n");
    sb.append(obj.getCompanyCountry() + "\n");
    sb.append(obj.getSymbol() + "\n");
    sb.append(obj.getType() + "\n");
    sb.append(obj.getPrice() + "\n");
    sb.append(obj.getQuantity() + "\n");
    sb.append(obj.getCommission() + "\n");
    sb.append(obj.getAddedFrom().toString().toLowerCase() + "\n");
    String dateT = obj.getBuyingDate().get(Calendar.MONTH) + "-" +
            obj.getBuyingDate().get(Calendar.DATE) + "-" + obj.getBuyingDate().get(Calendar.YEAR);
    sb.append(dateT);
    return sb;
  }

  @Override
  public void buyOnline(Calendar a, String companyTicker, double quantity) throws IllegalArgumentException {
    super.buyOnline(a, companyTicker, quantity);
    storeTransactionIntoFile(super.stockAndInfo.get(stockAndInfo.size() - 1));
    checkCachingAndAdd(/*super.stockAndInfo.get(stockAndInfo.size()-1).getSymbol()*/);
  }

  @Override
  public void buyOnlineWithPrice(Calendar a, String companyTicker, double quantity, double price) throws IllegalArgumentException {
    super.buyOnlineWithPrice(a, companyTicker, quantity, price);
    storeTransactionIntoFile(super.stockAndInfo.get(stockAndInfo.size() - 1));
    checkCachingAndAdd(/*super.stockAndInfo.get(stockAndInfo.size()-1).getSymbol()*/);
  }

  @Override
  public void buyOffline(Calendar a, String companyTicker, double quantity) throws IllegalArgumentException {
    super.buyOffline(a, companyTicker, quantity);
    storeTransactionIntoFile(super.stockAndInfo.get(stockAndInfo.size() - 1));
    checkCachingAndAdd(/*super.stockAndInfo.get(stockAndInfo.size()-1).getSymbol()*/);
  }

  @Override
  public void buyOfflineWithPrice(Calendar a, String companyTicker, double quantity, double price) throws IllegalArgumentException {
    super.buyOfflineWithPrice(a, companyTicker, quantity, price);
    storeTransactionIntoFile(super.stockAndInfo.get(stockAndInfo.size() - 1));
    checkCachingAndAdd(/*super.stockAndInfo.get(stockAndInfo.size()-1).getSymbol()*/);
  }

  @Override
  public double getTotalValue(Calendar d) throws IllegalArgumentException {
    double toRet = super.getTotalValue(d);
/*    for(Information stocks:stockAndInfo){
      if(companyDataCached.contains(stocks.getSymbol()) == Boolean.FALSE){
        checkCachingAndAdd(*//*stocks.getSymbol()*//*);
      }
    }*/
    checkCachingAndAdd();
    return toRet;
  }

  @Override
  public void buyLoopDate(double amount, GregorianCalendar start, int days) {
    ArrayList<Information> before = new ArrayList<>(stockAndInfo);
    super.buyLoopDate(amount, (GregorianCalendar) start.clone(), days);
    if(super.companyToPercent.isEmpty() == Boolean.FALSE) {
      Strategy sToPut = new Strategy();
      sToPut.setToStore(super.companyToPercent);
      //sToPut.setPercent(super.percentSet);
      sToPut.setAmount(amount);
      sToPut.setStartDate(start);
      //sToPut.setEndDate(end);
      sToPut.setRecurrence(days);
      saveStrategy(stratId, sToPut);
      System.out.println(sToPut);
      stratStore.put(stratId, sToPut);
      stratId++;
    }
    if(before.size()!=stockAndInfo.size()){
      stockStoreResolve(before);
    }
  }

  private void stockStoreResolve(ArrayList inf){
    for(int i=inf.size()-1;i<super.stockAndInfo.size();i++){
      storeTransactionIntoFile(super.stockAndInfo.get(i));
    }
  }

  @Override
  public void buyLoopDate(double amount, GregorianCalendar start, GregorianCalendar end, int days) {
    ArrayList<Information> before = new ArrayList<>(stockAndInfo);
    super.buyLoopDate(amount, (GregorianCalendar) start.clone(), end, days);
    if(super.companyToPercent.isEmpty() == Boolean.FALSE) {
      Strategy sToPut = new Strategy();
      sToPut.setToStore(super.companyToPercent);
      //sToPut.setPercent(super.percentSet);
      sToPut.setAmount(amount);
      sToPut.setStartDate(start);
      sToPut.setEndDate(end);
      sToPut.setRecurrence(days);
      saveStrategy(stratId, sToPut);
      System.out.println(sToPut);
      stratStore.put(stratId, sToPut);
      stratId++;
    }
    if(before.size()!=stockAndInfo.size()){
      stockStoreResolve(before);
    }
  }

  @Override
  public ArrayList<String> listAllSavedStrategies() {
    ArrayList<String> toRet = new ArrayList<>();
    for (int i = 0; i < stratId; i++) {
      toRet.add(stratStore.get(i).toString());
    }
    return toRet;
  }

  @Override
  public void applyListedStrategyByIndex(int indexOfStrategyFromList) {
    ArrayList<Information> before = new ArrayList<>(stockAndInfo);
    boolean perCentHold = percentSet;
    HashMap<String, Double> compPerHold = new HashMap<>(super.companyToPercent);

    if (stratStore.containsKey(indexOfStrategyFromList)) {
      Strategy fetch = stratStore.get(indexOfStrategyFromList);
      //CHECK IF ALL COMPANIES ARE IN PORTFOLIO FOR WHICH STRATEGY IS SAVED

      HashMap<String, Double> toCheck = (HashMap<String, Double>) fetch.getToStore();
      for (String k : toCheck.keySet()) {
        if (companyRecord.contains(k) == Boolean.FALSE) {
          throw new IllegalArgumentException("This Strategy cannot be applied since," +
                  "company " + k + " is not in the current portfolio");
        }
      }

      if (fetch.getEndDate() == null) {
        super.percentSet = fetch.getPercent();
        super.companyToPercent = fetch.getToStore();
        super.buyLoopDate(fetch.getAmount(), (GregorianCalendar) fetch.getStartDate(), fetch.getRecurrence());
        super.percentSet = perCentHold;
        super.companyToPercent = new HashMap<>(compPerHold);
      } else {
        super.percentSet = fetch.getPercent();
        super.companyToPercent = fetch.getToStore();
        super.buyLoopDate(fetch.getAmount(), (GregorianCalendar) fetch.getStartDate(), (GregorianCalendar) fetch.getEndDate(), fetch.getRecurrence());
        super.percentSet = perCentHold;
        super.companyToPercent = new HashMap<>(compPerHold);
      }
    } else {
      throw new IllegalArgumentException("Incorrect Strategy Index Entry");
    }
    if(before.size()!=stockAndInfo.size()){
      stockStoreResolve(before);
    }
  }
}
