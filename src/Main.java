import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
    public static int countOfElement;

    public static List<int[]> arrivalAndBurstTimesAndEnterSequence = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Utilities.takeElements();
            while (true) {
                Utilities.chooseOperationTypeAndOutputType();
                Utilities.askWantTryAgain();
                Utilities.askWantTryAgainWithSameElements();
            }
        } catch (InputMismatchException e) {
            Utilities.printWrongInput();
        }
    }
}