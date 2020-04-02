package queue;

import client.Client;

public class Queue {
    private java.util.Queue<Client>q;

    public Client first(){
        return q.peek();
    }

    public void pop(){
        this.q.poll();
    }

    public void push(Client c){
        this.q.add(c);
    }
}
