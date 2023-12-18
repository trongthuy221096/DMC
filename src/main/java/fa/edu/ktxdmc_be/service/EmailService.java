package fa.edu.ktxdmc_be.service;

import fa.edu.ktxdmc_be.common.EmailDetails;

/**
 * Represents an EmailService
 * @author 
 */
public interface EmailService {
	/**
	 * Send a simple email message with the provided email details.
	 *
	 * @param emailDetails The details of the email message to send.
	 * @return A String indicating the result of the email sending operation.
	 */
	String sendSimpleMail(EmailDetails emailDetails);

	/**
	 * Send an email message with an attachment using the provided email details.
	 *
	 * @param emailDetails The details of the email message with an attachment to
	 *                     send.
	 * @return A String indicating the result of the email sending operation.
	 */
	String sendMailWithAttachment(EmailDetails emailDetails);
}
