package model.repo;

public interface Repo<T> {
	public T find(String name);
	public void save(T obj);
}
