import java.util.ArrayList;
import java.util.List;

public class PriorityScheduling {

    public static List<Process> listOfProcessSortedByArrival = new ArrayList<>();

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

    public static void prioritySchedulingPreemptiveOperation() {
        setRequiredElements();

        Process currentProcess = listOfProcessSortedByArrival.getFirst();

        while (!listOfProcessSortedByArrival.stream().map(Process::timeNeededToFinish)
                .filter(i -> i != 0).toList().isEmpty()) {
            int finishTimeForCurrentProcess = times.getLast() + currentProcess.timeNeededToFinish();
            int currentProcessPriority = currentProcess.priority();
            List<Process> list = listOfProcessSortedByArrival.stream().
                    filter(process -> process.arrivalTime() <= finishTimeForCurrentProcess &&
                                      process.timeNeededToFinish() != 0 &&
                                      process.priority() < currentProcessPriority).toList();

            if (list.isEmpty()) {
                times.add(times.getLast() + currentProcess.timeNeededToFinish());
                currentProcess.decreaseTimeNeededToFinish(currentProcess.timeNeededToFinish());
                listOfProcessSequence.add(currentProcess.processNumber());

                List<Process> list1 = listOfProcessSortedByArrival.stream().
                        filter(process -> process.arrivalTime() <= times.getLast() &&
                                          process.timeNeededToFinish() != 0).
                        sorted(Utilities.compareByPriority).toList();

                if (list1.isEmpty()) {
                    break;
                } else {
                    currentProcess = list1.getFirst();
                }
            } else {
                Process first = list.getFirst();
                currentProcess.decreaseTimeNeededToFinish(first.arrivalTime() - times.getLast());
                listOfProcessSequence.add(currentProcess.processNumber());
                times.add(first.arrivalTime());
                currentProcess = first;
            }
        }
        Utilities.printGanttChart(times, listOfProcessSequence);

        printTableAndAvg();
    }

    private static void setRequiredElements() {
        Utilities.takePriorities();

        for (Process p : Utilities.listOfProcess) {
            Process process = new Process(p.processNumber(), p.arrivalTime(), p.burstTime());
            process.setPriority(p.priority());
            listOfProcessSortedByArrival.add(process);
        }

        listOfProcessSortedByArrival.sort(Utilities.compareByPriority);
        listOfProcessSortedByArrival.sort(Utilities.compareByArrival);

        times = new ArrayList<>(Utilities.countOfProcess + 1);
        listOfProcessSequence = new ArrayList<>(Utilities.countOfProcess);
        times.add(listOfProcessSortedByArrival.getFirst().arrivalTime());
    }

    private static void printTableAndAvg() {
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
                    waitingTime += (times.get(j + 1) - times.get(j));
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
