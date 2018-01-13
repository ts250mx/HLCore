/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.diverza;

public class DiverzaError
{
    private String message;

    private String level;

    private String error_details;

    private Request_headers[] request_headers;

    private Comp_error_details comp_error_details;

    private String error_id;

    private String code;

    private String stage;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getLevel ()
    {
        return level;
    }

    public void setLevel (String level)
    {
        this.level = level;
    }

    public String getError_details ()
    {
        return error_details;
    }

    public void setError_details (String error_details)
    {
        this.error_details = error_details;
    }

    public Request_headers[] getRequest_headers ()
    {
        return request_headers;
    }

    public void setRequest_headers (Request_headers[] request_headers)
    {
        this.request_headers = request_headers;
    }

    public Comp_error_details getComp_error_details ()
    {
        return comp_error_details;
    }

    public void setComp_error_details (Comp_error_details comp_error_details)
    {
        this.comp_error_details = comp_error_details;
    }

    public String getError_id ()
    {
        return error_id;
    }

    public void setError_id (String error_id)
    {
        this.error_id = error_id;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getStage ()
    {
        return stage;
    }

    public void setStage (String stage)
    {
        this.stage = stage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", level = "+level+", error_details = "+error_details+", request_headers = "+request_headers+", comp_error_details = "+comp_error_details+", error_id = "+error_id+", code = "+code+", stage = "+stage+"]";
    }
}