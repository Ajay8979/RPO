package com.ojas.rpo.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class OutlookMailSender extends Authenticator{

     Properties props = new Properties();
     
     private Properties getMailProperties() {
    	 Properties props = new Properties();
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.host", "outlook.office365.com");
         props.put("mail.smtp.port", "587");
         return props;
     }
     
    
    
	
	public OutlookMailSender() {
		super();
	}


	public void sendMail(final EmailEntity emailEntity) {
		Session session = Session.getInstance(getMailProperties(), new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(emailEntity.getFrom(), emailEntity.getPassword());
	        }
	      });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(emailEntity.getFrom()));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(emailEntity.getTo()));
	        DataHandler handler= new DataHandler(new ByteArrayDataSource(emailEntity.getMessageBody().getBytes(), "text/html"));
	        message.setSubject(emailEntity.getMessagesubject());
	        message.setDataHandler(handler);

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }

	}
	
	
	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
	
	
}
