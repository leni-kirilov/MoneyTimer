package bg.kirilov.timer.ui;

/**
 * TickingView is a class which can be updated by a TickingPresenter
 *
 * @author Leni Kirilov
 * @date 4/14/2014
 */
public interface TickingView {

    void startClock();

    void stopClock();

    void pauseClock();

    void resumeClock();

    void resetClock();

    void setPayRate(double payRate);

    void setNumberPeople(int number);
}
