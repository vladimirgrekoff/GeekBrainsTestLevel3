package lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;
    private CountDownLatch cDLatch;
    private Semaphore semaphore;

    private static boolean Winner;


    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }

    public CountDownLatch getCDLatch() {
        return cDLatch;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public boolean getWinner() {
        return Winner;
    }

    public void setWinner(boolean isWinner) {
        this.Winner = isWinner;
    }

    public Car(Race race, int speed, CountDownLatch cdl, CyclicBarrier cb, Semaphore smpr, CountDownLatch cdl1) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = cb;
        this.countDownLatch = cdl;
        this.cDLatch = cdl1;
        this.semaphore = smpr;
        this.Winner = false;

    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            countDownLatch.countDown();////////////////////
            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            try {
                race.getStages().get(i).go(this);
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
