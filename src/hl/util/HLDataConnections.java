/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import hl.log.Log;

/**
 *
 * @author Ruben
 */
public class HLDataConnections {

    private String Url;
    Statement Stmt = null;

    public HLDataRecordset getRs() {
        return rs;
    }

    public void setRs(HLDataRecordset rs) {
        this.rs = rs;
    }

    public HLDataRecordset getRsDetail() {
        return rsDetail;
    }

    public void setRsDetail(HLDataRecordset rsDetail) {
        this.rsDetail = rsDetail;
    }

    public String getSqlExecute() {
        return SqlExecute;
    }

    public void setSqlExecute(String SqlExecute) {
        this.SqlExecute = SqlExecute;
    }

    public String getSqlRead() {
        return SqlRead;
    }

    public void setSqlRead(String SqlRead) {
        this.SqlRead = SqlRead;
    }

    
    public HLDataRecordset rs = null;
    public HLDataRecordset rsDetail = null;
    
    private String ProcessPath;

    public String getProcessPath() {
        return ProcessPath;
    }

    public void setProcessPath(String ProcessPath) {
        this.ProcessPath = ProcessPath;
    }
    private String User;
    private String Passwd;
    private String Driver;
    public static Connection Conn = null;
    public static String SqlQuery = "";  
    private String SqlExecute = "";  
    private String SqlRead = "";  
    private String SqlReadDetail = "";  
    private String PropFileName = "config.properties";
    
    long RecordCount = 0;
    long CurrentRecord = 0;

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getUser() {
        return User;
        
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String Passwd) {
        this.Passwd = Passwd;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String Driver) {
        this.Driver = Driver;
    }

    public Connection getConn() {
        return Conn;
    }

    public void setConn(Connection Conn) {
        HLDataConnections.Conn = Conn;
    }
    


    public String getPropFileName() {
        return PropFileName;
    }

    public void setPropFileName(String PropFileName) {
        this.PropFileName = PropFileName;
    }
    
    public void Open()
    {
        rs.open(Conn, SqlQuery);
    }
    
    public void CloseRS()
    {
        rs.close();
    }
    
    public HLDataRecordset OpenRS(String sqlQuery) throws SQLException
    {
        rs = new HLDataRecordset();
        SqlRead=sqlQuery;
        rs.open(Conn, sqlQuery);
        rs.first();
        return rs;
    }
    
    public void OpenRSDetail()
    {
        rsDetail.open(Conn, SqlQuery);
    }
    
    public void CloseRSDetail()
    {
        rsDetail.close();
    }
    
 
    public HLDataConnections() throws FileNotFoundException, IOException, SQLException
    {
        HLFilesFunctions ff = new HLFilesFunctions(PropFileName);
        
        Url = ff.getProperty("Url");
        User = ff.getProperty("User");
        Passwd = ff.getProperty("Passwd");
        Driver = ff.getProperty("Driver");
        
        
        ConnectDatabase();
    }
    
    public HLDataConnections(String propFileName) throws FileNotFoundException, IOException, SQLException
    {
        
        HLFilesFunctions ff = new HLFilesFunctions(propFileName);
        
        Url = ff.getProperty("Url");
        User = ff.getProperty("User");
        Passwd = ff.getProperty("Passwd");
        Driver = ff.getProperty("Driver");
        
        
        ConnectDatabase();
    }
    
    public HLDataConnections(String url, String user, String passwd, String driver) throws FileNotFoundException, IOException, SQLException
    {
        
        Url = url;
        User = user;
        Passwd = passwd;
        Driver = driver;
        
        
        ConnectDatabase();
    }
    
    private void ConnectDatabase() throws SQLException
    {
        Log.println("Driver: " + Driver);
        Log.println("Url: " + Url);
        Log.println("User: " + User);
        Log.println("Password: *****");
        try
        {
            Log.println("Load Driver " + Driver);
            Class.forName(Driver);
        }
        catch(ClassNotFoundException e)
        {
            Log.Error(e);

        }


        Conn = DriverManager.getConnection(Url,User,Passwd);
        Stmt = Conn.createStatement();
        
        Log.OK("Connection " + Url + " is Active");

    }
    
    public void Execute() throws SQLException
    {
        SqlExecute = SqlQuery;
        Execute(SqlExecute);
    }
    
    public void Execute(String sqlExecute) throws SQLException
    {
        SqlQuery = sqlExecute;
        SqlExecute = SqlQuery;
        try
        {
            Stmt.executeUpdate(sqlExecute);  
            Log.OK(sqlExecute);
        }
        catch (SQLException ex)
        {
            Log.Error(sqlExecute);
            Log.Error(ex);
        }
    }
    
    public void CloseConnection() throws SQLException
    {
            Stmt.close();
            Conn.close();
        
    }
 
}
