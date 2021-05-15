package io.onedev.server.rest.jersey;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.hibernate.validator.constraints.NotEmpty;

@Provider
public class ResourceParamFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;
	
	@Context
	private UriInfo uriInfo;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Set<String> definedQueryParams = new HashSet<>();
		Set<String> requiredQueryParams = new HashSet<>();
		for (Parameter param: resourceInfo.getResourceMethod().getParameters()) {
			QueryParam queryParam = param.getAnnotation(QueryParam.class);
			if (queryParam != null) {
				definedQueryParams.add(queryParam.value());
				if (param.getType().isPrimitive() 
						|| param.getAnnotation(NotNull.class) != null 
						|| param.getAnnotation(NotEmpty.class) != null 
						|| param.getAnnotation(Size.class)!=null && param.getAnnotation(Size.class).min()>0) {
					requiredQueryParams.add(queryParam.value());
				}
			}
		}
		
		Set<String> suppliedQueryParams = new HashSet<>(uriInfo.getQueryParameters().keySet());
		suppliedQueryParams.removeAll(definedQueryParams);
		if (!suppliedQueryParams.isEmpty()) 
			throw new InvalidParamException("Unexpected query params: " + suppliedQueryParams);

		requiredQueryParams.removeAll(uriInfo.getQueryParameters().keySet());
		if (!requiredQueryParams.isEmpty()) 
			throw new InvalidParamException("Missing query params: " + requiredQueryParams);
	}

}