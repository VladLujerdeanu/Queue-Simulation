package simulator;

import client.Client;
import queue.Queue;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    public void addClient(List<Queue> qs, Client c) {
        int minSize = Integer.MAX_VALUE;
        Queue minQueue = null;
        for (Queue q : qs) {
            if (q.getQ().size() < minSize) {
                minSize = q.getQ().size();
                minQueue = q;
            }
        }
        if (minQueue != null) {
            minQueue.addClient(c);
        }
    }
}
