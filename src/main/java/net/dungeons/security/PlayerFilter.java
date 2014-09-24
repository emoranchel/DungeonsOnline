package net.dungeons.security;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/player/**"})
public class PlayerFilter implements Filter {

  @Inject
  private UserBean userBean;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (userBean.isLoggedIn()) {
      chain.doFilter(request, response);
    } else {
      ((HttpServletResponse) response).sendRedirect("/");
    }
  }

  @Override
  public void destroy() {
  }

}
