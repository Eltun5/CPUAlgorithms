import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS {
    public static void fcfsAlgorithm() {
        List<int[]> listSortedByArrival = Main.listOfArrivalBurstTimesAndEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();

        List<Integer> times = new ArrayList<>(Main.countOfProcess + 1);
        times.add(listSortedByArrival.getFirst()[0]);

        for (int[] arr : listSortedByArrival) {
            times.add(times.getLast() + arr[1]);
        }

        List<Integer> orders = listSortedByArrival.stream().map(arr -> arr[2]).toList();

        Utilities.printGanttChartTableAndAverages(times,orders,listSortedByArrival);
    }
}
