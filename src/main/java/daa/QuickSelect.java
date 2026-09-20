package daa;

public class QuickSelect {
    public static int select(int[] a, int k) {
        return select(a, k, new Metrics());
    }

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k = " + k + " is out of range [0, " + (a.length - 1) + "]");
        }

        int left = 0;
        int right = a.length - 1;

        while (left < right) {
            int[] bounds = QuickSort.partition(a, left, right, metrics);
            if (k < bounds[0]) {
                right = bounds[0] - 1;
            } else if (k > bounds[1]) {
                left = bounds[1] + 1;
            } else {
                return a[k]; 
            }
        }
        return a[k];
    }
}