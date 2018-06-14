package util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtil {

    private static final Logger log = Logger.getLogger(DateUtil.class);

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static XMLGregorianCalendar getDateAsXMLGregorianCalender(Date cDate) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(cDate);
        XMLGregorianCalendar XMLgcTime = null;
        try {
            XMLgcTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            log.error(e);
        }
        return XMLgcTime;
    }


    public static String getCurrentTimeInFormat(String format) {
        return getCurrentTimeInFormat(format, Calendar.SECOND, 10);
    }

    public static String getCurrentTimeInFormat(String format, int field, int delay) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar c1 = Calendar.getInstance();
        c1.add(field, delay);
        return formatter.format(c1.getTime());
    }

    public static Date getCurrentTimeAsDateInFormat(String format, int field, int delay) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String date1 = getCurrentTimeInFormat(format, field, delay);
        try {
            date = formatter.parse(date1);
        } catch (ParseException e) {
            log.error(e);
        }

        return date;
    }
}
