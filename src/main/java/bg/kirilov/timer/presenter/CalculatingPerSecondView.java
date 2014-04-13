package bg.kirilov.timer.presenter;

/**
 * A view to be updated by a thread each second.
 *
 * @author Leni Kirilov
 * @date 4/13/2014
 */
public interface CalculatingPerSecondView {

    void setClock(String formattedTimer);

    void setAmount(String formattedAmount);
}
