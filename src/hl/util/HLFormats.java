/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.util;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author Ruben
 */
public class HLFormats{

    private Locale locale = new Locale("ES","MX");
    private String Language = "ES";
    private String Country = "MX";
    
    
    public HLFormats() {
        
    }
    
    public HLFormats(String language, String country) {
        Language = language;
        Country =country;
        
    }
    
    public static String LeftZeros(long value, String pattern)
    {
        Formatter fmt = new Formatter();

        return fmt.format("%0" + pattern.length() +"d",value).toString();


    }
    
    public static String CurrencyFormat(String value)
    {

        String pattern = "$###,##0.00";
        DecimalFormat currency = new DecimalFormat(pattern);
        return currency.format(Double.valueOf(value));

    }
    
    public static String CurrencyFormat(BigDecimal value)
    {

        String pattern = "$###,##0.00";
        DecimalFormat currency = new DecimalFormat(pattern);
        return currency.format(value);

    }
    
    public static String CurrencyFormat(double value)
    {

        String pattern = "$###,##0.00";
        DecimalFormat currency = new DecimalFormat(pattern);
        return currency.format(value);

    }
        
    public static String ToFormat(double value, String pattern)
    {

        DecimalFormat currency = new DecimalFormat(pattern);
        return currency.format(value);

    }
    

    public static double Round(double value, int decimals)
    {
        int num=(int) Math.pow(10,decimals);
        return Math.rint(value*num)/num;
    }
    public static HLDateFormats DateFormat() {
        Date datevalue = new Date();
        return DateFormat(datevalue);  
    }
    

    
    
    public static HLDateFormats DateFormat(Date value) {
        HLDateFormats hld = new HLDateFormats(value);
        return hld;  
    }
    
    
    public static HLDateFormats DateFormat(String value) {
        HLDateFormats hld = new HLDateFormats(value);
        return hld;  
    }

    

    /*public static String getBase64Encode(String token) {
        String encodedBytes;
        encodedBytes = Base64.encode(token.getBytes());
        return encodedBytes;
    }
*/
    
    public static String getBase64Decode(String Texto) throws UnsupportedEncodingException {
        byte[] decoded = DatatypeConverter.parseBase64Binary(Texto);
        String decodedText = new String(decoded, "UTF-8");
        return decodedText;
    }
    /*
    @SuppressWarnings("empty-statement")
    public static String[] ForceWordWrap(String Text, int ForceLength)
    {
        
        String[] parts = Text.split(" ");
        String BufferText ="";
        String BufferTextAnt ="";
        int Rows=0;

        int RowIndex = 0;
        int TextLength = Text.length();
        int WordLength = 0;
        Rows =TextLength/ForceLength;
        if(!EsEntero(Math.sqrt(TextLength)/Math.sqrt(ForceLength)))
        {
            Rows++;
        }
        String[] TextReturn = new String[Rows];
        
        String[] TextAdd;
        if(parts.length>1)
        {
            if(ForceLength >= TextLength)
            {
                TextReturn[0]=Text);
                
            }
            else              
            {
                BufferTextAnt=parts[0];
                BufferText=parts[0];
                for (int i=0; i < parts.length; i++) 
                {       
                    if(i==(parts.length-1))
                        TextReturn.addAll(ForceWordWrap(BufferTextAnt, ForceLength));
                    else
                    {
                        BufferText+=" "+parts[i+1];
                    
                        WordLength=BufferText.length();
                        if(WordLength>=ForceLength)
                        {   
                            TextReturn.addAll(ForceWordWrap(BufferTextAnt, ForceLength));

                            BufferText=parts[i+1];
                            BufferTextAnt=parts[i+1];
                        }
                        else
                        {
                            BufferTextAnt+=" "+parts[i+1];
                        }
                    }
                }
                
                
            }
        }
        else
        {
            TextReturn = ForceWrap(Text, ForceLength);
        }
        
        return TextReturn;
    }
    */
    public static boolean EsEntero(double numero)
    { 
        int entero; 
        boolean esEntero = true; 
        
  
        
        return (numero == (int) numero); 
    } 
    public static String[] ForceWrap(String Text, int ForceLength)
    {
        
        String BufferText ="";
        int Rows=0;
        
        
        int RowIndex = 0;
        int TextLength = Text.length();
        
        Rows =TextLength/ForceLength;
        if(!EsEntero(Math.sqrt(TextLength)/Math.sqrt(ForceLength)))
        {
            Rows++;
        }
        String[] TextReturn = new String[Rows];
        
        
        int Inicio = 0;
        for (RowIndex=0; RowIndex < Rows; RowIndex++) 
        {
            if(Inicio+ForceLength>=TextLength)
            {
                TextReturn[RowIndex]=Text.substring(Inicio,TextLength);
            }
            else
            {
                TextReturn[RowIndex]=Text.substring(Inicio,Inicio+ForceLength);
            }
            Inicio+=ForceLength;
            
        }

        
        
        return TextReturn;
    }
    
    
    public static String ValidaNulosStr(String Texto) {
        String TextoNuevo = "";
        try
        {
            Texto = (Texto.isEmpty())?"":Texto; 
        }
        catch(Exception ex)
        {
            
        }
        return Texto;
        
    }
    
    public static BigDecimal ValidaNulos(BigDecimal valor) {
        BigDecimal valornuevo = BigDecimal.ZERO;
        try
        {
            valor = (BigDecimal) ((valor.equals(null))?0 :valor); 
        }
        catch(Exception ex)
        {
            
        }
        return valornuevo;
        
    }
      
    
}
