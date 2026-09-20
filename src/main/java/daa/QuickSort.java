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

    private static void quickSort(
            int[] a,
            int left,
            int right,
            Metrics metrics) {
        if (left >= right) {
            return;
        }

        int pivotIndex = left + RANDOM.nextInt(right - left + 1);
        int pivot = a[pivotIndex];

        int less = left;
        int current = left;
        int greater = right;

        while (current <= greater) {
            metrics.addComparison();

            if (a[current] < pivot) {
                swap(a, less, current);
                less++;
                current++;
            } else if (a[current] > pivot) {
                swap(a, current, greater);
                greater--;
            } else {
                current++;
            }
        }
        quickSort(a, left, less - 1, metrics);
        quickSort(a, greater + 1, right, metrics);
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}