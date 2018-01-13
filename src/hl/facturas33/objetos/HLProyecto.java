/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

import hl.facturas33.exceptions.HLProyectoException;
import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import hl.util.HLFilesFunctions;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 *
 * @author Ruben
 */
public class HLProyecto {
    
    HLDataConnections Conn;
    String SqlQuery;
    
    int IdProyecto;
    String Proyecto;
    String NombreComercial;
    String PathProyecto;

    public String getPathProyecto() {
        return PathProyecto;
    }

    public void setPathProyecto(String PathProyecto) {
        this.PathProyecto = PathProyecto;
    }
    
    String ColorPrimario;
    String ColorSecundario;
    
    public HLProyecto() {
    }
    
    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String NombreComercial) {
        this.NombreComercial = NombreComercial;
    }
    
    public HLProyecto(String Proyecto, HLDataConnections conn) throws SQLException, HLProyectoException, UnknownHostException, IOException {
        Conn=conn;
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblProyectos WHERE Proyecto = '" + Proyecto + "'";
        rs.open(Conn.getConn(), SqlQuery);
        rs.first();   
        if(rs.next())
        {
            IdProyecto=rs.getInt("IdProyecto");
            Proyecto = rs.getValue("Proyecto");
            NombreComercial = rs.getValue("NombreComercial");
            ColorPrimario = rs.getValue("ColorPrimario");
            ColorSecundario=rs.getValue("ColorSecundario");
            PathProyecto=HLFilesFunctions.PathExist(rs.getValue("PathProyecto"));
            
        }
        else
        {
            throw new HLProyectoException("No existe Proyecto " + Proyecto);
        }
        rs.close();
    }
    
    public String getColorPrimario() {
        return ColorPrimario;
    }

    public void setColorPrimario(String ColorPrimario) {
        this.ColorPrimario = ColorPrimario;
    }

    public String getColorSecundario() {
        return ColorSecundario;
    }

    public void setColorSecundario(String ColorSecundario) {
        this.ColorSecundario = ColorSecundario;
    }
    

    
    
    public int getIdProyecto() {
        return IdProyecto;
    }

    public void setIdProyecto(int IdProyecto) {
        this.IdProyecto = IdProyecto;
    }

    public String getProyecto() {
        return Proyecto;
    }

    public void setProyecto(String Proyecto) {
        this.Proyecto = Proyecto;
    }
    
    
}
