package main;

import client.Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainClass {
    private static int clientsNumber = 0;
    private static int queuesNumber = 0;
    private static int simInterval = 0;
    private static int minArrivalTime = 0;
    private static int maxArrivalTime = 0;
    private static int minServiceTime = 0;
    private static int maxServiceTime = 0;

    public static void main(String[] args) {
        readFile(args[0]);
        Client[] clients = new Client[clientsNumber];
        for(Client c: clients){
            c = Client.RandGenerator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime);
            System.out.println(c.getId() + " " + c.getArrivalTime() + " " + c.getServiceTime() + "\n");
        }
    }

    public static void readFile(String path){
        Scanner read = null;
        try {
            File input = new File("in-test-1.txt");
            read = new Scanner(input);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        if(read != null) {
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
}
