package lesson1.task3;

import java.util.ArrayList;

public class Box <T extends Fruit>{
    private T fruit;
    private int count;
    private float weight;
    private ArrayList<T> fruitsInBox;

    public Box() {
        this.fruitsInBox  = new ArrayList<>();
    }

    public Box(T fruit) {
        this.fruitsInBox  = new ArrayList<>();
        this.fruit = fruit;
    }

    public float getWeight() {
        return weight;
    }

    public void setFruit(T fruit) {
        this.fruit = fruit;
    }

    public T getFruit() {
        return fruit;
    }

    public int getCount(){
        return count;
    }

    public void setWeight() {
        this.weight = (float) fruitsInBox.size() * fruit.weight;
    }

    public void  addFruit(int n) {
        for (int i= 0; i < n; i++) {
            this.fruitsInBox.add(fruit);
            this.count = this.fruitsInBox.size();
            setWeight();
        }
    }
    public void  deleteFruit(int n) {
        for (int i= 0; i < n; i++) {
            this.fruitsInBox.remove(fruit);
            this.count = this.fruitsInBox.size();
            setWeight();
        }
    }

    public String getBoxType() {
        return this.getFruit().getClass().getSimpleName();
    }

    public boolean compare(Box<T> anotherBox){
        return  (this.getWeight() == anotherBox.getWeight());
    }

}
