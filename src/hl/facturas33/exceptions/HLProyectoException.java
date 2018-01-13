/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.exceptions;

import java.io.IOException;

/**
 *
 * @author Ruben
 */
public class HLProyectoException extends Exception {
    public HLProyectoException(String ErrorDetail) throws IOException {
        System.out.println(ErrorDetail); 
        throw new java.io.IOException(ErrorDetail);
    }
}
