package lesson1.task3;

public class Main {
    public static void main(String[] args) {
/////////////////////////////Задание 3 ///////////////////////////////////////
        System.out.println("\nРешение задания 3\n");
        //распределение фруктов по ящикам
        int[] apples = new int[]{40, 30, 55, 65, 50, 60};
        int[] oranges = new int[]{40, 60, 45, 55};

        Fruit apple = new Apple();
        Fruit orange = new Orange();

        int numberBox = apples.length + oranges.length;
        Box<Fruit>[] boxes = new Box[numberBox]; //массив ящиков
        //получить ящики с фруктами
        fillBoxesWithFruit(apple, orange, apples, oranges, boxes);

        System.out.println("Полученные ящики\n");
        System.out.println("всего ящиков с фруктами: " + boxes.length + "\n");
        for (Box<Fruit> box:boxes) {
            System.out.println("Фрукт в ящике: " + box.getFruit().getName());
            System.out.println("Вес нетто: " + box.getWeight());
        }
        System.out.println();

        float totalWeight = getTotalWeight(boxes); //общий вес

        float requiredWeight;
        requiredWeight = totalWeight / (float) boxes.length; //требуемый вес ящика

        System.out.println("Общий вес: " + totalWeight);
        System.out.println("Требуемый вес ящика: " + requiredWeight);
        //перераспределить фрукты до одинакового веса ящиков
        makeBoxesOfFruitsWithEqualWeight(apple, orange, boxes, requiredWeight);

        System.out.println("\nПосле распределения фруктов\n");
        for (Box<Fruit> box : boxes) {
            System.out.println("Фрукт в ящике: " + box.getFruit().getName());
            System.out.println("Вес нетто: " + box.getWeight());
        }

    }

    private static void fillBoxesWithFruit(Fruit apple, Fruit orange, int[] apples, int[] oranges, Box<Fruit>[] boxes) {
        //заполнение ящиков фруктами
        for (int i = 0; i < boxes.length; i++){
            boxes[i] = new Box<>();
            if (i < apples.length) {
                boxes[i].setFruit(apple);
                for (int j = 0; j < apples[i]; j++) {
                    apple = new Apple();
                    boxes[i].addFruit(apple);
                }

            } else {
                boxes[i].setFruit(orange);
                for (int j = 0; j < oranges[i- apples.length]; j++) {
                    orange = new Orange();
                    boxes[i].addFruit(orange);
                }

            }
        }
    }

    private static float getTotalWeight(Box<Fruit>[] boxes) {
        //общий вес фруктов
        float totalWeight = 0f;

        for (Box<Fruit> box : boxes) {
            totalWeight += box.getWeight();
        }
        return totalWeight;
    }

    private static void makeBoxesOfFruitsWithEqualWeight(Fruit apple, Fruit orange, Box<Fruit>[] boxes, float requiredWeight) {
        //распределить фрукты до одинакового веса ящиков
        int j;

        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i].getWeight() != requiredWeight) {
                j = 0;
                while (boxes[i].getWeight() != 0) {
                    if (boxes[i].getBoxType().equals(boxes[j].getBoxType()) && i != j) {
                        transferFruitFromBoxToBox(boxes, requiredWeight, j, i);
                    }
                    j++;
                    if (j == boxes.length) break;
                }
            }
            if (boxes[i].getWeight() == 0) {
                changeBoxType(apple, orange, boxes, i);
            }
        }
    }

    private static void transferFruitFromBoxToBox(Box<Fruit>[] boxes, float requiredWeight, int j, int i) {
        //пересыпать фрукты из ящика в ящик
        int numFruit;
        if (boxes[j].getWeight() < requiredWeight) {
            numFruit = (int) ((requiredWeight - boxes[j].getWeight()) / boxes[i].getFruit().getWeight());
            for (int k = 0; k < numFruit; k++) {
                boxes[j].addFruit(boxes[i].getFruitsInBox().get(boxes[i].getCount()-1));
                boxes[i].deleteFruit(boxes[i].getFruitsInBox().get(boxes[i].getCount()-1));
            }
        } else if (boxes[j].getWeight() > requiredWeight) {
            numFruit = (int) ((boxes[j].getWeight() - requiredWeight) / boxes[j].getFruit().getWeight());
            for (int k = 0; k < numFruit; k++) {
                boxes[j].deleteFruit(boxes[j].getFruitsInBox().get(boxes[j].getCount()-1));
                boxes[i].addFruit(boxes[j].getFruitsInBox().get(boxes[j].getCount()-1));
            }
        }
    }

    private static void changeBoxType(Fruit apple, Fruit orange, Box<Fruit>[] boxes, int i) {
        //изменить назначение ящика
        if (boxes[i].getBoxType().equals("Apple")) {
            boxes[i].setFruit(orange);
        } else {
            boxes[i].setFruit(apple);
        }
    }
}
