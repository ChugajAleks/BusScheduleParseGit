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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Алекс
 */
public class BusScheduleParseDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, ParseException, IOException, Exception {
        FileInputStream fileStream = new FileInputStream("BusSchedule_Kramatorsk.xml");
        InputStream input = fileStream;
        ScheduleParse parse = new BusSchedulerParse();
        //переменная день недели "dayOfWeek" может принимать следующие значения
        //Monday Tuesday Wednesday Thursday Friday Saturday Sunday
        //определяем текущий день недели
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        dayOfWeek = "Thursday";
        List<BusSchedule> listSchedule = parse.unmarshallinhScheduleForOneDay(input, dayOfWeek);
        System.out.println("OUTPUT: " + listSchedule.toString());
        input.close();
        fileStream.close();
    }
    
}
