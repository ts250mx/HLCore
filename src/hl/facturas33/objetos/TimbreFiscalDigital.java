/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

/**
 *
 * @author Ruben
 */
public class TimbreFiscalDigital {
    private String RfcProvCertif;

    private String SelloSAT;

    private String UUID;

    private String FechaTimbrado;

    private String Version;

    private String NoCertificadoSAT;

    private String SelloCFD;

    public String getRfcProvCertif ()
    {
        return RfcProvCertif;
    }

    public void setRfcProvCertif (String RfcProvCertif)
    {
        this.RfcProvCertif = RfcProvCertif;
    }

    public String getSelloSAT ()
    {
        return SelloSAT;
    }

    public void setSelloSAT (String SelloSAT)
    {
        this.SelloSAT = SelloSAT;
    }

    public String getUUID ()
    {
        return UUID;
    }

    public void setUUID (String UUID)
    {
        this.UUID = UUID;
    }

    public String getFechaTimbrado ()
    {
        return FechaTimbrado;
    }

    public void setFechaTimbrado (String FechaTimbrado)
    {
        this.FechaTimbrado = FechaTimbrado;
    }

    public String getVersion ()
    {
        return Version;
    }

    public void setVersion (String Version)
    {
        this.Version = Version;
    }

    public String getNoCertificadoSAT ()
    {
        return NoCertificadoSAT;
    }

    public void setNoCertificadoSAT (String NoCertificadoSAT)
    {
        this.NoCertificadoSAT = NoCertificadoSAT;
    }

    public String getSelloCFD ()
    {
        return SelloCFD;
    }

    public void setSelloCFD (String SelloCFD)
    {
        this.SelloCFD = SelloCFD;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RfcProvCertif = "+RfcProvCertif+", SelloSAT = "+SelloSAT+", UUID = "+UUID+", FechaTimbrado = "+FechaTimbrado+", Version = "+Version+", NoCertificadoSAT = "+NoCertificadoSAT+", SelloCFD = "+SelloCFD+"]";
    }
}
			
			