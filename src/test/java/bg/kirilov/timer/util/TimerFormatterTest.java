package bg.kirilov.timer.util;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Leni Kirilov
 * @date 4/19/2014
 */
@RunWith(Parameterized.class)
public class TimerFormatterTest {

    private Period inputPeriod;
    private String expectedTime;

    private static PeriodFormatter format = Formatters.getPeriodFormatter();

    public TimerFormatterTest(Period inputPeriod, String expected) {
        this.inputPeriod = inputPeriod;
        this.expectedTime = expected;
    }

    @Parameterized.Parameters
    public static Collection doubleNumbers() {
        String expected1 = "00:00:03";
        Duration d1 = new Duration(0L);
        d1 = d1.plus(TimeConstants.ONE_SECOND_IN_MILLIS);

        String expected2 = "00:05:15";
        Duration d2 = new Duration(Duration.ZERO);
        d2 = d2.plus(TimeConstants.ONE_SECOND_IN_MILLIS * 15);
        d2 = d2.plus(TimeConstants.ONE_SECOND_IN_MILLIS * TimeConstants.SECONDS_IN_A_MINUTE * 5);

        String expected3 = "15:55:44";
        Duration d3 = new Duration(0L);
        d3 = d3.plus(TimeConstants.ONE_SECOND_IN_MILLIS * 44);
        d3 = d3.plus(TimeConstants.ONE_SECOND_IN_MILLIS * TimeConstants.SECONDS_IN_A_MINUTE * 55);
        d3 = d3.plus(TimeConstants.ONE_SECOND_IN_MILLIS * TimeConstants.SECONDS_IN_AN_HOUR * 15);

        return Arrays.asList(new Object[][]{
                {d1.toPeriod(), expected1},
                {d2.toPeriod(), expected2},
                {d3.toPeriod(), expected3}

        });
    }

    @Test
    public void testFormatting() {
        String result = format.print(inputPeriod);
        Assert.assertEquals(
                "Expected time (" + inputPeriod + ") should be in the format xx:xx:xx but found [" + result,
                expectedTime, result);
    }
}
