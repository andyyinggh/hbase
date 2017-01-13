package cn.edu.cuit.datasource;

import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSourceFactory extends AbstractDataSourceFacotry<ComboPooledDataSource> {
	
	
	
	@Override
	public ComboPooledDataSource createDataSource() {
		return new ComboPooledDataSource();
	}

	@Override
	public void setDriver(ComboPooledDataSource dataSource, String driver) {
		try {
			dataSource.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUrl(ComboPooledDataSource dataSource, String url) {
		dataSource.setJdbcUrl(url);
	}

	@Override
	public void setUser(ComboPooledDataSource dataSource, String user) {
		dataSource.setUser(user);
	}

	@Override
	public void setPassword(ComboPooledDataSource dataSource, String password) {
		dataSource.setPassword(password);
	}

	@Override
	public void setAdvancedConfig(ComboPooledDataSource dataSource) {
	}


}
