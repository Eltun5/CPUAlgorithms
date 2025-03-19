import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJN {
    public static void sjnAlgorithm() {
        List<int[]> listSortedByArrival = Main.listOfArrivalBurstTimesAndEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();

        List<Integer> orders = new ArrayList<>(Main.countOfProcess);
        List<Integer> times = new ArrayList<>(Main.countOfProcess + 1);

        times.add(listSortedByArrival.getFirst()[0]);

        System.out.println();

        for (int i = 0; i < Main.countOfProcess; i++) {
            int currentTime = times.getLast();
            int[] nextProcess = listSortedByArrival.stream().
                    filter(arr -> arr[0] <= currentTime && !orders.contains(arr[2])).
                    sorted(Comparator.comparing(arr -> arr[1])).toList().getFirst();
            orders.add(nextProcess[2]);
            times.add(times.getLast() + nextProcess[1]);
        }

        Utilities.printGanttChartTableAndAverages(times, orders, listSortedByArrival);
    }
}
