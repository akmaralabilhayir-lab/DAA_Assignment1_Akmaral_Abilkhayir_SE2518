package daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
}