package bg.kirilov.timer.presenter.validator;

/**
 * Generic class for parsing raw input</br>
 * It can be used as a Builder pattern and getting the final validation result at the end.
 * If the input can be parsed it will be saved in validNumber field.<br>
 * Otherwise the flag isValid will be false and a "invalidReason" will contain message.
 */
//TODO write unit tests
public class InputValidator {

    private InputValidity<?> inputValidity;

    /**
     * Lambda function definition for parsing Strings into Numbers
     */
    private static interface Parser {
        public Number parse(String s);
    }

    /**
     * Validates inputString is a valid Integer
     *
     * @param rawInput - String representation
     * @return validator for further validation of input
     */
    public InputValidator validateInt(String rawInput) {
        return validate(rawInput, (String input) -> {
            return Integer.parseInt(input);
        });
    }

    /**
     * Validates inputString is a valid Double
     *
     * @param rawInput - String representation
     * @return validator for further validation of input
     */
    public InputValidator validateDouble(String rawInput) {
        return validate(rawInput, (String input) -> {
            return Double.parseDouble(input);
        });
    }

    private InputValidator validate(String rawInput, Parser parser) {
        InputValidity inputValidity = null;

        try {
            inputValidity = new InputValidity(true, "", parser.parse(rawInput));
        } catch (Exception e) {
            inputValidity = new InputValidity(false, "Input string is not a valid number", null);
        }

        this.inputValidity = inputValidity;
        return this;
    }

    /**
     * Validates the number parsed is a positive number
     *
     * @return validator for further validation of input
     */
    public InputValidator validatePositiveNumber() {
        return inputValidity.isValid() ?
                validatePositiveNumber(inputValidity.getValidNumber()) :
                this;
    }

    /**
     * Validates the input number is a positive number
     *
     * @return validator for further validation of input
     */
    public InputValidator validatePositiveNumber(Number n) {
        if (n.doubleValue() <= 0) {
            inputValidity = new InputValidity(false, "Positive number expected.", null);
        } else {
            inputValidity = new InputValidity(true, "", n);
        }
        return this;
    }

    /**
     * Validator finishes
     *
     * @return InputValidity object containing validity status, reason and the number parsed
     */
    public InputValidity getResult() {
        return inputValidity;
    }
}
