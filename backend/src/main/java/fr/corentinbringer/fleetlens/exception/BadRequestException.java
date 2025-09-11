package fr.corentinbringer.fleetlens.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(String userMessage) {
        super(HttpStatus.BAD_REQUEST, userMessage);
    }
}

