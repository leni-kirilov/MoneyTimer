package bg.kirilov.timer.presenter;

import bg.kirilov.timer.calculator.MoneyPerSecondCalculator;
import bg.kirilov.timer.ui.TickingPanel;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.NumberFormat;

/**
 * A class revealing functionality for starting, stopping, pausing a clock.<br>
 * It also counts how many seconds it was ticking. Calculates how much money<br>
 * was spent for the time based on number of participants and pay rate.<br>
 * <br>
 * Once a clock is stopped it cannot be reused. The thread has finished its job.
 *
 * @author Leni Kirilov
 * @version 2010-February
 */
//TODO split this class into 2 - 1 for ticking and another one for updating UI when ticks are applied
public class CalculatingPerSecondThread extends Thread {

    private static final int ONE_SECOND_IN_MILLIS = 1000;

    //used to calculate the amount
    private final MoneyPerSecondCalculator calculator;
    /**
     * Defines whether the thread should finish its lifecycle!
     */
    private boolean dead;
    /**
     * Determines whether the thread should pause its process.
     */
    private boolean paused;
    /**
     * Determines if the run method is still going on.
     */
    private boolean running;
    /**
     * Counts the seconds that have passed in actual running.
     */
    private Duration duration;
    /**
     * The view which is going to be updated per second
     */
    private CalculatingPerSecondView tickingView;
    /**
     * The amount to be displayed is stored here.
     */
    private double amount;
    /**
     * The formatter of the panel is used to display the data.
     */
    private NumberFormat formatter;

    public static PeriodFormatter timerFormatter;

    static {
        timerFormatter = new PeriodFormatterBuilder()
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

    public CalculatingPerSecondThread(TickingPanel panel) {
        this.duration = new Duration(0L);
        this.tickingView = panel;
        this.formatter = panel.getFormatter();
        this.calculator = new MoneyPerSecondCalculator(panel.getNumberPeople(), panel.getPayRate());
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

                    //paused thread must stop immediately
                    if (dead) {
                        break;
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

    //
    // =========================== PROTECTED METHODS =========================
    //

    /**
     * Stops the current thread.
     */
    public void stopThread() {
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
     * Returns the final total duration of the session.
     *
     * @return String - formatted accordingly
     */
    public String getFinalTime() {
        return getCurrentTimeFormatted();
    }

    /**
     * @return boolean - true if the thread is currently paused
     */
    public boolean isPaused() {
        return paused;
    }

    public void resumeClock() {
        setPaused(false);
        synchronized (this) {
            notify();
        }
    }

    /**
     * Sets the paused/resumed state of the thread.
     *
     * @param paused - set true if you want to pause the thread
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * The execution of the thread will stop when the thread is resumed!<br>
     * PRECONDITION - the thread is paused!<br>
     * If thread isn't paused - this will have no effect!
     */
    public void dieAtResume() {
        if (paused) {
            dead = true;
        }
    }

    /**
     * Update 1 second to all data - clock and amount to pay.
     */
    private void update() {
        duration = duration.plus(ONE_SECOND_IN_MILLIS);
        tickingView.setClock(getCurrentTimeFormatted());
        updateAmount();
    }

    private String getCurrentTimeFormatted() {
        return timerFormatter.print(duration.toPeriod());
    }

    /**
     * Updates the amount
     */
    private void updateAmount() {
        //TODO an lambda  cam be input here and calculate the value
        amount = calculator.calculate(duration.toStandardSeconds().getSeconds());

        //TODO should this formatter be extracted as well?
        String amountStr = formatter.format(amount);
        tickingView.setAmount(amountStr);
    }

    public static void main(String[] args) {

        Duration duration = new Duration(0L);
        for (int i = 0; i < 100; i++) {
            duration = duration.plus(100000L);
            System.out.print("; all seconds= " + duration.toStandardSeconds().getSeconds());
            System.out.print("; seconds= " + duration.getStandardSeconds());
            System.out.println("; all minutes= " + duration.toStandardMinutes().getMinutes());

            System.out.println(timerFormatter.print(duration.toPeriod()));
        }
    }
}
