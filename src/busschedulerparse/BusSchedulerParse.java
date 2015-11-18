package busschedulerparse;

import java.awt.Color;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class BusSchedulerParse {

    //private InputStream input;

    public List<BusSchedule> unmarshallinhBusSchedule(InputStream input) throws XMLStreamException, ParseException, Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(input);

        List<BusSchedule> busScheduleList = parseStreamReader(reader);
        reader.close();
        
        return busScheduleList;
    }

    private List<BusSchedule> parseStreamReader(XMLStreamReader reader) throws ParseException, Exception {
        List<BusSchedule> busScheduleList = null;
        BusSchedule busSchedule = null;
        DirectionRouteSchedule directionRouteSchedule = null;
        List<ScheduleItem> scheduleItemList = null;
        ScheduleItem scheduleItem = null;
        List<RoutePoint> routePointList = null;
        RoutePoint routePoint = null;
        //определяем текущий день недели
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        String nameElement;
        String direction;
        String scheduleId = null;
        int event = reader.getEventType();
        while (true) {
            try {
                
                switch (event) {
                    case XMLStreamConstants.START_DOCUMENT:
                        busScheduleList = new ArrayList<BusSchedule>();
                        //достать инфу о том для какого города это расписание 
                        break;
                    case XMLStreamConstants.START_ELEMENT:  
                        nameElement = reader.getName().toString();
                        if (nameElement.equals("bus")) {
                            busSchedule = new BusSchedule();
                            busSchedule.setName(reader.getAttributeValue(0));
                        } 
                        else if (nameElement.equals("color")) {
                            int red = Integer.parseInt(reader.getAttributeValue(0));
                            int green = Integer.parseInt(reader.getAttributeValue(1));
                            int blue = Integer.parseInt(reader.getAttributeValue(2));
                            Color color = new Color(red, green, blue);
                            busSchedule.setColor(color);
                        }
                        else if (nameElement.equals("marker")) {
                            String markerAdress = reader.getAttributeValue(0);
                            busSchedule.setMarkerAdress(markerAdress);
                        }
                        else if (nameElement.equals("dir")) {
                            directionRouteSchedule = new DirectionRouteSchedule();
                            direction = reader.getAttributeValue(0);
                            directionRouteSchedule.setName(direction);
                        }
                        else if (nameElement.equals(dayOfWeek)) {
                            scheduleId = reader.getAttributeValue(0);
                        }
                        else if (nameElement.equals("schedule")) {
                            scheduleItemList = new ArrayList<ScheduleItem>();
                        }
                        else if (nameElement.equals("sequenceId" + scheduleId)) {
                            int id = Integer.parseInt(reader.getAttributeValue(0));
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                            Date startTime = null;
                            startTime = sdf.parse(reader.getAttributeValue(1));
                            long travelTime = Long.parseLong(reader.getAttributeValue(2));
                            scheduleItem = new ScheduleItem(id, startTime, travelTime);
                            
                        }
                        else if (nameElement.equals("routePoint")) {
                            routePointList = new ArrayList<RoutePoint>();
                        }
                        else if (nameElement.equals("point")) {
                            int id = Integer.parseInt(reader.getAttributeValue(0));
                            double lat = Double.parseDouble(reader.getAttributeValue(1));
                            double longnit = Double.parseDouble(reader.getAttributeValue(2));
                            double ratioVel = Double.parseDouble(reader.getAttributeValue(3));
                            String type = reader.getAttributeValue(4);
                            routePoint = new RoutePoint(id, lat, longnit, ratioVel, type);
                            
                        }
                        break;
                    
                    case XMLStreamConstants.END_ELEMENT:
                        nameElement = reader.getName().toString();
                         
                        if (nameElement.equals("bus")) {
                            if(busSchedule.readyAdd())
                                busScheduleList.add(busSchedule);
//                            else
//                                throw new XMLStreamException("Class BusSchedule not ready for add to List");
                        }
                        else if (nameElement.equals("dir")) {
                            if (directionRouteSchedule.readyAdd()) {
                                if (directionRouteSchedule.getName().equals("forward")) {
                                    busSchedule.setForwardDirection(directionRouteSchedule);
                                } else {
                                    busSchedule.setReversDirection(directionRouteSchedule);
                                }
                            }
//                            else
//                                throw new XMLStreamException("Class DirectionRouteSchedule not ready for add to List");
                        }
                        else if (nameElement.equals("schedule")) {
                            if (scheduleItemList.size() > 0)
                                directionRouteSchedule.setSchedule(scheduleItemList);
//                            else
//                                throw new XMLStreamException("size scheduleItemList = 0");
                        }
                        else if (nameElement.equals("sequenceId" + scheduleId)) {
                            if (scheduleItem.readyAdd())
                                scheduleItemList.add(scheduleItem);
//                            else
//                                throw new XMLStreamException("Class ScheduleItem not ready for add to List");
                        }
                        else if (nameElement.equals("routePoint")) {
                            if(routePointList.size() > 0)
                                directionRouteSchedule.setRoute(routePointList);
//                            else
//                                throw new XMLStreamException("size routePointList = 0");
                        }
                        else if (nameElement.equals("point")) {
                            if (routePoint.readyAdd())
                                routePointList.add(routePoint);
//                            else
//                                throw new XMLStreamException("Class RoutePoint not ready for add to List");
                        }
                        break;
               }
                if (!reader.hasNext()) {
                    break;
                }
                event = reader.next();
            } catch (XMLStreamException ex) {
                Logger.getLogger(BusSchedulerParse.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return busScheduleList;
    }

    

}
