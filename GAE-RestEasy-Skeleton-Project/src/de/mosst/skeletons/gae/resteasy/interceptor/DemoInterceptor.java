package de.mosst.skeletons.gae.resteasy.interceptor;

import java.lang.reflect.Method;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.logging.Logger;

import de.mosst.skeletons.gae.resteasy.annotation.NoAccess;



@Provider
public class DemoInterceptor implements ContainerRequestFilter {
	
	private static Logger LOG = Logger.getLogger(DemoInterceptor.class);
	
	private static final Response ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());

	@Override
	public void filter(ContainerRequestContext requestContext) {
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		LOG.debug("Intercept Class " + method.getName() + "  Method: " + method.getName());
		
		if(method.isAnnotationPresent(NoAccess.class)){
			requestContext.abortWith(ACCESS_DENIED);
		}
	}
}
