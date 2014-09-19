package app.dao;

import java.util.List;

public interface GenericDao< T > {
	T create(T t);
	void delete(Object id);
	T read(Object id);
	T update(T t);
}