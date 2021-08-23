package com.inthebytes.stacklunch.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
		value = "notification.text.enabled",
		havingValue = "true",
		matchIfMissing = false
		)
public class TextMessageSendService {

	// TODO: Populate with code once that story is pushed to GitHub
}
