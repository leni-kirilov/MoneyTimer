package bg.kirilov.timer.presenter;

/**
 * A view to be updated by a thread on regular time interval.
 *
 * @author Leni Kirilov
 * @since 4/13/2014
 */
//TODO may be this should extend TickingView interface
public interface CalculatingView {

    void setClock(String formattedClock);

    void setAmount(String formattedAmount);

    //TODO seems like setPayRate and setNumberOfPeople are relevant for this interface. and not the Ticking one
}
