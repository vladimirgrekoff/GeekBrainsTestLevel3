package lesson7;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {


        Class<?> catCl = Cat.class;
        Object cat = new Cat();
        TestingClass.start(catCl, cat);



        Class<?> dogCl = Dog.class;
        Object dog = new Dog();
        TestingClass.start(dogCl, dog);

    }
}
