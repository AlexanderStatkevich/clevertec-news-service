package ru.clevertec.statkevich.newsservice.cache;

import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.Nullable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * Implementation of least recently used cache algorithm using Spring Framework interface.
 */
public class LruCache extends AbstractValueAdaptingCache implements Cache {

    private final String name;
    private final Deque<Object> cacheObjectList;
    private final ConcurrentMap<Object, Node> pointerMap;
    private final int cacheCapacity;

    public LruCache(boolean allowNullValues, String name, int cacheCapacity) {
        super(allowNullValues);
        this.name = name;
        this.pointerMap = new ConcurrentReferenceHashMap<>();
        this.cacheObjectList = new LinkedList<>();
        this.cacheCapacity = cacheCapacity;
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
        if (pointerMap.containsKey(key)) {
            Node currentNode = pointerMap.get(key);
            cacheObjectList.remove(currentNode.key);
            cacheObjectList.addFirst(key);
            return this.pointerMap.get(key).value();
        } else {
            if (cacheObjectList.size() == cacheCapacity) {
                Object temp = cacheObjectList.removeLast();
                pointerMap.remove(temp);
            }
            return null;
        }
    }

    @Override
    @Nullable
    public <T> T get(Object key, Callable<T> valueLoader) {
        return (T) fromStoreValue(this.pointerMap.computeIfAbsent(key, k -> {
            try {

                if (pointerMap.containsKey(key)) {
                    Node currentNode = pointerMap.get(key);
                    cacheObjectList.remove(currentNode.key);
                } else if (cacheObjectList.size() == cacheCapacity) {
                    Object temp = cacheObjectList.removeLast();
                    pointerMap.remove(temp);
                }

                T value = valueLoader.call();
                Node newItem = new Node(key, toStoreValue(value));
                cacheObjectList.addFirst(newItem.key);
                return newItem;
            } catch (Throwable ex) {
                throw new ValueRetrievalException(key, valueLoader, ex);
            }
        }).value());
    }

    @Override
    public void put(Object key, @Nullable Object value) {

        if (pointerMap.containsKey(key)) {
            Node currentNode = pointerMap.get(key);
            cacheObjectList.remove(currentNode.key);
        } else {
            if (cacheObjectList.size() == cacheCapacity) {
                Object temp = cacheObjectList.removeLast();
                pointerMap.remove(temp);
            }
        }
        Node newItem = new Node(key, toStoreValue(value));
        cacheObjectList.addFirst(newItem.key);
        pointerMap.put(key, newItem);
    }

    @Override
    @Nullable
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        if (pointerMap.containsKey(key)) {
            Node currentNode = pointerMap.get(key);
            cacheObjectList.remove(currentNode.key);
        } else {
            if (cacheObjectList.size() == cacheCapacity) {
                Object temp = cacheObjectList.removeLast();
                pointerMap.remove(temp);
            }
        }

        Node newItem = new Node(key, toStoreValue(value));
        cacheObjectList.addFirst(newItem.key);

        Node node = pointerMap.putIfAbsent(key, newItem);
        return toValueWrapper(node.value());
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

    private record Node(Object key, Object value) {
    }
}
