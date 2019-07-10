package com.km.parceltracker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {

	private ReloadableResourceBundleMessageSource messageSource;

	@Autowired
	public MessageResolver(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Get a message with arguments from the message source using the message key.
	 *
	 * @param msgKey String Key of the message.
	 * @param args   Object... argument parameters of the message.
	 * @return String message from message source.
	 */
	public String getMessage(String msgKey, Object... args) {
		try {
			return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException ex) {
			return getMessage("no.message.available");
		}
	}
}
