package portfoliosystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ButtonPressListener implements ActionListener {
  private Map<String,Runnable> actions;

  public void setActions(Map<String, Runnable> actions) {
    this.actions = actions;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(actions.containsKey(e.getActionCommand())){
      actions.get(e.getActionCommand()).run();
    }
  }
}
