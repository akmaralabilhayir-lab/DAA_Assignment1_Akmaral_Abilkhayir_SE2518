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
}