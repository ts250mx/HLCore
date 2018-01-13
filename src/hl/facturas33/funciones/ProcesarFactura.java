/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.funciones;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import hl.facturas33.diverza.DiverzaOK;
import hl.facturas33.diverza.HLCuentaDiverza;
import hl.facturas33.exceptions.DiverzaException;
import hl.facturas33.exceptions.HLSolicitudFacturaException;
import hl.facturas33.facturadigital.HLCuentaFacturaDigital;
import hl.facturas33.objetos.Comprobante;
import hl.facturas33.objetos.HLEmisorReceptor;
import hl.facturas33.objetos.HLRespuestaFactura;
import hl.facturas33.objetos.HLSolicitudFactura;
import hl.facturas33.objetos.HLSucursal;
import hl.facturas33.objetos.TimbreFiscalDigital;
import hl.log.Log;
import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import hl.util.HLFilesFunctions;
import hl.util.HLFormats;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
/**
 *
 * @author Ruben
 */
public class ProcesarFactura {

    String SqlQuery;
    String PropFile;
    
    String FolioFactura;
    String Url;
    String User;
    String Passwd;
    String Driver;
    
    int IdPac=0;
    HLDataConnections Conn;
    HLSucursal cs;
    
    HLEmisorReceptor rc = new HLEmisorReceptor();
    
    HLCuentaDiverza cd;
    HLCuentaFacturaDigital cf;
    ComprobanteParser cxml = new ComprobanteParser();;
    
    String SolicitudFacturaJson;

    public String getSolicitudFacturaJson() {
        Gson json = new Gson();
        
        HLRespuestaFactura rf = new HLRespuestaFactura();
        
        rf.setIdFactura(sf.getIdFactura());
        rf.setError(sf.getError());
        rf.setErrorCode(sf.getErrorCode());
        rf.setXML64(sf.getXML64());
        rf.setSerie(sf.getSerie());
        rf.setUUID(sf.getUUID());
        rf.setTipoSerie(sf.getTipoSerie());
        rf.setPDFFile(sf.getPDFFile());
        
        return json.toJson(rf);
    }

    public HLSolicitudFactura getSf() {
        return sf;
    }

    public void setSf(HLSolicitudFactura sf) {
        this.sf = sf;
    }
    
    
    HLSolicitudFactura sf;
    
    
    public ProcesarFactura(String PropFile) {
        try {
            
            String hostName= java.net.InetAddress.getLocalHost().getHostAddress(); 

            //nombre 



            Conn = new HLDataConnections(PropFile);
            
            Url = Conn.getUrl();
            User = Conn.getUser();
            Passwd = Conn.getPasswd();
            Driver = Conn.getDriver();
            
            
        } catch (Exception ex) {
           System.out.println(ex);
        }

        
    }
    
    
    
    private void VerificarSolicitudFactura(String jsonText) throws IOException, SQLException, HLSolicitudFacturaException, HLSolicitudFacturaException
    {

        try
        {
            
            Gson g = new Gson();
            sf = g.fromJson(jsonText, HLSolicitudFactura.class);
            
            
            sf.setXMLInicio(HLFormats.getBase64Decode(sf.getXML64()));

            sf.IniciarSolicitud(Conn, jsonText);
        }
        catch(SQLException | JsonSyntaxException | UnsupportedEncodingException ex)
        {
            Log.Error(ex);
        }
}
    
    public void EjecutarFactura(String jsonText, boolean EsTest)
    {
        

        try{
            VerificarSolicitudFactura(jsonText);
            sf.PublicarPaso("Validando cuenta de acceso con correo");
            cs = new HLSucursal(sf.getIdSucursal(), sf.getTipoSerie(), "facturas", sf.getProyecto(), Conn);
  
            sf.setSerie(cs.getSerie());
            
            sf.setWorkPath(cs.getWorkPath());
            
            cs.getServerMailSucursal().addAttachment(sf.getPDFPath());
            cs.getServerMailSucursal().addAttachment(sf.getXMLPath());
            cs.getServerMailSucursal().setToMail(sf.getCorreoElectronico());
            
            if(ComprobanteTimbrado())
            {
                sf.PublicarPaso("Comprobante timbrado previamente");
                EnviarComprobanteCorreo();
                sf.TerminarProceso();
                return;
            }
            
            
            
            
            sf.PublicarPaso("Lectura de XML de entrada");
            cxml.LeerXmlInicial(sf.getPathFolioFactura() + "-XmlStart.xml");
            
            cxml.setPathLogo(cs.getProyecto().getPathProyecto() + "logo.png");
            cxml.setColorPrimario(cs.getProyecto().getColorPrimario());
            cxml.setColorSecundario(cs.getProyecto().getColorSecundario());
            cxml.setObservaciones(sf.getObservaciones());
            cxml.setCasoUso(sf.getUsoCFDI());
            cxml.setMetodoPago(sf.getMetodoPago());
            cxml.setFormaPago(sf.getFormaPago());
            cxml.setSucursal(cs.getCuentaSucursal());
            cxml.setCorreoElectronico(cs.getCorreoElectronico());
            cxml.setDireccion(cs.getDireccion());
            cxml.setTelefonos(cs.getTelefonos());
            cxml.setRegimenFiscalDesc(cs.getEmisor().getRegimenFiscalDesc());
            
            sf.PublicarPaso("Creacion de objeto comprobante");
            Comprobante comprobante = cxml.getComprobante();

            sf.PublicarPaso("Enlazando objeto emisor");
            Comprobante.Emisor emisor = new Comprobante.Emisor();
            emisor.setNombre(cs.getEmisor().getEmisorReceptor());
            emisor.setRfc(cs.getRFC());
            emisor.setRegimenFiscal(cs.getEmisor().getRegimenFiscal());
            
            comprobante.setSerie(cs.getSerie());
            comprobante.setLugarExpedicion(cs.getCodigoPostal());
            comprobante.setNoCertificado(cs.getNoCertificado());
            comprobante.setEmisor(emisor);
            
            GuardarComprobante(comprobante);
            
            
            sf.PublicarPaso("Accesando pacs");
            if(cs.getIdPacPrimario()==1)
            {
                cd = new HLCuentaDiverza(cs.getRFC(), Conn);
                EjecutarPacDiverza();
            }

            if(cs.getIdPacPrimario()==2)
            {
                cf= new HLCuentaFacturaDigital(cs.getRFC(), Conn);
                cf.EjecutarPac(sf, comprobante);
            }
            
            
            sf.PublicarPaso("Relacionando XML timbrado Diverza con objeto comprobante");
            cxml.LeerXml(sf.getPathFolioFactura() +".xml");
            
            comprobante = cxml.getComprobante();
            GuardarComprobante(comprobante);
            
            
            sf.PublicarPaso("Relacionando XML timbrado Diverza con objeto timbre");
            TimbreFiscalDigital timbre = cxml.getTimbre();
            GuardarTimbre(timbre);
            sf.setUUID(timbre.getUUID());
            
            sf.PublicarPaso("Creando codigo bidimensional");
            String QRText="?re=" + comprobante.getEmisor().getRfc() + "&rr=" + comprobante.getReceptor().getRfc() + "&tt=" + String.valueOf(comprobante.getTotal()).toString() + "&id=" + timbre.getUUID();
            
            HLFilesFunctions.QRToFile(QRText, sf.getPathFolioFactura() +"-QR.png");
            
            
            cxml.setPathQR(sf.getPathFolioFactura() +"-QR.png");
            //HLFilesFunctions.WriteFile(cxml.CrearHTML(comprobante, timbre), "c:\\fuentes\\kyk\\kykserver2\\" + diverzaOK.getUuid() +".htm");
            sf.PublicarPaso("Escribiendo HTML");
            
            HLFilesFunctions.WriteFileXHTML(cxml.CrearHTML(comprobante, timbre), sf.getPathFolioFactura() +".html");
            //HLFilesFunctions.WriteFile(cxml.CrearHTML(comprobante, timbre), WorkPath + FolioFactura + ".html");
            sf.PublicarPaso("Convirtiendo HTML a PDF");
            HLFilesFunctions.HTMLToPDF(sf.getPathFolioFactura() + ".html",  sf.getPathFolioFactura() + ".pdf");
            
            sf.PublicarPaso("Convirtiendo PDF a Base64");
            sf.setPDFFile(HLFilesFunctions.EncodeFileToBase64Binary(sf.getPathFolioFactura() + ".pdf"));
            
            GuardarXMLPDF();
            
            EnviarComprobanteCorreo();
            
            
            sf.TerminarProceso();
            
        }
        catch(Exception ex)
        {
            try {
                sf.PublicarError(ex.toString());
            } catch (SQLException ex1) {
                Logger.getLogger(ProcesarFactura.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            
        }
        try {
            Conn.CloseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ProcesarFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void EnviarComprobanteCorreo() throws SQLException
    {
        
        if(!sf.getCorreoElectronico().trim().equals(""))
        {
        try
            {
                
                cs.getServerMailSucursal().setSubject("Factura de " + sf.getFolioFactura() + " de " + cs.getProyecto().getNombreComercial());
                cs.getServerMailSucursal().setBody("Buen dia, se anexa factura # " + sf.getFolioFactura() + " de " + cs.getProyecto().getNombreComercial());
                cs.getServerMailSucursal().SendMail();
                
                sf.PublicarCorreo(sf.getCorreoElectronico(), cs.getServerMailSucursal().getFrom(), cs.getServerMailSucursal().getBody(), cs.getServerMailSucursal().getSubject(), "OK");
                
            } catch (MessagingException | SQLException ex) {
                
                sf.PublicarAdvertencia(ex.getMessage());
                sf.PublicarCorreo(sf.getCorreoElectronico(), cs.getServerMailSucursal().getFrom(), cs.getServerMailSucursal().getBody(), cs.getServerMailSucursal().getSubject(), ex.getMessage());
          
            }
        }
            
    }
    
    private void GuardarXMLPDF() throws SQLException
    {

        SqlQuery = "UPDATE tblComprobantes SET FechaAct = Now(), Timbrada = 1, PDF = '" + sf.getPDFFile() + "', XML = '" + sf.getXML64() + "' WHERE Folio = " + sf.getIdFactura() + " AND Serie = '" + sf.getSerie() + "' AND IdCuentaSucursal = " + cs.getIdCuentaSucursal();
        Conn.Execute(SqlQuery);
        
        
    }
    
    private void GuardarComprobante(Comprobante comprobante) throws SQLException
    {
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblComprobantes WHERE TokenComprobante = '" + sf.getTokenComprobante() + "'";
        rs.open(Conn.getConn(), SqlQuery);
        
        rs.first();
        if(!rs.next())
        {
            SqlQuery = "INSERT INTO tblComprobantes(IdCuentaSucursal, IdSucursal, Proyecto, TokenComprobante, FechaAct, Version, Serie, Folio, Fecha, Sello, FormaPago, NoCertificado, Certificado, CondicionesPago, Subtotal, Descuento, Moneda, TipoCambio, TipoComprobante, MetodoPago, LugarExpedicion, CorreoEnviar) "
                       +"VALUES(" + cs.getIdCuentaSucursal() + "," + sf.getIdSucursal() + ",'" + sf.getProyecto() + "','" + sf.getTokenComprobante() + "', Now(),'" + comprobante.getVersion() + "','" + comprobante.getSerie() + "','" + comprobante.getFolio() + "','" + comprobante.getFecha() + "','" + comprobante.getSello() + "','" + comprobante.getFormaPago() + "','" + comprobante.getNoCertificado() + "','" + comprobante.getCertificado() + "','" + comprobante.getCondicionesDePago() + "'," + comprobante.getSubTotal() + "," + HLFormats.ValidaNulos(comprobante.getDescuento())  + ",'" + comprobante.getMoneda() + "'," + HLFormats.ValidaNulos(comprobante.getTipoCambio()) + ",'" + comprobante.getTipoDeComprobante() + "','" + comprobante.getMetodoPago() + "','" + comprobante.getLugarExpedicion() + "','" + sf.getCorreoElectronico() + "')";
            Conn.Execute(SqlQuery);
        }
        else
        {
            SqlQuery = "UPDATE tblComprobantes SET FechaAct = Now(), Version = '" + comprobante.getVersion() + "', Serie = '" + comprobante.getSerie() + "', Folio = '" + comprobante.getFolio() + "', Fecha = '" + comprobante.getFecha() + "', "
                        +"Sello = '" + comprobante.getSello() + "', FormaPago = '" + comprobante.getFormaPago() + "', NoCertificado = '" + comprobante.getNoCertificado() + "', Certificado = '" + comprobante.getCertificado() + "', "
                        +"CondicionesPago = '" + comprobante.getCondicionesDePago() + "', Subtotal = " + comprobante.getSubTotal() + ", Descuento = " + HLFormats.ValidaNulos(comprobante.getDescuento()) + ", Moneda = '" + comprobante.getMoneda() + "', "
                        +"TipoCambio = " + HLFormats.ValidaNulos(comprobante.getTipoCambio()) + ", TipoComprobante = '" + comprobante.getTipoDeComprobante() + "', MetodoPago = '" + comprobante.getMetodoPago() + "', LugarExpedicion = '" + comprobante.getLugarExpedicion() + "'," 
                        +"RFCEmisor = '" + comprobante.getEmisor().getRfc() + "', Emisor = '" + comprobante.getEmisor().getRfc() + "', RegimenFiscal = '" + comprobante.getEmisor().getRegimenFiscal() + "', RFCReceptor = '" + comprobante.getReceptor().getRfc() + "', "
                        +"Receptor = '" + comprobante.getReceptor().getNombre() + "', UsoCFDI = '" + comprobante.getReceptor().getUsoCFDI() + "', CorreoEnviar = '" + sf.getCorreoElectronico() + "', IdPac = " + IdPac + " "
                        +"WHERE TokenComprobante = '" + sf.getTokenComprobante() + "'";
            Conn.Execute(SqlQuery);
        }
        
        rs.close();
        
    }
    
    
    private boolean ComprobanteTimbrado()
    {
        try
        {
            HLDataRecordset rs = new HLDataRecordset();
            SqlQuery = "SELECT * FROM tblComprobantes WHERE Timbrada = 1 AND Folio = " + sf.getIdFactura() + " AND Serie = '" + sf.getSerie() + "' AND IdCuentaSucursal = " + cs.getIdCuentaSucursal();
            rs.open(Conn.getConn(), SqlQuery);

            rs.first();
            if(!rs.next())
            {
                rs.close();
                return false;
            }
            else
            {
                sf.setIdComprobante(rs.getInt("IdComprobante"));
                sf.setPDFFile(rs.getValue("PDF"));
                sf.setXML64(rs.getValue("XML"));
                sf.setUUID(rs.getValue("UUID"));

            }

            rs.close();
            
            return true;
        }
        catch(Exception ex)
        {
            Log.Error(ex);
            return false;
        }
        
    }
    
    
    /*
    private void GuardarCorreos() throws SQLException
    {
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblComprobantes WHERE TokenComprobante = '" + sf.getTokenComprobante() + "'";
        rs.open(Conn.getConn(), SqlQuery);
        
        rs.first();
        if(!rs.next())
        {
            SqlQuery = "INSERT INTO tblComprobantes(IdCuentaSucursal, IdSucursal, Proyecto, TokenComprobante, FechaAct, Version, Serie, Folio, Fecha, Sello, FormaPago, NoCertificado, Certificado, CondicionesPago, Subtotal, Descuento, Moneda, TipoCambio, TipoComprobante, MetodoPago, LugarExpedicion, CorreoEnviar) "
                       +"VALUES(" + cs.getIdCuentaSucursal() + "," + sf.getIdSucursal() + ",'" + sf.getProyecto() + "','" + sf.getTokenComprobante() + "', Now(),'" + comprobante.getVersion() + "','" + comprobante.getSerie() + "','" + comprobante.getFolio() + "','" + HLFormats.DateFormat(comprobante.getFecha()).SQLDate() + "','" + comprobante.getSello() + "','" + comprobante.getFormaPago() + "','" + comprobante.getNoCertificado() + "','" + comprobante.getCertificado() + "','" + comprobante.getCondicionesDePago() + "'," + comprobante.getSubTotal() + "," + HLFormats.ValidaNulos(comprobante.getDescuento())  + ",'" + comprobante.getMoneda() + "'," + HLFormats.ValidaNulos(comprobante.getTipoCambio()) + ",'" + comprobante.getTipoDeComprobante() + "','" + comprobante.getMetodoPago() + "','" + comprobante.getLugarExpedicion() + "','" + sf.getCorreoElectronico() + "')";
            Conn.Execute(SqlQuery);
        }
        else
        {
            SqlQuery = "UPDATE tblComprobantes SET FechaAct = Now(), Version = '" + comprobante.getVersion() + "', Serie = '" + comprobante.getSerie() + "', Folio = '" + comprobante.getFolio() + "', Fecha = '" + HLFormats.DateFormat(comprobante.getFecha()).SQLDate() + "', "
                        +"Sello = '" + comprobante.getSello() + "', FormaPago = '" + comprobante.getFormaPago() + "', NoCertificado = '" + comprobante.getNoCertificado() + "', Certificado = '" + comprobante.getCertificado() + "', "
                        +"CondicionesPago = '" + comprobante.getCondicionesDePago() + "', Subtotal = " + comprobante.getSubTotal() + ", Descuento = " + HLFormats.ValidaNulos(comprobante.getDescuento()) + ", Moneda = '" + comprobante.getMoneda() + "', "
                        +"TipoCambio = " + HLFormats.ValidaNulos(comprobante.getTipoCambio()) + ", TipoComprobante = '" + comprobante.getTipoDeComprobante() + "', MetodoPago = '" + comprobante.getMetodoPago() + "', LugarExpedicion = '" + comprobante.getLugarExpedicion() + "'," 
                        +"RFCEmisor = '" + comprobante.getEmisor().getRfc() + "', Emisor = '" + comprobante.getEmisor().getRfc() + "', RegimenFiscal = '" + comprobante.getEmisor().getRegimenFiscal() + "', RFCReceptor = '" + comprobante.getReceptor().getRfc() + "', "
                        +"Receptor = '" + comprobante.getReceptor().getNombre() + "', UsoCFDI = '" + comprobante.getReceptor().getUsoCFDI() + "', CorreoEnviar = '" + sf.getCorreoElectronico() + "', IdPac = " + cs.getIdPacPrimario() + " "
                        +"WHERE TokenComprobante = '" + sf.getTokenComprobante() + "'";
            Conn.Execute(SqlQuery);
        }
        
        rs.close();
        
    }
   */
    
    private void GuardarTimbre(TimbreFiscalDigital timbre) throws SQLException
    {
        
            SqlQuery = "UPDATE tblComprobantes SET FechaAct = Now(), FechaTimbrado = '" + HLFormats.DateFormat(timbre.getFechaTimbrado()).SQLDate() + "', UUID = '" + timbre.getUUID() + "', "
                        +"NoCertificadoSAT = '" + timbre.getNoCertificadoSAT() + "', SelloSAT = '" + timbre.getSelloSAT() + "', SelloCFD = '" + timbre.getSelloCFD() + "', RFCProvCertif = '" + timbre.getRfcProvCertif() + "' "
                        +"WHERE TokenComprobante = '" + sf.getTokenComprobante() + "'";
            Conn.Execute(SqlQuery);
        
        
    }
    
    

    
    public void CerrarAcceso() throws SQLException
    {
        Conn.CloseConnection();

    }
    
    
    
    
    
    
   
    
    private void EjecutarPacFacturaDigital() throws JAXBException, UnsupportedEncodingException, IOException, Exception
    {
        sf.PublicarPaso("Escribiendo XML envio Factura Digital");
        HLFilesFunctions.WriteFile(cxml.XML(Comprobante.class), sf.getPathFolioFactura() + "-XmlSend.xml");
    }
    
    private void EjecutarPacDiverza() throws JAXBException, UnsupportedEncodingException, IOException, Exception
    {
        
        
        HLFilesFunctions.WriteFile(cxml.XML(Comprobante.class), sf.getPathFolioFactura() + "-XmlSend.xml");
        
        sf.PublicarPaso("Transformando XML a base 64");
        String XMLEnvio = cxml.XML64(Comprobante.class);
            

        sf.PublicarPaso("Construyendo JSON envio Diverza");
        String JsonEnvio = "";
            
            JsonEnvio = "{'credentials': "
                    +"{"
                    +"'id':'" + cd.getIdClienteDiverza() + "',"
                    +"'token':'" + cd.getToken() + "'"
                    +"},"
                    +"'issuer': {"
                    +"'rfc':'" + cs.getRFC() + "'"
                    +"},"
                    +"'receiver': {"
                    +"'emails': [ {"
                    +"'email': '" + cs.getCorreoElectronico() + "',"
                    +"'format': 'xml+pdf',"
                    +"'template': 'letter'"
                    + "}"
                    +"]},"
                    +"'document': {"
                    +"'ref-id': '" + sf.getFolioFactura() + "',"
                    +"'certificate-number': '',"
                    +"'section': 'all',"
                    +"'format': 'xml',"
                    + "'template': 'letter',"
                    +"'type': 'application/vnd.diverza.cfdi_3.3+xml',"
                    +"'content': '" + XMLEnvio +"'}}";
            
            
            JsonEnvio = JsonEnvio.replace("'", "\"");
            
            sf.PublicarPaso("Escribiendo JSON envio Diverza");
            HLFilesFunctions.WriteFile(JsonEnvio, sf.getPathFolioFactura() + "-JsonSend.txt");
            
            javax.net.ssl.SSLSocketFactory sslSocketFactory = FactorySimple.getFactorySimple();
            
            SSLContext.getInstance("TLS");
            
            sf.PublicarPaso("Estableciendo conexion Diverza");
            HttpsURLConnection con = (HttpsURLConnection) new URL(cd.getSitioFactura()).openConnection();
            con.setSSLSocketFactory(sslSocketFactory);
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "application/json");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setDoOutput(true);
            
            
            sf.PublicarPaso("Enviando JSON Diverza");
            con.getOutputStream().write(JsonEnvio.getBytes());
            
            sf.PublicarPaso("Deserealizando JSON Diverza");
            Gson gson = new Gson();
            sf.PublicarPaso("Obteniendo respuesta Diverza");
            String sReturned="";
            
            int ResponseCode = con.getResponseCode();
            if(ResponseCode!=200 && ResponseCode!=201)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                sReturned = in.readLine();
                
                sf.setError(sReturned);
                
                //DiverzaError de = gson.fromJson(sReturned, DiverzaError.class);
                
                //sf.PublicarError(de.getError_details().toString());
                sf.PublicarError(sReturned);
                
                throw new DiverzaException(sReturned);
                
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            sReturned = in.readLine();
            
            System.out.println(sReturned);
            
            
            
            
            
            
            byte[] b = new byte[con.getInputStream().available()];

            
            DiverzaOK diverzaOK= gson.fromJson(sReturned, DiverzaOK.class);
            
            sf.PublicarPaso("Decodificando XML timbrado Diverza");
            
            byte[] decoded = DatatypeConverter.parseBase64Binary(diverzaOK.getContent());
            String decodedText = new String(decoded, "UTF-8");
            
            
            
            String encoded = DatatypeConverter.printBase64Binary(decodedText.getBytes());
            sf.setXML64(encoded);
            sf.setXMLFile(decodedText);

    }
    
    
    
}
