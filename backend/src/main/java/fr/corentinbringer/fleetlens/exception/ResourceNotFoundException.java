package fr.corentinbringer.fleetlens.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String userMessage) {
        super(HttpStatus.NOT_FOUND, userMessage);
    }
}

