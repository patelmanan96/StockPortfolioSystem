package portfoliosystem.controller;

import java.io.IOException;

/**
 * This is the interface for the Controller. It contains the method which takes control of the
 * program and calls the different methods of the portfoliosystem.view and the portfoliosystem.model
 * as and when deemed appropriate.
 */
public interface ControllerFunctions {
  /**
   * This method starts the applications by giving the control of the flow of the application to the
   * Controller and calls the methods of the portfoliosystem.model and the portfoliosystem.view of
   * the application as deemed correctly.
   *
   * @throws IOException If not able to take input from portfoliosystem.view or give output to
   *                     portfoliosystem.view.
   */
  void startPortfolioManagementSystem() throws IllegalArgumentException;
}