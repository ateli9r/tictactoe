package egovframework.ateli9r.tictactoe.repos;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import egovframework.ateli9r.tictactoe.typedef.dto.SendMailFormDto;


@Component("messageRepository")
public class MessageProdRepository implements MessageRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProdRepository.class);

    @Override
    public boolean sendVerifyEmail(SendMailFormDto request) {
        int chk = 0;
        if (!request.getMailTo().isEmpty()) chk++;
        if (!request.getTitle().isEmpty()) chk++;
        if (!request.getContent().isEmpty()) chk++;
        if (chk != 3) return false;

        String host = System.getenv("SMTP_HOST");
        String port = System.getenv("SMTP_PORT");
        String userId = System.getenv("SMTP_USER_ID");
        String userPw = System.getenv("SMTP_USER_PW");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userId, userPw);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userId));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(request.getMailTo()));
            message.setSubject(request.getTitle());
            message.setText(request.getContent());

            Transport.send(message);
            
            return true;
        } catch (MessagingException e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }
}
