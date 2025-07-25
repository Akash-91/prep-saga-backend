package com.prep_saga.PrepSaga.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String userName, String token) {
        String subject = "Verify your PrepSaga account";
        String verificationLink = "http://localhost:8082/api/auth/verify?token=" + token;
        String content = getHtmlTemplate(userName, verificationLink);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML content
            helper.setFrom("akash.akki07@gmail.com"); // Replace with your verified sender

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String getHtmlTemplate(String userName, String verificationLink) {
        return """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Verify your email</title>
                        <style>
                            .container {
                                font-family: Arial, sans-serif;
                                padding: 20px;
                                background-color: #f9f9f9;
                                border-radius: 10px;
                                width: fit-content;
                                max-width: 600px;
                                margin: auto;
                                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                            }
                            .btn {
                                display: inline-block;
                                padding: 10px 20px;
                                margin-top: 20px;
                                background-color: #007bff;
                                color: white;
                                text-decoration: none;
                                border-radius: 5px;
                                font-weight: bold;
                            }
                            .footer {
                                font-size: 12px;
                                color: #777;
                                margin-top: 20px;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h2>Welcome to PrepSaga 👋</h2>
                            <p>Hi <strong>%s</strong>,</p>
                            <p>Thanks for signing up. Please verify your email by clicking the button below:</p>
                            <a class="btn" href="%s">Verify Email</a>
                            <p class="footer">If you didn't sign up, you can safely ignore this email.</p>
                        </div>
                    </body>
                    </html>
                """.formatted(userName, verificationLink);
    }
}