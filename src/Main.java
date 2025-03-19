import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Main {
    public static int countOfProcess;

    public static List<int[]> listOfArrivalBurstTimesAndEnterSequence = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Please dont enter process which has empty time between 2 process.\n" +
                           "For example: We enter 2 process\n" +
                           "P1 (arrival time is 0, burst time is 1) ,\n" +
                           "P2 (arrival time is 5, burst time is 1) \n" +
                           "This time the algorithm will not work as expected!!!\n");
        try {
            Utilities.takeProcesses();
            while (true) {
                Utilities.chooseAlgorithmType();
                Utilities.askWantTryAgain();
                Utilities.askWantTryAgainWithSameProcesses();
            }
        } catch (InputMismatchException e) {
            Utilities.printWrongInput();
        }
    }
}