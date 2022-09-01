package lesson7;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

//@testAnnotation
public class Cat {
    public String name;
    protected int age = 4;
    private String secret = "Я подрал диван";


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSecret() {
        return secret;
    }

    public void voice() {
        System.out.println("Мяу!");
    }


    protected void run() {
        System.out.println("Тыгдык");
    }

    private void work(String companyName) {
        System.out.println("Кот " + getName() + " устраивается на работу программистом в " + companyName);
    }

    @testAnnotation(priority = 7)
    @Test
    private void testRun() {
        run();
    }

    @testAnnotation(priority = 5)
    @Test
    private void testVoice() {
        voice();
    }

    @testAnnotation(priority = 6)
    @Test
    private void testWork() {
        String companyName = "Microsoft";
        work(companyName);
    }

    @testAnnotation(priority = 1)
    @Test
    private void testSetAge() {
        int age = 2;
        setAge(age);
        System.out.println("Записан возраст котика");
    }

    @testAnnotation(priority = 3)
    @Test
    private void testGetAge() {
        System.out.println("Возраст котика: " + getAge());
    }

    @testAnnotation(priority = 1)
    @Test
    private void testSetName() {
        String name = "Ричард";
        setName(name);
        System.out.println("Коту дано имя");
    }

    @testAnnotation(priority = 2)
    @Test
    private void testGetName() {
        System.out.println("Имя кота: " + getName());
    }

    @testAnnotation(priority = 4)
    @Test
    private void testGetSecret() {
        System.out.println("У кота есть секрет: " + getSecret());
    }




}
