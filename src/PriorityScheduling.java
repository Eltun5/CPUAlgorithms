import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduling {

    public static List<Integer> priorities;

    public static List<int[]> listOfArrivalBurstPriorityEnterSequence;

    public static List<int[]> listSortedByArrival;

    public static List<Integer> times;

    public static List<Integer> orders;

    public static void prioritySchedulingNonPreemptiveOperation() {
        setRequiredElements();

        for (int i = 0; i < Main.countOfProcess; i++) {
            int currentTime = times.getLast();
            List<int[]> listSortedByPriority = listSortedByArrival.stream().
                    filter(arr -> arr[0] <= currentTime && !orders.contains(arr[3])).
                    sorted(Comparator.comparing(arr -> arr[2])).toList();
            orders.add(listSortedByPriority.getFirst()[3]);
            times.add(times.getLast() + listSortedByPriority.getFirst()[1]);
        }

        Utilities.printGanttChartTableAndAverages(times, orders, listSortedByArrival);
    }

    private static void setRequiredElements() {
        priorities = Utilities.takePriorities();
        listOfArrivalBurstPriorityEnterSequence = new ArrayList<>();
        var list = Main.listOfArrivalBurstTimesAndEnterSequence;

        for (int i = 0; i < Main.countOfProcess; i++) {
            listOfArrivalBurstPriorityEnterSequence.
                    add(new int[]{list.get(i)[0], list.get(i)[1], priorities.get(i), i + 1});
        }

        listSortedByArrival = listOfArrivalBurstPriorityEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[2])).
                sorted(Comparator.comparing(arr -> arr[0])).toList();
        times = new ArrayList<>(Main.countOfProcess + 1);
        orders = new ArrayList<>(Main.countOfProcess);
        times.add(listSortedByArrival.getFirst()[0]);
    }
}
