/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.diverza;


public class Comp_error_details
{
    private String field;

    private String message;

    private String code;

    public String getField ()
    {
        return field;
    }

    public void setField (String field)
    {
        this.field = field;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [field = "+field+", message = "+message+", code = "+code+"]";
    }
}
			
			