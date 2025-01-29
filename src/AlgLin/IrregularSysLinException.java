package AlgLin;

/**
 * Exception thrown when a linear system is found to be irregular during resolution.
 */
public class IrregularSysLinException extends Exception {
    /**
     * Constructs a new IrregularSysLinException with a custom message.
     * @param message Custom message describing the irregularity.
     */
    public IrregularSysLinException(String message) {
        super(message);
    }
    
    /**
     * Returns a string representation of the exception.
     * @return The irregular system message.
     */
    @Override
    public String toString() {
        return "IrregularSysLinException: " + super.getMessage();
    }
}
