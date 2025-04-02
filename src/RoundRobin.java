import java.util.ArrayList;
import java.util.List;

public class RoundRobin {

    public static void roundRobinAlgorithm() {
        int timeQuantum = Utilities.takeTimeQuantum();

        List<Integer> listOfProcessSequence = new ArrayList<>();
        List<Integer> times = new ArrayList<>();

        List<Process> listOfProcessSortedByArrival = new ArrayList<>();

        for (Process p : Utilities.listOfProcess) {
            listOfProcessSortedByArrival.add(new Process(p.processNumber(), p.arrivalTime(), p.burstTime()));
        }

        listOfProcessSortedByArrival.sort(Utilities.compareByArrival);

        times.add(listOfProcessSortedByArrival.getFirst().arrivalTime());

        while (!listOfProcessSortedByArrival.stream().
                map(Process::timeNeededToFinish).
                filter(i -> i != 0).toList().isEmpty()) {

            for (int i = 0; i < Utilities.countOfProcess; i++) {
                if (listOfProcessSortedByArrival.get(i).timeNeededToFinish() > 0 &&
                    times.getLast() >= listOfProcessSortedByArrival.get(i).arrivalTime()) {

                    listOfProcessSequence.add(listOfProcessSortedByArrival.get(i).processNumber());

                    if (listOfProcessSortedByArrival.get(i).timeNeededToFinish() >= timeQuantum) {
                        times.add(times.getLast() + timeQuantum);
                    } else {
                        times.add(times.getLast() + listOfProcessSortedByArrival.get(i).timeNeededToFinish());
                    }
                    listOfProcessSortedByArrival.get(i).
                            decreaseTimeNeededToFinish(timeQuantum);
                }
            }
        }
        Utilities.printGanttChart(times, listOfProcessSequence);

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();
        for (int i = 0; i < Utilities.countOfProcess; i++) {
            int processSequence = listOfProcessSortedByArrival.get(i).processNumber();
            Process currentProcess = listOfProcessSortedByArrival.get(i);

            int lastIndexOfOrder = listOfProcessSequence.lastIndexOf(processSequence);
            int indexOfOrder = listOfProcessSequence.indexOf(processSequence);
            int waitingTime = times.get(indexOfOrder) - currentProcess.arrivalTime();
            for (int j = indexOfOrder + 1; j < lastIndexOfOrder; j++) {
                if (listOfProcessSequence.get(j) != processSequence) {
                    waitingTime += (times.get(j) - times.get(j - 1));
                }
            }
            int turnaroundTime = times.get(lastIndexOfOrder + 1) - currentProcess.arrivalTime();
            sumOfWaitingTime += waitingTime;
            sumOfTurnaroundTime += turnaroundTime;
            System.out.printf("""
                            P%s      | %s            | %s          | %s            | %s
                            """
                    , processSequence, currentProcess.arrivalTime(), currentProcess.burstTime(),
                    waitingTime, turnaroundTime
            );
        }

        System.out.println("Average Waiting time is : " + (sumOfWaitingTime / Utilities.countOfProcess));
        System.out.println("Average Turnaround time is : " + (sumOfTurnaroundTime / Utilities.countOfProcess));

    }
}
