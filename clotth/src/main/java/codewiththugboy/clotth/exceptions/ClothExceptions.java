package codewiththugboy.clotth.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class ClothExceptions extends Exception {
    private int statusCode;

    public ClothExceptions(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
