package buddy.exception;

public class EmptyDescriptionException extends BuddyException {
    public EmptyDescriptionException(String taskType) {
        super("OOPS!!! The description of a " + taskType + " cannot be empty.");
    }
}