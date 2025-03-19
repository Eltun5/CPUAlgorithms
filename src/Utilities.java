import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilities {
    public static Scanner sr = new Scanner(System.in);

    public static final String algorithms = """
            First-Come, First-Served (1)
            Shortest Job Next (Non-Preemptive) (2)
            Round Robin (3)
            Priority Scheduling (Non-Preemptive) (4)
            Enter int:""";

    public static final String enterCountOfProcesses = "Please enter how many processes there will be:";

    public static final String wrongNumMassage = "Please enter correct integer!!!";

    public static final String wrongInputMassage = "Please enter correct integer!!!";

    public static final String headerOfTable = """
            Process | Arrival Time | Burst Time | Waiting Time | Turnaround Time
            ------- | ------------ | ---------- | ------------ | ---------------""";


    public static void printAlgorithmTypes() {
        System.out.print(algorithms);
    }

    public static void printMassageWitchEnterCount() {
        System.out.print(enterCountOfProcesses);
    }

    public static void printMassageWhichEnteredWrongNum() {
        System.out.print(wrongNumMassage);
    }

    public static void printWrongInput() {
        System.out.print(wrongInputMassage);
    }

    public static void printHeaderOfTable() {
        System.out.println(headerOfTable);
    }

    public static void takeProcesses() {
        printMassageWitchEnterCount();
        Main.countOfProcess = sr.nextInt();

        Main.countOfProcess = accurateGraterThanZero(Main.countOfProcess, enterCountOfProcesses );

        while (Main.countOfProcess <= 0) {
            System.out.print("Please enter number equals or grater than 0 :");
            printMassageWitchEnterCount();
            Main.countOfProcess = sr.nextInt();
        }

        for (int i = 0; i < Main.countOfProcess; i++) {
            String arrivalMassage="Enter the arrival time of P" + (i + 1) + " :";
            System.out.print(arrivalMassage);
            int arrivalTime = sr.nextInt();

            arrivalTime = accurateGraterThanZero(arrivalTime, arrivalMassage);

            String burstMassage="Enter the burst time of P" + (i + 1) + " :";
            System.out.print(burstMassage);
            int burstTime = sr.nextInt();

            burstTime = accurateGraterThanZero(burstTime, burstMassage);

            Main.listOfArrivalBurstTimesAndEnterSequence.add(new int[]{arrivalTime, burstTime, (i + 1)});
        }
    }

    public static void askWantTryAgain() {
        System.out.print("Do you want to try again (yes is 1, no is 0):");
        if (sr.nextInt() == 0) System.exit(0);
    }

    public static void askWantTryAgainWithSameProcesses() {
        System.out.print("Do you want to try again with same processes (yes is 1, no is 0):");
        if (sr.nextInt() == 0) Utilities.takeProcesses();
    }

    public static void chooseAlgorithmType() {
        printAlgorithmTypes();
        int operation = sr.nextInt();

        switch (operation) {
            case 1 -> FCFS.fcfsAlgorithm();
            case 2 -> SJN.sjnAlgorithm();
            case 3 -> RoundRobin.roundRobinAlgorithm();
            case 4 -> PriorityScheduling.prioritySchedulingNonPreemptiveOperation();
            default -> printMassageWhichEnteredWrongNum();
        }
    }

    public static int takeTimeQuantum() {
        System.out.print("Please enter time quantum:");
        int timeQuantum = sr.nextInt();
        while (timeQuantum <= 0) {
            System.out.print("Please enter time quantum number grater than 0 :");
            timeQuantum = sr.nextInt();
        }
        return timeQuantum;
    }

    public static List<Integer> takePriorities() {
        List<Integer> priorities = new ArrayList<>(Main.countOfProcess);
        for (int i = 0; i < Main.countOfProcess; i++) {
            String massage = "Please enter priority of P" + (i + 1) + " : ";
            System.out.print(massage);
            int input = sr.nextInt();
            input = accurateGraterThanZero(input, massage);
            priorities.add(input);
        }
        return priorities;
    }

    public static void printGanttChartTableAndAverages(List<Integer> times, List<Integer> orders, List<int[]> listSortedByArrival) {
        Utilities.printGanttChart(times, orders);

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();
        for (int i = 0; i < Main.countOfProcess; i++) {
            int order = orders.get(i);
            int[] currentArr = listSortedByArrival.stream().
                    filter(arr -> order == arr[arr.length - 1]).toList().getFirst();
            int arrivalTime = currentArr[0];
            int waitingTime = times.get(i) - arrivalTime;
            int turnaroundTime = times.get(i + 1) - arrivalTime;
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

    public static void printGanttChart(List<Integer> times, List<Integer> orders) {
        System.out.print("|");

        for (int i : orders) {
            System.out.print(" P" + (i) + " |");
        }

        System.out.println();

        for (int time : times) {
            System.out.print(time + (time > 9 ? (time > 99 ? "  " : "   ") : "    "));
        }

        System.out.println();
    }

    public static int accurateGraterThanZero(int value, String massage) {
        while (value < 0) {
            System.out.println("Please enter number grater than 0.");
            System.out.print(massage);
            value = sr.nextInt();
        }
        return value;
    }
}
