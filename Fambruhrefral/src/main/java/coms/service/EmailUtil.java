package coms.service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailUtil {

  @Autowired
  private JavaMailSender javaMailSender;
  
  @Autowired
  private TemplateEngine templateEngine;

  public void sendAccountVerificationEmail(String email, String jwtToken) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    mimeMessageHelper.setTo(email);
    mimeMessageHelper.setSubject("Fambruh - New User email verification");
    mimeMessageHelper.setText("""
        <div>
          <a href="http://localhost:9400/verify-account?email=%s&jwtToken=%s" target="_blank">Click this link to verify</a>
        </div>
        """.formatted(email, jwtToken), true);

    javaMailSender.send(mimeMessage);
  }
  
  public void sendPasswordResetEmail(String email, String jwtEmailToken) throws MessagingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("Fambruh - Password reset verification");
	    mimeMessageHelper.setText("""
	        <div>
	          <a href="http://localhost:4200/set-password?email=%s&token=%s" target="_blank">Click this link to set a new password</a>
	        </div>
	        """.formatted(email, jwtEmailToken), true);

	    javaMailSender.send(mimeMessage);
  }
  
  public void sendmessage (String to, String subject, String text) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);
		
		javaMailSender.send(message);
  }
  
  public void sendOrderInvoiceEmail (String recipientEmail, String subject, String templateName, Map<String, Object> templateVariables) throws MessagingException {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

      Context thymeleafContext = new Context();
      thymeleafContext.setVariables(templateVariables);

      String htmlBody = templateEngine.process(templateName, thymeleafContext);

      helper.setTo(recipientEmail);
      helper.setSubject(subject);
      helper.setText(htmlBody, true);

      javaMailSender.send(message);
  }
  
  public void sendMimeEmailWithAttachment(String recipientEmail, String subject, String templateName, Map<String, Object> templateVariables, String attachmentFileName, byte[] attachmentData) throws MessagingException {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

      Context thymeleafContext = new Context();
      thymeleafContext.setVariables(templateVariables);

      String htmlBody = templateEngine.process(templateName, thymeleafContext);

      helper.setTo(recipientEmail);
      helper.setSubject(subject);
      helper.setText(htmlBody, true);

      helper.addAttachment(attachmentFileName, new ByteArrayResource(attachmentData));

      javaMailSender.send(message);
  }
  
}
