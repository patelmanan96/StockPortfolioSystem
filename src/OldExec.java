import java.io.BufferedReader;
import java.io.InputStreamReader;

import portfoliosystem.controller.ControllerFunctions;
import portfoliosystem.model.PCSManager;
import portfoliosystem.model.PortfolioManager;
import portfoliosystem.view.ViewOperations;
import portfoliosystem.view.ViewOperationsImpl;

public class OldExec {
  public static void main(String args[]){
    PortfolioManager pm = new PCSManager();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    ViewOperations vo = new ViewOperationsImpl(br, System.out);
    ControllerFunctions cf = new portfoliosystem.controller.ControllerFunctionsImpl(pm, vo);
    cf.startPortfolioManagementSystem();
  }
}
