/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busschedulerparse;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Алекс
 */
public class DirectionRouteSchedule {
    private String name;
    private List<ScheduleItem> schedule;
    private List<RoutePoint> route;
    
    public boolean readyAdd(){
        boolean condition = (name != null) & (schedule != null) &
                (route != null);
        return condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScheduleItem> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleItem> schedule) {
        this.schedule = schedule;
    }
    
    public List<RoutePoint> getRoute() {
        return route;
    }

    public void setRoute(List<RoutePoint> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "DirectionRouteSchedule{" + "\n\t name=" + name + "\n\t schedule=" + schedule + "\n\t route=" + route + "\n\t}";
    }
    
    
}
