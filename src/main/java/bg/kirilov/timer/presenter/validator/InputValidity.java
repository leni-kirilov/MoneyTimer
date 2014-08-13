package bg.kirilov.timer.presenter.validator;

/**
 * Simple POJO.<br>
 * Result of validating a input
 */
public class InputValidity<T extends Number> {
    private boolean isValid;
    private String invalidReason;
    private T validNumber;

    public InputValidity(boolean isValid, String reason, T number) {
        this.isValid = isValid;
        this.invalidReason = reason;
        this.validNumber = number;
    }

    public InputValidity(boolean isValid, T validNumber) {
        this(isValid, "", validNumber);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public T getValidNumber() {
        return validNumber;
    }
}
