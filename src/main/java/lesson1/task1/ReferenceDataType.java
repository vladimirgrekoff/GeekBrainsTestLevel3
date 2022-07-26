package lesson1.task1;

public class ReferenceDataType<T> {
    private T[] arrayRefType;
    private int length;

    public ReferenceDataType(T[] arrayRefType) {
        setArrayRefType(arrayRefType);
        setLength(arrayRefType.length);
    }


    public T[] getArrayRefType() {
        return arrayRefType;
    }

    public void setArrayRefType(T[] arrayRefType) {
        this.arrayRefType = arrayRefType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void exchangeElements(int i, int j) {
        T temp;
        if (0 <= i && i < this.length && 0 <= j && j < this.length) {
            temp = this.arrayRefType[i];
            this.arrayRefType[i] = this.arrayRefType[j];
            this.arrayRefType[j] = temp;
        } else {
            System.out.println("Указанные индексы выходят за границы массива");
        }
    }

    public void typeInfo() {
        String[] strType = this.getArrayRefType().getClass().getName().split("\\.");
        System.out.println("Тип данных: " + strType[2]);
    }
}
