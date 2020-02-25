package com.groundtruth.bloom;

import orestes.bloomfilter.FilterBuilder;

public class OrestesBloomFilter implements BloomFilter {

    private final orestes.bloomfilter.BloomFilter<String> bloomFilter;

    public OrestesBloomFilter(int expectedElems, float falsePositiveProbability) {
        bloomFilter = new FilterBuilder(expectedElems, falsePositiveProbability).buildBloomFilter();
    }

    @Override
    public void add(String elem) {
        bloomFilter.add(elem);
    }

    @Override
    public boolean contains(String elem) {
        return bloomFilter.contains(elem);
    }
}
