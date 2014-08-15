package bg.kirilov.timer.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Leni Kirilov
 * @date 4/19/2014
 */
@RunWith(Parameterized.class)
public class NumberFormatterTest {

    private Double inputNumber;
    private String expected;

    private static NumberFormat format = Formatters.getNumberFormatter(Locale.US);

    public NumberFormatterTest(Double inputNumber, String expected) {
        this.inputNumber = inputNumber;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection doubleNumbers() {
        return Arrays.asList(new Object[][]{
                {2d, "2.00"},
                {60d, "60.00"},
                {55.3, "55.30"},
                {0.353, "0.35"},
                {100.02, "100.02"}
        });
    }

    @Test
    public void testFormatting() {
        String result = format.format(inputNumber);
        Assert.assertEquals(
                "Expected number (" + inputNumber + ") should be in the format x.00 but found [" + result,
                expected, result);
    }
}
