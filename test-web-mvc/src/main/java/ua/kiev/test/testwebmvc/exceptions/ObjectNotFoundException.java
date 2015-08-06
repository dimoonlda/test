package ua.kiev.test.testwebmvc.exceptions;

/**
 * Created by lutay.d on 06.08.2015.
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
