
public class InputQueue {
    private Queue queue = new Queue(15);

    public InputQueue(Maze maze) {
        // Fill screen with objects
        for (int i = 0; i < 20; i++) {
            Object newItem = createItemRandomly();
            maze.updateSlot(newItem);
        }

        // Fill input queue
        for (int i = 0; i < 15; i++) {
            Object newItem = createItemRandomly();
            queue.enqueue(newItem);
        }
    }

    public void pushQueueItem(Maze maze) {
        Object newItem = createItemRandomly();
        queue.enqueue(newItem);
    }

    public Object popItemFromQueue() {
        return queue.dequeue();
    }

    private Object createItemRandomly() {
        int randomInt = Utils.getRandomNumber(1, 40);
        Coords randomCoords = Utils.findEmptyPosition();

        if (randomInt > 0 && randomInt < 13) {
            return new Tressure('1', randomCoords);
        }
        if (randomInt > 12 && randomInt < 21) {
            return new Tressure('2', randomCoords);
        }
        if (randomInt > 20 && randomInt < 27) {
            return new Tressure('3', randomCoords);
        }
        if (randomInt > 26 && randomInt < 32) {
            return new Tressure('4', randomCoords);
        }
        if (randomInt > 31 && randomInt < 36) {
            return new Tressure('5', randomCoords);
        }
        if (randomInt > 35 && randomInt < 38) {
            return new Trap(randomCoords);
        }
        if (randomInt > 37 && randomInt < 39) {
            return new Warp(randomCoords);
        }
        if (randomInt > 38 && randomInt < 41) {
            return new ComputerRobot(randomCoords);
        }
        return new Tressure('1', randomCoords);
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public String getQueueString() {
        String result = "";

        for (int i = 0; i < 15; i++) {
            Object iteratedItem = queue.dequeue();
            result += Utils.getSymbolOfEntity(iteratedItem);
            queue.enqueue(iteratedItem);
        }

        return result;
    }
}
