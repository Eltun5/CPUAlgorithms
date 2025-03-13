import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJN {
    public static void sjnOperation() {
        List<int[]> arrOfArrivalBurstEnterSequence = new ArrayList<>();
        for (int i = 0; i < Main.countOfElement; i++) {
            arrOfArrivalBurstEnterSequence.add(new int[]{Main.arrivalTimes[i], Main.burstTimes[i], i + 1});
        }

        List<int[]> sortedListForArrival = arrOfArrivalBurstEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();

        List<Integer> orders = new ArrayList<>(Main.countOfElement);
        List<Integer> times = new ArrayList<>(Main.countOfElement + 1);
        times.add(sortedListForArrival.getFirst()[0]);

        System.out.println();
        for (int i = 0; i < Main.countOfElement; i++) {
            int currentTime = times.getLast();
            List<int[]> list = sortedListForArrival.stream().
                    filter(arr -> arr[0] <= currentTime && !orders.contains(arr[2])).
                    sorted(Comparator.comparing(arr -> arr[1])).toList();
            orders.add(list.getFirst()[2]);
            times.add(times.getLast() + list.getFirst()[1]);
        }

        Utilities.printTableAndAverages(times,orders,sortedListForArrival);
    }

}
