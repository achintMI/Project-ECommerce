package com.order.Order.Email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("mailsend")
public class MailSender {

    @Autowired
    JavaMailSender javaMailSender;

    /**
     * Make this private
     */
    public void sendMail(String from, String to, String subject, String body) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);
        javaMailSender.send(mail);
    }

    public void setMail(String emailUser){
        /**
         * Why declaring seperate variables??
         */
        String from = "Ecommerce";
        String to = emailUser;
        String subject = "Order Placed";
        String body = "Congrats Order Placed, thanks for shopping with us";
        sendMail(from, to, subject, body);
    }
}
