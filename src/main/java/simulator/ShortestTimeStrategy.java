package simulator;

import client.Client;
import queue.Queue;

import java.util.List;

public class ShortestTimeStrategy implements Strategy {
    public void addClient(List<Queue> qs, Client c) {
        int minTime = Integer.MAX_VALUE;
        Queue minQueue = null;
        for (Queue q : qs) {
            if (q.getWaitingTime().intValue() < minTime) {
                minTime = q.getWaitingTime().intValue();
                minQueue = q;
            }
        }

        if (minQueue != null) {
            minQueue.addClient(c);
        }
    }
}
