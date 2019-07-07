package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PortfolioManagerStoreImpl extends PCSManager implements PortfolioManagerExtended {

  // If true reset delete all files and create a new structure. If false use the old structure.
  private File head;
  private boolean oneRetrieve;
  private boolean firstCall = Boolean.TRUE;

  public PortfolioManagerStoreImpl() {
    super();
    oneRetrieve = Boolean.TRUE;
    head = new File("Portfolios");
  }

  private void reset(boolean choice) {
    // Reset the structure
    if (choice) {
      // Checking if the files are there
      deleteStructure(head.getName());
      createStructureHead();
    } else {
      //System.out.println(head.getAbsolutePath());
      loadPortfoliosFromFolder(head.getAbsolutePath());
    }
  }

  private boolean deleteStructure(String name){
    File trav = new File(name);

    try{
      delete(trav);
      return Boolean.TRUE;
    }
    catch (IOException a){
      return Boolean.FALSE;
    }
    /*if (trav.exists()) {
      for (String k : trav.list()) {
        (new File(trav.getPath(), k)).delete();
      }
      if (trav.delete() == Boolean.FALSE) {
        throw new IllegalStateException("Reset Failed");
      }
      return Boolean.TRUE;
    }
    else {
      return Boolean.FALSE;
    }*/
  }

  private void delete(File f) throws IOException {
    if (f.isDirectory()) {
      for (File c : f.listFiles())
        delete(c);
    }
    if (!f.delete())
      throw new FileNotFoundException("Failed to delete file: " + f);
  }

  private void createStructureHead() {
    File port = new File(head.getName());
    if (port.mkdir() == Boolean.FALSE) {
      throw new IllegalStateException("Unable to Create File Structure");
    }
  }

  private void loadPortfoliosFromFolder(String name){
    File toFetchP = new File(name);
    // Get the portfolio folder names
    if(toFetchP.exists()){
      for(String k:toFetchP.list()){
        // Call the constructor of the new class to fetch data from the files, passes the entrire
        // path.
        if(k.equalsIgnoreCase(".DS_Store") ||
                k.equalsIgnoreCase("D_Cache")){
          continue;
        }
        super.portfolios.put(k,new PCSStoreFImpl(new File(toFetchP,k).getPath()));
        super.portfolioIndexing.put(super.index,k);
        super.index++;
      }
    }
    else{
      createStructureHead();
    }
  }

  private boolean putPortfolioIntoFileStructure(String portName){
    try{
      File f = new File(head.getName(),portName);
      if(f.mkdir() == Boolean.FALSE){
        throw new IllegalStateException("Unable to Create the portfolio folder");
      }
      return Boolean.TRUE;
    }
    catch (Exception a){
      return Boolean.FALSE;
    }
  }

  // MAKING THE FIRST CALL TO CREATE PORTFOLIO WILL RESET EVERYTHING.
  @Override
  public void createPortfolio(String name) {
    if(firstCall) {
      reset();
      firstCall = Boolean.FALSE;
      oneRetrieve = Boolean.FALSE;
    }
    if (super.portfolios.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio Already Exists");
    } else {
      putPortfolioIntoFileStructure(name);
      super.portfolioIndexing.put(index, name);
      super.portfolios.put(name, new PCSStoreFImpl(new File(head,name).getPath()));
      index++;
    }
  }

  @Override
  public void retrieve() {
    if(oneRetrieve){
      // RETRIEVE ONCE
      reset(!oneRetrieve);
      oneRetrieve = Boolean.FALSE;
      firstCall = Boolean.FALSE;
    }
  }

  @Override
  public void reset() {
    // MUST CALL RESET BEFORE DOING ANY ADDITION
    if(firstCall) {
      reset(true);
      firstCall = Boolean.FALSE;
      oneRetrieve = Boolean.FALSE;
    }
  }
}
