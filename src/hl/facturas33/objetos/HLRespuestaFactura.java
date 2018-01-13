/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

/**
 *
 * @author Ruben
 */
public class HLRespuestaFactura {

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String Serie) {
        this.Serie = Serie;
    }

    public String getXML64() {
        return XML64;
    }

    public void setXML64(String XML64) {
        this.XML64 = XML64;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public long getIdFactura() {
        return IdFactura;
    }

    public void setIdFactura(long IdFactura) {
        this.IdFactura = IdFactura;
    }

    public int getTipoSerie() {
        return TipoSerie;
    }

    public void setTipoSerie(int TipoSerie) {
        this.TipoSerie = TipoSerie;
    }
    String UUID;
    String Serie;
    String XML64;
    String Error;
    int ErrorCode;
    long IdFactura;
    int TipoSerie;
    String PDFFile;
    

    public String getPDFFile() {
        return PDFFile;
    }

    public void setPDFFile(String PDFFile) {
        this.PDFFile = PDFFile;
    }
    
}
