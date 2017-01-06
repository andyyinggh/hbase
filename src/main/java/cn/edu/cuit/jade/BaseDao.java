package cn.edu.cuit.jade;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import cn.jadepool.sql.Jade;

public class BaseDao<T> {
	
	private Class<T> entityClass;
	private static DataSource dataSource = new C3P0DataSourceFactory().getDataSource();
	
	protected String tableName;
	
	@SuppressWarnings("unchecked")
	public BaseDao() {
		entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}
	
	public BaseDao(String tableName) {
		this();
		this.tableName = tableName.toLowerCase();
	}
	
	public Jade getJade() throws SQLException {
		return new Jade(dataSource.getConnection());
	}
	
	public int saveEntity(T entity) {
		return saveEntity(this.tableName, entity);
	}
	
	public int saveEntity(String tableName, T entity) {
		Jade jade = this.getJade();
		
	}
	
	/*public int updateEntity(T entity) {
		
	}*/
	
	
	
	/*public int deleteEntity(T entity) {
		
	}*/
	

	public Map<String, Object> entityToMap(T entity) {
		Map<String, Object> result = null;
		
		if(entity != null) {
			for(Method m : entityClass.getDeclaredMethods()) {
				
			}
		}
	}

}
