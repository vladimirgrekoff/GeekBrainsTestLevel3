package lesson4;

public class PrintLetter {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public void printA() {
        synchronized (mon) {
            try {
                while (currentLetter != 'A') {
                    mon.wait();
                }
                System.out.print("A");
                currentLetter = 'B';
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (mon) {
            try {
                while (currentLetter != 'B') {
                    mon.wait();
                }
                System.out.print("B");
                currentLetter = 'C';
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (mon) {
            try {
                while (currentLetter != 'C') {
                    mon.wait();
                }
                System.out.print("C");
                currentLetter = 'A';
                mon.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


