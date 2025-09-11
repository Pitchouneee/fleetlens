package fr.corentinbringer.fleetlens.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String userMessage) {
        super(HttpStatus.FORBIDDEN, userMessage);
    }
}

