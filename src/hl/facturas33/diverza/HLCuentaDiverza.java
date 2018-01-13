/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.diverza;

import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import java.sql.SQLException;

/**
 *
 * @author Ruben
 */
public class HLCuentaDiverza {
    
    HLDataConnections Conn;
    String SqlQuery;
    int IdPac;
    String Emisor;
    String RFCEmisor;
    String IdClienteDiverza;
    String Token;
    String SitioFactura;
    String SitioPDF;
    String SitioCancelacion;
    String CorreoRecepcion;

    public int getIdPac() {
        return IdPac;
    }

    public void setIdPac(int IdPac) {
        this.IdPac = IdPac;
    }

    public String getEmisor() {
        return Emisor;
    }

    public void setEmisor(String Emisor) {
        this.Emisor = Emisor;
    }

    public String getRFCEmisor() {
        return RFCEmisor;
    }

    public void setRFCEmisor(String RFCEmisor) {
        this.RFCEmisor = RFCEmisor;
    }

    public String getIdClienteDiverza() {
        return IdClienteDiverza;
    }

    public void setIdClienteDiverza(String IdClienteDiverza) {
        this.IdClienteDiverza = IdClienteDiverza;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getSitioFactura() {
        return SitioFactura;
    }

    public void setSitioFactura(String SitioFactura) {
        this.SitioFactura = SitioFactura;
    }

    public String getSitioPDF() {
        return SitioPDF;
    }

    public void setSitioPDF(String SitioPDF) {
        this.SitioPDF = SitioPDF;
    }

    public String getSitioCancelacion() {
        return SitioCancelacion;
    }

    public void setSitioCancelacion(String SitioCancelacion) {
        this.SitioCancelacion = SitioCancelacion;
    }

    public String getCorreoRecepcion() {
        return CorreoRecepcion;
    }

    public void setCorreoRecepcion(String CorreoRecepcion) {
        this.CorreoRecepcion = CorreoRecepcion;
    }

    public HLCuentaDiverza(String RFC, HLDataConnections conn) throws SQLException {
        Conn=conn;
        
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblPropiedadesPacs WHERE (RFC = 'TODOS' OR RFC = '" + RFC + "') AND IdPac = 1 ORDER BY Propiedad, CASE WHEN RFC = 'TODOS' THEN 1 ELSE 2 END";
        rs.open(Conn.getConn(), SqlQuery);
        rs.first();
        while(rs.next())
        {
            if(rs.getValue("Propiedad").equals("Sitio Factura"))
            {
                SitioFactura=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("Sitio PDF"))
            {
                SitioPDF=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("Sitio Cancelacion"))
            {
                SitioCancelacion=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("Token"))
            {
               Token=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("Id Cliente"))
            {
                IdClienteDiverza=rs.getValue("Valor");
            }


        }

        rs.close();
    }
    
    
}
