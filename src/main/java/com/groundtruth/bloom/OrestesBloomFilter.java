package com.groundtruth.bloom;

import orestes.bloomfilter.FilterBuilder;

//https://github.com/Baqend/Orestes-Bloomfilter
// the nice thing about this is it has a redis implementation for when the bloom filter grows
// beyond what we can fit in main memory
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
