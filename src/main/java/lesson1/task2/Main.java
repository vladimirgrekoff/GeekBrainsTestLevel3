package lesson1.task2;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

/////////////////////////////Задание 2 с помощью обобщенного метода///////////////////////////////////////
        System.out.println("\nРешение задания 2 с помощью обобщенного класса\n");
        Integer[] intArr = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Integer> intArrayList = convertArrayToArrayList(intArr);
        printArrayListInfo(intArrayList);

        System.out.println();

        Float[] fltArr = new Float[]{0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f};
        ArrayList<Float> fltArrayList = convertArrayToArrayList(fltArr);
        printArrayListInfo(fltArrayList);

    }
    public static <T> ArrayList<T> convertArrayToArrayList(T[] array){
        //обобщенный метод преобразования массива в ArrayList
        return new ArrayList<T>(Arrays.asList(array));
    }

    private static <T> void printArrayListInfo(ArrayList<T> typeArrayList) {
        //обобщенный метод вывода информации о типе данных и значений элементов
        String temp;
        for (T t : typeArrayList) {
            temp = t.getClass().getName().replace("java.lang.", "Тип: ");
            System.out.println(temp + ", значение: " + t.toString());
        }
    }
}
