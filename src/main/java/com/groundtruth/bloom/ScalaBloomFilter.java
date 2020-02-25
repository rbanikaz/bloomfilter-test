package com.groundtruth.bloom;

import bloomfilter.CanGenerateHashFrom;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

public class ScalaBloomFilter implements BloomFilter {

    private final bloomfilter.mutable.BloomFilter<byte[]> bloomFilter;

    public ScalaBloomFilter(int expectedElems, float falsePositiveProbability) {
        bloomFilter = bloomfilter.mutable.BloomFilter.apply(
                expectedElems,
                falsePositiveProbability,
                CanGenerateHashFrom.CanGenerateHashFromByteArray$.MODULE$);
    }

    @Override
    public void add(String elem) {
        bloomFilter.add(elem.getBytes(Charsets.UTF_8));
    }

    @Override
    public boolean contains(String elem) {
        return bloomFilter.mightContain(elem.getBytes(Charsets.UTF_8));
    }
}
