package de.fh_dortmund.ticket_system.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the login.xhml page.
 * 
 * @author itcuties
 * 
 */
@WebFilter("/pages/*")
public class LoginFilter implements Filter
{

	/**
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException,
			IOException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		Authentication login = (Authentication) req.getSession().getAttribute("auth");

		if (login == null || !login.isLoggedIn())
		{
			// User is not logged in, so redirect to index.
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/login.xhtml");
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException
	{
		// Nothing to do here!
	}

	public void destroy()
	{
		// Nothing to do here!
	}

}