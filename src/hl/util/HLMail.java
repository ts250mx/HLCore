package hl.util;

import hl.log.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class HLMail {

    
    String Body;
    String From;
    String Subject;
    
    List<String> AddressErrors= new ArrayList();
    List<String> AddressOK= new ArrayList();

    public List<String> getAddressOK() {
        return AddressOK;
    }

    public void setAddressOK(List<String> AddressOK) {
        this.AddressOK = AddressOK;
    }

    public List<String> getAddressErrors() {
        return AddressErrors;
    }

    public void setErrorAddress(List<String> ToAddressErrors) {
        this.AddressErrors = AddressErrors;
    }
    List<String> Attachment = new ArrayList();
    String ToMail;
    private Store store;
    private Folder folder;
    private Message[] mensajes;
    private int Length;
    
    String SmtpServer;


    public String getBody() {
        return Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public List<String> getAttachment() {
        return Attachment;
    }

    public void setAttachment(List<String> Attachment) {
        this.Attachment = Attachment;
    }
    
    public void addAttachment(String attachment) {
        this.Attachment.add(attachment);
    }

    public String getToMail() {
        return ToMail;
    }

    public void setToMail(String toMail) {
        toMail = toMail.replace(" ", "");
        toMail = toMail.replace(";", ",");
        this.ToMail = toMail;
    }

    public String getSmtpServer() {
        return SmtpServer;
    }

    public void setSmtpServer(String SmtpServer) {
        this.SmtpServer = SmtpServer;
    }

    public String getSmtpUser() {
        return SmtpUser;
    }

    public void setSmtpUser(String SmtpUser) {
        this.SmtpUser = SmtpUser;
    }

    public String getSmtpPasswd() {
        return SmtpPasswd;
    }

    public void setSmtpPasswd(String SmtpPasswd) {
        this.SmtpPasswd = SmtpPasswd;
    }

    public String getSmtpPort() {
        return SmtpPort;
    }

    public void setSmtpPort(String SmtpPort) {
        this.SmtpPort = SmtpPort;
    }

    public String getSmtpSSL() {
        return SmtpSSL;
    }

    public void setSmtpSSL(String SmtpSSL) {
        this.SmtpSSL = SmtpSSL;
    }

    public String getSmtpTLS() {
        return SmtpTLS;
    }

    public void setSmtpTLS(String SmtpTLS) {
        this.SmtpTLS = SmtpTLS;
    }

    public String getSmtpAuth() {
        return SmtpAuth;
    }

    public void setSmtpAuth(String SmtpAuth) {
        this.SmtpAuth = SmtpAuth;
    }

    public String getPop3Server() {
        return Pop3Server;
    }

    public void setPop3Server(String Pop3Server) {
        this.Pop3Server = Pop3Server;
    }

    public String getPop3User() {
        return Pop3User;
    }

    public void setPop3User(String Pop3User) {
        this.Pop3User = Pop3User;
    }

    public String getPop3Passwd() {
        return Pop3Passwd;
    }

    public void setPop3Passwd(String Pop3Passwd) {
        this.Pop3Passwd = Pop3Passwd;
    }

    public String getPop3Port() {
        return Pop3Port;
    }

    public void setPop3Port(String Pop3Port) {
        this.Pop3Port = Pop3Port;
    }

    public String getPop3SSL() {
        return Pop3SSL;
    }

    public void setPop3SSL(String Pop3SSL) {
        this.Pop3SSL = Pop3SSL;
    }

    public String getPop3TLS() {
        return Pop3TLS;
    }

    public void setPop3TLS(String Pop3TLS) {
        this.Pop3TLS = Pop3TLS;
    }

    public String getPop3SmtpAuth() {
        return Pop3SmtpAuth;
    }

    public void setPop3SmtpAuth(String Pop3SmtpAuth) {
        this.Pop3SmtpAuth = Pop3SmtpAuth;
    }

    public Properties getProps() {
        return Props;
    }

    public void setProps(Properties Props) {
        this.Props = Props;
    }
    String SmtpUser;
    String SmtpPasswd;
    String SmtpPort;
    String SmtpSSL;
    String SmtpTLS;
    String SmtpAuth="true";
    
    String Pop3Server;
    String Pop3User;
    String Pop3Passwd;
    String Pop3Port;
    String Pop3SSL;
    String Pop3TLS;
    String Pop3SmtpAuth="true";

    Properties Props;
    
	
    public HLMail()
    {
        Props = new Properties();
    }
	
    public void ReadMail()
    {

    }
	
    public void startReadMail(String User, String Passwd, String Pop3Server) throws MessagingException
    {
            Properties prop = new Properties();

            // Deshabilitamos TLS
            prop.setProperty("mail.pop3.starttls.enable", "false");

            // Hay que usar SSL
            //prop.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
            prop.setProperty("mail.pop3.socketFactory.fallback", "false");

            // Puerto 995 para conectarse.
            prop.setProperty("mail.pop3.port","110");
            prop.setProperty("mail.pop3.socketFactory.port", "110");

            Session sesion = Session.getInstance(prop);
            sesion.setDebug(true);

            store = sesion.getStore("pop3");
            store.connect(Pop3Server,User,Passwd);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            mensajes = folder.getMessages();

            Length = mensajes.length;
            /*for (int i=0;i<mensajes.Length;i++)
            {

                MessagesList.add(i)
                System.out.println("From:"+mensajes[i].getFrom()[0].toString());
                System.out.println("Subject:"+mensajes[i].getSubject());
            } */

    }
        
        public int getLength()
        {
            return Length;
        }
        
        public String getBody(int index)
        {
            try {
                return mensajes[index].getContent().toString();
            } catch (IOException ex) {
                Logger.getLogger(HLMail.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            } catch (MessagingException ex) {
                Logger.getLogger(HLMail.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
        
        public String getSubject(int index)
        {
            try {
                return mensajes[index].getSubject().toString();
            } catch (MessagingException ex) {
                Logger.getLogger(HLMail.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
        
        public String getFrom(int index)
        {
            try {
                InternetAddress fromAddress = (InternetAddress) mensajes[index].getFrom()[0];
                //return mensajes[index].getFrom()[1].toString();
                return fromAddress.getAddress();
            } catch (MessagingException ex) {
                Logger.getLogger(HLMail.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
        
        public void deleteMessage(int index) throws MessagingException
        {
            try {
                mensajes[index].setFlag(Flags.Flag.DELETED, true);
                //mensajes[index].setFlag(Flags.Flag.DRAFT, true);
                
            } catch (MessagingException ex) {
                System.out.println(ex.toString());
            }
        }
        
        public void folderClose() throws MessagingException
        {
            try {
                folder.close(true);

            } catch (MessagingException ex) {
                System.out.println(ex.toString());
            }
        }
        
        public void SendMail() throws AddressException, MessagingException
        {
            
            Props.setProperty("mail.smtp.host", SmtpServer);
            Props.setProperty("mail.smtp.starttls.enable", SmtpTLS);
            Props.setProperty("mail.smtp.port",SmtpPort);
            Props.setProperty("mail.smtp.user", SmtpUser);
            Props.setProperty("mail.smtp.auth", SmtpAuth);
            Props.setProperty("mail.smtp.ssl", SmtpSSL);
            
            Session session = Session.getDefaultInstance(Props);
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);
            String[] temp;
            String delimiter = ",";
            temp = ToMail.split(delimiter);
            for(int i =0; i < temp.length ; i++)
            {
                try
                {
                    InternetAddress ia = new InternetAddress(temp[i]);
                    message.addRecipient(Message.RecipientType.TO, ia);
                }catch(Exception ex)
                {
                    AddressErrors.add(temp[i]);
                    Log.Error(ex);
                    
                }
                AddressOK.add(temp[i]);
                
            }
            

            

            

            message.setFrom(new InternetAddress(SmtpUser));

            

            

            if(message.getAllRecipients().length==0)
            {
                throw new AddressException();
            }
            
            MimeMultipart content = new MimeMultipart("related");
            MimeBodyPart html = new MimeBodyPart();

            html.setContent(Body, "text/html");
            html.setHeader("MIME-Version" , "1.0" );

            content.addBodyPart(html);

            
            
            if(Attachment.size()>0)
            {
                for(int i =0; i < Attachment.size() ; i++)
                {
                    File f = new File(Attachment.get(i));
                    MimeBodyPart adjuntosPart = new MimeBodyPart();
                    DataSource source = new FileDataSource( Attachment.get(i) );
                    adjuntosPart.setDataHandler( new DataHandler(source) );

                    adjuntosPart.setHeader("Content-ID","<image" + i + ">");
                    adjuntosPart.setFileName(f.getName()  );
                    content.addBodyPart(adjuntosPart);
                }
            }

            message.setContent(content);
            message.setSubject(Subject);


            Transport t = session.getTransport("smtp");
            
            
            

            t.connect(SmtpUser,SmtpPasswd);
            message.saveChanges();

            t.sendMessage(message,message.getAllRecipients());

            t.close();
            
            
            
        }

        
}
