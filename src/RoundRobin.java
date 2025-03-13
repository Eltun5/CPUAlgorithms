import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoundRobin {

    public static void rrOperation() {
        int timeQuantum = Utilities.takeTimeQuantum();

        List<int[]> listOfArrivalBurstEnterSequence = new ArrayList<>();
        for (int i = 0; i < Main.countOfElement; i++) {
            listOfArrivalBurstEnterSequence.add(new int[]{Main.arrivalTimes[i], Main.burstTimes[i], i + 1});
        }

        List<Integer> orders = new ArrayList<>();
        List<Integer> times = new ArrayList<>();

        List<int[]> sortedListForArrival = listOfArrivalBurstEnterSequence.stream().
                sorted(Comparator.comparing(arr -> arr[0])).toList();
        List<Integer> copyOfBurstTimes = sortedListForArrival.stream().map(arr -> arr[1]).collect(Collectors.toList());

        times.add(sortedListForArrival.getFirst()[0]);

        while (!copyOfBurstTimes.stream().filter(i -> i != 0).toList().isEmpty()) {
            for (int i = 0; i < Main.countOfElement; i++) {
                if (copyOfBurstTimes.get(i) > 0 && times.getLast() >= sortedListForArrival.get(i)[0]) {
                    orders.add(sortedListForArrival.get(i)[2]);
                    if (copyOfBurstTimes.get(i) >= timeQuantum) {
                        times.add(times.getLast() + timeQuantum);
                        copyOfBurstTimes.set(i, copyOfBurstTimes.get(i) - timeQuantum);
                    } else {
                        times.add(times.getLast() + copyOfBurstTimes.get(i));
                        copyOfBurstTimes.set(i, 0);
                    }
                }
            }
        }
        Utilities.printGanttChart(times, orders);

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();
        for (int i = 0; i < Main.countOfElement; i++) {
            int order = sortedListForArrival.get(i)[2];
            int[] currentArr = sortedListForArrival.stream().
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

        System.out.println("Average Waiting time is : " + (sumOfWaitingTime / Main.countOfElement));
        System.out.println("Average Turnaround time is : " + (sumOfTurnaroundTime / Main.countOfElement));

    }
}
