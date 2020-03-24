Testing some open source bloom filter implementations

SCALA:
https://github.com/alexandrnikitin/bloom-filter-scala

GUAVA:
https://guava.dev/releases/23.0/api/docs/com/google/common/hash/BloomFilter.html

ORESTES:
https://github.com/Baqend/Orestes-Bloomfilter

LOVA:
https://github.com/lovasoa/bloomfilter

## RESULTS:

```
SCALA      NumAllocations: 96;         FalsePositiveRate: 0.04839999973773956; QPS: 1244379.4491231728
GUAVA      NumAllocations: 1920000136; FalsePositiveRate: 0.04899999871850014; QPS: 817545.5744868225
ORESTES    NumAllocations: 800000080;  FalsePositiveRate: 0.04569999873638153; QPS: 831717.514132752
LOVA       NumAllocations: 112;        FalsePositiveRate: 0.04839999973773956; QPS: 1218425.5675395832
```
All 4 implementations are correct in that none of them exceed the assigned false positive rate of 0.05

But the GUAVA and ORESTES implementations are doing lots of allocations internally (creating new objects)

The SCALA and LOA versions use less allocations and have higher QPS than the others (likely due to less allocations) 

## Dev
To run this test, first do a clean build:
```
 mvn clean install
```
Then run with below VM params:
```
-javaagent:/Users/rbanikaz/.m2/repository/com/google/code/java-allocation-instrumenter/java-allocation-instrumenter/3.1.0/java-allocation-instrumenter-3.1.0.jar
```

Output gets printed to console
