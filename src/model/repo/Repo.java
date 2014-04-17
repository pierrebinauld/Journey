package model.repo;

public interface Repo<T> {
	public T findById(int id);
	public void save(T obj);
}
