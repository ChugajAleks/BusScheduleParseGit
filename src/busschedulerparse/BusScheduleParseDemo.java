/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busschedulerparse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Алекс
 */
public class BusScheduleParseDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, ParseException, IOException {
        FileInputStream fileStream = new FileInputStream("BusSchedule_Kramatorsk.xml");
        InputStream input = fileStream;
        BusSchedulerParse parse = new BusSchedulerParse();
        List<BusSchedule> listSchedule = parse.unmarshallinhBusSchedule(input);
        System.out.println("OUTPUT: " + listSchedule.toString());
        input.close();
        fileStream.close();
    }
    
}
