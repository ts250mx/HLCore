/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

import hl.facturas33.exceptions.HLEmisorReceptorException;
import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Ruben
 */
public class HLEmisorReceptor {
    
    HLDataConnections Conn;
    String SqlQuery;
    
    int IdEmisorReceptor;
    String EmisorReceptor;
    String RegimenFiscal;
    String RFC;
    String Calle;
    String NumExterior;
    String NumInterior;
    String Colonia;
    String Municipio;
    String Estado;
    String Pais;
    String CodigoPostal;
    String Telefonos;

    public String getTelefonos() {
        return Telefonos;
    }

    public void setTelefonos(String Telefonos) {
        this.Telefonos = Telefonos;
    }
    
    int IdRazonSocial;
    String RegimenFiscalDesc;
    HLProyecto Proyecto;
    String Direccion;

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    
    

    public String getRegimenFiscalDesc() {
        return RegimenFiscalDesc;
    }

    public void setRegimenFiscalDesc(String RegimenFiscalDesc) {
        this.RegimenFiscalDesc = RegimenFiscalDesc;
    }
    
    

    
    
    public HLEmisorReceptor() {
        
        
    }
    
    
    
    public HLEmisorReceptor(String rFC, HLDataConnections conn) throws SQLException, HLEmisorReceptorException, IOException
    {

        Conn=conn;
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblEmisoresReceptores WHERE RFC = '" + rFC + "'";
        rs.open(Conn.getConn(), SqlQuery);
        rs.first();   
        if(rs.next())
        {
            IdEmisorReceptor=rs.getInt("IdEmisorReceptor");
            EmisorReceptor = rs.getValue("EmisorReceptor");
            RFC = rFC;
            RegimenFiscal=rs.getValue("RegimenFiscal");
            RegimenFiscalDesc=rs.getValue("RegimenFiscalDesc");
            IdRazonSocial=rs.getInt("IdRazonSocial");
            Calle=rs.getValue("Calle");
            NumExterior=rs.getValue("NumExterior");
            NumInterior=rs.getValue("NumInterior");
            Colonia=rs.getValue("Colonia");
            Municipio=rs.getValue("Municipio");
            Estado=rs.getValue("Estado");
            Pais=rs.getValue("Pais"); 
            CodigoPostal = rs.getValue("CodigoPostal");
            Telefonos = rs.getValue("Telefonos");
            Direccion = Calle + " # " + NumExterior;
            if(!NumInterior.equals("")) {
                Direccion+=" - " + NumInterior;
            }

            Direccion+=" Colonia " + Colonia+ ", CodigoPostal " + CodigoPostal + " " + Municipio + ", " + Estado + ", " + Pais;
            
        }
        else
        {
            throw new HLEmisorReceptorException("No existe Emisor/Receptor " + RFC);
        }
        rs.close();
            
            
            
       
    }
    
    public HLProyecto getProyecto() {
        return Proyecto;
    }

    public void setProyecto(HLProyecto Proyecto) {
        this.Proyecto = Proyecto;
    }
    
    public int getIdEmisorReceptor() {
        return IdEmisorReceptor;
    }

    public void setIdEmisorReceptor(int IdEmisorReceptor) {
        this.IdEmisorReceptor = IdEmisorReceptor;
    }

    public String getEmisorReceptor() {
        return EmisorReceptor;
    }

    public void setEmisorReceptor(String EmisorReceptor) {
        this.EmisorReceptor = EmisorReceptor;
    }

    public String getRegimenFiscal() {
        return RegimenFiscal;
    }

    public void setRegimenFiscal(String RegimenFiscal) {
        this.RegimenFiscal = RegimenFiscal;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumExterior() {
        return NumExterior;
    }

    public void setNumExterior(String NumExterior) {
        this.NumExterior = NumExterior;
    }

    public String getNumInterior() {
        return NumInterior;
    }

    public void setNumInterior(String NumInterior) {
        this.NumInterior = NumInterior;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String Colonia) {
        this.Colonia = Colonia;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String Municipio) {
        this.Municipio = Municipio;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public int getIdRazonSocial() {
        return IdRazonSocial;
    }

    public void setIdRazonSocial(int IdRazonSocial) {
        this.IdRazonSocial = IdRazonSocial;
    }

    
    
}
