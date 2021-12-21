package solvd.crud_assignment;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
	Optional<T> get(long id);

	List<T> getAll();

	void save(T input);

	void update(T input, String[] parameters);

	void delete(T target);
}
