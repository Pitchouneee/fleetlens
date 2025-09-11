package fr.corentinbringer.fleetlens.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String error;
    private final String message;
    private final Instant timestamp;
    private final String path;

    @Builder.Default
    private final Map<String, String> validationErrors = null;
}
