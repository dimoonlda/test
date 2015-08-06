package ua.kiev.test.testwebmvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.kiev.test.testwebmvc.exceptions.ObjectNotFoundException;
import ua.kiev.test.testwebmvc.model.RestErrorInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by lutay.d on 06.08.2015.
 */
@ControllerAdvice
public class RestExceptionProcessor {

    private final static Logger log = LoggerFactory.getLogger(RestExceptionProcessor.class);

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> requestHandlingObjectNotFoundException(HttpServletRequest req, RuntimeException ex) {
        log.warn(ex.getMessage(), ex);
        Locale locale = LocaleContextHolder.getLocale();
        String errorURL = req.getRequestURL().toString() + " " + locale.getLanguage();
        RestErrorInfo errorInfo = new RestErrorInfo(errorURL, ex.getMessage());
        return new ResponseEntity<>(errorInfo.toJson(),  setContentTypeheader(), HttpStatus.NOT_FOUND);
    }

    private MultiValueMap<String, String> setContentTypeheader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(com.google.common.net.HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
