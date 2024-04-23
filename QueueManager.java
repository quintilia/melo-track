import java.util.LinkedList;
import java.util.Queue;

public class QueueManager {

    private Queue<String> queue;

    public QueueManager() {
        this.queue = new LinkedList<>();
    }

    public void addToQueue(String musicId) {
        queue.add(musicId);
    }

    public String removeFromQueue() {
        return queue.poll();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

}

