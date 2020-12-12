package com.custom.spring.mvc.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlLangAppenderFilter extends OncePerRequestFilter {
	private static final Logger LOG = LogManager.getLogger("customLog");
	//Fields to exlude specified URLS
    private final Set<String> exludedUrls = new HashSet<>(
    		Arrays.asList(
    				"/**/bundle/messages*",
    				"/**/bundle/messages**",
    				"/**/processorders*", 
    				"/**/processorders**", 
    				"/**/*.*"));
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
        if (!request.getParameterMap().containsKey("lang")) {
            ServletUriComponentsBuilder builder = 
            		ServletUriComponentsBuilder.fromRequest(request);
			Map<String, String[]> params = request.getParameterMap();
			params.forEach((k, v) -> {
				builder.queryParam(k, (Object[]) v);	
			});
			builder.replaceQueryParam("lang", request.getLocale());
			//Set the HTTP response status to 301 (permanent redirect)
			response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
			//Set the HTTP response location to the new URI (trigger redirect)
			response.setHeader(HttpHeaders.LOCATION, builder.toUriString());
        } 
    	try {
    		filterChain.doFilter(request, response);
		} catch (IOException | ServletException ex) {
			LOG.info("UrlLangAppenderFilter::doFilterInternal" + ex); 	
		}
    }
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return exludedUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
	}
}

