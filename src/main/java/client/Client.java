package client;

import java.util.Random;

public class Client {
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

    /// Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
}
