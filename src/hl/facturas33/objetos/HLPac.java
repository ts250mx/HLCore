/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.objetos;

import hl.facturas33.diverza.HLCuentaDiverza;

/**
 *
 * @author Ruben
 */
public class HLPac {

    

    int IdPac;
    String Pac;
    HLCuentaDiverza CuentaDiverza;
    
    public HLPac() {
        
        
    }
    
    public int getIdPac() {
        return IdPac;
    }

    public void setIdPac(int IdPac) {
        this.IdPac = IdPac;
    }

    public String getPac() {
        return Pac;
    }

    public void setPac(String Pac) {
        this.Pac = Pac;
    }

    public HLCuentaDiverza getCuentaDiverza() {
        return CuentaDiverza;
    }

    public void setCuentaDiverza(HLCuentaDiverza CuentaDiverza) {
        this.CuentaDiverza = CuentaDiverza;
    }
    
    
}
