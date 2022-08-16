package lesson4;

public class Main {
    public static void main(String[] args) {
        PrintLetter printLetter = new PrintLetter();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                printLetter.printA();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                printLetter.printB();
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                printLetter.printC();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
