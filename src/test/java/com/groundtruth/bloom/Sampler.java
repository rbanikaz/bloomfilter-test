package com.groundtruth.bloom;

public class Sampler implements com.google.monitoring.runtime.instrumentation.Sampler {

    public Sampler(String type) {
        this.type = type;
        this.numOfAllocations = 0;
    }
    private String type;
    private int numOfAllocations;

    @Override
    public void sampleAllocation(int count, String desc, Object newObj, long size) {
        numOfAllocations += size;
    }

    int getNumOfAllocations() {
        return numOfAllocations;
    }

}
