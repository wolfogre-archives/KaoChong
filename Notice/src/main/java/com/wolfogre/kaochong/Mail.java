package com.wolfogre.kaochong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class Mail {

    @Autowired
    Console console;

    private Session session;
    private String password;

    @PostConstruct
    public void init() {
        console.writeLine("Please input password for send email");
        password = console.readLine();
        sendMail("考虫提醒系统启动", "系统成功启动！", new String[]{"i@wolfogre.com", "wolfogre@qq.com", "wolfogre@163.com"});
    }

    public Mail() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", "smtp.wolfogre.com");
        props.setProperty("mail.transport.protocol", "smtp");
        session = Session.getInstance(props);
    }


    public void sendMail(String subject, String text, String[] sendTo) {
        Address[] addresses = new Address[sendTo.length];
        try {
            for(int i = 0; i < sendTo.length; ++i)
                addresses[i] = new InternetAddress(sendTo[i]);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }

        Message msg = new MimeMessage(session);
        try {
            msg.setSubject(subject);
            msg.setText(text);
            msg.setRecipients(Message.RecipientType.TO, addresses);
            msg.setFrom(new InternetAddress(MimeUtility.encodeText("考虫提醒") + "<kaochong@wolfogre.com>"));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try{
            Transport transport = session.getTransport();
            transport.connect("kaochong@wolfogre.com", password);
            transport.sendMessage(msg, addresses);
            transport.close();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
