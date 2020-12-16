package com.blogging.PJBlogging.service;

import com.blogging.PJBlogging.exceptions.SpringPJBloggingException;
import com.blogging.PJBlogging.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
private final JavaMailSender mailSender;
private final MailContentBuilder mailContentBuilder;
//Making the call to send email Asynchronous so that it do not wait for the response from the Email server.
@Async
void sendEmail(NotificationEmail notificationEmail) {
    //The input for the JavaMailSender.send method is the mimeMessagePreparator and hence it needs to be created.
    MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(("paulson.jose14@email.com"));
        messageHelper.setTo(notificationEmail.getRecipient());
        messageHelper.setSubject(notificationEmail.getSubject());
        messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()),true);
    };
    try {
        mailSender.send(mimeMessagePreparator);
        log.info("Activation email Send");
    } catch (MailException e) {
        throw new SpringPJBloggingException("Exception occured while sending the mail to " + notificationEmail.getRecipient());
    }
}
}
