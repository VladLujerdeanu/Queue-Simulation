package simulator;

import client.Client;
import queue.Queue;

import java.util.List;

public interface Strategy {

    /// Adds a client based on the preferred implementation
    void addClient(List<Queue> qs, Client c);
}
