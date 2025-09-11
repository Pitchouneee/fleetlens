package fr.corentinbringer.fleetlens.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {

    private final HttpStatus status;
    private final String userMessage;

    protected BaseException(HttpStatus status, String userMessage) {
        super(userMessage);
        this.status      = status;
        this.userMessage = userMessage;
    }
}

