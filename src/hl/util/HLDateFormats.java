/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Ruben
 */
public class HLDateFormats{
    private static Locale locale = new Locale("ES","MX");
    private static String language = "ES";
    private static String country = "MX";
    private static Date datevalue;
    
    

    public HLDateFormats(Date date) {

        datevalue = date;
    }
    
    public HLDateFormats(String value) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        datevalue = null;
        try {

        datevalue = sdf.parse(value);

        } catch (ParseException ex) {

        ex.printStackTrace();

        }
    }
    
    public String Format(String frmt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat(frmt);
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    public String LongDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("EEEEEEEEE dd MMMM yyyy");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    public String LongDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("EEEEEEEEE dd MMMM yyyy hh:mm:ss");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    public String Time() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    public String HourString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("hh");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    /*public int Hour() {
        return datevalue.getHours(); 
    }
    
    
    public String HourMinutes() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    public String MinutesString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("mm");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    public int Minutes() {
        return datevalue.getMinutes();
    }
    */
    /*public int Day() {
        return datevalue.getDay();
    }
    
    public int Month() {
        return datevalue.getMonth();
    }
    
    public int Year() {
        return datevalue.getYear();
    }
    
    public int WeekDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("E");
        datevalue = cal.getTime();
        return Integer.valueOf(df.format(datevalue)); 
    }
    */
    public String SecondsString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("ss");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    /*public int Seconds() {
        return datevalue.getSeconds();
    }
    
    */
    public String SQLDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    
    public String YYYYMMDD() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        datevalue = cal.getTime();
        return df.format(datevalue); 
    }
    
    
    public String WeekDayName() {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("EEEEEEEEE");
        datevalue = cal.getTime();
        return df.format(datevalue); 
        
        
    }
    
    public String MonthName() {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("MMMM");
        datevalue = cal.getTime();
        return df.format(datevalue); 
        
        
    }
    
    public String MonthShortName() {
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(datevalue);
        SimpleDateFormat df = new SimpleDateFormat("MMM");
        datevalue = cal.getTime();
        return df.format(datevalue); 
        
        
    }
    
}
