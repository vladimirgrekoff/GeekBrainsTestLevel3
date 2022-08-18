package lesson5;

import java.util.concurrent.BrokenBarrierException;

public abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c) throws BrokenBarrierException, InterruptedException;
}
