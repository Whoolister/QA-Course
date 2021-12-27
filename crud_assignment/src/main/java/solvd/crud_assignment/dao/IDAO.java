package solvd.crud_assignment.dao;

import java.util.List;

public interface IDAO<T> {
	void save(T input);

	T get(int id);

	List<T> getAll();

	void update(T input, Object[] parameters);

	void delete(T target);

	void deleteAll();
}
