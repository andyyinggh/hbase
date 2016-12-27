package cn.edu.cuit.jetty;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.webapp.WebAppContext;


public class JettyServer {
	private static Map<Integer, Server> SERVER_MAP = new HashMap<Integer, Server>();
	private static Set<ServiceInfo> SERVICES = new HashSet<ServiceInfo>();

	public static synchronized int start(int port) {
		if (SERVER_MAP.containsKey(port)) {
			return 0;
		}

		Server server = new Server(port);

		// create servlet context handler
		ServletContextHandler servletContextHandler = new ServletContextHandler();

		// create web app context handler
		WebAppContext webAppContext = new WebAppContext();
		String webResBase = "webContent";
		webAppContext.setResourceBase(webResBase);
		webAppContext.setContextPath("/");
		// disables Jetty's memory-mapped mode and avoid web file locking
		webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
//		webAppContext.addFilter(JettyAPIFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST)); //$NON-NLS-1$

		// combines these two contexts into context collection
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { webAppContext, servletContextHandler });

		// set the combined contexts into server
		server.setHandler(contexts);

		SERVER_MAP.put(port, server);

		try {
			server.start();
		} catch (Throwable throwable) {
			System.out.println(throwable.getMessage());
			return -1;
		}

		return 0;
	}

	public static synchronized int addServlet(int port, String path, HttpServlet servlet) {
		try {
			if (!SERVER_MAP.containsKey(port)) {
				throw new IllegalArgumentException("There is no Jetty server on port " + port);
			}

			ServiceInfo info = new ServiceInfo(port, path);

			if (SERVICES.contains(info)) {
				if (removeServlet(port, path, servlet) != 0) {
					return -1;
				}
			}
			ServletHolder servletHolder = new ServletHolder(servlet);
			(getServletContextHandler(port)).addServlet(servletHolder, path);
			SERVICES.add(info);

		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return -1;
		}
		return 0;
	}

	public static synchronized int removeServlet(int port, String path, HttpServlet servlet) {
		try {
			ServiceInfo info = new ServiceInfo(port, path);
			if (SERVICES.contains(info)) {
				ServletContextHandler servletContextHandler = getServletContextHandler(port);
				ServletHandler servletHandler = servletContextHandler.getServletHandler();

				List<ServletHolder> servlets = new ArrayList<ServletHolder>();

				Set<String> names = new HashSet<String>();

				for (ServletHolder holder : servletHandler.getServlets()) {
					if (servlet.getClass().isInstance(holder.getServlet())) {
						names.add(holder.getName());
					} else {
						servlets.add(holder);
					}
				}

				List<ServletMapping> mappings = new ArrayList<ServletMapping>();

				for (ServletMapping mapping : servletHandler.getServletMappings()) {
					if (!names.contains(mapping.getServletName())) {
						mappings.add(mapping);
					}
				}

				servletHandler.setServletMappings(mappings.toArray(new ServletMapping[0]));
				servletHandler.setServlets(servlets.toArray(new ServletHolder[0]));

				SERVICES.remove(info);
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return -1;
		}
		return 0;
	}

	private static ServletContextHandler getServletContextHandler(int port) {
		Server server = SERVER_MAP.get(port);
		if (server == null) {
			throw new IllegalArgumentException("There is no Jetty server on port " + port);
		}
		ContextHandlerCollection contextHandler = (ContextHandlerCollection) server.getHandler();
		for (Handler handler : contextHandler.getHandlers()) {
			if (handler instanceof ServletContextHandler) {
				return (ServletContextHandler) handler;
			}
		}

		throw new IllegalStateException("There is no ServletContextHandler in Jetty server.");
	}
}

class ServiceInfo {
	private int port;
	private String path;

	/**
	 * @param port
	 * @param path
	 */
	public ServiceInfo(int port, String path) {
		super();
		this.port = port;
		this.path = path;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ServiceInfo other = (ServiceInfo) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
}