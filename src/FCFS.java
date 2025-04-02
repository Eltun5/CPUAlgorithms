import java.util.ArrayList;
import java.util.List;

public class FCFS {
    public static void fcfsAlgorithm() {
        List<Process> listOfProcessSortedByArrival = new ArrayList<>();

        for (Process p : Utilities.listOfProcess) {
            listOfProcessSortedByArrival.add(new Process(p.processNumber(), p.arrivalTime(), p.burstTime()));
        }

        listOfProcessSortedByArrival.sort(Utilities.compareByArrival);

        List<Integer> times = new ArrayList<>(Utilities.countOfProcess + 1);
        times.add(listOfProcessSortedByArrival.getFirst().arrivalTime());

        for (Process process : listOfProcessSortedByArrival) {
            times.add(times.getLast() + process.burstTime());
        }

        List<Integer> listOfProcessSequence = listOfProcessSortedByArrival.stream().
                map(Process::processNumber).toList();

        Utilities.printGanttChartTableAndAverages(times,
                listOfProcessSequence,
                listOfProcessSortedByArrival);
    }
}
