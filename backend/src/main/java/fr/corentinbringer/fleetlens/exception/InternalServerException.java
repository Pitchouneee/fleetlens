package fr.corentinbringer.fleetlens.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends BaseException {

    public InternalServerException(String userMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, userMessage);
    }
}

