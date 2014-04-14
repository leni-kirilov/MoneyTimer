package bg.kirilov.timer.ui;

/**
 * TickingView is a class which can be updated by a TickingPresenter
 *
 * @author Leni Kirilov
 * @since 4/14/2014
 */
public interface TickingView {

    void startClock();

    void stopClock();

    /**
     * Pauses the visualization of the clock
     */
    void pauseClock();

    /**
     * Visualizes a resuming clock.
     */
    void resumeClock();

    /**
     * Returns the clock and the input fields in initial state
     */
    void resetClock();

    void showReport(String report);

    /**
     * The user is asked for input if he wants to abort measuring
     *
     * @return true if the input is YES
     */
    boolean askIfWantToAbort();

    /**
     * The user is asked for input if he wants to be shown the end report
     *
     * @return true if the input is YES
     */
    boolean askIfWantReport();

    /**
     * Sets pay rate in UI.
     *
     * @param payRate - valid double number
     */
    void setPayRate(double payRate);

    /**
     * Updates numberOfPeople label in the UI
     *
     * @param number - valid integer number
     */
    void setNumberPeople(int number);
}
