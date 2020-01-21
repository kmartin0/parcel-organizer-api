package com.km.parcelorganizer;

import com.km.parcelorganizer.util.MessageResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MessageResolverTests {

	@Autowired
	private MessageResolver messageResolver;

	@AfterEach
	void after() {
		LocaleContextHolder.setLocale(Locale.getDefault());
	}

	@Test
	void testGetMessageNoArgs_isCorrect() {
		Assertions.assertEquals(
				"Test message without args.",
				messageResolver.getMessage("test.message.no.args")
		);
	}

	@Test
	void testGetMessageNoArgsLocaleNl_isCorrect() {
		LocaleContextHolder.setLocale(new Locale("nl"));
		Assertions.assertEquals(
				"Test bericht zonder argumenten.",
				messageResolver.getMessage("test.message.no.args")
		);
	}

	@Test
	void testNotExistingMessageNoArgs_isNoMessageAvailable() {
		Assertions.assertEquals(
				"No message available.",
				messageResolver.getMessage("test.non.existing.message")
		);
	}

	@Test
	void testMessageWithArgs_isCorrect() {
		Assertions.assertEquals(
				"Test message with args 1 and two.",
				messageResolver.getMessage("test.message.with.args", 1, "two")
		);
	}

	@Test
	void testNotExistingMessageWithArgs_isNoMessageAvailable() {
		Assertions.assertEquals("No message available.",
				messageResolver.getMessage("test.non.existing.message", 1, 12)
		);
	}

}

