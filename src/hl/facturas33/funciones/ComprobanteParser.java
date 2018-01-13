/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.funciones;

import hl.log.Log;
import hl.util.HLFormats;
import hl.util.NumberToLetterConverter;
import hl.facturas33.objetos.CMoneda;
import hl.facturas33.objetos.CTipoDeComprobante;
import hl.facturas33.objetos.Comprobante;
import hl.facturas33.objetos.Comprobante.Emisor;
import hl.facturas33.objetos.TimbreFiscalDigital;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.SAXException;

/**
 *
 * @author Ruben
 */
public class ComprobanteParser {

    public String getPathLogo() {
        return PathLogo;
    }

    public void setPathLogo(String PathLogo) {
        this.PathLogo = PathLogo;
    }

    public String getPathQR() {
        return PathQR;
    }

    public void setPathQR(String PathQR) {
        this.PathQR = PathQR;
    }

    String PathLogo;
    String PathQR;
    String Observaciones;

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
    
    String ColorPrimario;
    String ColorSecundario;
    

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String Sucursal) {
        this.Sucursal = Sucursal;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    private String RegimenFiscalDesc;

    public String getRegimenFiscalDesc() {
        return RegimenFiscalDesc;
    }

    public void setRegimenFiscalDesc(String RegimenFiscalDesc) {
        this.RegimenFiscalDesc = RegimenFiscalDesc;
    }
    

    public String getTelefonos() {
        return Telefonos;
    }

    public void setTelefonos(String Telefonos) {
        this.Telefonos = Telefonos;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String CorreoElectronico) {
        this.CorreoElectronico = CorreoElectronico;
    }

    public String getCasoUso() {
        return CasoUso;
    }

    public void setCasoUso(String CasoUso) {
        this.CasoUso = CasoUso;
    }

    public String getMetodoPago() {
        return MetodoPago;
    }

    public void setMetodoPago(String MetodoPago) {
        this.MetodoPago = MetodoPago;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public void setFormaPago(String FormaPago) {
        this.FormaPago = FormaPago;
    }
    String Sucursal;
    String Direccion;
    String Telefonos;
    String CorreoElectronico;
    String CasoUso;
    String MetodoPago;
    String FormaPago;

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }
    
    
    
    Comprobante comprobante;
    

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public TimbreFiscalDigital getTimbre() {
        return timbre;
    }

    public void setTimbre(TimbreFiscalDigital timbre) {
        this.timbre = timbre;
    }
    TimbreFiscalDigital timbre;


    String FileName ="";
    String Folio = "";

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String Folio) {
        this.Folio = Folio;
    }

    

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }
    
    public ComprobanteParser()
    {
        
    }
    
    public void LeerXmlInicial(String XMLPath) throws JAXBException, SAXException, IOException, JDOMException
    {
        
        comprobante = new Comprobante();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(XMLPath);
        
        
        

            Document document =  (Document) builder.build( xmlFile );
            Element rootNode = document.getRootElement();
            
            
            
            comprobante.setVersion(NodeValue(rootNode,"Version"));
            //comprobante.setSerie(hlc.getCuentaSucursal().getSerie());
            comprobante.setFolio(NodeValue(rootNode,"Folio"));
            comprobante.setFecha(NodeValue(rootNode,"Fecha"));
            comprobante.setSello(NodeValue(rootNode,"Sello"));
            comprobante.setFormaPago(NodeValue(rootNode,"FormaPago"));
            comprobante.setCertificado("");
            //comprobante.setNoCertificado(hlc.getCuentaSucursal().getNoCertificado());
            comprobante.setCondicionesDePago(NodeValue(rootNode,"CondicionesDePago"));
            comprobante.setSubTotal(BigDecimalNodeValue(rootNode,"SubTotal"));
            comprobante.setMoneda(CMoneda.valueOf(NodeValue(rootNode,"Moneda")));
            comprobante.setTotal(BigDecimalNodeValue(rootNode,"Total"));
            comprobante.setTipoDeComprobante(CTipoDeComprobante.valueOf(NodeValue(rootNode,"TipoDeComprobante")));
            comprobante.setMetodoPago(NodeValue(rootNode,"MetodoPago"));
            //comprobante.setLugarExpedicion(hlc.getCuentaSucursal().getCodigoPostal());

            System.out.println(comprobante.getVersion());
            System.out.println(comprobante.getSerie());
            System.out.println(comprobante.getFolio());
            System.out.println(comprobante.getFecha());
            System.out.println(comprobante.getSello());
            System.out.println(comprobante.getFormaPago());
            System.out.println(comprobante.getCertificado());
            
            System.out.println(comprobante.getNoCertificado());
            System.out.println(comprobante.getCondicionesDePago());
            System.out.println(comprobante.getSubTotal().toString());
            System.out.println(comprobante.getMoneda().toString());
            System.out.println(comprobante.getTotal().toString());
            System.out.println(comprobante.getTipoDeComprobante().toString());
            System.out.println(comprobante.getMetodoPago().toString());
            System.out.println(comprobante.getLugarExpedicion());
            
            Comprobante.Emisor emisor = new Comprobante.Emisor();
            Comprobante.Receptor receptor = new Comprobante.Receptor();
            Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
            Comprobante.Impuestos impuestos = new Comprobante.Impuestos();
            Comprobante.Impuestos.Retenciones retenciones = new Comprobante.Impuestos.Retenciones();
            Comprobante.Impuestos.Traslados traslados = new Comprobante.Impuestos.Traslados();
            Comprobante.Impuestos.Retenciones.Retencion retencion = new Comprobante.Impuestos.Retenciones.Retencion();
            Comprobante.Impuestos.Traslados.Traslado traslado = new Comprobante.Impuestos.Traslados.Traslado();
            
            Comprobante.CfdiRelacionados cfdi = new Comprobante.CfdiRelacionados();
            Comprobante.Addenda addenda = new Comprobante.Addenda();
            Comprobante.Complemento complemento = new Comprobante.Complemento();
            
            TimbreFiscalDigital timbre = new TimbreFiscalDigital();
            
            retenciones.getRetencion().add(retencion);
            traslados.getTraslado().add(traslado);        
            
            //emisor.setRfc(hlc.getCuentaSucursal().getEmisor().getRFC());
            //emisor.setNombre(hlc.getCuentaSucursal().getEmisor().getEmisor());
            //emisor.setRegimenFiscal(hlc.getCuentaSucursal().getEmisor().getRegimenFiscal());
            //comprobante.emisor = emisor;
            
            impuestos.setRetenciones(retenciones);
            impuestos.setTraslados(traslados);
            
            System.out.println(String.valueOf(rootNode.getChildren().size()));
            for ( int i = 0; i < rootNode.getChildren().size(); i++ )
            {
                Element node = rootNode.getChildren().get(i);
                
                
                if("Receptor".equals(node.getName()))
                {
                    receptor.setRfc(NodeValue(node,"Rfc"));
                    receptor.setNombre(NodeValue(node,"Nombre"));
                    receptor.setUsoCFDI(NodeValue(node,"UsoCFDI"));
                    
                    comprobante.setReceptor(receptor);
                }
                 
                if("Conceptos".equals(node.getName()))
                {
                    
                    for ( int j = 0; j < node.getChildren().size(); j++ )
                    {
                        Element nodej = node.getChildren().get(j);
                        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();

                        concepto.setCantidad(BigDecimalNodeValue(nodej,"Cantidad"));
                        concepto.setClaveProdServ(NodeValue(nodej,"ClaveProdServ"));
                        concepto.setClaveUnidad(NodeValue(nodej,"ClaveUnidad"));
                        

                        concepto.setDescripcion(NodeValue(nodej,"Descripcion"));
                        //concepto.setDescuento(BigDecimalNodeValue(nodej,"Descuento"));
                        concepto.setImporte(BigDecimalNodeValue(nodej,"Importe"));
                        concepto.setNoIdentificacion(NodeValue(nodej,"NoIdentificacion"));
                        concepto.setUnidad(NodeValue(nodej,"Unidad"));
                        concepto.setValorUnitario(BigDecimalNodeValue(nodej,"ValorUnitario"));
                        
                        Comprobante.Conceptos.Concepto.Impuestos impuestosd = new Comprobante.Conceptos.Concepto.Impuestos();
                        Comprobante.Conceptos.Concepto.Impuestos.Retenciones retencionesd = new Comprobante.Conceptos.Concepto.Impuestos.Retenciones();
                        Comprobante.Conceptos.Concepto.Impuestos.Traslados trasladosd = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
                        for (int k = 0; k < nodej.getChildren().size(); k++ )
                        {
                            Element nodek = nodej.getChildren().get(k);
                            if("Impuestos".equals(nodek.getName()))
                            {
                                int f = 0;
                                Element nodel = nodek.getChildren().get(0);
                                if("Retenciones".equals(nodel.getName()))
                                {
                                    f++;
                                    for (int m = 0; m < nodel.getChildren().size(); m++ )
                                    {
                                        Element nodem = nodel.getChildren().get(m);
                                        Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retenciond = new Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion();
                                        retenciond.setBase(BigDecimalNodeValue(nodem,"Base"));
                                        retenciond.setImporte(BigDecimalNodeValue(nodem,"Importe"));
                                        retenciond.setImpuesto(NodeValue(nodem,"Impuesto"));
                                        retenciond.setTasaOCuota(BigDecimalNodeValue(nodem,"TasaOCuota"));
                                        retenciond.setTipoFactor(NodeValue(nodem,"TipoFactor"));

                                        retencionesd.getRetencion().add(retenciond);

                                    }
                                    impuestosd.setRetenciones(retencionesd);
                                }
                                
                                
                                nodel = nodek.getChildren().get(f);
                                if("Traslados".equals(nodel.getName()))
                                {
                                    f++;
                                    for (int m = 0; m < nodel.getChildren().size(); m++ )
                                    {
                                        Element nodem = nodel.getChildren().get(m);
                                        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado trasladod = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
                                        trasladod.setBase(BigDecimalNodeValue(nodem,"Base"));
                                        trasladod.setImporte(BigDecimalNodeValue(nodem,"Importe"));
                                        trasladod.setImpuesto(NodeValue(nodem,"Impuesto"));
                                        trasladod.setTasaOCuota(BigDecimalNodeValue(nodem,"TasaOCuota"));
                                        trasladod.setTipoFactor(NodeValue(nodem,"TipoFactor"));

                                        trasladosd.getTraslado().add(trasladod);

                                    }
                                    
                                    impuestosd.setTraslados(trasladosd);
                                }
                                
                            }
                            concepto.setImpuestos(impuestosd);
                            
                        }
                        
                        conceptos.getConcepto().add(concepto);
                    }
                    
                    comprobante.setConceptos(conceptos);

                    System.out.println(String.valueOf(comprobante.getConceptos().getConcepto().size()));
                }
                
                
                
                
                if("Impuestos".equals(node.getName()))
                {
                    
                    comprobante.setImpuestos(impuestoshead(node));
                }
                

                
            }
            
            
            
            //System.out.println(comprobante.getEmisor().nombre);
            
            System.out.println(String.valueOf(comprobante.getConceptos().getConcepto().size()));
            
            
            
            
            

   
    }
    
    public String XML(Class clase) throws JAXBException, UnsupportedEncodingException, IOException
    {
        ////File xmlPrueba = new File("c:\\fuentes\\kyk\\kykserver2\\prueba.xml");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(clase);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");

        //try {
            jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespaceMapper());
            //m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNamespaceMapper());
        //} catch(PropertyException e) {
            // In case another JAXB implementation is used
        //}
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(comprobante,baos);
        
        
        
        
        
        String xml = new String(baos.toByteArray(), "UTF-8");
        
        return xml;
        
    }
    
    public String XML64(Class clase) throws JAXBException, UnsupportedEncodingException, IOException
    {
        ////File xmlPrueba = new File("c:\\fuentes\\kyk\\kykserver2\\prueba.xml");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(clase);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");

        //try {
            jaxbMarshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespaceMapper());
            //m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNamespaceMapper());
        //} catch(PropertyException e) {
            // In case another JAXB implementation is used
        //}
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(comprobante,baos);
        
        
        
        
        
        String xml = new String(baos.toByteArray(), "UTF-8");
        
        //HLFilesFunctions.WriteFile(xml, "c:\\fuentes\\hlcore\\xmlsend.xml");
        //String xml = new String(xmlArray, "UTF-8");
        //byte[] message = "hello world".getBytes("UTF-8");
        String encoded = DatatypeConverter.printBase64Binary(xml.getBytes());
        System.out.println(encoded);
        String xml64 = encoded;
        
        return xml64;
        
    }
    
    public void LeerXml(String path) throws JAXBException, SAXException
    {

        timbre = new TimbreFiscalDigital();
        comprobante = new Comprobante();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(path);
        
        try
        {
            Document document = (Document) builder.build( xmlFile );

            Element rootNode = document.getRootElement();
            comprobante.setVersion(NodeValue(rootNode,"Version"));
            comprobante.setSerie(NodeValue(rootNode,"Serie"));
            comprobante.setFolio(NodeValue(rootNode,"Folio"));
            comprobante.setFecha(NodeValue(rootNode,"Fecha"));
            comprobante.setSello(NodeValue(rootNode,"Sello"));
            comprobante.setFormaPago(NodeValue(rootNode,"FormaPago"));
            comprobante.setCertificado(NodeValue(rootNode,"Certificado"));
            comprobante.setNoCertificado(NodeValue(rootNode,"NoCertificado"));
            comprobante.setCondicionesDePago(NodeValue(rootNode,"CondicionesDePago"));
            comprobante.setSubTotal(BigDecimalNodeValue(rootNode,"SubTotal"));
            comprobante.setMoneda(CMoneda.valueOf(NodeValue(rootNode,"Moneda")));
            comprobante.setTotal(BigDecimalNodeValue(rootNode,"Total"));
            comprobante.setTipoDeComprobante(CTipoDeComprobante.valueOf(NodeValue(rootNode,"TipoDeComprobante")));
            comprobante.setMetodoPago(NodeValue(rootNode,"MetodoPago"));
            comprobante.setLugarExpedicion(NodeValue(rootNode,"LugarExpedicion"));

            System.out.println(comprobante.getVersion());
            System.out.println(comprobante.getSerie());
            System.out.println(comprobante.getFolio());
            System.out.println(comprobante.getFecha());
            System.out.println(comprobante.getSello());
            System.out.println(comprobante.getFormaPago());
            System.out.println(comprobante.getCertificado());
            
            System.out.println(comprobante.getNoCertificado());
            System.out.println(comprobante.getCondicionesDePago());
            System.out.println(comprobante.getSubTotal().toString());
            System.out.println(comprobante.getMoneda().toString());
            System.out.println(comprobante.getTotal().toString());
            System.out.println(comprobante.getTipoDeComprobante().toString());
            System.out.println(comprobante.getMetodoPago().toString());
            System.out.println(comprobante.getLugarExpedicion());
            
            Comprobante.Emisor emisor = new Comprobante.Emisor();
            Comprobante.Receptor receptor = new Comprobante.Receptor();
            Comprobante.Conceptos conceptos = new Comprobante.Conceptos();
            Comprobante.Impuestos impuestos = new Comprobante.Impuestos();
            Comprobante.Impuestos.Retenciones retenciones = new Comprobante.Impuestos.Retenciones();
            Comprobante.Impuestos.Traslados traslados = new Comprobante.Impuestos.Traslados();
            Comprobante.Impuestos.Retenciones.Retencion retencion = new Comprobante.Impuestos.Retenciones.Retencion();
            Comprobante.Impuestos.Traslados.Traslado traslado = new Comprobante.Impuestos.Traslados.Traslado();
            
            Comprobante.CfdiRelacionados cfdi = new Comprobante.CfdiRelacionados();
            Comprobante.Addenda addenda = new Comprobante.Addenda();
            Comprobante.Complemento complemento = new Comprobante.Complemento();
            
            
            retenciones.getRetencion().add(retencion);
            traslados.getTraslado().add(traslado);        
                    
            impuestos.setRetenciones(retenciones);
            impuestos.setTraslados(traslados);
            
            System.out.println(String.valueOf(rootNode.getChildren().size()));
            for ( int i = 0; i < rootNode.getChildren().size(); i++ )
            {
                Element node = rootNode.getChildren().get(i);
                
                if("Emisor".equals(node.getName()))
                {
                    emisor.setRfc(NodeValue(node,"Rfc"));
                    emisor.setNombre(NodeValue(node,"Nombre"));
                    emisor.setRegimenFiscal(NodeValue(node,"RegimenFiscal"));

                    comprobante.setEmisor(emisor);
                }
                
                if("Receptor".equals(node.getName()))
                {
                    receptor.setRfc(NodeValue(node,"Rfc"));
                    receptor.setNombre(NodeValue(node,"Nombre"));
                    receptor.setUsoCFDI(NodeValue(node,"UsoCFDI"));
                    receptor.setResidenciaFiscal(NodeValue(node,"ResidenciaFiscal"));
                    comprobante.setReceptor(receptor);
                }
                 
                if("Conceptos".equals(node.getName()))
                {
                    
                    for ( int j = 0; j < node.getChildren().size(); j++ )
                    {
                        Element nodej = node.getChildren().get(j);
                        Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();

                        concepto.setCantidad(BigDecimalNodeValue(nodej,"Cantidad"));
                        concepto.setClaveProdServ(NodeValue(nodej,"ClaveProdServ"));
                        concepto.setClaveUnidad(NodeValue(nodej,"ClaveUnidad"));
                        

                        concepto.setDescripcion(NodeValue(nodej,"Descripcion"));
                        //if(Double.parseDouble(NodeValue(nodej,"Descuento"))> 0)
                        //{
                            concepto.setDescuento(BigDecimalNodeValue(nodej,"Descuento"));
                        //}
                        concepto.setImporte(BigDecimalNodeValue(nodej,"Importe"));
                        concepto.setNoIdentificacion(NodeValue(nodej,"NoIdentificacion"));
                        concepto.setUnidad(NodeValue(nodej,"Unidad"));
                        concepto.setValorUnitario(BigDecimalNodeValue(nodej,"ValorUnitario"));
                        
                        Comprobante.Conceptos.Concepto.Impuestos impuestosd = new Comprobante.Conceptos.Concepto.Impuestos();
                        Comprobante.Conceptos.Concepto.Impuestos.Retenciones retencionesd = new Comprobante.Conceptos.Concepto.Impuestos.Retenciones();
                        Comprobante.Conceptos.Concepto.Impuestos.Traslados trasladosd = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
                        for (int k = 0; k < nodej.getChildren().size(); k++ )
                        {
                            Element nodek = nodej.getChildren().get(k);
                            if("Impuestos".equals(nodek.getName()))
                            {
                                int f = 0;
                                Element nodel = nodek.getChildren().get(0);
                                if("Retenciones".equals(nodel.getName()))
                                {
                                    f++;
                                    for (int m = 0; m < nodel.getChildren().size(); m++ )
                                    {
                                        Element nodem = nodel.getChildren().get(m);
                                        Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retenciond = new Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion();
                                        retenciond.setBase(BigDecimalNodeValue(nodem,"Base"));
                                        retenciond.setImporte(BigDecimalNodeValue(nodem,"Importe"));
                                        retenciond.setImpuesto(NodeValue(nodem,"Impuesto"));
                                        retenciond.setTasaOCuota(BigDecimalNodeValue(nodem,"TasaOCuota"));
                                        retenciond.setTipoFactor(NodeValue(nodem,"TipoFactor"));

                                        retencionesd.getRetencion().add(retenciond);

                                    }
                                    impuestosd.setRetenciones(retencionesd);
                                }
                                
                                
                                nodel = nodek.getChildren().get(f);
                                if("Traslados".equals(nodel.getName()))
                                {
                                    f++;
                                    for (int m = 0; m < nodel.getChildren().size(); m++ )
                                    {
                                        Element nodem = nodel.getChildren().get(m);
                                        Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado trasladod = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
                                        trasladod.setBase(BigDecimalNodeValue(nodem,"Base"));
                                        trasladod.setImporte(BigDecimalNodeValue(nodem,"Importe"));
                                        trasladod.setImpuesto(NodeValue(nodem,"Impuesto"));
                                        trasladod.setTasaOCuota(BigDecimalNodeValue(nodem,"TasaOCuota"));
                                        trasladod.setTipoFactor(NodeValue(nodem,"TipoFactor"));

                                        trasladosd.getTraslado().add(trasladod);

                                    }
                                    
                                    impuestosd.setTraslados(trasladosd);
                                }
                                
                            }
                            concepto.setImpuestos(impuestosd);
                            
                        }
                        
                        conceptos.getConcepto().add(concepto);
                    }
                    
                    comprobante.setConceptos(conceptos);

                    System.out.println(String.valueOf(comprobante.getConceptos().getConcepto().size()));
                }
                
                
                
                
                if("Impuestos".equals(node.getName()))
                {
                    
                    comprobante.setImpuestos(impuestoshead(node));
                }
                
                if("Complemento".equals(node.getName()))
                {
                    for ( int j = 0; j < node.getChildren().size(); j++ )
                    {
                        Element nodej = node.getChildren().get(j);
                        if("TimbreFiscalDigital".equals(nodej.getName()))
                        {
                            timbre.setVersion(NodeValue(nodej,"Version"));
                            timbre.setUUID(NodeValue(nodej,"UUID"));
                            timbre.setRfcProvCertif(NodeValue(nodej,"RfcProvCertif"));
                            timbre.setFechaTimbrado(NodeValue(nodej,"FechaTimbrado"));
                            timbre.setSelloCFD(NodeValue(nodej,"SelloCFD"));
                            timbre.setNoCertificadoSAT(NodeValue(nodej,"NoCertificadoSAT"));
                            timbre.setSelloSAT(NodeValue(nodej,"SelloSAT"));
                            timbre.setVersion(NodeValue(nodej,"Version"));
                            
                        }
                    }
                }
                
            }

            
            System.out.println(String.valueOf(comprobante.getConceptos().getConcepto().size()));
            
            System.out.println(timbre.getUUID());

            
        }catch ( IOException io ) {
            System.out.println( io.getMessage() );
        }catch ( JDOMException jdomex ) {
            System.out.println( jdomex.getMessage() );
        }
    }
    
    private Comprobante.Conceptos.Concepto.Impuestos.Retenciones retenciones(Element node)
    {

        Comprobante.Conceptos.Concepto.Impuestos.Retenciones retencionesd = new Comprobante.Conceptos.Concepto.Impuestos.Retenciones();
        for (int k = 0; k < node.getChildren().size(); k++ )
        {
            Element nodek = node.getChildren().get(k);


            
            if("Retenciones".equals(nodek.getName()))
            {

                for (int l = 0; l < nodek.getChildren().size(); l++ )
                {
                    Element nodel = nodek.getChildren().get(k);

                    Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion retenciond = new Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion();
                    retenciond.setBase(BigDecimalNodeValue(nodel,"Base"));
                    retenciond.setImporte(BigDecimalNodeValue(nodel,"Importe"));
                    retenciond.setImpuesto(NodeValue(nodel,"Impuesto"));
                    retenciond.setTasaOCuota(BigDecimalNodeValue(nodel,"TasaOCuota"));
                    retenciond.setTipoFactor(NodeValue(nodel,"TipoFactor"));

                    retencionesd.getRetencion().add(retenciond);
                }
            }

            
        }
        
        return retencionesd;
           
    }
    
    private Comprobante.Conceptos.Concepto.Impuestos.Traslados traslados(Element node)
    {

        Comprobante.Conceptos.Concepto.Impuestos impuestos = new Comprobante.Conceptos.Concepto.Impuestos();
        Comprobante.Conceptos.Concepto.Impuestos.Traslados trasladosd = new Comprobante.Conceptos.Concepto.Impuestos.Traslados();
        for (int k = 0; k < node.getChildren().size(); k++ )
        {
            Element nodek = node.getChildren().get(k);


            if("Traslados".equals(nodek.getName()))
            {

                for (int l = 0; l < nodek.getChildren().size(); l++ )
                {
                    Element nodel = nodek.getChildren().get(k);

                    Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado trasladod = new Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado();
                    trasladod.setBase(BigDecimalNodeValue(nodel,"Base"));
                    trasladod.setImporte(BigDecimalNodeValue(nodel,"Importe"));
                    trasladod.setImpuesto(NodeValue(nodel,"Impuesto"));
                    trasladod.setTasaOCuota(BigDecimalNodeValue(nodel,"TasaOCuota"));
                    trasladod.setTipoFactor(NodeValue(nodel,"TipoFactor"));


                    trasladosd.getTraslado().add(trasladod);
                }
                
                
            }
            
        }
        

        return trasladosd;
                            
    }
    
    private Comprobante.Impuestos impuestoshead(Element node)
    {

        Comprobante.Impuestos impuestos = new Comprobante.Impuestos();
        Comprobante.Impuestos.Retenciones retencionesd = new Comprobante.Impuestos.Retenciones();
        Comprobante.Impuestos.Traslados trasladosd = new Comprobante.Impuestos.Traslados();
        
        
        for (int k = 0; k < node.getChildren().size(); k++ )
        {
            Element nodek = node.getChildren().get(k);

            if("Retenciones".equals(nodek.getName()))
            {

                for (int l = 0; l < nodek.getChildren().size(); l++ )
                {
                    Element nodel = nodek.getChildren().get(l);

                    Comprobante.Impuestos.Retenciones.Retencion retenciond = new Comprobante.Impuestos.Retenciones.Retencion();
                    retenciond.setImporte(BigDecimalNodeValue(nodel,"Importe"));
                    retenciond.setImpuesto(NodeValue(nodel,"Impuesto"));


                    retencionesd.getRetencion().add(retenciond);
                }
            }

            if("Traslados".equals(nodek.getName()))
            {

                for (int l = 0; l < nodek.getChildren().size(); l++ )
                {
                    Element nodel = nodek.getChildren().get(l);

                    Comprobante.Impuestos.Traslados.Traslado trasladod = new Comprobante.Impuestos.Traslados.Traslado();
                    trasladod.setImporte(BigDecimalNodeValue(nodel,"Importe"));
                    trasladod.setImpuesto(NodeValue(nodel,"Impuesto"));
                    trasladod.setTasaOCuota(BigDecimalNodeValue(nodel,"TasaOCuota"));
                    trasladod.setTipoFactor(NodeValue(nodel,"TipoFactor"));

                    trasladosd.getTraslado().add(trasladod);
                }
                
                
            }
            
        }
        if(retencionesd.getRetencion().size()>0)
            impuestos.setRetenciones(retencionesd);
        
        if(trasladosd.getTraslado().size()>0)
            impuestos.setTraslados(trasladosd);
        
        impuestos.setTotalImpuestosRetenidos(BigDecimalNodeValue(node,"TotalImpuestosRetenidos"));
        impuestos.setTotalImpuestosTrasladados(BigDecimalNodeValue(node,"TotalImpuestosTrasladados"));
        
        return impuestos;
                            
    }
                            
                            
                            
    private String NodeValue(Element node, String attribute)
    {
        String Value;
        try
        {
            Value =  node.getAttributeValue(attribute);
            return Value;
        }
        catch(Exception ex)
        {
            return "";
        }
    }
    
    private BigDecimal BigDecimalNodeValue(Element node, String attribute)
    {
        BigDecimal Value;
        try
        {
            Value =new BigDecimal(node.getAttributeValue(attribute).toString());
            return Value;
        }
        catch(Exception ex)
        {
            return BigDecimal.valueOf(0.000000);
        }
    }
    
    private String AreaReceptor(Comprobante comprobante, TimbreFiscalDigital timbre)
    {
        String html = "";
        html += "<tr><td> \n";
        html += "<table style='width:100%;'> \n";
        html +="<tr> \n";
        html +="<td class='Top Center HeadRojo Head12' style='width:60%; max-width:60%; min-width: 60%;' colspan='2'>INFORMACION DEL CLIENTE</td>";
        html +="<td class='Top Center HeadRojo Head12' style='width:40%; max-width:40%; min-width: 40%;' colspan='2'>INFORMACION ADICIONAL</td>";
        html +="</tr>";
        html += "<tr> \n";
        html += "<td style='width:20%; max-width:20%; min-width: 20%;'>Nombre del Cliente: </td> \n";
        html += "<td style='width:40%; max-width:40%; min-width: 40%;'><b>" + comprobante.getReceptor().getNombre()  + "</b></td> \n";
        html += "<td style='width:15%; max-width:15%; min-width: 15%;'>No. Certificado: </td>";
        html += "<td style='width:25%; max-width:25%; min-width: 25%;'><b>" + comprobante.getNoCertificado()  + "</b></td> \n";
        html += "</tr> \n";
        html += "<tr style='background-color:#f4f2f2;'> \n";
        html += "<td>R.F.C.:</td> \n";
        html += "<td><b>" + comprobante.getReceptor().getRfc()  + "</b></td> \n";
        html += "<td>Metodo de Pago:</td> \n";
        html += "<td style='font-weight: bold;'><b>" +  MetodoPago  + "</b></td> \n";
        html += "</tr> \n";

        html += "<tr> \n";
        html += "<td>Uso del CFDI:</td> \n";
        html += "<td><b>" +  CasoUso  + "</b></td> \n";
        html += "<td>Forma de Pago: </td> \n";
        html += "<td><b>" +  FormaPago  + "</b></td> \n";
        html += "</tr> \n";

        html += "<tr style='background-color:#f4f2f2;'> \n";
        html += "<td>Moneda:</td> \n";
        html += "<td><b>" +  comprobante.getMoneda()  + "</b></td> \n";
        html += "<td>Condiciones de Pago:</td> \n";
        html += "<td><b>Contado</b></td> \n";
        html += "</tr> \n";
        html += "</table> \n";
        html += "</td></tr> \n";
                    
        return html;
    }
    private String AreaHeader(Comprobante comprobante, TimbreFiscalDigital timbre)
    {
        String html = "";
        html += "<tr><td> \n";
        html += "<table style='width:100%;'> \n";
        html += "<tr> \n";
        html += "<td style='width:20%; max-width:20%; min-width:20%;'> \n";
            html += "<img src='" + PathLogo + "' style='width:180px; max-width:180px; min-width:180px;' /> \n";
        html += "</td> \n";

        html += "<td style='width:60%; max-width:60%; min-width:60%;'> \n";
            html += "<table style='width:100%;'> \n";
            html += "<tr><td class='Center Head18'> \n" + comprobante.getEmisor().getNombre() + "</td></tr> \n";
            html += "<tr><td class='Center Head10 Negrita'>Sucursal " + Sucursal + ",R.F.C. " + comprobante.getEmisor().getRfc()+"</td></tr> \n";
            html += "<tr><td class='Center Head10'> Regimen Fiscal " + RegimenFiscalDesc + "</td></tr> \n";
            html += "<tr><td class='Center Head8'>" + Direccion + "</td></tr> \n";
            html += "<tr><td class='Center Head8'>Telefonos " + Telefonos + ", Correo Electronico " + CorreoElectronico + "</td></tr> \n";
            

            html += "</table> \n";

        html += "</td> \n";
        html += "<td style='width:20%; max-width:20%; min-width: 20%;'> \n";
            html += "<table style='width:100%;'> \n";
            html += "<tr> \n";
            html += "<td class='Center HeadRojo Head12 Negrita' colspan='3'>FACTURA</td> \n";
            html += "</tr> \n";

            
            /*html += "<td class='Center HeadClaro' width='50%'>Tipo Comprobante</td> \n";
            if(comprobante.getTipoDeComprobante().name().equals("I"))
            {
                html += "<td class='HeadBlanco' width='50%'>INGRESO</td> \n";
                    
            }
            
            html += "</tr> \n";*/

            html += "<tr class='Center Text10 HeadClaro'> \n";
            html += "<td style='width:50%; max-width:50%; min-width: 50%;'>Tipo Comprobante</td> \n";
            html += "<td style='width:20%; max-width:20%; min-width: 20%;'>Serie</td> \n";
            html += "<td style='width:30%; max-width:30%; min-width: 30%;'>Folio</td> \n";
            html += "</tr> \n";

            html += "<tr> \n";
            if(comprobante.getTipoDeComprobante().name().equals("I"))
            {
                html += "<td class='HeadBlanco'>INGRESO</td> \n";
                    
            }
            html += "<td class='HeadBlanco'><b>" + comprobante.getSerie() + "</b></td> \n";
            html += "<td class='HeadBlanco'><b>" + comprobante.getFolio() + "</b></td> \n";
            html += "</tr> \n";


            html += "<tr class='HeadRojo Center'> \n";
            html += "<td colspan='3'>FECHA EMISIÓN</td> \n";
            html += "</tr> \n";


            html += "<tr class='HeadBlanco Negrita'> \n";
            html += "<td colspan='3'><b>" + comprobante.getFecha() + "</b></td> \n";
            html += "</tr> \n";

            html += "<tr class='Center HeadRojo'> \n";
            html += "<td colspan='3'>LUGAR EXPEDICIÓN</td> \n";
            html += "</tr> \n";


            html += "<tr class='HeadBlanco Negrita'> \n";
            html += "<td colspan='3'><b>" + comprobante.getLugarExpedicion() + "</b></td> \n";
            html += "</tr> \n";

            html += "</table> \n";

        html += "</td> \n";
        html += "</tr> \n";
        html += "</table> \n";
        html += "</td></tr> \n";
        return html;
    }
    
    private String AreaConceptos(Comprobante comprobante, TimbreFiscalDigital timbre)
    {
        String html = "";
        html += "<tr><td> \n";
        html += "<table style='width:100%;'> \n";
        html += "<tr> \n";
        html += "<td class='Center HeadRojo Head12 Negrita' colspan='14'>SECCIÓN DE CONCEPTOS DEL COMPROBANTE</td> \n";

        html += "<tr> \n";
        
        html += "<td class='HeadClaro Head10 Center' style='width:6%; max-width:7%; min-width: 7%;' rowspan='2'>Código SAT</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:10%; max-width:8%; min-width: 8%;' rowspan='2'>Código Barras</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:4%; max-width:4%; min-width: 4%;' rowspan='2'>Cant.</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:4%; max-width:4%; min-width: 4%;' rowspan='2'>Clave Und.</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:3%; max-width:3%; min-width: 3%;' rowspan='2'>Und.</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:20%; max-width:20%; min-width: 20%;' rowspan='2'>Descripcion</td> \n";

        html += "<td class='HeadClaro Head10 Center' style='width:6%; max-width:7%;min-width: 7%;' rowspan='2'>Valor Unitario</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:5%; max-width:5%; min-width: 5%;' rowspan='2'>Dcto.</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:7%; max-width:7%; min-width: 7%;' rowspan='2'>Importe</td> \n";
        html += "<td class='HeadClaro Head10 Center' colspan='5'>Impuestos</td></tr> \n";
        html += "<tr class='HeadClaro Head10 Center'>\n";
        html += "<td class='HeadClaro Head10 Center' style='width:7%; max-width:7%; min-width: 7%;'>Base</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:6%; max-width:6%; min-width: 6%;'>Impuesto</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:5%; max-width:5%; min-width: 5%;'>Tipo Factor</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:5%; max-width:5%; min-width: 5%;'>Tasa/ Cuota</td> \n";
        html += "<td class='HeadClaro Head10 Center' style='width:7%; max-width:7%; min-width: 7%;'>Importe</td> \n";
        
        html += "</tr> \n";
        
                boolean EsBlanco=true;
                
                String BackColor="#ffffff";
                
                List<Comprobante.Conceptos.Concepto> list = comprobante.getConceptos().getConcepto();
                
                for (int i=0; i < comprobante.getConceptos().getConcepto().size(); i++){

                Comprobante.Conceptos.Concepto concepto =  comprobante.getConceptos().getConcepto().get(i);
                Comprobante.Conceptos.Concepto.Impuestos conceptoImpuestos =  concepto.getImpuestos();
                
                /*
                List<String> Descripcion = HLFormats.ForceWordWrap(concepto.getDescripcion(),30);
                
                int rowspan = Descripcion.size();
                */
                
                int rowspan=1;
                if(EsBlanco)
                {
                    BackColor="#FFFFFF";
                    EsBlanco=false;
                }
                else
                {
                    BackColor="#f4f2f2";
                    EsBlanco=true;
                }
                
                html += "<tr class='LineBottom' style='background-color:" + BackColor + ";'> \n";
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important;'> \n" + concepto.getClaveProdServ() + "</td> \n";
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important;'> \n" + concepto.getNoIdentificacion() + "</td> \n";
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'> \n" + concepto.getCantidad() + "</td> \n";
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'> \n" + concepto.getClaveUnidad() + "</td> \n";
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'> \n" + concepto.getUnidad() + "</td> \n";
               
                /*if(rowspan==1)
                    html += "<td class='LineBottom Text9' style='word-break: break-all !important;'> \n" + Descripcion.get(0) + "</td> \n";
                else
                    html += "<td class='Text9' style='word-break: break-all !important;'> \n" + Descripcion.get(0) + "</td> \n";
*/
                
                html += "<td class='Text9' style='word-break: break-all !important;'> \n" + concepto.getDescripcion() + "</td> \n";
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'> \n" + HLFormats.CurrencyFormat(concepto.getValorUnitario()) + "</td> \n";
                
                if(Double.parseDouble(concepto.getDescuento().toString())>0)
                    html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'>" + HLFormats.CurrencyFormat(concepto.getDescuento()) + "</td> \n";
                else
                    html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>-</td> \n";
                
                html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'>\n" + HLFormats.CurrencyFormat(concepto.getImporte()) + "</td> \n";
                
                try
                {
                    if(conceptoImpuestos != null )
                    {


                            if(conceptoImpuestos.getRetenciones() != null)
                            {
                                Comprobante.Conceptos.Concepto.Impuestos.Retenciones conceptoRetenciones = conceptoImpuestos.getRetenciones();
                                for (int j=0; j < conceptoRetenciones.getRetencion().size(); j++){

                                    Comprobante.Conceptos.Concepto.Impuestos.Retenciones.Retencion conceptoRetencion =  conceptoRetenciones.getRetencion().get(j);
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='wword-break: break-all !important; text-align:right;'>" +  HLFormats.CurrencyFormat(conceptoRetencion.getBase()) + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>" + conceptoRetencion.getImpuesto() + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>" + conceptoRetencion.getTipoFactor() + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>" + conceptoRetencion.getTasaOCuota() + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'>" +  HLFormats.CurrencyFormat(conceptoRetencion.getImporte()) + "</td> \n"; 


                                }

                            }

                            if(conceptoImpuestos.getTraslados() != null)
                            {
                                Comprobante.Conceptos.Concepto.Impuestos.Traslados conceptoTraslados = conceptoImpuestos.getTraslados();
                                for (int j=0; j < conceptoTraslados.getTraslado().size(); j++){

                                    Comprobante.Conceptos.Concepto.Impuestos.Traslados.Traslado traslado =  conceptoTraslados.getTraslado().get(j);
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'>" + HLFormats.CurrencyFormat(traslado.getBase()) + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>" + traslado.getImpuesto() + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>" + traslado.getTipoFactor() + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:center;'>" + traslado.getTasaOCuota() + "</td> \n"; 
                                    html+="<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'>" +  HLFormats.CurrencyFormat(traslado.getImporte()) + "</td> \n"; 
                                }

                            }

                    }
                    else
                    {
                        html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'> " + HLFormats.CurrencyFormat(concepto.getImporte()) + "</td> \n";
                        html += "<td rowspan = '" + rowspan + "' class='LineBottom Center Text9'> \n";
                        html += "002";
                        html += "</td><td rowspan = '" + rowspan + "' class='LineBottom Center Text9'>Tasa</td> \n"
                                +"<td rowspan = '" + rowspan + "' class='LineBottom Center Text9'>0.00</td><td rowspan = '" + rowspan + "' class='LineBottom Right Text9'>0.00</td> \n";
                    }
                }
                catch(Exception ex2)
                {
                    html += "<td rowspan = '" + rowspan + "' class='LineBottom Text9' style='word-break: break-all !important; text-align:right;'> " + HLFormats.CurrencyFormat(concepto.getImporte()) + "</td> \n";
                        html += "<td rowspan = '" + rowspan + "' class='LineBottom Center Text9'> \n";
                        html += "002";
                        html += "</td><td rowspan = '" + rowspan + "' class='LineBottom Center Text9'>Tasa</td> \n"
                                +"<td rowspan = '" + rowspan + "' class='LineBottom Center Text9'>0.00</td> \n"
                                +"<td rowspan = '" + rowspan + "' class='LineBottom Right Text9'>0.00</td> \n";
                }
               
                
                
                /*
                if(rowspan>1)
                {   
                    for(int n=1; n<rowspan; n++)
                    {
                        html += "<tr style='background-color:" + BackColor + ";'> \n";
                        if(n==rowspan-1)
                        {
                            html += "<td class='LineBottom Text9'>" + Descripcion.get(n) + "</td>";
                        }
                        else
                        {
                            html += "<td class='Text9'>" + Descripcion.get(n) + "</td>";
                        }
                        html += "</tr> \n";
                    }
                }
                * */
            }
            html += "<tr class='top'> \n";
            html += "<td colspan='9' class='Top HeadClaro Head12'>" + NumberToLetterConverter.convertNumberToLetter(Double.valueOf(comprobante.getTotal().toString())) + "</td> \n";
            html += "<td colspan='3' class='LineBottom Top Right HeadClaro Text10'>Subtotal:</td> \n";
            html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>" + HLFormats.CurrencyFormat(comprobante.getSubTotal()) + "</td> \n";
            html += "</tr> \n";
            html += "<tr> \n";
            html += "<td colspan='2' class='Text10'>Observaciones:</td> \n";
            html += "<td colspan='7' class='Text10 Negrita'>" + Observaciones + "</td> \n";
            html += "<td colspan='3' class='LineBottom Top Right HeadClaro Text10'>Impuestos Trasladados:</td> \n";
            try
                {
                    if(comprobante.getImpuestos() != null)
                    {
                        if(comprobante.getImpuestos().getTotalImpuestosTrasladados() != null)
                        {
                            if(Double.parseDouble(comprobante.getImpuestos().getTotalImpuestosTrasladados().toString())>0)
                                html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>" + HLFormats.CurrencyFormat(comprobante.getImpuestos().getTotalImpuestosTrasladados()) + "</td> \n";
                            else
                                html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
                        }   
                        else
                        {
                            html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
                        }
                    }
                    else
                    {
                        html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
                    }
                }
            catch(Exception ex2)
            {
                
            }
            html += "</tr> \n";

            
            
            
            
            html += "<tr> \n";
            html += "<td colspan='9' class='Negrita Text10'></td> \n";
            html += "<td colspan='3' class='LineBottom Top Right HeadClaro Text10'>Impuestos Retenidos:</td> \n";
            
            try
                {
            if(comprobante.getImpuestos() != null)
            {
                if(comprobante.getImpuestos().getTotalImpuestosRetenidos() != null)
                {
                    if(Double.parseDouble(comprobante.getImpuestos().getTotalImpuestosRetenidos().toString())>0)
                        html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>" + HLFormats.CurrencyFormat(comprobante.getImpuestos().getTotalImpuestosTrasladados()) + "</td> \n";
                    else
                        html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
                }   
                else
                {
                    html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
                }
            }
            else
               {
                   html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
               }
            }
            catch(Exception ex2)
            {
                html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
            }
            html += "</tr> \n";
            
            html += "<tr> \n";
            html += "<td colspan='9' class='Negrita Text10'></td> \n";
            html += "<td colspan='3' class='LineBottom Top Right HeadClaro Text10'>Total Descuentos:</td> \n";
            
            if(comprobante.getDescuento()!=null)
                {
                    html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>" + HLFormats.CurrencyFormat(comprobante.getDescuento())+"</td> \n";
                }
                else
                {
                    html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>-</td> \n";
                }
            html += "</tr> \n";
            html += "<tr> \n";
            html += "<td colspan='9' class='Negrita Text10'></td> \n";
            html += "<td colspan='3' class='LineBottom Top Right HeadClaro Text10'>Total:</td> \n";
            html += "<td colspan='2' class='LineBottom Top Right Negrita Text10'>" + HLFormats.CurrencyFormat(comprobante.getTotal()) + "</td> \n";
            
            html += "</tr> \n";
            html += "</table> \n";
            html += "</td> \n"
                    +"</tr> \n";
        return html;
    }
    
    
   
    
    
    private String AreaTimbre(Comprobante comprobante, TimbreFiscalDigital timbre)
    {
        String html = "";
        html += "<tr><td> \n";
        html += "<table style='table-layout:fixed; height:2in; min-height:2in; max-height:2in'> \n";
                    html += "<tr>";
                    html += "<td class='Center HeadRojo Head12 Negrita' colspan='4'>INFORMACIÓN DEL TIMBRE FISCAL DIGITAL</td> \n";
                    html += "</tr> \n";
                    html += "<tr> \n";
                    html += "<td class='Center HeadClaro Negrita' style='width:20%; max-width:20%; min-width:20%;'>R.F.C. del PAC </td> \n";
                    html += "<td class='Center HeadClaro Negrita' style='width:26.66%; max-width:26.66%; min-width:26.66%;'>Folio Fiscal</td> \n";
                    html += "<td class='Center HeadClaro Negrita' style='width:26.66%; max-width:26.66%; min-width:26.66%;'>No. Certificado Digital SAT</td> \n";
                    html += "<td class='Center HeadClaro Negrita' style='width:26.66%; max-width:26.66%; min-width:26.66%;'>Fecha y Hora de Certificaci&oacute;n</td> \n";
                    html += "</tr> \n";
                    html += "<tr class='Top Center Gris'> \n";
                    html += "<td style='width:20%; max-width:20%; min-width:20%;'>" + timbre.getRfcProvCertif() + "</td> \n";
                    html += "<td style='width:26.66%; max-width:26.66%; min-width:26.66%;'>" + timbre.getUUID() + "</td> \n";
                    html += "<td style='width:26.66%; max-width:26.66%; min-width:26.66%;'> \n" + timbre.getNoCertificadoSAT()  + "</td> \n";
                    html += "<td style='width:26.66%; max-width:26.66%; min-width:26.66%;'> \n" + timbre.getFechaTimbrado() + "</td> \n";
                    html += "</tr> \n";
                    html += "<tr> \n";
                    html += "<td colspan='4'></td> \n";
                    html += "</tr> \n";
                    String CadenaOriginal = "||" + timbre.getVersion() + "|" + timbre.getUUID() + "|" + timbre.getFechaTimbrado() + "|" + timbre.getSelloCFD()
                        + "|" + timbre.getNoCertificadoSAT() + "||";
                    String[] SelloCFD = HLFormats.ForceWrap(timbre.getSelloCFD(), 31);
                    String[] SelloSAT = HLFormats.ForceWrap(timbre.getSelloSAT(), 31);
                    String[] SelloCadenaOriginal = HLFormats.ForceWrap(CadenaOriginal, 31);
                    
                    int Renglones = 1;
                    
                    if(SelloCFD.length>Renglones)
                        Renglones=SelloCFD.length;
                    
                    if(SelloSAT.length>Renglones)
                        Renglones=SelloSAT.length;
                    
                    if(SelloCadenaOriginal.length>Renglones)
                        Renglones=SelloCadenaOriginal.length;
                    html += "<tr> \n";
                    html += "<td rowspan='" + Renglones+1 + "'> \n";
                    html += "<img src='" + PathQR + "' style='width:180px; max-width:180px; min-width:180px; height:180px;' /> \n";
                    html += "</td>";
                    html += "<td class='Center HeadClaro Negrita' style='word-break: break-all !important; width:26.66%; max-width:26.66%; min-width:26.66%;'>Sello Digital del Emisor:</td> \n";
                    html += "<td class='Center HeadClaro Negrita' style='word-break: break-all !important; width:26.66%; max-width:26.66%; min-width:26.66%;'>Sello Digital del SAT:</td> \n";
                    html += "<td class='Center HeadClaro Negrita' style='word-break: break-all !important; width:26.66%; max-width:26.66%; min-width:26.66%;'>Cadena Original del Complemento de certificación del SAT:</td> \n";
                    html += "</tr> \n";
                    for(int n=0; n<Renglones; n++)
                    {
                        int RowSpanSelloCFD = 1;
                        int RowSpanSelloSAT = 1;
                        int RowSpanCadenaOriginal = 1;
                        
                        html += "<tr class='Top Gris'> \n";
                        if(n<=SelloCFD.length-1)
                        {
                            if(n==SelloCFD.length-1) {
                                RowSpanSelloCFD=Renglones-SelloCFD.length+1;
                            }
                            
                            html += "<td rowspan=" + RowSpanSelloCFD + " style='word-break: break-all !important;' >" + SelloCFD[n]  + "</td> \n";
                        }
                            
                        if(n<=SelloSAT.length-1)
                        {
                            if(n==SelloSAT.length-1)
                                RowSpanSelloSAT=Renglones-SelloSAT.length+1;
                            
                            html += "<td rowspan=" + RowSpanSelloSAT + " style='word-break: break-all !important;' >" + SelloSAT[n]  + "</td> \n";
                        }
                        
                        if(n<=SelloCadenaOriginal.length-1)
                        {
                            if(n==SelloCadenaOriginal.length-1)
                                RowSpanCadenaOriginal=Renglones-SelloCadenaOriginal.length+1;
                            
                            html += "<td rowspan=" + RowSpanCadenaOriginal + " style='word-break: break-all !important;' >" + SelloCadenaOriginal[n]  + "</td> \n";
                        }
                        

                        html += "</tr> \n";
                    }
                    html += "</table> \n";
        html += "</td></tr> \n";
        return html;
    }
    
    public String CrearHTML(Comprobante comprobante, TimbreFiscalDigital timbre)
    {
        String html = "";
        
        try
        {   
                html +="<!DOCTYPE html>\n";
                html+="<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>\n";
                html +="<html> \n";
                html +="<head> \n";
                html +="<title>factura</title>\n";
                html +="<style type='text/css'> \n";
                html +="body table{ \n";
                html +="font: 10px arial, sans-serif; \n";
                html +="padding: 0px; \n";
                html +="} \n";
                
          
                    html +="@page { \n"
                    +"margin-left: 0.2in; \n"
                    +"margin-right: 0.2in; \n"
                    +"size: 8.5in 11.1in;\n"
                    +"} \n";
                
                html +=".Head12{ \n";
                html +="font: 12px arial, sans-serif; \n";
                html +="font-weight: bold;  \n";
                html +="word-break: normal; \n";
                html +="size: letter; \n";
                html +="} \n";
                
                html +=".Head8{ \n";
                html +="font: 8px arial, sans-serif; \n";
                html +="font-weight: bold; \n";
                html +="word-break: normal; \n";
                html +="} \n";
                
                html +=".Head10{ \n";
                html +="font: 10px arial, sans-serif; \n";
                html +="font-weight: bold;  \n";
                html +="word-break: normal; \n";
                html +="} \n";
                
                html +=".Text8{ \n";
                html +="font: 8px arial, sans-serif; \n";
                html +="} \n";
                
                html +=".Text10{ \n";
                html +="font: 10px arial, sans-serif; \n";
                html +="} \n";
                
                html +=".Text9{ \n";
                html +="font: 10px arial, sans-serif; \n";
                html +="} \n";
                
                html +=".Head18{ \n";
                html +="font: 18px arial, sans-serif; \n";
                html +="font-weight: bold;  \n";
                html +="} \n";
                
                html +=".divCadenas { \n";
                html +="width: 200px; \n";
                html +="background-color: white; \n";
                html +="word-break: break-all; vertical-align: text-top; \n";
                html +="} \n";
                
                html +=".LineBottom { \n";
                html +="border-bottom: 1px solid #f4f2f2; \n";
                html +="padding: 0px; \n";
                
                html +="} \n";
                
                html +=".HeadRojo { \n";
                html +="background-color: " + ColorPrimario + "; \n";
                html +="color: white; \n";
                html +="word-break: break-word; \n";
                html +="font-weight: bold; \n";
                html +="text-align: center; \n";
                html +="vertical-align: text-bottom; \n";
                html +="} \n";
                
                
                html +=".HeadBlanco { \n";
                html +="background-color: white; \n";
                html +="color: black; \n";
                html +="word-break: break-word; \n";
                html +="font-weight: bold;  \n";
                html +="text-align:center; \n";
                html +="vertical-align: text-bottom; \n";
                html +="} \n";

                html +=".Gris { \n";
                html +="color: #3f3f3f; \n";
                html +="word-break: break-all; \n";
                html +="text-align: center; \n";
                html +="vertical-align: text-top; \n";
                html +="} \n";
                
                html +=".HeadClaro { \n";
                html +="background-color:" + ColorSecundario + "; \n";
                html +="color: black; \n";
                html +="word-break: break-word; \n";
                html +="} \n";
                

                html +=".Center { \n";
                html +="text-align: center; \n";
                html +="vertical-align: middle; \n";
                html +="} \n";
                
                html +=".Right { \n";
                html +="text-align: right; \n";
                html +="} \n";
                
                html +=".Negrita { \n";
                html +="font-weight: bold;  \n";
                html +="} \n";
                
                
                
                html +=".Top { \n";
                html +="vertical-align: text-top; \n";
                html +="} \n";
                
                html +=".Bottom { \n";
                html +="vertical-align: text-bottom; \n";
                html +="} \n";
                
                
                html +="</style> \n";
                html +="</head> \n";
                html +="<body> \n";
                html += "<table style='width: 100%; height: 10in;'> \n";
                
                html +=AreaHeader(comprobante, timbre);
                html +=AreaReceptor(comprobante,timbre);
                html +=AreaConceptos(comprobante,timbre);
                //html +=AreaTotales(comprobante,timbre);
                html +=AreaTimbre(comprobante,timbre);
                html += "<tr style='font-size: xx-small;'> \n";
                html += "<td class='Center HeadBlanco'>Este documento es una representacion impresa de un CFDI ver. " + comprobante.getVersion() + "</td> \n";
                html += "</tr> \n";

                html += "</table>";
                html +="</body> \n";
                //html = html.Replace("'", @"""");

                /*html = html.replace("á", "&aacute;");
                html = html.replace("é", "&eacute;");
                html = html.replace("í", "&iacute;");
                html = html.replace("ó", "&oacute;");
                html = html.replace("ú", "&uacute;");
                html = html.replace("ñ", "&ntilde;");*/
        } catch (Exception ex) {

            System.out.println(ex);
        }
        return html;
    }
}
