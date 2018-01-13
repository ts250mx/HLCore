/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

import hl.facturas33.exceptions.HLSolicitudFacturaException;
import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import hl.util.HLFilesFunctions;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Ruben
 */
public class HLSolicitudFactura {

    HLDataConnections Conn;
    String SqlQuery;
    
    protected long IdComprobante;
    protected String UUID;
    protected String XMLInicio;
    protected String XML64;
    protected String TokenSolicitud;
    protected String TokenComprobante;
    protected String TipoSolicitud;
    protected String Proyecto;
    protected int IdCuentaSucursal=0;
    protected int IdSucursal;
    protected int TipoSerie;
    protected long IdFactura;
    protected String CorreoElectronico;
    protected String Receptor;
    protected long IdReceptor;
    protected String XMLFile;
    protected String PDFFile;
    protected String CorreoStatus;
    protected String Error;
    protected int ErrorCode;
    protected String Paso;
    protected String Serie;
    protected String Observaciones;
    protected String MetodoPago;
    protected String FormaPago;
    protected String UsoCFDI;
    protected String FolioFactura;
    protected String FolioFacturaPath;
    protected String WorkPath;
    protected long IdSolicitudFactura;

    public int getIdComputadora() {
        return IdComputadora;
    }

    public void setIdComputadora(int IdComputadora) {
        this.IdComputadora = IdComputadora;
    }

    public String getComputadora() {
        return Computadora;
    }

    public void setComputadora(String Computadora) {
        this.Computadora = Computadora;
    }

    public String getApp() {
        return App;
    }

    public void setApp(String App) {
        this.App = App;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String AppVersion) {
        this.AppVersion = AppVersion;
    }

    public String getUrlSolicitud() {
        return UrlSolicitud;
    }

    public void setUrlSolicitud(String UrlSolicitud) {
        this.UrlSolicitud = UrlSolicitud;
    }
    
    protected int IdComputadora;
    protected String Computadora;
    protected String App;
    protected String AppVersion;
    protected String UrlSolicitud;
    
    

    public long getIdSolicitudFactura() {
        return IdSolicitudFactura;
    }

    public void setIdSolicitudFactura(long IdSolicitudFactura) {
        this.IdSolicitudFactura = IdSolicitudFactura;
    }
    

    public void setWorkPath(String WorkPath) throws IOException {
        
        this.WorkPath = WorkPath;
        HLFilesFunctions.WriteFile(XMLInicio,getPathFolioFactura() + "-XmlStart.xml");
    }
    protected String PDFPath;
    protected String XMLPath;

    public String getFolioFactura() {
        FolioFactura = Serie+IdFactura;
        
        return FolioFactura;
    }

    public String getPathFolioFactura() {
        
        FolioFacturaPath = WorkPath + getFolioFactura();
        return FolioFacturaPath;
    }
    
    


    public String getWorkPath() {
        return WorkPath;
    }

    public String getPDFPath() {
        PDFPath=getPathFolioFactura()+".pdf";
        return PDFPath;
    }


    public String getXMLPath() {
        XMLPath=getPathFolioFactura()+".xml";
        return XMLPath;
    }

    
    

    public String getXML64() {
        return XML64;
    }

    public void setXML64(String XML64) {
        this.XML64 = XML64;
    }

    public String getTokenSolicitud() {
        return TokenSolicitud;
    }

    public void setTokenSolicitud(String TokenSolicitud) {
        this.TokenSolicitud = TokenSolicitud;
    }

    public String getTokenComprobante() {
        return TokenComprobante;
    }

    public void setTokenComprobante(String TokenComprobante) {
        this.TokenComprobante = TokenComprobante;
    }
    
    public void PublicarPaso(String paso) throws SQLException
    {
        System.out.println(paso);
        
        Paso=paso;
        ErrorCode = 0;
        Error="";
        
        SqlQuery = "UPDATE tblSolicitudes SET FechaAct = Now(), ErrorCode = 0, Error = '', Paso = '" + paso + "' WHERE IdSolicitud = " + IdSolicitudFactura;
        Conn.Execute(SqlQuery);
    }
    
    public void PublicarError(String error) throws SQLException
    {
        System.out.println(Error);
        
        Error=error;
        ErrorCode=2;
        
        
        SqlQuery = "UPDATE tblSolicitudes SET FechaAct = Now(), Error = '" + Error.replace("'", "") + "', ErrorCode = 2 WHERE IdSolicitud = " + IdSolicitudFactura;
        Conn.Execute(SqlQuery);
    }
    
    
    public void PublicarAdvertencia(String error) throws SQLException
    {
        System.out.println(Error);
        
        Error = error;
        ErrorCode=1;
        
        
        SqlQuery = "UPDATE tblSolicitudes SET FechaAct = Now(), Error = '" + Error.replace("'", "") + "', ErrorCode = 1 WHERE IdSolicitud = " + IdSolicitudFactura;
        Conn.Execute(SqlQuery);
    }
    
    public void PublicarCorreo(String ToMail, String FromMail, String Body, String Subject, String Respuesta) throws SQLException
    {
        System.out.println(Error);
        
        
        
        SqlQuery = "INSERT INTO tblComprobantesCorreos(IdComprobante, IdSolicitud, ToMail, FromMail, FechaEnvio, Respuesta, Body, Subject) "
                    +"VALUES(" + IdComprobante + "," + IdSolicitudFactura + ",'" + ToMail + "','" + FromMail + "', Now(),'" + Respuesta + "','" + Body + "','" + Subject + "')";
        Conn.Execute(SqlQuery);
    }
    
    public void TerminarProceso() throws SQLException
    {
        System.out.println("Proceso Terminado");
        
        if(ErrorCode!=1)
        {
            Error = "";
            ErrorCode = 0;
        }
        
        
        SqlQuery = "UPDATE tblSolicitudes SET FechaAct = Now(), Error = '', ErrorCode = 0, Paso = 'Proceso Terminado' WHERE IdSolicitud = " + IdSolicitudFactura;
        Conn.Execute(SqlQuery);
    }
    
    public void IniciarSolicitud(HLDataConnections conn, String jsonText) throws SQLException, IOException, HLSolicitudFacturaException
    {
        IniciarSolicitud(conn,0,jsonText);
    }
    public void IniciarSolicitud(HLDataConnections conn, int Intento, String jsonText) throws SQLException, IOException, HLSolicitudFacturaException
    {
        Conn = conn;
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblSolicitudes WHERE TokenSolicitud = '" + TokenSolicitud + "'";
        rs.open(Conn.getConn(), SqlQuery);
        rs.first();
        
        if (rs.next())
        {
            IdSolicitudFactura = rs.getInt("IdSolicitud");
            ErrorCode = rs.getInt("ErrorCode");
            Error = rs.getValue("Error");
            Paso = rs.getValue("Paso");
            
            SqlQuery = "UPDATE tblSolicitudes SET JsonInicio = '" + jsonText + "' WHERE IdSolicitud = " + IdSolicitudFactura;
            Conn.Execute(SqlQuery);
        }
        else
        {
            if(Intento>0) {
                throw new HLSolicitudFacturaException("Demasiados intentos");
            }
            
            Paso = "Inicio de solicitud";
            ErrorCode = 3;
            Error = "Factura sin procesar";
            
            SqlQuery = "INSERT INTO tblSolicitudes(TipoSolicitud, FechaSolicitud, Proyecto, IdFactura, Serie, TipoSerie, IdSucursal, TokenSolicitud, ErrorCode, Error, Paso, FechaAct, JsonInicio, App, AppVersion, IdComputadora, Computadora, UrlSolicitud) "
                    +"VALUES('" + TipoSolicitud + "',Now(),'" + Proyecto + "'," + IdFactura + ",'" + Serie + "'," + TipoSerie + "," + IdSucursal + ",'" + TokenSolicitud + "'," + ErrorCode + ",'" + Error + "','" + Paso + "', Now(),'" + jsonText + "','" + App + "','" + AppVersion + "'," + IdComputadora + ",'" + Computadora + "','" + UrlSolicitud + "')";
            Conn.Execute(SqlQuery);
            IniciarSolicitud(conn, 1, jsonText);
            
            SqlQuery = "REPLACE INTO tblComputadoras (IdComputadora, App, AppVersion, Computadora, FechaAct, IdSucursal) "
                    +"VALUES(" + IdComputadora + ",'" + App + "','" + AppVersion + "','" + Computadora + "', Now()," + IdSucursal + ")";
            Conn.Execute(SqlQuery);
        }
        
        rs.close();
        
        
    }
   

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public long getIdReceptor() {
        return IdReceptor;
    }

    public void setIdReceptor(long IdReceptor) {
        this.IdReceptor = IdReceptor;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    public String getPaso() {
        return Paso;
    }

    public void setPaso(String Paso) {
        this.Paso = Paso;
    }
    
    
    public String getMetodoPago() {
        return MetodoPago;
    }

    public void setMetodoPago(String metodoPagoDesc) {
        this.MetodoPago = metodoPagoDesc;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String formaPagoDesc) {
        this.FormaPago = formaPagoDesc;
    }

    public String getUsoCFDI() {
        return UsoCFDI;
    }

    public void setUsoCFDI(String usoCFDIDesc) {
        this.UsoCFDI = usoCFDIDesc;
    }
    
    
    

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }
    
    

    public int getIdSucursal() {
        return IdSucursal;
    }

    public void setIdSucursal(int IdSucursal) {
        this.IdSucursal = IdSucursal;
    }
    
    public long getIdComprobante() {
        return IdComprobante;
    }

    public void setIdComprobante(long IdComprobante) {
        this.IdComprobante = IdComprobante;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

   

    public String getTipoSolicitud() {
        return TipoSolicitud;
    }

    public void setTipoSolicitud(String TipoSolicitud) {
        this.TipoSolicitud = TipoSolicitud;
    }

    public String getProyecto() {
        return Proyecto;
    }

    public void setProyecto(String Proyecto) {
        this.Proyecto = Proyecto;
    }

    public int getIdCuentaSucursal() {
        return IdCuentaSucursal;
    }

    public void setIdCuentaSucursal(int IdCuentaSucursal) {
        this.IdCuentaSucursal = IdCuentaSucursal;
    }
    
    
    public String getXMLInicio() {
        return XMLInicio;
    }

    public void setXMLInicio(String XMLInicio) throws IOException {
        
        
            
        this.XMLInicio = XMLInicio;
    }

    public int getTipoSerie() {
        return TipoSerie;
    }

    public void setTipoSerie(int TipoSerie) {
        this.TipoSerie = TipoSerie;
    }

    public long getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(long IdFactura) {
        this.IdFactura = IdFactura;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String CorreoElectronico) {
        this.CorreoElectronico = CorreoElectronico;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String Receptor) {
        this.Receptor = Receptor;
    }

    public String getXMLFile() {
        return XMLFile;
    }

    public void setXMLFile(String XMLFile) throws IOException {
        HLFilesFunctions.WriteFile(XMLFile, getPathFolioFactura() +".xml");
        this.XMLFile = XMLFile;
    }

    public String getCorreoStatus() {
        return CorreoStatus;
    }

    public void setCorreoStatus(String CorreoStatus) {
        this.CorreoStatus = CorreoStatus;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        
        this.Serie = Serie;
    }

    public void setPDFFile(String EncodeFileToBase64Binary) {
        PDFFile=EncodeFileToBase64Binary;
    }

    public String getPDFFile() {
        return PDFFile;
    }
    
    
    
}
