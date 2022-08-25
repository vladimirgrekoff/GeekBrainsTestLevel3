package lesson6.task2;

import java.util.Arrays;

public class ReturnArray {
    public static void main(String[] args) {
        ReturnArray returnArray = new ReturnArray();
        int[] intArray;
        int[] outArray;
        intArray = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};
//        intArray = new int[]{1, 2, 4, 4, 4, 3, 8, 1, 7};
//        intArray = new int[]{1, 2, 5, 6, 2, 3, 8, 1, 7};
//        intArray = new int[]{1, 4, 5, 6, 2, 3, 8, 1, 4};
//        intArray = new int[0];
//        intArray = null;

        try {
            if (intArray != null) {
                outArray = returnArray.ReturnArrayNumbersAfterLast4(intArray);
                System.out.println(outArray.length);
                System.out.println(Arrays.toString(outArray));
            } else {
                System.out.println("\"массив\" не должен иметь значение null");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
    public ReturnArray() {
        System.out.println("Объект создан");
    }

    public int[] ReturnArrayNumbersAfterLast4(int[] array) {
        int[] result;
        int j = 0;
        int index = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) {
                index = i;
            }
        }

        if (index == -1) {
            result = new int[0];
            throw new RuntimeException("В массиве отсутствуют 4");
        }

        result = new int[array.length - index - 1];

        for (int i = (index + 1); i < array.length; i++) {
            result[j] = array[i];
            j++;
        }
        return result;
    }
}
