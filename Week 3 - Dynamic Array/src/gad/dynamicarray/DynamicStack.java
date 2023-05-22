package gad.dynamicarray;

public class DynamicStack implements Stack {
    private DynamicArray array;
    private Result result;
    private int index = 0;

    public DynamicStack(int growthFactor, int maxOverhead, Result result) {
        array = new DynamicArray(growthFactor, maxOverhead);
        this.result = result;
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public void pushBack(int value) {
        int currentLength = array.getLength();
        int occupiedSpace = 0;
        if (currentLength > 0){
            occupiedSpace = new Interval.NonEmptyInterval(0, index - 1).getSize(currentLength);
        }
        if (currentLength == 0){
            Interval usage = Interval.EmptyInterval.getEmptyInterval();
            array.reportUsage(usage, currentLength + 1);
        } else if (occupiedSpace < currentLength) {
            Interval usage = new Interval.NonEmptyInterval(0, occupiedSpace);
            array.reportUsage(usage, occupiedSpace);
        } else if (occupiedSpace == currentLength){
            Interval usage = new Interval.NonEmptyInterval(0, occupiedSpace - 1);
            array.reportUsage(usage, currentLength + 1);
        }
        array.set(index, value);
        index++;

        result.logArray(arrayConversion(array));
    }

    @Override
    public int popBack() {
        int value = array.get(index - 1);
        Interval usage = new Interval.NonEmptyInterval(0, index - 1);
        array.reportUsage(usage, index);
        index--;
        usage = new Interval.NonEmptyInterval(0, index - 1);
        array.reportUsage(usage, index);
        result.logArray(arrayConversion(array));
        return value;
    }

    public int[] arrayConversion(DynamicArray array){
        int[] copy = new int[array.getLength()];
        for (int i = 0; i < array.getLength(); i++){
            copy[i] = array.get(i);
        }
        return copy;
    }

    @Override
    public String toString() {
        return array + ", length: " + size();
    }

}