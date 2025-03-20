import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityScheduling {

    public static List<Process> listOfProcessSortedByArrival;

    public static List<Integer> times;

    public static List<Integer> listOfProcessSequence;

    public static void prioritySchedulingNonPreemptiveOperation() {
        setRequiredElements();

        for (int i = 0; i < Utilities.countOfProcess; i++) {
            int currentTime = times.getLast();
            Process nextProcess = listOfProcessSortedByArrival.stream().
                    filter(process -> process.arrivalTime() <= currentTime &&
                                      !listOfProcessSequence.contains(process.processNumber())).
                    sorted(Utilities.compareByPriority).toList().getFirst();
            listOfProcessSequence.add(nextProcess.processNumber());
            times.add(times.getLast() + nextProcess.burstTime());
        }

        Utilities.printGanttChartTableAndAverages(times,
                listOfProcessSequence,
                listOfProcessSortedByArrival);
    }

    private static void setRequiredElements() {
        Utilities.takePriorities();

        Collections.copy(Utilities.listOfProcess, listOfProcessSortedByArrival);
        listOfProcessSortedByArrival.sort(Utilities.compareByPriority);
        listOfProcessSortedByArrival.sort(Utilities.compareByArrival);

        times = new ArrayList<>(Utilities.countOfProcess + 1);
        listOfProcessSequence = new ArrayList<>(Utilities.countOfProcess);
        times.add(listOfProcessSortedByArrival.getFirst().arrivalTime());
    }
}
