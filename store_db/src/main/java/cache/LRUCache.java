package cache;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRUCache<K, V> {

    private final int size;
    private final Map<K, V> entityMap;
    private final Queue<K> keyQueue;

    public LRUCache(int size) {
        this.size = size;
        this.entityMap = new ConcurrentHashMap<>(size);
        this.keyQueue = new ConcurrentLinkedQueue<>();
    }

    public void put(K key, V value) {
        if (keyQueue.contains(key)) {
            return;
        }
        if (this.size() + 1 >= this.size) {
            K oldestKey = keyQueue.poll();
            entityMap.remove(oldestKey);
        }
        keyQueue.offer(key);
        entityMap.put(key, value);
    }

    public V get(K key) {
        if (!keyQueue.contains(key)) {
            return null;
        }
        keyQueue.remove(key);
        keyQueue.offer(key);
        return entityMap.get(key);
    }

    public void remove(K key) {
        if (!keyQueue.contains(key)) {
            return;
        }
        keyQueue.remove(key);
        entityMap.remove(key);
    }

    public int size() {
        return entityMap.size();
    }
}
