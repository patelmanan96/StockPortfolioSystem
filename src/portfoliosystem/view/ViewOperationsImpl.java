package portfoliosystem.view;

import java.io.IOException;
import java.util.Scanner;

/**
 * This is the class that implements the interface  ViewOperations. It contains the implementations
 * of the methods to take user input from the console and display the output back to to the
 * console.
 */
public class ViewOperationsImpl implements ViewOperations {
  final Readable rd;
  final Appendable ap;

  /**
   * This is the constructor that constructs the object to the portfoliosystem.view class.
   *
   * @param r Readable object for the input.
   * @param a An appendable object for the output.
   */
  public ViewOperationsImpl(Readable r, Appendable a) {
    this.rd = r;
    this.ap = a;
  }

  @Override
  public void displayToConsole(String k) throws IOException {
    ap.append(k);
  }

  @Override
  public String getDataFromConsole() {
    return new Scanner(this.rd).next();
  }
}
