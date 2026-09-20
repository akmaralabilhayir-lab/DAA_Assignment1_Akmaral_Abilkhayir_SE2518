package daa;

public class Metrics {

    private long comparisons;
    private int currentDepth;
    private int maxDepth;

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void addComparison() {
        comparisons++;
    }

    public void enterRecursion() {
        currentDepth++;

        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    private long startTime;
    private long elapsedNanos;

    public long getElapsedNanos() { return elapsedNanos; }
    public double getElapsedMs()  { return elapsedNanos / 1_000_000.0; }

    public void start() { startTime = System.nanoTime(); }
    public void stop()  { elapsedNanos = System.nanoTime() - startTime; }

    public void reset() {
        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTime = 0;
        elapsedNanos = 0;
    }
}