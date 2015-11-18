/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busschedulerparse;

import java.util.Date;

/**
 *
 * @author Алекс
 */
public class ScheduleItem {
    private int id;
    private Date timeStart;
    private long travelTime;
    
    public ScheduleItem(int id, Date timeStart, long travelTime) {
        this.id = id;
        this.timeStart = timeStart;
        this.travelTime = travelTime;
    }
    
    public boolean readyAdd(){
        boolean condition = (timeStart != null) & (travelTime != 0.0);
        return condition;
    }

    @Override
    public String toString() {
        return "\n\t\tScheduleItem{" + "id=" + id + ", timeStart=" + timeStart + ", travelTime=" + travelTime + '}';
    }
   
    
}
