package bg.kirilov.timer.presenter.validator;

import java.util.function.Function;

/**
 * Generic class for parsing raw input</br>
 * It can be used as a Builder pattern and getting the final validation result at the end.
 * If the input can be parsed it will be saved in validNumber field.<br>
 * Otherwise the flag isValid will be false and a "invalidReason" will contain message.
 */
//TODO search for a validation library
public class InputValidator {

    private InputValidity<?> inputValidity;

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

    private InputValidator validate(String rawInput, Function<String, Number> parser) {
        InputValidity inputValidity = null;

        try {
            inputValidity = new InputValidity(true, parser.apply(rawInput));
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

    //TODO in order to be a real builder, validating should stack and display all possible errors, not just the last
    //TODO if needs to parse Strings, or validate in a stateless way - provide a static method?
    /**
     * Validates the input number is a positive number
     *
     * @return validator for further validation of input
     */
    InputValidator validatePositiveNumber(Number n) {
        if (n.doubleValue() <= 0) {
            inputValidity = new InputValidity(false, "Positive number expected.", null);
        } else {
            inputValidity = new InputValidity(true, n);
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
