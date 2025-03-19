import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoundRobin {

    public static void roundRobinAlgorithm() {
        int timeQuantum = Utilities.takeTimeQuantum();

        List<Integer> orders = new ArrayList<>();
        List<Integer> times = new ArrayList<>();

        List<int[]> listSortedByArrival = Main.listOfArrivalBurstTimesAndEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();

        List<Integer> listOfTimeWitchNeedToFinish = listSortedByArrival.stream().map(arr -> arr[1]).collect(Collectors.toList());

        times.add(listSortedByArrival.getFirst()[0]);

        while (!listOfTimeWitchNeedToFinish.stream().filter(i -> i != 0).toList().isEmpty()) {
            for (int i = 0; i < Main.countOfProcess; i++) {
                if (listOfTimeWitchNeedToFinish.get(i) > 0 &&
                    times.getLast() >= listSortedByArrival.get(i)[0]) {
                    orders.add(listSortedByArrival.get(i)[2]);

                    if (listOfTimeWitchNeedToFinish.get(i) >= timeQuantum) {
                        times.add(times.getLast() + timeQuantum);
                        listOfTimeWitchNeedToFinish.set(i,
                                listOfTimeWitchNeedToFinish.get(i) - timeQuantum);
                    } else {
                        times.add(times.getLast() + listOfTimeWitchNeedToFinish.get(i));
                        listOfTimeWitchNeedToFinish.set(i, 0);
                    }
                }
            }
        }
        Utilities.printGanttChart(times, orders);

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();
        for (int i = 0; i < Main.countOfProcess; i++) {
            int order = listSortedByArrival.get(i)[2];
            int[] currentArr = listSortedByArrival.stream().
                    filter(arr -> order == arr[2]).toList().getFirst();
            int lastIndexOfOrder = orders.lastIndexOf(order);
            int indexOfOrder = orders.indexOf(order);
            int waitingTime = times.get(indexOfOrder)-currentArr[0];
            for (int j = indexOfOrder + 1; j < lastIndexOfOrder; j++) {
                if (orders.get(j) != order) {
                    waitingTime += (times.get(j) - times.get(j - 1));
                }
            }
            int turnaroundTime = times.get(lastIndexOfOrder+1)- currentArr[0];
            sumOfWaitingTime += waitingTime;
            sumOfTurnaroundTime += turnaroundTime;
            System.out.printf("""
                            P%s      | %s            | %s          | %s            | %s
                            """
                    , order, currentArr[0], currentArr[1], waitingTime, turnaroundTime
            );
        }

        System.out.println("Average Waiting time is : " + (sumOfWaitingTime / Main.countOfProcess));
        System.out.println("Average Turnaround time is : " + (sumOfTurnaroundTime / Main.countOfProcess));

    }
}
