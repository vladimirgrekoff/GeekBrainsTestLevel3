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
    public ArrayList<T> getFruitsInBox() {
        return fruitsInBox;
    }
    public int getCount(){
        return count;
    }

    public void setWeight(T fruit) {
        this.weight = (float) this.fruitsInBox.size() * fruit.getWeight();
    }

    public void  addFruit(T fruit) {
        this.fruitsInBox.add(fruit);
        this.count = this.fruitsInBox.size();
        setWeight(fruit);
    }
    public void  deleteFruit(T fruit) {
        this.fruitsInBox.remove(fruit);
        this.count = this.fruitsInBox.size();
        setWeight(fruit);
    }

    public String getBoxType() {
        return this.getFruit().getClass().getSimpleName();
    }

    public boolean compare(Box<T> anotherBox){
        return  (this.getWeight() == anotherBox.getWeight());
    }

}
