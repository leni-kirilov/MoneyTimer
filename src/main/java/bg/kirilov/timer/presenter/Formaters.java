package bg.kirilov.timer.presenter;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.NumberFormat;

/**
 * Util class for managing stateless String formatters
 *
 * @author Leni Kirilov
 * @since 4/13/2014
 */
//TODO write unit tests to insure the wanted effect if a change is needed
public class Formaters {

    private static NumberFormat formatter;
    private static PeriodFormatter periodFormatter;

    private Formaters() {
    }

    /**
     * Produces nice formatting of numbers with floating point.<br>
     * 2 characters after the floating point.
     */
    public static NumberFormat getNumberFormatter() {
        if (formatter == null) {
            formatter = NumberFormat.getInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
        }
        return formatter;
    }

    public static PeriodFormatter getPeriodFormatter() {
        if (periodFormatter == null) {
            periodFormatter = new PeriodFormatterBuilder()
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendHours()
                    .appendSeparator(":")
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendMinutes()
                    .appendSeparator(":")
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendSeconds()
                    .toFormatter();
        }
        return periodFormatter;
    }

    public static void main(String[] args) {

        Duration duration = new Duration(0L);
        for (int i = 0; i < 100; i++) {
            duration = duration.plus(100000L);
            System.out.print("; all seconds= " + duration.toStandardSeconds().getSeconds());
            System.out.print("; seconds= " + duration.getStandardSeconds());
            System.out.println("; all minutes= " + duration.toStandardMinutes().getMinutes());

            System.out.println(periodFormatter.print(duration.toPeriod()));
        }
    }
}