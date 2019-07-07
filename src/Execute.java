import java.io.BufferedReader;
import java.io.InputStreamReader;

import portfoliosystem.controller.ControllerFunctions;
import portfoliosystem.controller.GUIControllerImpl;
import portfoliosystem.model.PCSManager;
import portfoliosystem.model.PortfolioManager;
import portfoliosystem.model.PortfolioManagerStoreImpl;
import portfoliosystem.view.GView;
import portfoliosystem.view.MainFrame;
import portfoliosystem.view.ViewOperations;
import portfoliosystem.view.ViewOperationsImpl;

/**
 * This is the main body of the Portfolio Management System. It initializes the
 * portfoliosystem.model, portfoliosystem.view and the portfoliosystem.controller and the
 * portfoliosystem.controller runs the program from here.
 */
public class Execute {
  /**
   * This is the main class of te application. It creates the objects of the portfoliosystem.model,
   * portfoliosystem.view and the portfoliosystem.controller and given the control of the program to
   * the portfoliosystem.controller.
   */
  public static void main(String[] args) {
    PortfolioManager pm = new PortfolioManagerStoreImpl();
    //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //ViewOperations vo = new ViewOperationsImpl(br, System.out);
    //ControllerFunctions cf = new portfoliosystem.controller.ControllerFunctionsImpl(pm, vo);

    GView gv = new MainFrame();
    ControllerFunctions cf = new GUIControllerImpl(gv,pm);

    cf.startPortfolioManagementSystem();
  }
}
