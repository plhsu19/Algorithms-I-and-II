/* *****************************************************************************
 *  Name: Pei-Lun Hsu
 *  Date: 28012020
 *  Description: First assignment Union Find of algorithms
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int number;
    private final int numTrials;
    private final double mean;
    private final double std;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or expriment trails smaller than 0");
        }
        // array to store thresholds for all trials
        double[] thresholds = new double[trials];
        number = n;
        numTrials = trials;

        // conduct t times trials, record each threshold in the array
        for (int t = 0; t < trials; t++) {
            double threshold = oneTrail();
            thresholds[t] = threshold;
        }
        mean = StdStats.mean(thresholds);
        std = StdStats.stddev(thresholds);

    }

    // private method for one tial experiment
    // return the threshold for one tial
    private double oneTrail() {
        Percolation p = new Percolation(number);

        // continue open() until map percolates
        while (!p.percolates()) {
            int i = StdRandom.uniform(1, number + 1);
            int j = StdRandom.uniform(1, number + 1);
            while (p.isOpen(i, j)) {
                i = StdRandom.uniform(1, number + 1);
                j = StdRandom.uniform(1, number + 1);
            }
            p.open(i, j);
        }

        double threshold = (double) p.numberOfOpenSites() / (number * number);
        return threshold;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return std;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - (CONFIDENCE_95 * std / Math.sqrt(numTrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + (CONFIDENCE_95 * std / Math.sqrt(numTrials)));
    }

    // test the object PercolationDtats
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        int tra = Integer.parseInt(args[1]);
        Stopwatch watch = new Stopwatch();
        PercolationStats exp = new PercolationStats(num, tra);
        double runTime = watch.elapsedTime();
        System.out.println("mean                    = " + exp.mean());
        System.out.println("stddev                  = " + exp.stddev());
        System.out.println("95% confidence interval = [" + exp.confidenceLo() + ", "
                                   + exp.confidenceHi() + "]");
        System.out.println("running time when       = " + runTime + " sec.");
    }
}
