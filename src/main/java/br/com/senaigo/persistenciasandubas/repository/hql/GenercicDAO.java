package br.com.senaigo.persistenciasandubas.repository.hql;

public interface GenercicDAO {
	<T> T findByField(Class<?> clazz, String field, Object value);
	
	Boolean existsByField(Class<?> clazz, String field, Object value)throws Exception;
	
	Long countByField(Class<?> clazz, String field, Object value)throws Exception;
	
	<T> T findByIdEager(Class<T> type, String pk);
	
	<T> T update(T entity);
	
	void delete(Object objeto) throws Exception;
}
