package seedu.address.commons.exceptions;

/**
 * Signals an error caused by removing not existing data.
 */
public abstract class NotExistentDataException extends IllegalValueException {
    public NotExistentDataException(String message) {
        super(message);
    }
}
