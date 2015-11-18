/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busschedulerparse;

import java.awt.Color;


/**
 *
 * @author Алекс
 */
public class BusSchedule {
    private String name = null;
    private Color color = null;
    private String markerAdress = null;
    private DirectionRouteSchedule forwardDirection = null;
    private DirectionRouteSchedule reversDirection = null; 
    
    public boolean readyAdd(){
        boolean conditionReady = (name != null) & (color != null) & (markerAdress != null) &
                (forwardDirection != null || reversDirection != null);
        return conditionReady;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMarkerAdress() {
        return markerAdress;
    }

    public void setMarkerAdress(String markerAdress) {
        this.markerAdress = markerAdress;
    }

    public DirectionRouteSchedule getForwardDirection() {
        return forwardDirection;
    }

    public void setForwardDirection(DirectionRouteSchedule forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    public DirectionRouteSchedule getReversDirection() {
        return reversDirection;
    }

    public void setReversDirection(DirectionRouteSchedule reversDirection) {
        this.reversDirection = reversDirection;
    }

    @Override
    public String toString() {
        return "\nBusSchedule{" + "\n name=" + name + "\n color=" + color + "\n markerAdress=" + markerAdress + "\n forwardDirection=" + forwardDirection + "\n reversDirection=" + reversDirection + "\n}";
    }
    
    
     
}
