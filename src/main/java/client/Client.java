package client;

import java.util.Random;

public class Client implements Comparable<Client> {
    private static int CLIENT = 0; // global client id
    private int id;
    private int arrivalTime;
    private int serviceTime; // time needed for the client to be served

    public Client(int newArrivalTime, int newServiceTime) {
        this.id = ++CLIENT;
        this.arrivalTime = newArrivalTime;
        this.serviceTime = newServiceTime;
    }

    public static Client RandGenerator(int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime) {
        Random rand = new Random();
        return new Client(rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime,
                rand.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime);
    }

    public void print() {
        System.out.print("(" + id + ", " + arrivalTime + ", " + serviceTime + ")");
    }

    @Override
    public String toString() {
        return "(" + id + ", " + arrivalTime + ", " + serviceTime + ")";
    }

    public int compareTo(Client c) {
        return this.arrivalTime - c.arrivalTime;
    }

    /// Getters and Setters

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
