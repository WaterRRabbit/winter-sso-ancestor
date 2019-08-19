package org.winterframework.sso.example.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: YHG
 * @Date: 2019/8/18 10:18
 */
public class WebSsoFilter extends BaseSsoFilter {

    private String ssoServer;

    public void setSsoServer(String ssoServer) {
        this.ssoServer = ssoServer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String mapping = request.getServletPath();
        if (isExcluded(mapping)){
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("user")!=null){
            filterChain.doFilter(request, response);
            return;
        }
        String ssoSessionId = request.getParameter("ssoSessionId");
        if (ssoSessionId!=null){
            request.getSession().setAttribute("user", "admin");
            filterChain.doFilter(request, response);
            return;
        }
        String from = request.getRequestURL().toString();
        response.sendRedirect(ssoServer + "/login?from="+from);
    }
}
