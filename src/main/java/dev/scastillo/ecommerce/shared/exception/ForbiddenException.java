package dev.scastillo.ecommerce.shared.exception;

public class ForbiddenException extends  RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
