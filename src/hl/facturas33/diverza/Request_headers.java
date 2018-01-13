/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.diverza;

public class Request_headers
{
    private String host;

    public String getHost ()
    {
        return host;
    }

    public void setHost (String host)
    {
        this.host = host;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [host = "+host+"]";
    }
}