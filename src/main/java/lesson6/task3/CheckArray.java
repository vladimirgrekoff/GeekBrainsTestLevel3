package lesson6.task3;

public class CheckArray {
    public static void main(String[] args) {
        CheckArray checkArray = new CheckArray();
        boolean resultCheck;
        int[] array;

        array = new int[]{1, 1, 1, 4, 4, 1, 4, 4};
//        array = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
//        array = new int[]{4, 4, 4, 4, 4, 4, 4, 4};
//        array = new int[]{1, 1, 1, 4, 4, 1, 4, 3};
//        array = new int[0];
//        array = null;

        if (array != null) {
            resultCheck = checkArray.isArrayNumbersOnlyOneAndFour(array);
            System.out.println(resultCheck);
        } else {
            System.out.println("\"массив\" не должен иметь значение null");
        }
    }
    public CheckArray() {
        System.out.println("Объект для проверки массива создан");
    }
    public boolean isArrayNumbersOnlyOneAndFour(int[] array) {
        int count = 0;
        int countOne = 0;
        int contFour = 0;
        boolean result;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1) {
                countOne++;
            }
            if (array[i] == 4) {
                contFour++;
            }
        }

        count = countOne + contFour;

        if (countOne == 0 || contFour == 0) {
            result = false;
        } else result = (count == array.length);

        return result;
    }
}
