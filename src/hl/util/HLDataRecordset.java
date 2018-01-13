/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hl.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;
import hl.log.Log;        //</editor-fold>
;

/**
 *
 * @author Administrador
 */
public class HLDataRecordset {
    Connection Conn = null;
    Statement Stmt = null;
    ResultSet Rs = null;
    ResultSetMetaData Rsmd = null;
    String SqlQuery = "";
    long RecordCount = 0;
    long CurrentRecord = 0;
    
    boolean Active = false;
    boolean First = false;
    
    
    
    public HLDataRecordset() 
    {
        
    }
        
    public void open(Connection Conn, String SqlQuery)
    {
        try
        {
            
            this.Conn = Conn;
            Stmt = this.Conn.createStatement();
            this.SqlQuery = SqlQuery;
            Rs = Stmt.executeQuery(this.SqlQuery);
            Rsmd = Rs.getMetaData(); 

            Log.OK(SqlQuery);
        }
        catch(Exception ex)
        {
            Log.Error(SqlQuery);
            Log.Error(ex);
        }
        

    }

    public ResultSetMetaData getMetaData() throws SQLException
    {
        return Rsmd;
    }
    
    public int getColumnCount() throws SQLException
    {
        return Rsmd.getColumnCount();        
    }

    public String getColumnName(int Field) throws SQLException
    {
        return Rsmd.getColumnName(Field);
    }

    public long getRecordCount()
    {
        try
        {
            int current = Rs.getRow();
            Rs.last();
            int count = Rs.getRow();
            Rs.first();
            Rs.relative(current);

            RecordCount = count;
            return RecordCount;
        }
        catch(SQLException ex)
        {
            return 0;
        }
            
    }
    
    public boolean next()
    {
        try
        {
            if (Rs.getRow() > 0)
            {
                if (First==true)
                {
                    First=false;
                    return true;
                }
                else
                {
                    return Rs.next();
                }
            }
            return false;
        }
        catch (Exception e)
        {
            Log.Error("NEXT " + SqlQuery);
            Log.Error(e);
            return false;
        }
    }
    
    public void close()
    {
        try {
            Stmt.close();
            Stmt = null;
        } catch (SQLException ex) {
           Log.Error(ex);
        }
        
    }
    
    public void first() throws SQLException
    {
        Rs.first();
        First=true;
    }
    
    public Object getObject(String Field) throws SQLException
    {
        return Rs.getObject(Field);
    }

    public Object getObject(int Field) throws SQLException
    {
        return Rs.getObject(Field);
    }

    
    public InputStream getBinaryStream(int Field) throws SQLException
    {
        return Rs.getBinaryStream(Field);
    }
    
    public InputStream getBinaryStream(String Field) throws SQLException
    {
        return Rs.getBinaryStream(Field);
    }
    
    public String getBlob(int Field) throws SQLException
    {
        java.sql.Blob ablob = Rs.getBlob(Field);
        String str = new String(ablob.getBytes(1l, (int) ablob.length()));
        return str;
    }
    
    public String getBlob(String Field) throws SQLException
    {
        java.sql.Blob ablob = Rs.getBlob(Field);
        String str = new String(ablob.getBytes(1l, (int) ablob.length()));
        return str;
    }
    
    
    public int getInt(String Field) throws SQLException
    {
        return Rs.getInt(Field);
    }
    
    public int getInt(int Field) throws SQLException
    {
        return Rs.getInt(Field);
    }
    
    public String getString(String Field) throws SQLException
    {
        return Rs.getString(Field);
    }
    
    public String getString(int Field) throws SQLException
    {
        return Rs.getString(Field);
    }
    
    
    public double getCurrency(String Field) throws SQLException
    {
        return Rs.getDouble(Field);
    }
    
    
    public double getCurrency(int Field) throws SQLException
    {
        return Rs.getDouble(Field);
    }
    
    public HLDateFormats getDateFormat(int Field) throws SQLException
    {
        HLDateFormats hld = new HLDateFormats(getDate(Field));
        return hld;
    }
    
    public HLDateFormats getDateFormat(String Field) throws SQLException
    {
        HLDateFormats hld = new HLDateFormats(getDate(Field));
        return hld;
    }
    
    
    
    public Date getDate(String Field) throws SQLException
    {
        return Rs.getDate(Field);
    }
    
    public Date getDate(int Field) throws SQLException
    {
        return Rs.getDate(Field);
    }
    
    public String getValue(String Field) 
    {
        try
        {
        return Rs.getString(Field);
        }
        catch(SQLException e)
        {
            return "";
        }
        catch(Exception e)
        {
            return "";
        }
    }
    
    public String getValue(int Field)
    {
        try
        {
        return Rs.getString(Field);
        }
        catch(SQLException e)
        {
            return "";
        }
        catch(Exception e)
        {
            return "";
        }
    }
}
