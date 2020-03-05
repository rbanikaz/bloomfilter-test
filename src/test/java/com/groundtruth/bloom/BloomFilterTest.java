package com.groundtruth.bloom;

import com.google.monitoring.runtime.instrumentation.AllocationRecorder;
import com.groundtruth.bloom.BloomFilter.BloomFilterType;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import static java.lang.Math.*;
import static java.lang.Math.log;

public class BloomFilterTest {
    Map results = new HashMap<String, Integer>();

    public void checkCorrectness() {

        for (var bloomFilterType: BloomFilterType.values()) {
            System.out.println("Testing correctness of bloom filter type: " + bloomFilterType);
            var s = new Sampler(bloomFilterType.toString());
            AllocationRecorder.addSampler(s);
            int expectedElems = 10_000;
            float falsePositiveProbability = 0.05f;
            BloomFilter bloomFilter = BloomFilter.newBloomFilter(bloomFilterType, expectedElems, falsePositiveProbability);
            checkCorrectness(bloomFilter, expectedElems, falsePositiveProbability);
            AllocationRecorder.removeSampler(s);
            results.put(bloomFilterType, s.getNumOfAllocations());
        }
    }

    public void checkQuerySpeed() {
        for (var bloomFilterType: BloomFilterType.values()) {
            System.out.println("Testing query speed of bloom filter type: " + bloomFilterType);
            var s = new Sampler(bloomFilterType.toString());
            AllocationRecorder.addSampler(s);
            int expectedElems = 10_000;
            float falsePositiveProbability = 0.1f;
            BloomFilter bloomFilter = BloomFilter.newBloomFilter(bloomFilterType, expectedElems, falsePositiveProbability);
            checkQuerySpeed(bloomFilter, expectedElems, falsePositiveProbability);
            AllocationRecorder.removeSampler(s);
            results.put(bloomFilterType, s.getNumOfAllocations());

        }
    }

    private void checkCorrectness(BloomFilter bloomFilter, int expectedElems, float falsePositiveProbability ) {

            System.out.println("Testing correctness.\n"+
                    "Creating a Set and filling it together with our filter...");
            var inside = new HashSet<>((int)(expectedElems / 0.75));
            while(inside.size() < expectedElems) {
                var v = UUID.randomUUID().toString();
                inside.add(v);
                bloomFilter.add(v);
                assert bloomFilter.contains(v) : "There should be no false negative";
            }

            // testing
            int found = 0, total = 0;
            double rate = 0;
            while (total < expectedElems) {
                String v = UUID.randomUUID().toString();
                if (inside.contains(v)) continue;
                total++;
                found += bloomFilter.contains(v) ? 1 : 0;

                rate = (float) found / total;
                if (total % 1000 == 0 || total == expectedElems) {
                    System.out.format(
                            "\rElements incorrectly found to be inside: %8d/%-8d (%3.2f%%)",
                            found, total, 100*rate
                    );
                }
            }
            System.out.println("\n");

            double ln2 = Math.log(2);
            int bitsize = (int)ceil((expectedElems * log(falsePositiveProbability)) / log(1 / pow(2, log(2))));
            double expectedRate = Math.exp(-ln2*ln2 * bitsize / expectedElems);
            assert rate <= expectedRate * 1.10 : "error rate p = e^(-ln2^2*m/n)";
    }


    private void checkQuerySpeed(BloomFilter bloomFilter, int expectedElems, float falsePositiveProbability) {
        System.out.println("Testing query speed...");
        var bean = ManagementFactory.getThreadMXBean();

        for(int i = 0; i < expectedElems; i++) {
            bloomFilter.add(UUID.randomUUID().toString());
        }

        boolean xor = true; // Make sure our result isn’t optimized out
        long start = bean.getCurrentThreadCpuTime();
        for(int i = 0; i < expectedElems; i++) {
            xor ^= bloomFilter.contains(UUID.randomUUID().toString());
        }
        long end = bean.getCurrentThreadCpuTime();
        long time = end - start;

        System.out.format(
                "Queried %d elements in %d ns.\n" +
                        "Query speed: %g elements/second\n\n",
                expectedElems,
                time,
                expectedElems/(time*1e-9)
        );
    }

    public static void main(String[] args) {

        var bloomFilterTest = new BloomFilterTest();
        //bloomFilterTest.checkCorrectness();
        bloomFilterTest.checkQuerySpeed();

        for (var type: bloomFilterTest.results.keySet()) {
            System.out.println(type + "      " + bloomFilterTest.results.get(type));
        }


    }

}











