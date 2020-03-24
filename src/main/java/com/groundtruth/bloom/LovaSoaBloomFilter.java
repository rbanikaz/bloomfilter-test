package com.groundtruth.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnels;

import static java.lang.Math.*;
//https://github.com/lovasoa/bloomfilter
public class LovaSoaBloomFilter implements BloomFilter {

    private final com.github.lovasoa.bloomfilter.BloomFilter bloomFilter;

    public LovaSoaBloomFilter(int expectedElems, float falsePositiveProbability) {
        int bits = (int)ceil((expectedElems * log(falsePositiveProbability)) / log(1 / pow(2, log(2))));
        bloomFilter = new com.github.lovasoa.bloomfilter.BloomFilter(expectedElems, bits);
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
