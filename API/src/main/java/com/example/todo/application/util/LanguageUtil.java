package com.example.todo.application.util;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.todo.application.constants.ErrorCodes;
import com.example.todo.application.exception.BadRequestException;
@Service
public class LanguageUtil {

  @Autowired
  private MessageSource messageSource;

  @Value("${global.language}")
  private String globalLanguage;

  /**
   * Retrieves the translated text for the given message key and language.
   *
   * @param msgKey The key of the message to be translated.
   * @param obj    An array of objects to be used as arguments for parameterized
   *               messages.
   * @param lang   The language code for the translation.
   * @return The text.
   * @throws BadRequestException if the requested language is not supported.
   */
  public String getTranslatedText(String msgKey, Object[] obj, String lang) {
    // Check if the requested language is supported
    if (!lang.equals(globalLanguage)) {
      throw new BadRequestException(getTranslatedText(ErrorCodes.LANGUAGE_NOT_SUPPORTED, null, globalLanguage));
    }

    // Return the translated text for the given message key and language
    return messageSource.getMessage(msgKey, obj, Locale.ENGLISH);
  }
}
