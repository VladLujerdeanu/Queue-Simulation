package queue;

import client.Client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.*;

public class Queue implements Runnable {
    private BlockingQueue<Client> q;
    private AtomicInteger waitingTime;
    private AtomicInteger avgWaitingTime;
    private AtomicInteger avgClientsNumber;
    private boolean running = true;

    /// Class Constructor
    public Queue(int clientsNumber) {
        q = new ArrayBlockingQueue<Client>(clientsNumber);
        waitingTime = new AtomicInteger(0);
        avgWaitingTime = new AtomicInteger(0);
        avgClientsNumber = new AtomicInteger(0);
    }

    /// Implementation of Runnable
    public void run() {
        while (running) {
            if (!q.isEmpty()) {
                Client crt = q.peek();
                int sleepTime = crt.getServiceTime();
                try {
                    sleep(sleepTime * 100);
                } catch (InterruptedException e) {
                    System.out.println("The task has been interrupted.");
                    e.printStackTrace();
                }
                q.remove();
            } else {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /// Add a client to the queue
    public void addClient(Client c) {
        q.add(c);
        synchronized (this) {
            notify();
        }
        waitingTime.addAndGet(c.getServiceTime());
        avgWaitingTime.addAndGet(waitingTime.intValue());
        avgClientsNumber.incrementAndGet();
    }

    /// Print function for debugging
    public void printQueue() {
        for (Client c : q) {
            c.print();
        }
    }

    public String toString() {
        String temp = "";
        for (Client c : q) {
            temp += c.toString();
        }
        return temp;
    }

    /// Method to stop the thread
    public void close() {
        this.running = false;
        synchronized (this) {
            notify();
        }
    }

    /// Getters and Setters
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = new AtomicInteger(waitingTime);
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    public java.util.Queue<Client> getQ() {
        return q;
    }

    public float getAvgWaitingTime() {
        return avgWaitingTime.floatValue();
    }

    public int getAvgClientNumber(){
        return avgClientsNumber.intValue();
    }

    public State getState(){
        return Thread.currentThread().getState();
    }
}
