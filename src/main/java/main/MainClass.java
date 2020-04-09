package main;

import simulator.Scheduler;
import simulator.SimulationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    private static int clientsNumber = 0;
    private static int queuesNumber = 0;
    private static int simInterval = 0;
    private static int minArrivalTime = 0;
    private static int maxArrivalTime = 0;
    private static int minServiceTime = 0;
    private static int maxServiceTime = 0;
    private static String writePath = "";

    public static void main(String[] args) {
        readFile(args[0]);
        writePath = args[1];
        Scheduler.StrategyType strategyType = Scheduler.StrategyType.SHORTEST_TIME;
        if(args.length > 2){
            if(args[2].equals("Queue")){
                strategyType = Scheduler.StrategyType.SHORTEST_QUEUE;
            }
        }
        SimulationManager sm = new SimulationManager(clientsNumber, queuesNumber, simInterval, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, strategyType);
        Thread start = new Thread(sm);
        start.start();
    }

    public static void readFile(String path) {
        Scanner read = null;
        try {
            File input = new File(path);
            read = new Scanner(input);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        if (read != null) {
            try {
                clientsNumber = read.nextInt();
                read.nextLine();
                queuesNumber = read.nextInt();
                read.nextLine();
                simInterval = read.nextInt();
                read.nextLine();
                String temp1 = read.nextLine();
                String[] tempSep1 = temp1.split(",");
                minArrivalTime = Integer.parseInt(tempSep1[0]);
                maxArrivalTime = Integer.parseInt(tempSep1[1]);
                String temp2 = read.nextLine();
                String[] tempSep2 = temp2.split(",");
                minServiceTime = Integer.parseInt(tempSep2[0]);
                maxServiceTime = Integer.parseInt(tempSep2[1]);
            } catch (Exception e) {
                System.out.println("Something went wrong while reading the file.");
                e.printStackTrace();
            }
        }
    }

    public static void writeFile(ArrayList<String> args) {
        try {
            File outFile = new File(writePath);
            if (outFile.createNewFile()) {
                System.out.println("File successfully created.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter output = new FileWriter(writePath);
            for (String s : args) {
                output.write(s);
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
