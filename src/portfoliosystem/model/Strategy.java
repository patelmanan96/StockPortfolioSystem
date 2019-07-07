package portfoliosystem.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Strategy {
  private HashMap<String,Double> toStore;
  private boolean percent = Boolean.TRUE;
  private double amount;
  private GregorianCalendar startDate;
  private GregorianCalendar endDate=null;
  private int recurrence;
  Strategy(){
    toStore = new HashMap<>();
    //percent = Boolean.FALSE;
  }

  public void setToStore(HashMap<String, Double> toStore) {
    this.toStore = toStore;
  }

  public HashMap<String, Double> getToStore() {
    return toStore;
  }

  public boolean getPercent() {
    return percent;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getAmount() {
    return amount;
  }

  public void setStartDate(GregorianCalendar startDate) {
    this.startDate = startDate;
  }

  public GregorianCalendar getStartDate() {
    return startDate;
  }

  public void setEndDate(GregorianCalendar endDate) {
    this.endDate = endDate;
  }

  public GregorianCalendar getEndDate() {
/*    if(endDate==null){
      return Calendar.getInstance();
    }*/
    return endDate;
  }

  public void setRecurrence(int recurrence) {
    this.recurrence = recurrence;
  }

  public int getRecurrence() {
    return recurrence;
  }

  @Override
  public String toString() {
    StringBuilder statString = new StringBuilder();
    statString.append("Companies & Their Weights : "+"\n");
    for(String comp:toStore.keySet()){
      statString.append(comp+" : "+String.format("%.2f",toStore.get(comp))+"%");
      statString.append("\n");
    }
    statString.append("Start Date : "+startDate.getTime()+"\n");
    if(endDate != null){
      statString.append("End Date : "+endDate.getTime()+"\n");
    }
    statString.append("Recurring Period : "+recurrence);
    return statString.toString();
  }
}
