import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FCFS {
    public static void fcfsOperation() {

        int[] originalArrival = Arrays.copyOf(Main.arrivalTimes, Main.countOfElement);
        int[] sortedArrival = Arrays.copyOf(Main.arrivalTimes, Main.countOfElement);
        Arrays.sort(sortedArrival);

        List<Integer> orders = new ArrayList<>(Main.countOfElement);

        List<Integer> times = new ArrayList<>(Main.countOfElement + 1);
        times.add(sortedArrival[0]);

        for (int i = 0; i < Main.countOfElement; i++) {
            for (int j = 0; j < Main.countOfElement; j++) {
                if (sortedArrival[i] == originalArrival[j]) {
                    orders.add(j + 1);
                    originalArrival[j] = -1;
                    times.add(times.getLast() + Main.burstTimes[j]);
                }
            }
        }
        Utilities.printGanttChart(times, orders);

        System.out.println();

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();
        for (int i = 0; i < Main.countOfElement; i++) {
            int order = orders.get(i);
            int currentArrival = sortedArrival[i];
            int waitingTime = times.get(i) - currentArrival;
            int turnaroundTime = times.get(i + 1) - currentArrival;
            sumOfWaitingTime += waitingTime;
            sumOfTurnaroundTime += turnaroundTime;
            System.out.printf(
                    "P%s      | %s            | %s          | %s            | %s%n",
                    order, currentArrival, Main.burstTimes[order - 1], waitingTime, turnaroundTime
            );

        }

        System.out.println();

        System.out.println("Average Waiting time is : " + (sumOfWaitingTime / Main.countOfElement));
        System.out.println("Average Turnaround time is : " + (sumOfTurnaroundTime / Main.countOfElement));

    }
}
