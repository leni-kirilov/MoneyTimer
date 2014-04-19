package bg.kirilov.timer.presenter;

import bg.kirilov.timer.calculator.Calculator;
import org.joda.time.Duration;

import java.text.NumberFormat;

/**
 * A class revealing functionality for starting, stopping, pausing a clock.<br>
 * It also counts how many seconds it was ticking. Calculates how much money<br>
 * was spent for the time based on number of participants and pay rate.<br>
 * <br>
 * Once a clock is stopped it cannot be reused. The thread has finished its job.
 *
 * @author Leni Kirilov
 * @since 2010-February
 * @version 2014-April
 */
//TODO split this class into 2 - 1 for ticking and another one for updating UI when ticks are applied
//TODO make this a class implementing Runnable. that way no issue with overriding Thread.class final methods
public class CalculatingPerSecondThread extends Thread {

    private static final int ONE_SECOND_IN_MILLIS = 1000;

    //STATE fields

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

    //HELPER fields

    /**
     * Counts the seconds that have passed in actual running.
     */
    private Duration duration;
    /**
     * The view which is going to be updated per second
     */
    private CalculatingPerSecondView view;
    /*
     * used to calculate the amount
     */
    private final Calculator calculator;
    /**
     * The formatter of the panel is used to display the data.
     */
    private NumberFormat formatter;

    public CalculatingPerSecondThread(CalculatingPerSecondView view, NumberFormat formatter, Calculator calculator) {
        this.duration = new Duration(0L);
        this.view = view;
        this.formatter = formatter;
        this.calculator = calculator;
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
                    update();
                    Thread.sleep(ONE_SECOND_IN_MILLIS);
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
     * Update 1 second to all data - clock and amount to pay.
     */
    private void update() {
        duration = duration.plus(ONE_SECOND_IN_MILLIS);
        view.setClock(getCurrentTimeFormatted());
        updateAmount();
    }

    public String getCurrentTimeFormatted() {
        return Formaters.getPeriodFormatter().print(duration.toPeriod());
    }

    /**
     * Updates the amount
     */
    private void updateAmount() {
        //TODO an lambda  cam be input here and calculate the value
        amount = calculator.calculate(duration.toStandardSeconds().getSeconds());
        view.setAmount(getFinalAmount());
    }
}