/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busschedulerparse;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author aleks
 */
public interface ScheduleParse {
    
    public List<BusSchedule> unmarshallinhScheduleForOneDay(InputStream input, String dayOfWeek) throws Exception;
    
}
