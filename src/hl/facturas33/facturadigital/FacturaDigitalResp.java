/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.facturadigital;

public class FacturaDigitalResp
{
  private String mensaje;

  public String getMensaje() { return this.mensaje; }

  public void setMensaje(String mensaje) { this.mensaje = mensaje; }

  private int codigo;

  public int getCodigo() { return this.codigo; }

  public void setCodigo(int codigo) { this.codigo = codigo; }

  private Cfdi cfdi;

  public Cfdi getCfdi() { return this.cfdi; }

  public void setCfdi(Cfdi cfdi) { this.cfdi = cfdi; }
}