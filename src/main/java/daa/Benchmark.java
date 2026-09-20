package daa;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class Benchmark {
    private static final String[] ALGORITHMS = {"MergeSort", "QuickSort", "QuickSelect"};
    private static final String[] INPUTS = {"random", "sorted", "duplicates"};
    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final int RUNS = 5;
    private static final long SEED = 42;

    public static void main(String[] args) throws IOException {
        warmUp();

        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(Path.of("results.csv")))) {
            out.println("algorithm,input,n,time_ms,comparisons,max_depth");

            for (String algorithm : ALGORITHMS) {
                for (String input : INPUTS) {
                    for (int n : SIZES) {
                        int[] base = generate(input, n);
                        Metrics[] runs = new Metrics[RUNS];

                        for (int r = 0; r < RUNS; r++) {
                            int[] data = base.clone();   // fresh copy: algorithms modify the array
                            Metrics metrics = new Metrics();
                            metrics.start();
                            run(algorithm, data, metrics);
                            metrics.stop();
                            runs[r] = metrics;
                        }

                        Arrays.sort(runs, Comparator.comparingLong(Metrics::getElapsedNanos));
                        Metrics median = runs[RUNS / 2];

                        String line = String.format(Locale.US, "%s,%s,%d,%.3f,%d,%d",
                                algorithm, input, n, median.getElapsedMs(),
                                median.getComparisons(), median.getMaxDepth());
                        out.println(line);
                        System.out.println(line);
                    }
                }
            }
        }
        System.out.println("Saved results.csv");
    }

    private static void run(String algorithm, int[] data, Metrics metrics) {
        switch (algorithm) {
            case "MergeSort" -> MergeSort.sort(data, metrics);
            case "QuickSort" -> QuickSort.sort(data, metrics);
            case "QuickSelect" -> QuickSelect.select(data, data.length / 2, metrics);
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    private static int[] generate(String input, int n) {
        Random random = new Random(SEED);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            switch (input) {
                case "random" -> a[i] = random.nextInt();
                case "sorted" -> a[i] = i;
                case "duplicates" -> a[i] = random.nextInt(10);
                default -> throw new IllegalArgumentException("Unknown input: " + input);
            }
        }
        return a;
    }

    // untimed runs so the JIT compiles the hot code before measuring
    private static void warmUp() {
        int[] base = generate("random", 10_000);
        for (String algorithm : ALGORITHMS) {
            for (int i = 0; i < 5; i++) {
                run(algorithm, base.clone(), new Metrics());
            }
        }
    }
}