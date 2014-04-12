package bg.kirilov.timer;

import java.text.NumberFormat;
import javax.swing.JLabel;

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
public class TickingThread extends Thread {

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
    private int currentSeconds;
    /**
     * Here it's displayed the total time passed in a suitable format.
     */
    private JLabel clockLabel;
    /**
     * Here it's displayed the total amount to pay for the session in a suitable format.
     */
    private JLabel amountLabel;
    /**
     * The multiplication of numberOfPeople by payRate.
     */
    private double peoplePay;
    /**
     * The amount to be displayed is stored here.
     */
    private double amount;
    /**
     * The formatter of the panel is used to display the data.
     */
    private NumberFormat formatter;
    /**
     * Used to concatenate strings faster.
     */
    private StringBuilder stringBuilder = new StringBuilder(1);

    /**
     * Added constructor just for the purpose of inheritance
     */
    protected TickingThread() {
        this(new TickingPanel());
    }

    protected TickingThread(TickingPanel panel) {
        this.clockLabel = panel.getClockLabel();
        this.amountLabel = panel.getAmountLabel();
        this.formatter = panel.getFormatter();

        this.peoplePay = panel.getNumberPeople() * panel.getPayRate();
    }

    @Override
    public void start() {
        running = true;
        super.start();
    }

    /**
     * Simmulates the work of an actual clock. Can be paused, resumed and stopped.
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
                    Thread.sleep(1000);
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
    protected void stopThread() {
        running = false;
    }

    /**
     * Returns the final amount to be payed for the sessions.
     * @return String - formatted accordingly
     */
    protected String getFinalAmount() {
        return formatter.format(amount);
    }

    /**
     * Returns the final total duration of the session.
     * @return String - formatted accordingly
     */
    protected String getFinalTime() {
        return stringBuilder.toString();
    }

    /**
     *
     * @return boolean - true if the thread is currently paused
     */
    protected boolean isPaused() {
        return paused;
    }

    /**
     * Sets the paused/resumed state of the thread.
     *
     * @param paused - set true if you want to pause the thread
     */
    protected void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * The execution of the thread will stop when the thread is resumed!<br>
     * PRECONDITION - the thread is paused!<br>
     * If thread isn't paused - this will have no effect!
     */
    protected void dieAtResume() {
        if (paused) {
            dead = true;
        }
    }

    //
    //================PRIVATE METHODS=========================
    //
    /**
     * Update 1 second to all data - clock and amount to pay.
     */
    private void update() {
        currentSeconds++;
        updateClock();
        updateAmount();
    }

    /**
     * Updates the clock
     */
    private void updateClock() {
        int temp = currentSeconds;

        int hours = temp / (60 * 60);
        String hourStr = "";
        if (hours < 10) {
            hourStr = "0";
        }

        temp = currentSeconds % (60 * 60);
        int minutes = temp / 60;
        String minutesStr = "";
        if (minutes < 10) {
            minutesStr = "0";
        }

        int seconds = temp % 60;
        String secondsStr = "";
        if (seconds < 10) {
            secondsStr = "0";
        }

        stringBuilder.delete(0, stringBuilder.length());

        stringBuilder.append(hourStr).append(hours).append(":");
        stringBuilder.append(minutesStr).append(minutes).append(":");
        stringBuilder.append(secondsStr).append(seconds);
        clockLabel.setText(stringBuilder.toString());
    }

    /**
     * Updates the amount
     */
    private void updateAmount() {
        amount = peoplePay * currentSeconds / (60 * 60.0);
        String text = formatter.format(amount);
        if (text.equals("0")) {
            text = "0.00";
        }
        amountLabel.setText(text);
    }
}
