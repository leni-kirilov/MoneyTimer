package bg.kirilov.timer.presenter.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Leni Kirilov
 * @date 4/20/2014
 */
public class InputValidatorTest {

    private InputValidator validator;

    @Before
    public void init() {
        validator = new InputValidator();
    }

    @Test
    public void validateInteger() {
        String rawInt = "5";
        InputValidity<Integer> result = validator.validateInt(rawInt).getResult();
        Assert.assertTrue(result.isValid());
        Assert.assertEquals(5, (long) result.getValidNumber());
    }

    @Test
    public void validateInteger_negative() {
        String rawInt = "5.10";
        InputValidity result = validator.validateInt(rawInt).getResult();
        assertInvalid(result, "Input string is not a valid number");
    }

    @Test
    public void validateDouble() {
        String rawDouble = "5.03";
        InputValidity<Double> result = validator.validateDouble(rawDouble).getResult();
        Assert.assertTrue(result.isValid());
        Assert.assertEquals("", 5.03, result.getValidNumber(), 0.05);
    }

    @Test
    public void validateDouble_negative() {
        String rawDouble = "asd";
        InputValidity result = validator.validateDouble(rawDouble).getResult();
        assertInvalid(result, "Input string is not a valid number");
    }

    @Test
    public void validatePositiveInteger() {
        Number n = 5;
        InputValidity<Integer> result = validator.validatePositiveNumber(n).getResult();
        Assert.assertTrue("Positive validity is expected", result.isValid());
    }

    @Test
    public void validatePositiveInteger_negative() {
        Number n = -5;
        InputValidity result = validator.validatePositiveNumber(n).getResult();
        assertInvalid(result, "Positive number expected");
    }

    @Test
    public void validatePositiveDouble() {
        Number n = 5.5d;
        InputValidity<Double> result = validator.validatePositiveNumber(n).getResult();
        Assert.assertTrue("Positive validity is expected", result.isValid());
    }

    @Test
    public void validatePositiveDouble_negative() {
        Number n = -5.0;
        InputValidity result = validator.validatePositiveNumber(n).getResult();
        assertInvalid(result, "Positive number expected");
    }

    @Test
    public void validatePositiveDoubleBuilder() {
        String s = "5.0";
        InputValidity<Double> result = validator.validateDouble(s).validatePositiveNumber().getResult();
        Assert.assertTrue(result.isValid());
        Assert.assertTrue("Positive validity is expected", result.isValid());
    }

    @Test
    public void validatePositiveDoubleBuilder_negativeInput() {
        String s = "ad5";
        InputValidity result = validator.validateDouble(s).validatePositiveNumber().getResult();
        assertInvalid(result, "Input string is not a valid number");
    }

    private void assertInvalid(InputValidity result, String expectedErrorMsg) {
        Assert.assertFalse(result.isValid());
        Assert.assertTrue("Expected string not found in [" + result.getInvalidReason(),
                result.getInvalidReason().contains(expectedErrorMsg));
    }
}
