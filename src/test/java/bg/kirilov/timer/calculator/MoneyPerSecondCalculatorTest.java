package bg.kirilov.timer.calculator;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leni Kirilov
 * @date 4/13/2014
 */
public class MoneyPerSecondCalculatorTest {

    @Test
    public void testConstructor() {
        new MoneyPerSecondCalculator(0, 0);
    }

    @Test
    public void testPositive_simpleCalculation() {
        int count = 1;
        double rate = 1.0;
        MoneyPerSecondCalculator calculator = new MoneyPerSecondCalculator(count, rate);

        Assert.assertEquals(0, calculator.calculate(0L), 0.2);
        Assert.assertEquals(1, calculator.calculate(3600L), 0.2);
    }
}
