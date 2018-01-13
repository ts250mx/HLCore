/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

import hl.facturas33.exceptions.HLEmisorReceptorException;
import hl.facturas33.exceptions.HLProyectoException;
import hl.facturas33.exceptions.HLSucursalException;
import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import hl.util.HLFilesFunctions;
import hl.util.HLFormats;
import hl.util.HLMail;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 *
 * @author Ruben
 */
public class HLSucursal {

    HLDataConnections Conn;
    
    String SqlQuery;
    
    int IdCuentaSucursal;
    String Telefonos;
    String Direccion;
    String CuentaSucursal;   
    String Serie;
    String NoCertificado;
    int IdSucursal;
    int IdSerie;
    String Calle;
    String NumExterior;
    String NumInterior;
    String Colonia;
    String Municipio;
    String Estado;
    String Pais;
    String CodigoPostal;
    String CorreoElectronico;
    int EsMatriz;
    HLEmisorReceptor Emisor;
    HLProyecto Proyecto;
    HLPac PacPrimario;
    HLPac PacSecundario;
    HLMail ServerMailSucursal;
    String WorkPath;
    int IdPacPrimario;
    int IdPacSecundario;
    String RFC;

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public int getIdPacPrimario() {
        return IdPacPrimario;
    }

    public void setIdPacPrimario(int IdPacPrimario) {
        this.IdPacPrimario = IdPacPrimario;
    }

    public int getIdPacSecundario() {
        return IdPacSecundario;
    }

    public void setIdPacSecundario(int IdPacSecundario) {
        this.IdPacSecundario = IdPacSecundario;
    }

    public String getWorkPath() {
        return WorkPath;
    }

    public void setWorkPath(String WorkPath) {
        this.WorkPath = WorkPath;
    }
    

    public HLMail getServerMailSucursal() {
        return ServerMailSucursal;
    }

    public void setServerMailSucursal(HLMail ServerMailSucursal) {
        this.ServerMailSucursal = ServerMailSucursal;
    }
    
    public HLSucursal() {
    
    }
    
    public String getTelefonos() {
        return Telefonos;
    }

    public void setTelefonos(String Telefonos) {
        this.Telefonos = Telefonos;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    public HLProyecto getProyecto() {
        return Proyecto;
    }

    public void setProyecto(HLProyecto Proyecto) {
        this.Proyecto = Proyecto;
    }

    public HLPac getPacPrimario() {
        return PacPrimario;
    }

    public void setPacPrimario(HLPac PacPrimario) {
        this.PacPrimario = PacPrimario;
    }

    public HLPac getPacSecundario() {
        return PacSecundario;
    }

    public HLEmisorReceptor getEmisor() {
        return Emisor;
    }

    public void setEmisor(HLEmisorReceptor Emisor) {
        this.Emisor = Emisor;
    }

    

    
    
    
    
    public int getEsMatriz() {
        return EsMatriz;
    }

    public void setEsMatriz(int EsMatriz) {
        this.EsMatriz = EsMatriz;
    }
    

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int IdSucursal) {
        this.IdSucursal = IdSucursal;
    }
    
    
    public int getIdCuentaSucursal() {
        return IdCuentaSucursal;
    }

    public void setIdCuentaSucursal(int IdCuentaSucursal) {
        this.IdCuentaSucursal = IdCuentaSucursal;
    }

    public String getCuentaSucursal() {
        return CuentaSucursal;
    }

    public void setCuentaSucursal(String CuentaSucursal) {
        this.CuentaSucursal = CuentaSucursal;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        this.Serie = Serie;
    }

    public String getNoCertificado() {
        return NoCertificado;
    }

    public void setNoCertificado(String NoCertificado) {
        this.NoCertificado = NoCertificado;
    }

    public int getIdSerie() {
        return IdSerie;
    }

    public void setIdSerie(int IdSerie) {
        this.IdSerie = IdSerie;
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

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String CorreoElectronico) {
        this.CorreoElectronico = CorreoElectronico;
    }
    
    public HLSucursal(int idSucursal, int tipoSerie, String tipoSolicitud, String proyecto, HLDataConnections conn) throws SQLException, HLSucursalException, HLEmisorReceptorException, HLProyectoException, UnknownHostException, IOException
    {
        
        Conn=conn;
        HLDataRecordset rs = new HLDataRecordset();
        
        Proyecto = new HLProyecto(proyecto, conn);

        SqlQuery = "SELECT A.*, B.SmtpServer, B.SmtpUser, B.SmtpPasswd, B.SmtpPort, B.TLS, B.SSL FROM tblCuentasSucursales A LEFT JOIN tblCorreosSucursales C ON A.IdCuentaSucursal = C.IdCuentaSucursal LEFT JOIN tblCuentasCorreo B ON B.IdCuentaCorreo = C.IdCuentaCorreo WHERE IdSucursal = " + idSucursal + " AND Proyecto = '" + proyecto + "'";
        rs.open(Conn.getConn(), SqlQuery);
        rs.first();   
        if(rs.next())
        {

            IdCuentaSucursal=rs.getInt("IdCuentaSucursal");
            IdSucursal=rs.getInt("IdSucursal");
            CuentaSucursal=rs.getValue("CuentaSucursal");
            Serie=rs.getValue("Serie" + tipoSerie);
            RFC = rs.getValue("RFC");
            Emisor = new HLEmisorReceptor(RFC, conn);
            CodigoPostal=rs.getValue("CodigoPostal");
            IdPacPrimario=rs.getInt("IdPacPrimario");
            IdPacSecundario=rs.getInt("IdPacSecundario");
            if(rs.getInt("EsMatriz")==1)
            {
                Calle=Emisor.getCalle();
                NumExterior=Emisor.getNumExterior();
                NumInterior=Emisor.getNumInterior();
                Colonia=Emisor.getColonia();
                Municipio=Emisor.getMunicipio();
                Estado=Emisor.getEstado();
                Pais=Emisor.getPais();
                CodigoPostal=Emisor.getCodigoPostal();
                Telefonos=Emisor.getTelefonos();
            }
            else
            {
                Calle=rs.getValue("Calle");
                NumExterior=rs.getValue("NumExterior");
                NumInterior=rs.getValue("NumInterior");
                Colonia=rs.getValue("Colonia");
                Municipio=rs.getValue("Municipio");
                Estado=rs.getValue("Estado");
                Pais=rs.getValue("Pais");
                Telefonos=rs.getValue("Telefonos");
            }

            Direccion = Calle + " # " + NumExterior;
            if(!NumInterior.equals("")) {
                Direccion+=" - " + NumInterior;
            }

            Direccion+=" Colonia " + Colonia+ ", CodigoPostal " + CodigoPostal + " " + Municipio + ", " + Estado + ", " + Pais;
            
            CorreoElectronico=rs.getValue("CorreoElectronico");
            NoCertificado=rs.getValue("NoCertificado");
            EsMatriz=rs.getInt("EsMatriz");
            
            ServerMailSucursal = new HLMail();
            ServerMailSucursal.setSmtpServer(rs.getValue("SmtpServer"));
            ServerMailSucursal.setSmtpUser(rs.getValue("SmtpUser"));
            
            ServerMailSucursal.setSmtpPasswd(rs.getValue("SmtpPasswd"));
            ServerMailSucursal.setSmtpPort(rs.getValue("SmtpPort"));
            ServerMailSucursal.setSmtpSSL(rs.getValue("SSL"));
            ServerMailSucursal.setSmtpTLS(rs.getValue("TLS"));
            
            WorkPath = Proyecto.getPathProyecto();
            WorkPath = HLFilesFunctions.PathExist(WorkPath+RFC);
            WorkPath = HLFilesFunctions.PathExist(WorkPath+tipoSolicitud);
            WorkPath = HLFilesFunctions.PathExist(WorkPath+CuentaSucursal);
            WorkPath = HLFilesFunctions.PathExist(WorkPath+HLFormats.DateFormat().YYYYMMDD());

        }
        else
        {
            throw new HLSucursalException("No existe la sucursal " + idSucursal + " registrada en el proyecto " + proyecto);
        }
        rs.close();

       
    }
    
}
