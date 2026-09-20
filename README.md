# DAA Assignment 1: Divide and Conquer

Java implementation of MergeSort, QuickSort and QuickSelect, with a metrics class, a benchmark that exports `results.csv`, JUnit 5 tests and plots.

## Requirements

- JDK 25 (set in `pom.xml`)
- Maven 3.6+ (or IntelliJ IDEA with its bundled Maven)
- Python 3 with `matplotlib` (only for plots)

## Project structure

```
src/main/java/daa/
  MergeSort.java     one reusable buffer, Insertion Sort cutoff (15), linear merge
  QuickSort.java     random pivot, 3-way partition, smaller side first
  QuickSelect.java   k-th smallest element, reuses the QuickSort partition
  Metrics.java       comparisons, max recursion depth, time
  Benchmark.java     runs all cases and writes results.csv
src/test/java/daa/
  AlgorithmsTest.java   JUnit 5 tests
plot.py                 builds the plots from results.csv
REPORT.md               asymptotic analysis, recurrences, plots, discussion
```

## Build

```
mvn compile
```

## Run the tests

```
mvn test
```

## Run the benchmark

```
mvn compile
java -cp target/classes daa.Benchmark
```

This runs every algorithm on n = 1 000, 10 000, 100 000, 1 000 000 and on three input types (random, sorted, duplicates). Each case runs 5 times and the median time is saved. The result is written to `results.csv` in the project root with columns `algorithm,input,n,time_ms,comparisons,max_depth`.

In IntelliJ IDEA the benchmark can also be started with the green run button next to `main` in `Benchmark.java`.

## Build the plots

```
pip install matplotlib
python plot.py
```

Three PNG files are saved into `plots/`: `time_vs_n.png`, `depth_vs_n.png`, `ratio_vs_n.png`.

## Usage example

```java
Metrics metrics = new Metrics();
int[] a = {5, 3, 8, 1, 9, 2};

QuickSort.sort(a, metrics);
System.out.println(metrics.getComparisons() + " comparisons, depth " + metrics.getMaxDepth());

int third = QuickSelect.select(new int[]{5, 3, 8, 1, 9, 2}, 2);   // 3rd smallest, k starts from 0
```

## Git workflow

- `main`: working code, release tag `v1.0`
- feature branches: `feature/metrics`, `feature/mergesort`, `feature/quicksort`, `feature/select`
- commit messages follow the form `feat(quicksort): recurse into smaller side first`
