/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.facturadigital;

import com.google.gson.Gson;
import hl.facturas33.diverza.DiverzaOK;
import hl.facturas33.exceptions.DiverzaException;
import hl.facturas33.exceptions.FacturaDigitalException;
import hl.facturas33.funciones.FactorySimple;
import hl.facturas33.objetos.Comprobante;
import hl.facturas33.objetos.HLSolicitudFactura;
import hl.util.HLDataConnections;
import hl.util.HLDataRecordset;
import hl.util.HLFilesFunctions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Ruben
 */
public class HLCuentaFacturaDigital {
    HLDataConnections Conn;
    String SqlQuery;
    
    int IdPac;
    String SitioCreditos;
    String SitioFactura;
    String SitioCancelacion;
    String CorreoRecepcion;
    String Usuario;
    String Passwd;
    
    public HLCuentaFacturaDigital(String RFC, HLDataConnections conn) throws SQLException {
        Conn=conn;
        
        HLDataRecordset rs = new HLDataRecordset();
        SqlQuery = "SELECT * FROM tblPropiedadesPacs WHERE (RFC = 'TODOS' OR RFC = '" + RFC + "') AND IdPac = 2 ORDER BY Propiedad, CASE WHEN RFC = 'TODOS' THEN 1 ELSE 2 END";
        rs.open(Conn.getConn(), SqlQuery);
        rs.first();
        while(rs.next())
        {
            if(rs.getValue("Propiedad").equals("Sitio Factura"))
            {
                SitioFactura=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("Sitio Creditos"))
            {
                SitioCreditos=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("Sitio Cancelacion"))
            {
                SitioCancelacion=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("api-usuario"))
            {
                Usuario=rs.getValue("Valor");
            }
            if(rs.getValue("Propiedad").equals("api-password"))
            {
                Passwd=rs.getValue("Valor");
            }


        }

        rs.close();
    }
    
    public void EjecutarPac(HLSolicitudFactura sf, Comprobante comprobante) throws SQLException, IOException, FacturaDigitalException, NoSuchAlgorithmException, Exception
    {
        sf.PublicarPaso("Escribiendo JSON envio Factura Digiral");
        String jsonEnvio = "";
        jsonEnvio = JsonEnvio(comprobante);
        HLFilesFunctions.WriteFile(jsonEnvio, sf.getPathFolioFactura() + "-JsonSend.txt");

        javax.net.ssl.SSLSocketFactory sslSocketFactory =FactorySimple.getFactorySimple();
            
        SSLContext.getInstance("SSL");
        sf.PublicarPaso("Estableciendo conexion " + SitioFactura);
        HttpsURLConnection con = (HttpsURLConnection) new URL(SitioFactura).openConnection();
        con.setSSLSocketFactory(sslSocketFactory);
        con.setRequestMethod("POST");
        con.setRequestProperty("ACCEPT", "application/json");
        con.setRequestProperty("api-usuario", Usuario);
        con.setRequestProperty("api-password",Passwd);
        con.setRequestProperty("jsoncfdi", jsonEnvio);
        con.setDoOutput(true);
            
            
        sf.PublicarPaso("Enviando JSON Factura Digital");
        //con.getOutputStream().write(jsonEnvio.getBytes());
            
        sf.PublicarPaso("Deserealizando JSON Factura Digital");
        Gson gson = new Gson();
        sf.PublicarPaso("Obteniendo respuesta Factura Digital");
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

            throw new FacturaDigitalException(sReturned);

        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        sReturned = in.readLine();

        System.out.println(sReturned);
        byte[] b = new byte[con.getInputStream().available()];

            
        FacturaDigitalResp resp= gson.fromJson(sReturned, FacturaDigitalResp.class);

        sf.PublicarPaso("Decodificando XML timbrado Factura Digital");

        byte[] decoded = DatatypeConverter.parseBase64Binary(resp.getCfdi().getXmlBase64());
        String decodedText = new String(decoded, "UTF-8");
        
        String encoded = DatatypeConverter.printBase64Binary(decodedText.getBytes());
        sf.setXML64(encoded);
        sf.setXMLFile(decodedText);
    }
    
    
    public String JsonEnvio(Comprobante comprobante)
    {
        
        int ret = 0;
        String JsonReq="{";
        
        JsonReq+="\"Serie\": \"" + comprobante.getSerie() + "\", ";
        JsonReq+="\"Folio\": " + comprobante.getFolio() + ", ";
        JsonReq+="\"Fecha\": \"AUTO\", ";
        JsonReq+="\"FormaPago\": \"" + comprobante.getFormaPago() + "\", ";
        JsonReq+="\"CondicionesPago\": \"" + comprobante.getCondicionesDePago() + "\", ";
        JsonReq+="\"SubTotal\": \"" + comprobante.getSubTotal() + "\", ";
        JsonReq+="\"Descuento\": " + comprobante.getDescuento() + ", ";
        JsonReq+="\"Moneda\": \"" + comprobante.getMoneda() + "\",";
        JsonReq+="\"Total\": \"" + comprobante.getTotal() + "\",";
        JsonReq+="\"TipoDeComprobante\": \"" + comprobante.getTipoDeComprobante() + "\",";
        JsonReq+="\"MetodoPago\": \"" + comprobante.getMetodoPago() + "\",";
        JsonReq+="\"LugarExpedicion\": \"" + comprobante.getLugarExpedicion() + "\",";
        JsonReq+="\"LeyendaFolio\": \"FACTURA\", ";
        JsonReq+="\"Emisor\": {";
        JsonReq+="\"RegimenFiscal\": \"" + comprobante.getEmisor().getRegimenFiscal() + "\"";
        JsonReq+="}, ";
        JsonReq+="\"Receptor\": {";
        JsonReq+="\"Rfc\": \"" + comprobante.getReceptor().getRfc() + "\",";
        JsonReq+="\"Nombre\": \"" + comprobante.getReceptor().getNombre() + "\",";
        JsonReq+="\"UsoCFDI\": \"" + comprobante.getReceptor().getUsoCFDI() + "\"";
        JsonReq+="}, ";
        
                        

        JsonReq+="\"Conceptos\": [ ";
        
        for (int i=0; i < comprobante.getConceptos().getConcepto().size(); i++){

            Comprobante.Conceptos.Concepto concepto =  comprobante.getConceptos().getConcepto().get(i);
            
            if(i>0)
            {
                JsonReq+=", ";
            }

            JsonReq+= "{";
            JsonReq+="\"ClaveProdServ\": \"" + concepto.getClaveProdServ() + "\",";
            JsonReq+="\"NoIdentificacion\": \"" + concepto.getNoIdentificacion() + "\",";
            JsonReq+="\"Cantidad\": " + concepto.getCantidad() + ",";
            JsonReq+="\"ClaveUnidad\": \"" + concepto.getClaveUnidad() + "\",";
            JsonReq+="\"Unidad\": \"" + concepto.getUnidad() + "\",";
            JsonReq+="\"Descripcion\": \"" + concepto.getDescripcion() + "\",";
            JsonReq+="\"ValorUnitario\": \"" + concepto.getValorUnitario() + "\",";
            JsonReq+="\"Importe\": \"" + concepto.getImporte() + "\" ";
            
            Comprobante.Conceptos.Concepto.Impuestos conceptoImpuestos =  concepto.getImpuestos();
            if(conceptoImpuestos != null )
            {
                JsonReq+=", \"Impuestos\": {";
                if(conceptoImpuestos.getRetenciones() != null)
                {
                    
                    JsonReq+="\"Retenidos\": [";
                    Comprobante.Conceptos.Concepto.Impuestos.Retenciones conceptoRetenciones = conceptoImpuestos.getRetenciones();
                    for (int j=0; j < conceptoRetenciones.getRetencion().size(); j++){
                        ret = 1;
                        Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion conceptoRetencion =  conceptoRetenciones.getRetencion().get(j);
                        
                        if(j>0)
                        {
                            JsonReq+=", ";
                        }
                        JsonReq+="{";
                        JsonReq+="\"Base\": \"" + conceptoRetencion.getBase() + "\",";
                        JsonReq+="\"Impuesto\": \"" + conceptoRetencion.getImpuesto() + "\",";
                        JsonReq+="\"TipoFactor\": \"" + conceptoRetencion.getTipoFactor() + "\",";
                        JsonReq+="\"TasaOCuota\": \"" + conceptoRetencion.getTasaOCuota() + "\",";
                        JsonReq+="\"Importe\": \"" + conceptoRetencion.getImporte() + "\"";
                        JsonReq+="}";
                    }
                    
                    JsonReq+="] ";
                    
                }
                
                
                if(conceptoImpuestos.getTraslados() != null)
                {
                    Comprobante.Conceptos.Concepto.Impuestos.Traslados conceptoTraslados = conceptoImpuestos.getTraslados();
                    
                    if(ret==1)
                    {
                        JsonReq+=", ";
                    }
                    
                    JsonReq+="\"Traslados\": [";
                    
                    for (int j=0; j < conceptoTraslados.getTraslado().size(); j++){

                        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado conceptoTraslado =  conceptoTraslados.getTraslado().get(j);
                        
                        if(j>0)
                        {
                            JsonReq+=",";
                        }

                        JsonReq+="{";
                        JsonReq+="\"Base\": \"" + conceptoTraslado.getBase() + "\",";
                        JsonReq+="\"Impuesto\": \"" + conceptoTraslado.getImpuesto() + "\",";
                        JsonReq+="\"TipoFactor\": \"" + conceptoTraslado.getTipoFactor() + "\",";
                        JsonReq+="\"TasaOCuota\":\"" + conceptoTraslado.getTasaOCuota() + "\",";
                        JsonReq+="\"Importe\": \"" + conceptoTraslado.getImporte() + "\"";
                        JsonReq+="} ";
                    }
                    
                    JsonReq+="] ";
                }
                JsonReq+="}";
            }
            JsonReq+="}";
          
        }
        JsonReq+="],";
        
        Comprobante.Impuestos impuestos =  comprobante.getImpuestos();
        
        if(impuestos !=null)
        {
            JsonReq+="\"Impuestos\": {";
            
            if(Double.parseDouble(impuestos.getTotalImpuestosRetenidos().toString())>0)
            {
                JsonReq+="\"TotalImpuestosRetenidos\": \"" + impuestos.getTotalImpuestosRetenidos() + "\", ";
            }
            
            
            if(Double.parseDouble(impuestos.getTotalImpuestosTrasladados().toString())>0)
            {

                JsonReq+="\"TotalImpuestosTrasladados\": \"" + impuestos.getTotalImpuestosTrasladados() + "\", ";
            }
            
            ret = 0;
            
            if(impuestos.getRetenciones() != null)
            {
                JsonReq+="\"Retenciones\": [";
                Comprobante.Impuestos.Retenciones retenciones = impuestos.getRetenciones();
                for (int j=0; j < retenciones.getRetencion().size(); j++){
                    
                    ret = 1;
                    if(j>0)
                    {
                        JsonReq+=",";
                    }
                    Comprobante.Impuestos.Retenciones.Retencion retencion =  retenciones.getRetencion().get(j);
                    JsonReq+="{";
                    JsonReq+="\"Impuesto\": \"" + retencion.getImpuesto() + "\",";
                    JsonReq+="\"Importe\": \"" + retencion.getImporte() + "\"";
                    JsonReq+="},";
                }
                JsonReq+="] ";
            }
            
            if(impuestos.getTraslados() != null)
            {
                if(ret==1)
                {
                    JsonReq+=", ";
                }

                JsonReq+="\"Traslados\": [";
                Comprobante.Impuestos.Traslados traslados = impuestos.getTraslados();
                for (int j=0; j < traslados.getTraslado().size(); j++){
                    if(j>0)
                    {
                        JsonReq+=",";
                    }
                    Comprobante.Impuestos.Traslados.Traslado traslado =  traslados.getTraslado().get(j);
                    JsonReq+="{";
                    JsonReq+="\"Impuesto\": \"" + traslado.getImpuesto() + "\",";
                    JsonReq+="\"TipoFactor\": \"" + traslado.getTipoFactor() + "\",";
                    JsonReq+="\"TasaOCuota\": \"" + traslado.getTasaOCuota() + "\",";
                    JsonReq+="\"Importe\": \"" + traslado.getImporte() + "\"";
                    JsonReq+="}";
                }
                JsonReq+="] ";
            }
            
            JsonReq+="} ";
        }
        
        JsonReq+="} ";
            
        
        return JsonReq;
        
        
        
    }
        
        
    public HLDataConnections getConn() {
        return Conn;
    }

    public void setConn(HLDataConnections Conn) {
        this.Conn = Conn;
    }

    public int getIdPac() {
        return IdPac;
    }

    public void setIdPac(int IdPac) {
        this.IdPac = IdPac;
    }

    public String getSitioCreditos() {
        return SitioCreditos;
    }

    public void setSitioCreditos(String SitioCreditos) {
        this.SitioCreditos = SitioCreditos;
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

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String Passwd) {
        this.Passwd = Passwd;
    }
    
}
