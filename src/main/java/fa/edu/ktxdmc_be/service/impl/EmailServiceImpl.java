package fa.edu.ktxdmc_be.service.impl;

import java.io.File;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import fa.edu.ktxdmc_be.common.EmailDetails;
import fa.edu.ktxdmc_be.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendSimpleMail(EmailDetails emailDetails) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom(sender);
			mailMessage.setTo(emailDetails.getRecipient());
			mailMessage.setText(emailDetails.getMsgBody());
			mailMessage.setSubject(emailDetails.getSubject());

			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error while Sending Mail";
		}
	}

	@Override
	public String sendMailWithAttachment(EmailDetails emailDetails) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(emailDetails.getRecipient());
			mimeMessageHelper.setText(emailDetails.getMsgBody());
			mimeMessageHelper.setSubject(emailDetails.getSubject());

			FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));

			mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
			javaMailSender.send(mimeMessage);

			return "Mail Sent Successfully...";

		} catch (MessagingException e) {
			return "Error while sending mail!!!";
		}
	}
}
