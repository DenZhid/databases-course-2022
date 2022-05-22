package cache;

public interface EntityCache<T> {

    void add(T entity);

    T get(long id);

    void remove(long id);
}
