package fr.robotv2.robotcore.shared.queue;

import java.util.Timer;
import java.util.TimerTask;

public class ActionQueue extends NormalQueue<Runnable> {

    private final Timer timer = new Timer();
    private boolean init = false;
    private long delay;

    private final int maxOccurrence;
    private int currentOccurrence = 0;

    public ActionQueue(long milli, boolean init, int maxOccurrence) {
        this.delay = milli;
        this.maxOccurrence = maxOccurrence;
        if(init) initiate();
    }

    public ActionQueue(long milli, boolean init) {
        this(milli, init, -1);
    }

    public ActionQueue(long milli) {
        this(milli, false, -1);
    }

    public void initiate() {
        if(init) return;
        this.init = true;
        timer.scheduleAtFixedRate(new TimedTask(this), this.delay, this.delay);
    }

    public void stop() {
        if(!init) return;
        this.init = false;
        this.timer.cancel();
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
        stop();
        initiate();
    }

    public int getMaxOccurrence() {
        return maxOccurrence;
    }

    public int getCurrentOccurrence() {
        return currentOccurrence;
    }

    public void incrementCurrentOccurrence() {
        ++this.currentOccurrence;
    }

    public boolean isInit() {
        return init;
    }

    public static class TimedTask extends TimerTask {

        private final ActionQueue actionQueue;
        public TimedTask(ActionQueue queue) {
            this.actionQueue = queue;
        }

        @Override
        public void run() {
            if(actionQueue.getCurrentOccurrence() > actionQueue.getMaxOccurrence()) {
                actionQueue.stop();
                return;
            }

            if(actionQueue.hasNext()) {
                actionQueue.getNext().run();
                actionQueue.incrementCurrentOccurrence();
            }
        }
    }
}
