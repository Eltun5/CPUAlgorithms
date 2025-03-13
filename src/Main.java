import java.util.InputMismatchException;

public class Main {
    public static int countOfElement;

    public static int[] arrivalTimes;

    public static int[] burstTimes;

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