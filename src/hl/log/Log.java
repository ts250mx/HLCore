/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.log;

/**
 *
 * @author Ruben
 */
public class Log {

    public Log() {
    }
    

    
    public static void println(String Log)
    {
        System.out.println(Log);
    }
    
    public static void Log(String Log)
    {
        System.out.println(Log);
    }
    public static void Log(long Log)
    {
        System.out.println(Log);
    }
    
    public static void Log(int Log)
    {
        System.out.println(Log);
    }
    
    public static void OK(String Log)
    {
        System.out.println("HL OK!!! " + Log);
    }
    
    public static void Error(String Message)
    {
        System.out.println("HL ERROR!!!! " + Message);
    }
    
    public static void Error(Exception ex)
    {
        System.out.println("HL ERROR!!!! " + ex.getMessage());
        System.out.println(ex.toString());
    }
    
    public static void ErrorTrack(Exception ex)
    {
        Error(ex);
        System.out.println("HL ERROR TRACK!!!! " + ex.toString());
    }

    public static void log(String blob) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    
}
