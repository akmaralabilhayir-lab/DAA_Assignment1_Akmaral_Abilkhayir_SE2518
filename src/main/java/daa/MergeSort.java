package daa;

public class MergeSort {
    private static final int CUTOFF = 15;
    public static void sort(int[] a, Metrics metrics) {
        if(a == null || a.length <= 1) {
            return;
        }
        int[] buffer = new int[a.length];
        metrics.enterRecursion();
        mergeSort(a, buffer, 0, a.length -1, metrics);
        metrics.exitRecursion();
    }

    private static void mergeSort(int[] a, int[] buffer, int left, int right, Metrics metrics) {
        if (right - left + 1 <= CUTOFF) {
            insertionSort(a, left, right, metrics);
            return;
        }

        int middle = left + (right - left) / 2;

        metrics.enterRecursion();
        mergeSort(a, buffer, left, middle, metrics);
        metrics.exitRecursion();

        metrics.enterRecursion();
        mergeSort(a, buffer, middle + 1, right, metrics);
        metrics.exitRecursion();

        merge(a, buffer, left, middle, right, metrics);
    }

    private static void insertionSort(
            int[] a,
            int left,
            int right,
            Metrics metrics) {

        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= left) {
                metrics.addComparison();

                if (a[j] <= key) {
                    break;
                }

                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
    }

    private static void merge(int[] a, int[] buffer, int left,
                              int middle, int right, Metrics metrics) {
        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }
        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            metrics.addComparison();
            if (buffer[i] <= buffer[j]) {
                a[k] = buffer[i];
                i++;
            } else {
                a[k] = buffer[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            a[k] = buffer[i];
            i++;
            k++;
        }
        while (j <= right) {
            a[k] = buffer[j];
            j++;
            k++;
        }
    }
}
