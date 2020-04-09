package simulator;

import client.Client;
import queue.Queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private List<Queue> qs;
    private Strategy strategy;

    public enum StrategyType {SHORTEST_QUEUE, SHORTEST_TIME}

    /// Class Constructor
    public Scheduler(int maxQueues, int maxClientsPerQueue, StrategyType strategyType) {
        qs = Collections.synchronizedList(new ArrayList<Queue>());
        for (int i = 0; i < maxQueues; i++) {
            qs.add(new Queue(maxClientsPerQueue));
            (new Thread(qs.get(i))).start();
        }
        changeStrategy(strategyType);
    }

    /// Method to change between strategies
    public void changeStrategy(StrategyType newStrategy) {
        if (newStrategy == StrategyType.SHORTEST_QUEUE) {
            strategy = new ShortestQueueStrategy();
        }
        if (newStrategy == StrategyType.SHORTEST_TIME) {
            strategy = new ShortestTimeStrategy();
        }
    }

    /// Method that sends the client to be added to the appropriate queue
    public void addClientToQueue(Client c) {
        strategy.addClient(qs, c);
    }

    public List<Queue> getQs() {
        return qs;
    }

    /// Method that closes all the opened Queue Threads.
    public void closeThreads() {
        for (Queue q : qs) {
            q.close();
        }
    }

    /// Method that computes the average waiting time for all the queues
    public float getAvgWaitingTime() {
        float waitingTime = 0;
        int div = 0;
        for (Queue q : qs) {
            waitingTime += q.getAvgWaitingTime();
            div += q.getAvgClientNumber();
        }
        return waitingTime / div;
    }
}
