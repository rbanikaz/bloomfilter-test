package com.groundtruth.bloom;

public class BloomFilterTestResult {

    int numOfAllocations;
    double falsePositiveRate;
    double queriesPerSecond;

    @Override
    public String toString() {
        return "NumAllocations: " + numOfAllocations + "; FalsePositiveRate: " + falsePositiveRate + "; QPS: " + queriesPerSecond;
    }
}
