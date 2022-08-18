package lesson5;



public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c){
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            if (this.length == 40) {
                c.getCDLatch().countDown();
                if (c.getWinner() == false) {
                    c.setWinner(true);
                    System.out.println(c.getName() + " ПОБЕДИТЕЛЬ!!!");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}