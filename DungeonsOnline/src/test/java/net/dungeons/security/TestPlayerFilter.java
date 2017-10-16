package net.dungeons.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayerFilter {
  
  @Mock
  private UserBean userBean;
  @InjectMocks
  private PlayerFilter filter = new PlayerFilter();
  
  @Test
  public void shouldFilterAlreadyLoggedInUsers() throws Exception {
    ServletRequest request = mock(ServletRequest.class);
    ServletResponse response = mock(HttpServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    
    when(userBean.isLoggedIn()).thenReturn(true);
    
    filter.doFilter(request, response, chain);
    
    verify(chain).doFilter(request, response);
  }

  @Test
  public void shouldFilterNotLoggedInUsers() throws Exception {
    ServletRequest request = mock(ServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    
    when(userBean.isLoggedIn()).thenReturn(false);
    
    filter.doFilter(request, response, chain);
    
    verifyNoMoreInteractions(chain);
    verify(response).sendRedirect("/");
  }
}
