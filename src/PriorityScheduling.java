import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduling {

    public static List<Integer> priorities;

    public static List<int[]> listOfArrivalBurstPriorityEnterSequence;

    public static List<int[]> sortedListForArrival;

    public static List<Integer> times;

    public static List<Integer> orders;

    public static void prioritySchedulingNonPreemptiveOperation() {
        setRequiredElements();

        for (int i = 0; i < Main.countOfElement; i++) {
            int currentTime = times.getLast();
            List<int[]> listSortedByPriority = sortedListForArrival.stream().
                    filter(arr -> arr[0] <= currentTime && !orders.contains(arr[3])).
                    sorted(Comparator.comparing(arr -> arr[2])).toList();
            orders.add(listSortedByPriority.getFirst()[3]);
            times.add(times.getLast() + listSortedByPriority.getFirst()[1]);
        }

        Utilities.printTableAndAverages(times,orders,sortedListForArrival);
    }

    private static void setRequiredElements(){
        priorities = Utilities.takePriorities();
        listOfArrivalBurstPriorityEnterSequence = new ArrayList<>();

        for (int i = 0; i < Main.countOfElement; i++) {
            listOfArrivalBurstPriorityEnterSequence.
                    add(new int[]{
                            Main.arrivalTimes[i], Main.burstTimes[i], priorities.get(i), i + 1
                    });
        }

        sortedListForArrival = listOfArrivalBurstPriorityEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();
        times = new ArrayList<>(Main.countOfElement+1);
        orders = new ArrayList<>(Main.countOfElement);
        times.add(sortedListForArrival.getFirst()[0]);
    }
}
