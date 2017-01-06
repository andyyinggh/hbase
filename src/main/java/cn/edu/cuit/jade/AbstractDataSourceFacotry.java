package cn.edu.cuit.jade;

import javax.sql.DataSource;

import cn.edu.cuit.utils.ConfigUtil;

public abstract class AbstractDataSourceFacotry<T extends DataSource> implements DataSourceFactory {

	protected final String JDBC_DRIVER = "jdbc.driver";   //$NON-NLS-1$
	protected final String JDBC_URL = "jdbc.url";   //$NON-NLS-1$
	protected final String JDBC_USER = "jdbc.user";   //$NON-NLS-1$
	protected final String JDBC_PASS = "jdbc.password";   //$NON-NLS-1$
	
	private String driver;
	private String url;
	private String user;
	private String password;
	
	
	public AbstractDataSourceFacotry() {
		loadConfig();
	}

	public void loadConfig() {
		ConfigUtil.load("jdbc");
		this.driver = ConfigUtil.getString(JDBC_DRIVER);
		this.url = ConfigUtil.getString(JDBC_URL);
		this.user = ConfigUtil.getString(JDBC_USER);
		this.password = ConfigUtil.getString(JDBC_PASS);
	}
	
	@Override
	public final T getDataSource() {
		T dataSource = createDataSource();
		setDriver(dataSource, driver);
		setUrl(dataSource, url);
		setUser(dataSource, user);
		setPassword(dataSource, password);
		setAdvancedConfig(dataSource);
		
		return dataSource;
	}
	
	public abstract T createDataSource();
	
	public abstract void setDriver(T dataSource, String driver);
	
	public abstract void setUrl(T dataSource, String url);
	
	public abstract void setUser(T dataSource, String user);
	
	public abstract void setPassword(T dataSource, String password);
	
	public abstract void setAdvancedConfig(T dataSource);

}
