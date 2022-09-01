package lesson7;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Dog {

    private String name;

    private String sound;

    private int age;

    private String color;
    private static final int LIMIT_DOG_AGE = 20;
    private static final int LIMIT_RUNNING_DISTANCE = 500;
    private static final int LIMIT_SWIMMING_DISTANCE = 10;

    public Dog() {
        this.name = "Полкан";
    }


    public String getName() {
        return name;
    }

    public String getSound() {
        return sound;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void dogPrintInfo() {
        System.out.println(this.ToString());
    }

    public String ToString() {
        return String.format("Пес: %s, окрас %s, возраст: %d", getName(), getColor(), getAge());
    }


    public void voice() {
        System.out.println(getSound());
    }

    public void setDogSound() {
        String strVoice;
        if (getAge() <= 2) {
            strVoice = "Тяв-тяв";
        } else if (getAge() <= 8) {
            strVoice = "Р-р-р-гав";
        } else {
            strVoice = "Ваф-Ваф";
        }
        setSound(strVoice);
    }



    public String run(int runDistance) {
        String strRunDistance;

        if (LIMIT_RUNNING_DISTANCE >= runDistance) {
            strRunDistance = "Пёс " + getName() + " пробежал " + runDistance + " м.";
        } else {
            strRunDistance = "Пёс " + getName() + " не смог пробежать " + runDistance + " м.";
        }
        return strRunDistance;
    }

    public String swimming (int runDistance) {
        String strDistance = "";

        if (getAge() <= 2) {
            //проплыл 4,  если далеко назад
            if ((runDistance-4) > 4) {
                strDistance = "Пёс " + getName() + " проплыл 4м, развернулся поплыл назад.";
            } else {
                strDistance = "Пёс " + getName() + " проплыл " + runDistance + "м, добрался до берега.";
            }

        } else if (getAge() <= 7) {
            // проплыл 5, если далеко назад
            if (runDistance <= LIMIT_SWIMMING_DISTANCE) {
                strDistance = "Пёс " + getName() + " проплыл " + runDistance + "м, добрался до берега.";
            } else {
                strDistance = "Пёс " + getName() + " проплыл 5м, развернулся поплыл назад.";
            }

        } else {
            //проплыл 3,  если далеко назад
            if ((runDistance - 3) > 3 ) {
                strDistance = "Пёс " + getName() + " проплыл 3м, развернулся поплыл назад.";
            } else {
                strDistance = "Пёс " + getName() + " проплыл " + runDistance + "м, добрался до берега.";
            }
        }
        return strDistance;
    }

    @testAnnotation(priority = 7)
    @Test
    private void testSwimming1() {
        int runDistance = 10;
        System.out.println(swimming(runDistance));
    }

    @testAnnotation(priority = 8)
    @Test
    private void testSwimming2() {
        int runDistance = 15;
        System.out.println(swimming(runDistance));
    }

    @testAnnotation(priority = 5)
    @Test
    private void testRun1() {
        int runDistance = 150;
        System.out.println(run(runDistance));
    }

    @testAnnotation(priority = 6)
    @Test
    private void testRun2() {
        int runDistance = 600;
        System.out.println(run(runDistance));
    }

    @testAnnotation(priority = 2)
    @Test
    private void testSetDogSound() {
        setDogSound();
        System.out.println("Записан голос песика");
    }

    @testAnnotation(priority = 4)
    @Test
    private void testVoice() {
        voice();
    }

    @testAnnotation(priority = 1)
    @Test
    private void testSetColor() {
        String color = "черный";
        setColor(color);
        System.out.println("Записан окрас песика");
    }

    @testAnnotation(priority = 1)
    @Test
    private void testSetAge() {
        int age = 4;
        setAge(age);
        System.out.println("Записан возраст песика");
    }

    @testAnnotation(priority = 3)
    @Test
    private void testDogPrintInfo() {
        dogPrintInfo();
    }

    @BeforeSuite(alwaysRun = true)
    @testAnnotation(priority = 1)
    public void mainStart() {
        System.out.println("НАЧАЛО ТЕСТИРОВАНИЯ");
    }

    @AfterSuite(alwaysRun = true)
    @testAnnotation(priority = 10)
    public static void mainEnd() {
        System.out.println("ЗАВЕРШЕНИЕ ТЕСТИРОВАНИЯ");
    }
}


