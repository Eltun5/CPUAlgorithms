import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilities {
    public static Scanner sr = new Scanner(System.in);

    public static final String strategies = """
            First-Come, First-Served (1)
            Shortest Job Next (Non-Preemptive) (2)
            Round Robin (3)
            Priority Scheduling (Non-Preemptive) (4)
            Enter int:""";

    public static final String enterCount = "Please enter how many processes there will be:";

    public static final String wrongNum = "Please enter correct integer!!!";

    public static final String wrongInput = "Please enter correct integer!!!";

    public static final String headerOfTable = """
            Process | Arrival Time | Burst Time | Waiting Time | Turnaround Time
            ------- | ------------ | ---------- | ------------ | ---------------""";


    public static void printStrategies() {
        System.out.print(strategies);
    }

    public static void printEnterCount() {
        System.out.print(enterCount);
    }

    public static void printWrongNum() {
        System.out.print(wrongNum);
    }

    public static void printWrongInput() {
        System.out.print(wrongInput);
    }

    public static void printHeaderOfTable() {
        System.out.println(headerOfTable);
    }

    public static void takeElements() {
        printEnterCount();
        Main.countOfElement = sr.nextInt();

        while (Main.countOfElement <= 0) {
            System.out.print("Please enter number grater than 0 :");
            printEnterCount();
            Main.countOfElement = sr.nextInt();
        }

        for (int i = 0; i < Main.countOfElement; i++) {
            System.out.print("Enter the arrival time of P" + (i + 1) + " :");
            int arrivalTime = sr.nextInt();

            while (arrivalTime < 0) {
                System.out.print("Please enter number equals or grater than  0 :");
                System.out.print("Enter the arrival time of P" + (i + 1) + " :");
                arrivalTime = sr.nextInt();
            }

            System.out.print("Enter the burst time of P" + (i + 1) + " :");
            int burstTime = sr.nextInt();

            while (burstTime < 0) {
                System.out.print("Please enter number equals or grater than 0 :");
                System.out.print("Enter the burst time of P" + (i + 1) + " :");
                burstTime = sr.nextInt();
            }

            Main.arrivalAndBurstTimesAndEnterSequence.add(new int[]{arrivalTime, burstTime, (i + 1)});
        }
    }

    public static void askWantTryAgain() {
        System.out.print("Do you want to try again (yes is 1, no is 0):");
        if (sr.nextInt() == 0) System.exit(0);
    }

    public static void askWantTryAgainWithSameElements() {
        System.out.print("Do you want to try again with same processes (yes is 1, no is 0):");
        if (sr.nextInt() == 0) Utilities.takeElements();
    }

    public static void chooseOperationTypeAndOutputType() {
        printStrategies();
        int operation = sr.nextInt();

        switch (operation) {
            case 1 -> FCFS.fcfsOperation();
            case 2 -> SJN.sjnOperation();
            case 3 -> RoundRobin.rrOperation();
            case 4 -> PriorityScheduling.prioritySchedulingNonPreemptiveOperation();
            default -> printWrongNum();
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
        List<Integer> priorities = new ArrayList<>(Main.countOfElement);
        for (int i = 0; i < Main.countOfElement; i++) {
            System.out.print("Please enter priority of P" + (i + 1) + " : ");
            int input = sr.nextInt();
            while (input < 0) {
                System.out.println("Please enter number grater than 0.");
                System.out.print("Please enter priority of P" + (i + 1) + " : ");
                input = sr.nextInt();
            }
            priorities.add(input);
        }
        return priorities;
    }

    public static void printTableAndAverages(List<Integer> times, List<Integer> orders, List<int[]> sortedListForArrival) {
        Utilities.printGanttChart(times, orders);

        float sumOfWaitingTime = 0;
        float sumOfTurnaroundTime = 0;

        Utilities.printHeaderOfTable();
        for (int i = 0; i < Main.countOfElement; i++) {
            int order = orders.get(i);
            int[] currentArr = sortedListForArrival.stream().
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

        System.out.println("Average Waiting time is : " + (sumOfWaitingTime / Main.countOfElement));
        System.out.println("Average Turnaround time is : " + (sumOfTurnaroundTime / Main.countOfElement));
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

}
