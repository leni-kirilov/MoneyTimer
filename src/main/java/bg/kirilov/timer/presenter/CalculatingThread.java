package bg.kirilov.timer.presenter;

import bg.kirilov.timer.calculator.Calculator;
import bg.kirilov.timer.util.Formatters;
import bg.kirilov.timer.util.TimeConstants;
import org.joda.time.Duration;

import java.text.NumberFormat;

/**
 * A class for starting, stopping, pausing a clock while calculating something.<br>
 * It also counts how many seconds it was ticking. Calculates how much money<br>
 * was spent for the time based on number of participants and pay rate.<br>
 * <br>
 * Once a clock is stopped it cannot be reused. The thread has finished its job.
 * The update time interval is customizable
 *
 * @author Leni Kirilov
 * @version 2014-April
 * @since 2010-February
 */
//TODO split this class into 2 - 1 for ticking and another one for updating UI when ticks are applied
public class CalculatingThread extends Thread {

    /**
     * Determines whether the thread should pauseThread its process.
     */
    private boolean paused;
    /**
     * Determines if the run method is still going on.
     */
    private boolean running;
    /*
     * The amount to be displayed is stored here.
     */
    private double amount;

    /*
     * The interval to update view and recalculate amount
     */
    private long tickingIntervalInMillis;

    //HELPER fields

    /**
     * Counts the seconds that have passed in actual running.
     */
    private Duration duration;
    /**
     * The view which is going to be updated per second
     */
    private CalculatingView view;
    /*
     * used to calculate the amount
     */
    private final Calculator paymentCalculator;
    /**
     * The formatter of the panel is used to display the data.
     */
    private NumberFormat formatter;

    private CalculatingThread(CalculatingView view, NumberFormat formatter, Calculator paymentCalculator, long tickingIntervalInMillis) {
        this.duration = new Duration(Duration.ZERO);
        this.view = view;
        this.formatter = formatter;
        this.paymentCalculator = paymentCalculator;
        this.tickingIntervalInMillis = tickingIntervalInMillis;
    }

    /**
     * Default number formatter and 1-second interval
     *
     * @param view
     * @param paymentCalculator
     */
    public CalculatingThread(CalculatingView view, Calculator paymentCalculator) {
        this(view, Formatters.getNumberFormatter(), paymentCalculator, TimeConstants.ONE_SECOND_IN_MILLIS);
    }

    /**
     * Default number formatter
     *
     * @param view
     * @param paymentCalculator
     */
    public CalculatingThread(CalculatingView view, Calculator paymentCalculator, long tickingIntervalInMillis) {
        this(view, Formatters.getNumberFormatter(), paymentCalculator, tickingIntervalInMillis);
    }

    @Override
    public void start() {
        running = true;
        super.start();
    }

    /**
     * Simulates the work of an actual clock. Can be paused, resumed and stopped.
     */
    @Override
    @SuppressWarnings("SleepWhileHoldingLock")
    public void run() {
        try {

            while (running) {

                if (isPaused()) {
                    //paused thread waits to be resumed
                    synchronized (this) {
                        this.wait();
                    }

                } else {//updates if running and not paused
                    updateState();
                    Thread.sleep(tickingIntervalInMillis);
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Stops the current thread. Doesn't matter if it's paused or not.
     */
    public void stopThread() {
        resumeThread();
        running = false;
    }

    /**
     * Returns the final amount to be payed for the sessions.
     *
     * @return String - formatted accordingly
     */
    public String getFinalAmount() {
        return formatter.format(amount);
    }

    /**
     * @return boolean - true if the thread is currently paused
     */
    public boolean isPaused() {
        return paused;
    }

    public void resumeThread() {
        paused = false;
        synchronized (this) {
            notify();
        }
    }

    public void pauseThread() {
        this.paused = true;
    }

    /**
     * Update all data after the specified time interval has elapsed - clock and amount to pay.
     */
    private void updateState() {
        duration = duration.plus(tickingIntervalInMillis);
        view.setClock(getCurrentTimeFormatted());

        int passedSeconds = duration.toStandardSeconds().getSeconds();
        amount = paymentCalculator.calculate(passedSeconds);

        view.setAmount(getFinalAmount());
    }

    public String getCurrentTimeFormatted() {
        return Formatters.getPeriodFormatter().print(duration.toPeriod());
    }
}