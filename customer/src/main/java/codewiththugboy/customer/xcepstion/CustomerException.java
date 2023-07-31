package codewiththugboy.customer.xcepstion;

public class CustomerException extends RuntimeException{
    public CustomerException(String message, int i){
        super(message);
    }
}
