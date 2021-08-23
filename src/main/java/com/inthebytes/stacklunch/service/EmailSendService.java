package com.inthebytes.stacklunch.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest;

@Service
@ConditionalOnProperty(
		value = "notification.email.enabled",
		havingValue = "true",
		matchIfMissing = false
		)
public class EmailSendService {
	
	private SesClient client;
	
	@Value("${stacklunch.email.sender}")
	private String sender;
	
	public void sendEmail(String recipient,
	                 String subject,
	                 String bodyText,
	                 String bodyHTML,
	                 String link,
	                 String button
	) {
		client = SesClient.builder().build();
		JSONObject templateData = new JSONObject();
		templateData.put("title", subject)
				.put("HTMLmessage", bodyHTML)
				.put("TEXTmessage", bodyText);

		if (link != null && !link.isEmpty() && button != null && !button.isEmpty()) {
			templateData.put("link", new JSONObject()
					.put("href", link)
					.put("button", button)
			);
		}

		SendTemplatedEmailRequest templatedEmailRequest = SendTemplatedEmailRequest.builder()
				.source(sender)
				.template("SLEmailTemplate")
				.configurationSetName("Failure")
				.destination(Destination.builder().toAddresses(recipient).build())
				.templateData(templateData.toString())
				.build();

		this.client.sendTemplatedEmail(templatedEmailRequest);
		client.close();
	}
	
	
}
