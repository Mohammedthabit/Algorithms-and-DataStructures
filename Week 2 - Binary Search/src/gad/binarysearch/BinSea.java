package gad.binarysearch;

import gad.binarysearch.Interval.NonEmptyInterval;

public final class BinSea {

    private BinSea() {
    }

    public static int search(int[] sortedData, int value, Result result) {

        int mid;
        int left = 0;
        int right = sortedData.length - 1;

        while(left <= right){

            mid = (left + right) / 2;


            if (right - left == 0) {
                break;
            } else if (sortedData[mid] < value){
                left = mid + 1;
                result.addStep(mid);
            } else if (sortedData[mid] > value) {
                right = mid - 1;
                result.addStep(mid);
            } else {
                if (sortedData[left] > value){
                    result.addStep(left);
                    return left;
                } else if (sortedData[right] < value) {
                    result.addStep(right);
                    return right;
                }
                result.addStep(mid);
                return mid;
            }

        }

        return left;
    }


    public static int search(int[] sortedData, int value, boolean lowerBound, Result result) {

        int mid;
        int left = 0;
        int right = sortedData.length - 1;
        int temp = -1;

        while(left <= right){

            mid = (left + right) / 2;

            if (sortedData[mid] < value){
                left = mid + 1;
                result.addStep(mid);
            } else if (sortedData[mid] > value) {
                right = mid - 1;
                result.addStep(mid);
            } else {
                temp = mid;
                result.addStep(temp);
                break;
            }
        }

        if (temp == -1) {

            if (lowerBound) {

                if (left < sortedData.length && sortedData[left] >= value) {
                    temp = left;
                }

            } else {

                if (right >= 0 && sortedData[right] <= value) {
                    temp = right;
                }

            }

        } else {

            if (lowerBound) {

                while (temp > 0 && sortedData[temp - 1] >= value) {
                    temp--;
                }

            } else if (!lowerBound) {

                while (temp < sortedData.length - 1 && sortedData[temp + 1] <= value) {
                    temp++;
                }

            }

        }

        if (temp == -1) {

            return -1;

        } else {

            return temp;

        }
    }


    public static Interval search(int[] sortedData, NonEmptyInterval valueRange, Result resultLower, Result resultUpper) {

        int lowerResult = search(sortedData, valueRange.getFrom(), true, resultLower);

        if (lowerResult < 0){
            return Interval.EmptyInterval.getEmptyInterval();
        }

        int upperResult = search(sortedData, valueRange.getTo(), false, resultUpper);

        if (upperResult > sortedData.length - 1){
            return Interval.EmptyInterval.getEmptyInterval();
        }

        if (lowerResult > upperResult){
            return Interval.EmptyInterval.getEmptyInterval();
        }

        return Interval.fromArrayIndices(lowerResult, upperResult);
    }

    public static void main(String[] args) {
        int[] array = new int[] { 2, 7, 7, 42, 69, 1337, 2000, 9001 };
        int[] array2 = new int[] {1, 4, 4, 4, 5, 5, 5, 5, 8};

        //System.out.println(search(array, 7, new StudentResult()));
        //System.out.println(search(array, 100, new StudentResult()));

        //System.out.println(search(array, 7, false, new StudentResult()));
        //System.out.println(search(array, 100, true, new StudentResult()));
        //System.out.println(search(array2, 6, false, new StudentResult()));

        System.out.println(search(array2, new NonEmptyInterval(10, 16), new StudentResult(), new StudentResult()));
        //System.out.println(search(array, new NonEmptyInterval(9002, 10000), new StudentResult(), new StudentResult()));
    }
}
