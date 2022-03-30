package fr.robotv2.robotcore.shared.queue;

import java.util.LinkedList;

public class NormalQueue<T> {

    private final LinkedList<T> queue = new LinkedList<>();

    public LinkedList<T> getActions() {
        return queue;
    }

    public void addAction(T action) {
        queue.addLast(action);
    }

    public boolean hasNext() {
        return queue.getFirst() != null;
    }

    public T getNext() {
        T current = queue.getFirst();
        if(current == null) return null;
        this.queue.removeFirst();
        return current;
    }
}
