package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter{

	protected String encoding = null;
	protected FilterConfig config = null;
	
	@Override
	public void init(FilterConfig config) {
		this.config = config;
		this.encoding = config.getInitParameter("encoding");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 使用Filter解決QueryString的編碼問題
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		this.config = null;
		this.encoding = null;
	}
	
}
