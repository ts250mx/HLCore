/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.funciones;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Ruben
 */
public class FactorySimple {
    
    public static javax.net.ssl.SSLSocketFactory getFactorySimple() 
        throws Exception {
        //java 7
        
         TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

        } };
         
        SSLContext context = SSLContext.getInstance("SSL");
        System.out.println("Iniciando trustAllCerts");
        //SSLContext context = SSLContext.getInstance("TLSv1");
        // Init the SSLContext with a TrustManager[] and SecureRandom()
        //context.init(null, null, null);
 
            //java 8
        //SSLContext context = SSLContext.getInstance("TLSv1.2");

        context.init(null, trustAllCerts, new java.security.SecureRandom());

        return context.getSocketFactory();

    }
}
