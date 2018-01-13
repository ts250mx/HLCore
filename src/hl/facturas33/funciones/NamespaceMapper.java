/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.funciones;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 *
 * @author Ruben
 */
public class NamespaceMapper extends NamespacePrefixMapper {
    private static final String CFDI_PREFIX = "cfdi"; // DEFAULT NAMESPACE
    private static final String CFDI_URI = "http://www.sat.gob.mx/cfd/3";
    
  
    

    private static final String XSI_PREFIX = "xsi";
    

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if(CFDI_URI.equals(namespaceUri)) {
            return CFDI_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { CFDI_URI};
    }
}
