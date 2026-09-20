package daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgorithmsTest {
    @Test
    void mergeSortShouldSortRandomArrays() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(100) + 1;
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(1000);
            }

            int[] expected = array.clone();
            Arrays.sort(expected);
            Metrics metrics = new Metrics();
            MergeSort.sort(array, metrics);
            assertArrayEquals(expected, array);
        }
    }

    @Test
    void mergeSortShouldHandleEdgeCases() {

        Metrics metrics1 = new Metrics();
        int[] empty = {};
        MergeSort.sort(empty, metrics1);

        assertArrayEquals(new int[]{}, empty);

        Metrics metrics2 = new Metrics();
        int[] oneElement = {5};
        MergeSort.sort(oneElement, metrics2);

        assertArrayEquals(new int[]{5}, oneElement);

        Metrics metrics3 = new Metrics();
        int[] duplicates = {7, 7, 7, 7, 7};
        MergeSort.sort(duplicates, metrics3);

        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, duplicates);

        Metrics metrics4 = new Metrics();
        int[] sorted = {1, 2, 3, 4, 5};
        MergeSort.sort(sorted, metrics4);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void quickSortShouldSortRandomArrays() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(100) + 1;
            int[] array = new int[size];

            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(1000);
            }

            int[] expected = array.clone();
            Arrays.sort(expected);

            Metrics metrics = new Metrics();
            QuickSort.sort(array, metrics);
            assertArrayEquals(expected, array);
        }
    }

    @Test
    void quickSortShouldHandleEdgeCases() {
        int[] empty = {};
        QuickSort.sort(empty, new Metrics());
        assertArrayEquals(new int[]{}, empty);

        int[] one = {5};
        QuickSort.sort(one, new Metrics());
        assertArrayEquals(new int[]{5}, one);

        int[] equal = {7, 7, 7, 7, 7};
        QuickSort.sort(equal, new Metrics());
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, equal);

        int[] sorted = {1, 2, 3, 4, 5};
        QuickSort.sort(sorted, new Metrics());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, sorted);
    }

    @Test
    void quickSortDepthOnSortedArrayShouldBeLogarithmic() {
        int n = 100_000;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) array[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(array, metrics);

        double limit = 2 * Math.log(n) / Math.log(2);
        assertTrue(metrics.getMaxDepth() <= limit,
                "depth " + metrics.getMaxDepth() + " exceeds " + limit);
    }

    @Test
    void quickSelectShouldMatchSortedArray() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int size = random.nextInt(100) + 1;
            int[] array = new int[size];
            for (int i = 0; i < size; i++) array[i] = random.nextInt(1000);

            int[] sorted = array.clone();
            Arrays.sort(sorted);
            int k = random.nextInt(size);
            assertEquals(sorted[k], QuickSelect.select(array.clone(), k));
        }
    }

    @Test
    void quickSelectShouldRejectInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{}, 0));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2}, 2));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(new int[]{1, 2}, -1));
    }
}