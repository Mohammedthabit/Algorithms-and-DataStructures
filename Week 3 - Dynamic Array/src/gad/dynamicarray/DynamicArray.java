package gad.dynamicarray;

import java.util.Arrays;

public class DynamicArray {
    private int[] elements;
    private final int maxOverhead;
    private final int growthFactor;

    public DynamicArray(int growthFactor, int maxOverhead) {
        if (maxOverhead < 0 || growthFactor < 0 || growthFactor > maxOverhead) {
            throw new IllegalArgumentException();
        }
        elements = new int[0];
        this.growthFactor = growthFactor;
        this.maxOverhead = maxOverhead;
    }

    public int getLength() {
        return elements.length;
    }

    public Interval reportUsage(Interval usage, int minSize) {

        int currentLength = elements.length;

        if (minSize > currentLength || (minSize * maxOverhead) < currentLength){

            int newLength = growthFactor * minSize;
            int[] newArray = new int[newLength];
            int start;
            int end;
            if (usage.isEmpty()){
                start = end = 0;
            } else {
                start = usage.getFrom();
                end = usage.getTo();
            }

            if (end >= start) {

                System.arraycopy(elements, start, newArray, 0, usage.getSize(currentLength));

            } else {

                int firstPart = currentLength - start;
                int secondPart = end + 1;

                System.arraycopy(elements, start, newArray, 0, firstPart);

                System.arraycopy(elements, 0, newArray, firstPart, secondPart);

            }

            elements = newArray;

            if (usage.isEmpty()){
                return Interval.EmptyInterval.getEmptyInterval();
            }

            return new Interval.NonEmptyInterval(0, usage.getSize(currentLength) - 1);
        }

        if (usage.isEmpty()){
            return Interval.EmptyInterval.getEmptyInterval();
        }

        return new Interval.NonEmptyInterval(usage.getFrom(), usage.getTo());
    }

    public int get(int index) {
        return elements[index];
    }

    public void set(int index, int value) {
        if (0 <= index && index <= elements.length - 1) elements[index] = value;
    }

    public void reportArray(Result result) {
        result.logArray(elements);
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}