package fr.corentinbringer.fleetlens.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(String userMessage) {
        super(HttpStatus.UNAUTHORIZED, userMessage);
    }
}

