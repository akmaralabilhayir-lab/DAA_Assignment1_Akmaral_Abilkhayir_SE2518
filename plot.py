import csv
import math
import os
from collections import defaultdict

import matplotlib.pyplot as plt

CSV_FILE = "results.csv"
OUT_DIR = "plots"
os.makedirs(OUT_DIR, exist_ok=True)

COLORS = {"MergeSort": "tab:blue", "QuickSort": "tab:orange", "QuickSelect": "tab:green"}
STYLES = {"random": "-", "sorted": "--", "duplicates": ":"}

# data[(algorithm, input)] = list of (n, time_ms, comparisons, max_depth)
data = defaultdict(list)
with open(CSV_FILE, newline="") as f:
    for row in csv.DictReader(f):
        key = (row["algorithm"], row["input"])
        data[key].append((int(row["n"]), float(row["time_ms"]),
                          int(row["comparisons"]), int(row["max_depth"])))
for key in data:
    data[key].sort()


def ratio(algorithm, n, comparisons):
    if algorithm == "QuickSelect":
        return comparisons / n
    return comparisons / (n * math.log2(n))


def make_plot(filename, title, ylabel, value, logy=False):
    plt.figure(figsize=(11, 6))
    for (algorithm, inp), rows in data.items():
        xs = [r[0] for r in rows]
        ys = [value(algorithm, r) for r in rows]
        plt.plot(xs, ys, STYLES[inp], color=COLORS[algorithm], marker="o",
                 label=f"{algorithm} ({inp})")
    plt.xscale("log")
    if logy:
        plt.yscale("log")
    plt.xlabel("n")
    plt.ylabel(ylabel)
    plt.title(title)
    plt.grid(True, which="both", alpha=0.3)
    plt.legend(fontsize=8, loc="center left", bbox_to_anchor=(1, 0.5))
    plt.tight_layout()
    plt.savefig(os.path.join(OUT_DIR, filename), dpi=150)
    plt.close()


make_plot("time_vs_n.png", "Time vs n", "time, ms (median of 5)",
          lambda a, r: r[1], logy=True)
make_plot("depth_vs_n.png", "Max recursion depth vs n", "max depth",
          lambda a, r: r[3])
make_plot("ratio_vs_n.png",
          "Ratio: comparisons/(n*log2 n) for sorts, comparisons/n for QuickSelect",
          "ratio", lambda a, r: ratio(a, r[0], r[2]))

print("Saved PNG files to", OUT_DIR)