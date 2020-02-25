package com.groundtruth.bloom;

public interface BloomFilter {
    void add(String elem);
    boolean contains(String elem);

    enum BloomFilterType {
        GUAVA, LOVA, ORESTES, SCALA
    }

    static BloomFilter newBloomFilter(BloomFilterType type, int expectedElems, float falsePositiveProbability) {
        switch (type) {
            case GUAVA: return new GuavaBloomFilter(expectedElems, falsePositiveProbability);
            case LOVA: return new LovaSoaBloomFilter(expectedElems, falsePositiveProbability);
            case ORESTES: return new OrestesBloomFilter(expectedElems, falsePositiveProbability);
            case SCALA: return new ScalaBloomFilter(expectedElems, falsePositiveProbability);
        }

        throw new RuntimeException("Unknown type: " + type);
    }
}
