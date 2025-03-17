import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
    public static int countOfElement;

    public static List<int[]> arrivalAndBurstTimesAndEnterSequence = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Please dont enter process which has empty time between 2 process.\n" +
                           "For example: We enter 2 process\n" +
                           "P1 (arrival time is 0, burst time is 1) ,\n" +
                           "P2 (arrival time is 5, burst time is 1) \n" +
                           "This time the algorithm will not work as expected!!!\n");
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