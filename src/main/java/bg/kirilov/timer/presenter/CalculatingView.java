package bg.kirilov.timer.presenter;

/**
 * A view to be updated by a thread on regular time interval.
 *
 * @author Leni Kirilov
 * @since 4/13/2014
 */
public interface CalculatingView {

    void setClock(String formattedClock);

    void setAmount(String formattedAmount);
}
