package ru.clevertec.statkevich.newsservice.cache;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * Implementation of least frequently used cache algorithm using Spring Framework interface.
 */
public class LfuCache extends AbstractValueAdaptingCache implements Cache {

    private final int cacheCapacity;

    private final String name;

    private final ConcurrentMap<Object, Node> pointerMap;


    public LfuCache(boolean allowNullValues, int cacheCapacity, String name) {
        super(allowNullValues);
        this.cacheCapacity = cacheCapacity;
        this.name = name;
        this.pointerMap = new ConcurrentReferenceHashMap<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.pointerMap;
    }

    @Override
    protected Object lookup(Object key) {
        Node node = pointerMap.get(key);
        node.increment();
        return this.pointerMap.get(key).value;
    }

    @Override
    @Nullable
    public <T> T get(Object key, Callable<T> valueLoader) {
        return (T) fromStoreValue(this.pointerMap.computeIfAbsent(key, k -> getNode(key, valueLoader)).value);
    }

    private <T> Node getNode(Object key, Callable<T> valueLoader) {
        try {
            T call = valueLoader.call();
            Node node = pointerMap.get(key);
            if (node != null) {
                node.increment();
            } else {
                node = new Node(call, 1);
            }
            return node;
        } catch (Throwable ex) {
            throw new ValueRetrievalException(key, valueLoader, ex);
        }
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        if (pointerMap.size() == cacheCapacity) {
            Object leastFrequentlyKey = findLFU();
            pointerMap.remove(leastFrequentlyKey);
        }
        Node newNode = new Node(value, 1);
        this.pointerMap.put(key, (Node) toStoreValue(newNode));
    }

    @Override
    @Nullable
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        Object existing = this.pointerMap.putIfAbsent(key, (Node) toStoreValue(value));
        return toValueWrapper(existing);
    }

    @Override
    public void evict(Object key) {
        this.pointerMap.remove(key);
    }

    @Override
    public boolean evictIfPresent(Object key) {
        return (this.pointerMap.remove(key) != null);
    }

    @Override
    public void clear() {
        this.pointerMap.clear();
    }

    @Override
    public boolean invalidate() {
        boolean notEmpty = !this.pointerMap.isEmpty();
        this.pointerMap.clear();
        return notEmpty;
    }

    private Object findLFU() {
        Object key = null;
        int minFrequency = Integer.MAX_VALUE;
        for (Map.Entry<Object, Node> entry : pointerMap.entrySet()) {
            if (entry.getValue().frequency < minFrequency) {
                minFrequency = entry.getValue().frequency;
                key = entry.getKey();
            }
        }
        return key;
    }

    private static class Node {
        private Object value;
        private int frequency;

        public Node(Object value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        public void increment() {
            frequency += 1;
        }
    }
}
