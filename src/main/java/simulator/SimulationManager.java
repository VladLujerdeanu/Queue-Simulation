package simulator;

import client.Client;
import main.MainClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationManager implements Runnable {
    private static int simInterval = 0;
    private List<Client> clients;
    private Scheduler scheduler;

    public SimulationManager(int clientsNumber, int queuesNumber, int simInterval, int minArrivalTime,
                             int maxArrivalTime, int minServiceTime, int maxServiceTime, Scheduler.StrategyType strategyType) {

        SimulationManager.simInterval = simInterval;

        /// Initializing Clients
        clients = Collections.synchronizedList(new ArrayList<Client>());
        for (int i = 0; i < clientsNumber; i++) {
            clients.add(Client.RandGenerator(minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime));
        }
        Collections.sort(clients);
        scheduler = new Scheduler(queuesNumber, clientsNumber, strategyType);
    }

    public void run() {
        int currentTime = 0;
        ArrayList<String> output = new ArrayList<String>();
        while (currentTime < simInterval) {
            while (clients.size() > 0 && clients.get(0).getArrivalTime() == currentTime) {
                scheduler.addClientToQueue(clients.get(0));
                clients.remove(0);
            }
            String temp = "Time: " + currentTime + "\nWaiting Clients:";
            for (Client c : clients) {
                temp += c.toString();
            }
            temp += "\n";

            for (int i = 0; i < scheduler.getQs().size(); i++) {
                temp += "Queue " + i + ": ";
                if (scheduler.getQs().get(i).getQ().size() > 0) {
                    temp += scheduler.getQs().get(i).toString();
                    if (scheduler.getQs().get(i).getQ().peek().getServiceTime() > 0) {
                        scheduler.getQs().get(i).getQ().peek().setServiceTime(scheduler.getQs().get(i).getQ().peek().getServiceTime() - 1);
                    }
                    if (scheduler.getQs().get(i).getWaitingTime().intValue() > 0) {
                        scheduler.getQs().get(i).setWaitingTime(scheduler.getQs().get(i).getWaitingTime().intValue() - 1);
                    }
                    if(!scheduler.getQs().get(i).getQ().isEmpty() && scheduler.getQs().get(i).getState() == Thread.State.WAITING){
                        scheduler.getQs().get(i).getQ().notify();
                    }
                } else {
                    temp += "closed";
                }
                temp += "\n";
            }
            temp += "\n";
            output.add(temp);

            currentTime++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scheduler.closeThreads();
        output.add(new String("Average waiting time: " + scheduler.getAvgWaitingTime()));
        MainClass.writeFile(output);
    }
}
