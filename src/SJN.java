import java.util.ArrayList;
import java.util.List;

public class SJN {
    public static void sjnAlgorithm() {
        List<Process> listOfProcessSortedByArrival = new ArrayList<>();

        for (Process p : Utilities.listOfProcess) {
            listOfProcessSortedByArrival.add(new Process(p.processNumber(), p.arrivalTime(), p.burstTime()));
        }

        listOfProcessSortedByArrival.sort(Utilities.compareByArrival);

        List<Integer> listOfProcessSequence = new ArrayList<>(Utilities.countOfProcess);
        List<Integer> times = new ArrayList<>(Utilities.countOfProcess + 1);

        times.add(listOfProcessSortedByArrival.getFirst().arrivalTime());

        System.out.println();

        for (int i = 0; i < Utilities.countOfProcess; i++) {
            int currentTime = times.getLast();
            Process nextProcess = listOfProcessSortedByArrival.stream().
                    filter(process -> process.arrivalTime() <= currentTime &&
                                      !listOfProcessSequence.contains(process.processNumber())).
                    sorted(Utilities.compareByBurst).toList().getFirst();
            listOfProcessSequence.add(nextProcess.processNumber());
            times.add(times.getLast() + nextProcess.burstTime());
        }

        Utilities.printGanttChartTableAndAverages(times,
                listOfProcessSequence,
                listOfProcessSortedByArrival);
    }
}
