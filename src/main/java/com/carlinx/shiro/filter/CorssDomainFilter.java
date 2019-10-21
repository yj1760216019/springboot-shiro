package com.carlinx.shiro.filter;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "corssDomainFilter", urlPatterns = "/*")
public class CorssDomainFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CorssDomainFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		httpResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Accept, Origin, User-Agent, Content-Range, Content-Disposition, Content-Description");
		httpResponse.addHeader("Access-Control-Allow-Methods", "GET,PUT,DELETE,POST,OPTIONS");
		httpResponse.setHeader("Access-Control-Max-Age", "3600"); // 有效期
		httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		HttpServletRequest s = (HttpServletRequest) servletRequest;
		String origin = s.getHeader("Origin");
		httpResponse.addHeader("Access-Control-Allow-Origin",origin);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {

	}
}