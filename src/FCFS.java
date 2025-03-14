import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS {
    public static void fcfsOperation() {
        List<int[]> listSortedByArrival = Main.arrivalAndBurstTimesAndEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();

        List<Integer> times = new ArrayList<>(Main.countOfElement + 1);
        times.add(listSortedByArrival.getFirst()[0]);

        for (int[] arr : listSortedByArrival) {
            times.add(times.getLast() + arr[1]);
        }
        Utilities.printGanttChart(times, listSortedByArrival.stream().map(arr -> arr[2]).toList());

        System.out.println();

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();

        for (int i = 0; i < Main.countOfElement; i++) {
            int[] arr = listSortedByArrival.get(i);
            int waitingTime = times.get(i) - arr[0];
            int turnaroundTime = times.get(i + 1) - arr[0];
            sumOfWaitingTime += waitingTime;
            sumOfTurnaroundTime += turnaroundTime;
            System.out.printf(
                    "P%s      | %s            | %s          | %s            | %s%n",
                    arr[2], arr[0], arr[1], waitingTime, turnaroundTime
            );
        }

        System.out.println();

        System.out.println("Average Waiting time is : " + (sumOfWaitingTime / Main.countOfElement));
        System.out.println("Average Turnaround time is : " + (sumOfTurnaroundTime / Main.countOfElement));

    }
}
