/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.facturas33.diverza;

/**
 *
 * @author Ruben
 */
public class DiverzaOK {
    private String content;

    private String ref_id;

    private String uuid;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getRef_id ()
    {
        return ref_id;
    }

    public void setRef_id (String ref_id)
    {
        this.ref_id = ref_id;
    }

    public String getUuid ()
    {
        return uuid;
    }

    public void setUuid (String uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", ref_id = "+ref_id+", uuid = "+uuid+"]";
    }
}
