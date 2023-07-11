package codewiththugboy.customer.xcepstion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateResourceExceptions extends RuntimeException {
    public DuplicateResourceExceptions(String message) {
        super(message);
    }

}
