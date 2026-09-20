package daa;
import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();
    public static void sort(int[] a, Metrics metrics) {
        if (a == null || a.length <= 1) {
            return;
        }
        quickSort(a, 0, a.length - 1, metrics);
    }

    private static void quickSort(int[] a, int left, int right, Metrics metrics) {
        while (left < right) {
            int[] bounds = partition(a, left, right, metrics);
            int less = bounds[0];
            int greater = bounds[1];

            if (less - left < right - greater) {
                metrics.enterRecursion();
                quickSort(a, left, less - 1, metrics);
                metrics.exitRecursion();
                left = greater + 1;
            } else {
                metrics.enterRecursion();
                quickSort(a, greater + 1, right, metrics);
                metrics.exitRecursion();
                right = less - 1;
            }
        }
    }

    static int[] partition(int[] a, int left, int right, Metrics metrics) {
        int pivot = a[left + RANDOM.nextInt(right - left + 1)];
        int less = left, current = left, greater = right;

        while (current <= greater) {
            metrics.addComparison();
            if (a[current] < pivot) {
                swap(a, less, current);
                less++;
                current++;
            } else {
                metrics.addComparison();
                if (a[current] > pivot) {
                    swap(a, current, greater);
                    greater--;
                } else {
                    current++;
                }
            }
        }
        return new int[]{less, greater};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}