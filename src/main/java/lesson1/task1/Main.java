package lesson1.task1;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
/////////////////////////////Задание 1 с помощью обобщенного класса/////////////////////////////////////
        System.out.println("\nРешение задания 1 с помощью обобщенного класса\n");
        String[] strings = new String[]{"0","1","2","3","4","5","6","7","8","9"};
        ReferenceDataType<String> strArray = new ReferenceDataType<>(strings);
        strArray.exchangeElements(1,4);
        strArray.typeInfo();
        System.out.println(Arrays.toString(strArray.getArrayRefType()));

        Integer[] integers = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ReferenceDataType<Integer> intArray = new ReferenceDataType<>(integers);
        intArray.exchangeElements(2,5);
        intArray.typeInfo();
        System.out.println(Arrays.toString(intArray.getArrayRefType()));

        Float[] floats = new Float[]{0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f};
        ReferenceDataType<Float> fltArray = new ReferenceDataType<>(floats);
        fltArray.exchangeElements(3,6);
        fltArray.typeInfo();
        System.out.println(Arrays.toString(fltArray.getArrayRefType()));

        Character[] characters = new Character[]{'0','1','2','3','4','5','6','7','8','9'};
        ReferenceDataType<Character> chrArray = new ReferenceDataType<>(characters);
        chrArray.exchangeElements(4,7);
        chrArray.typeInfo();
        System.out.println(Arrays.toString(chrArray.getArrayRefType()));

/////////////////////////////Задание 1 с помощью обобщенного метода///////////////////////////////////////
        System.out.println("\nРешение задания 1 с помощью обобщенного метода\n");

        Character[] chars = new Character[]{'0','1','2','3','4','5','6','7','8','9'};
        String temp;

        chars = exchangeOfArrayElements(chars,0,9);
        temp = chars.getClass().getName().replace("[Ljava.lang.", "Тип: ");
        System.out.println(temp);
        System.out.println(Arrays.toString(chars));


        Float[] flt = new Float[]{0.0f, 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f};

        flt = exchangeOfArrayElements (flt, 1, 8);
        temp = flt.getClass().getName().replace("[Ljava.lang.", "Тип: ");
        System.out.println(temp);
        System.out.println(Arrays.toString(flt));

    }

    public static <T> T[] exchangeOfArrayElements (T[] array, int i, int j) {
        //обобщенный метод обмена элементов массива значениями
        T temp;

        if (0 <= i && i < array.length && 0 <= j && j < array.length) {
            temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        } else {
            System.out.println("индексы выходят за границы массива");
        }
        return array;
    }
}
