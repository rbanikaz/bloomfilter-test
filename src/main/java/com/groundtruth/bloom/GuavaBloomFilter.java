package com.groundtruth.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnels;

public class GuavaBloomFilter implements BloomFilter {

    private final com.google.common.hash.BloomFilter<String> bloomFilter;

    public GuavaBloomFilter(int expectedElems, float falsePositiveProbability) {
        bloomFilter = com.google.common.hash.BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedElems, falsePositiveProbability);
    }

    @Override
    public void add(String elem) {
        bloomFilter.put(elem);
    }

    @Override
    public boolean contains(String elem) {
        return bloomFilter.mightContain(elem);
    }
}
