package busschedulerparse;

import java.awt.Color;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class BusSchedulerParse implements ScheduleParse {


    @Override
    public List<BusSchedule> unmarshallinhScheduleForOneDay(InputStream input, String dayOfWeek) throws XMLStreamException, ParseException, Exception {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(input);
        List<BusSchedule> ScheduleList = parseStreamReader(reader, dayOfWeek);
        reader.close();

        return ScheduleList;
    }

    private List<BusSchedule> parseStreamReader(XMLStreamReader reader, String dayOfWeek) throws ParseException, Exception {
        BusSchedule busSchedule = null;
        List<BusSchedule> busScheduleList = null;
        DirectionRouteSchedule directionRouteSchedule = null;
        List<List<ScheduleItem>> scheduleList = null;
        List<RoutePoint> routePointList = null;
        String scheduleId = null;
        String direction = null;
        String nameElement = null;
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
                        } else if (nameElement.equals("color")) {
                            int red = Integer.parseInt(reader.getAttributeValue(0));
                            int green = Integer.parseInt(reader.getAttributeValue(1));
                            int blue = Integer.parseInt(reader.getAttributeValue(2));
                            Color color = new Color(red, green, blue);
                            busSchedule.setColor(color);
                        } else if (nameElement.equals("marker")) {
                            String markerAdress = reader.getAttributeValue(0);
                            busSchedule.setMarkerAdress(markerAdress);
                        } else if (nameElement.equals("dir")) {
                            directionRouteSchedule = new DirectionRouteSchedule();
                            direction = reader.getAttributeValue(0);
                            directionRouteSchedule.setName(direction);
                        } else if (nameElement.equals(dayOfWeek)) {
                            scheduleId = reader.getAttributeValue(0);
                        } else if (nameElement.equals("schedule")) {
                            scheduleList = new ArrayList<List<ScheduleItem>>();
                        } else if (nameElement.equals("scheduleId")) {
                            if (reader.getAttributeValue(0).equals(scheduleId)) {
                                scheduleList.add(createScheduleItemList(reader));
                                scheduleId = null;
                            }
                        } else if (nameElement.equals("routePoint")) {
                            routePointList = new ArrayList<RoutePoint>();
                        } else if (nameElement.equals("point")) {
                            routePointList.add(createRoutePoint(reader));
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        nameElement = reader.getName().toString();
                        if (nameElement.equals("bus")){
                            if (busSchedule.readyAdd()) {
                                    busScheduleList.add(busSchedule);
                                }
//                            else
//                                throw new XMLStreamException("Class BusSchedule not ready for add to List");
                        }
                        else if (nameElement.equals("dir")){
                            if (directionRouteSchedule.readyAdd()) {
                                    if (directionRouteSchedule.getName().equals("forward")) 
                                        busSchedule.setForwardDirection(directionRouteSchedule);
                                    else 
                                        busSchedule.setReversDirection(directionRouteSchedule);    
                                }
//                            else
//                                throw new XMLStreamException("Class DirectionRouteSchedule not ready for add to List");
                        }
                        else if (nameElement.equals("schedule")){
                            if (scheduleList.size() > 0) {
                                    directionRouteSchedule.setSchedule(scheduleList);
                            }
//                            else
//                                throw new XMLStreamException("size scheduleItemList = 0");
                        }
                        else if (nameElement.equals("routePoint")){
                            if (routePointList.size() > 0) {
                                    directionRouteSchedule.setRoute(routePointList);
                                }
//                            else
//                                throw new XMLStreamException("size routePointList = 0");
                        }                        
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

    private RoutePoint createRoutePoint(XMLStreamReader reader) throws XMLStreamException {
        RoutePoint routePoint = null;
        String nameElement = null;
        int event = reader.getEventType();
        while (true) {
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    nameElement = reader.getName().toString();
                    if (event == XMLStreamConstants.START_ELEMENT & nameElement.equals("point")) {
                        int id = Integer.parseInt(reader.getAttributeValue(0));
                        double lat = Double.parseDouble(reader.getAttributeValue(1));
                        double longnit = Double.parseDouble(reader.getAttributeValue(2));
                        double ratioVel = Double.parseDouble(reader.getAttributeValue(3));
                        String type = reader.getAttributeValue(4);
                        routePoint = new RoutePoint(id, lat, longnit, ratioVel, type);
                    }
                case XMLStreamConstants.END_ELEMENT:
                    nameElement = reader.getName().toString();
                    break;
            }
            if (event == XMLStreamConstants.END_ELEMENT & nameElement.equals("point")) 
                break;
            event = reader.next();
            if (!reader.hasNext()) {
                break;
            }
        }
        return routePoint;
    }

    private List<ScheduleItem> createScheduleItemList(XMLStreamReader reader) throws ParseException, NumberFormatException, XMLStreamException {
        ScheduleItem scheduleItem = null;
        List<ScheduleItem> scheduleItemList = new ArrayList<ScheduleItem>();
        String nameElement = null;
        int event = reader.getEventType();
        while (true) {
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    nameElement = reader.getName().toString();
                    if (nameElement.equals("sequenceId")) {
                        int id = Integer.parseInt(reader.getAttributeValue(0));
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        Date startTime = null;
                        startTime = sdf.parse(reader.getAttributeValue(1));
                        long travelTime = Long.parseLong(reader.getAttributeValue(2));
                        scheduleItem = new ScheduleItem(id, startTime, travelTime);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    nameElement = reader.getName().toString();
                    if (nameElement.equals("sequenceId")) {
                        if (scheduleItem.readyAdd()) {
                            scheduleItemList.add(scheduleItem);
                        }
//                            else
//                                throw new XMLStreamException("Class ScheduleItem not ready for add to List");
                    }
                    break;
            }
            if (event == XMLStreamConstants.END_ELEMENT & nameElement.equals("scheduleId")) {
                break;
            }
            event = reader.next();
            if (!reader.hasNext()) {
                break;
            }
        }
        return scheduleItemList;
    }
}
