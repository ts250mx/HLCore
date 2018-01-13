/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.util;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import hl.log.Log;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author Ruben
 */
public class HLFilesFunctions {
    private String FilePath = "";
    private String IniPath = "";
    private String PropFileName = "config.properties";

    public String getPropFileName() {
        return PropFileName;
    }

    public void setPropFileName(String propFileName) {
        this.PropFileName = propFileName;
    }
    
    public HLFilesFunctions(String propFileName) throws FileNotFoundException, IOException, SQLException
    {
        PropFileName = propFileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        this.FilePath = filePath;
    }
    
    public String getIniPath() {
        return IniPath;
    }

    public void setIniPath(String iniPath) {
        this.IniPath = iniPath;
    }

    public Properties getP() {
        return p;
    }

    public void setP(Properties p) {
        this.p = p;
    }
    Properties p = new Properties();
    
    
    
    
    public  String getProperty(String PropertyName) throws FileNotFoundException, IOException 
    {
        Properties prop = new Properties();
        
        prop.load(new FileReader(PropFileName));
                
        return prop.getProperty(PropertyName);

            
    }
    
    
    public static String ReadFile(String Path) throws FileNotFoundException, IOException
    {
        String ReadText;
        String Text = "";
        FileReader f = new FileReader(Path);
        BufferedReader b = new BufferedReader(f);
        while((ReadText = b.readLine())!=null) {
            Text+=ReadText;
        }
        b.close();
        
        return Text;
    
    }
            
    public static void WriteFile(String Content, String Path) throws IOException
    {
        BufferedWriter bf = new BufferedWriter(new FileWriter(Path));

        bf.write(Content);

        bf.close();


    }
    
    public static void WriteImage(BufferedImage bufferedimage, String ImagePath) throws IOException
    {
        
        File file = new File(ImagePath);
        
        ImageIO.write(bufferedimage, "png", file);



    }
    

    public static void WriteFileXHTML(String Content, String Path) throws IOException
    {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(Path))) {
            Content = Content.replace("á", "&aacute;");
            Content = Content.replace("é", "&eacute;");
            Content = Content.replace("í", "&iacute;");
            Content = Content.replace("ó", "&oacute;");
            Content = Content.replace("ú", "&uacute;");
            Content = Content.replace("ñ", "&ntilde;");
            
            Content = Content.replace("Á", "&Aacute;");
            Content = Content.replace("É", "&Eacute;");
            Content = Content.replace("Í", "&Iacute;");
            Content = Content.replace("Ó", "&Oacute;");
            Content = Content.replace("Ú", "&Uacute;");
            Content = Content.replace("Ñ", "&Ntilde;");

            ByteArrayInputStream b = new ByteArrayInputStream(Content.getBytes(Charset.forName("UTF-8")));
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Tidy tidy = new Tidy();
            tidy.setXHTML(true);
            tidy.parse(b, out);


            bf.write(out.toString());
        }


    }
    public static String OS() throws UnknownHostException
    {
        Log.println("Prueba -1");
        String OSName="";
        String hostName= java.net.InetAddress.getLocalHost().getHostAddress(); 
        InetAddress addr = InetAddress.getByName(hostName); 
        Log.OK(addr.getHostName());
        Log.OK(System.getProperty("os.name"));
        OSName =System.getProperty("os.name");
        return OSName;
            
    }
    
    public static boolean EsWindows() throws UnknownHostException
    {
        Log.println("Prueba 0");
        return OS().contains("Windows");
    }
    /**
     *
     * @param path
     * @return
     * @throws UnknownHostException
     */
    public static String WindowsPath(String path) throws UnknownHostException
    {
        Log.println("Prueba 1");
        String EnvPath=path;
        if(EsWindows())
        {
            Log.println("Prueba 2");
            EnvPath = EnvPath.replace("/home/","C:\\fuentes\\");
            Log.println("Prueba 3");
            EnvPath = EnvPath.replace("/","\\");
            
        }
        Log.println("Prueba 4");
        Log.println("Prueba 2");
        return EnvPath;
        
    }

    public static String PathExist(String Path) throws UnknownHostException
    {
        Path = Path.replace(" ", "");
        Path = Path.replace(".", "");
        Path = Path.replace(";", "");
        Path = Path.replace(",", "");
        Path = Path.toLowerCase();
        
        Path=WindowsPath(Path+"/");
        File dir = new File(Path);
        
        if(!dir.exists())
        {
            boolean isDirectoryCreated = dir.mkdir();
            if (isDirectoryCreated) {
              System.out.println("successfully");
            } else {
              System.out.println("not");
            }
        }
        
        return WindowsPath(Path+"/");
    }
    
    
    
    /**
     *
     * @param HTMLPath
     * @param PDFPath
     * @throws MalformedURLException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws com.lowagie.text.DocumentException
     */
    public static void HTMLToPDF(String HTMLPath, String PDFPath) throws MalformedURLException, FileNotFoundException, IOException, com.lowagie.text.DocumentException
    {
       
        String url = new File(HTMLPath).toURI().toURL().toString();
            try (OutputStream os = new FileOutputStream(PDFPath)) {
                ITextRenderer renderer = new ITextRenderer();
                
                

                renderer.setDocument(url);
                renderer.layout();
                
                renderer.createPDF(os);
            }
       
        /*
        Document document = new Document(PageSize.LETTER, 10, 10, 10, 10);
    
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PDFPath));
    
            //Open the document before adding any content
            document.open();
            
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTMLPath));
            

            //Close the document
            document.close();
*/

    }
    
    public static void QRToFile(String Text, String FileName)  {
        int size = 250;
        String fileType = "png";
        File file = new File(FileName);
        try {
            
            Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(Text, BarcodeFormat.QR_CODE, size,
                            size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                            BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                    for (int j = 0; j < CrunchifyWidth; j++) {
                            if (byteMatrix.get(i, j)) {
                                    graphics.fillRect(i, j, 1, 1);
                            }
                    }
            }
            ImageIO.write(image, fileType, file);
        }
        catch(Exception ex)
        {
            
        }
    
    }
    
    public static String EncodeFileToBase64Binary(String fileName)
			throws IOException {

		File file = new File(fileName);
		byte[] bytes = LoadFile(file);
		String encodedString = DatatypeConverter.printBase64Binary(bytes);

		return encodedString;
	}

	public static byte[] LoadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}
    

}
