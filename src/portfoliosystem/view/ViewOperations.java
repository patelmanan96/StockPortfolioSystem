package portfoliosystem.view;

import java.io.IOException;

/**
 * This is the interface for the portfoliosystem.view. It contains the declarations of the methods
 * to take inputs from the screen and display the appropriate output back to the screen.
 */
public interface ViewOperations {
  /**
   * This method displays the output back to the console.
   *
   * @param k The output from the portfoliosystem.controller that is to be displayed to the
   *          console.
   * @throws IOException When it is not possible to append the output to the Appendable object.
   */
  void displayToConsole(String k) throws IOException;

  /**
   * This method takes the input from the console from the user.
   *
   * @return The data inputted on the console as a String.
   */
  String getDataFromConsole();
}
