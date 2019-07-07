package portfoliosystem.model;

import java.util.ArrayList;
import java.util.List;

public interface StrategyAutoSaveRetrieve extends PortfolioCommissionStrategy {
  List<String> listAllSavedStrategies();

  void applyListedStrategyByIndex(int indexOfStrategyFromList);
}
