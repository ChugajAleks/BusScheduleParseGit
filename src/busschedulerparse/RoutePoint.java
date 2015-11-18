/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busschedulerparse;

/**
 *
 * @author Алекс
 */
public class RoutePoint {
    private int id;
    private double lat;
    private double longnit;
    private double ratioVel;
    private String type;

    public RoutePoint(int id, double lat, double longnit, double ratioVel, String type) {
        this.id = id;
        this.lat = lat;
        this.longnit = longnit;
        this.ratioVel = ratioVel;
        this.type = type;
    }
    
    public boolean readyAdd(){
        boolean condition = (lat != 0.0) & (longnit != 0.0) & (ratioVel != 0.0) &
                (type != null);
        return condition;
    }
    
    @Override
    public String toString() {
        return "\n\t\tRoutePoint{" + "id=" + id + ", lat=" + lat + ", longnit=" + longnit + ", ratioVel=" + ratioVel + ", type=" + type + '}';
    }

    
    
    
}
