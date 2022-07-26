package lesson1.task3;

public class Apple extends Fruit{
    String name = "Яблоко";
    public Apple() {
        super.weight = 1.0f;
        super.name = name;
    }

    public String getName() {
        return name;
    }
}
