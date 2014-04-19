package bg.kirilov.timer.util;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.NumberFormat;

/**
 * Util class for managing stateless String formatters
 *
 * @author Leni Kirilov
 * @since 4/13/2014
 */
public class Formatters {

    private static NumberFormat formatter;
    private static PeriodFormatter periodFormatter;

    private Formatters() {
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

    //Returns 00:00:00 time format
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
}