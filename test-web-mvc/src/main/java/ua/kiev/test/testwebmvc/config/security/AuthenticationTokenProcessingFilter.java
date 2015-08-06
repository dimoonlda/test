package ua.kiev.test.testwebmvc.config.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ua.kiev.test.testwebmvc.model.SecurityUser;
import ua.kiev.test.testwebmvc.utils.TokenUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Component
public class AuthenticationTokenProcessingFilter  extends GenericFilterBean {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);

    @Resource(name = "whiteList")
    private List<String> whitelist;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String token = request.getHeader(TokenUtils.USER_TOKEN);
        log.debug(String.format("Incoming token(%s)", token));

        if (isFromWhiteList(request.getPathInfo())) {
            // skipp authentication process
            chain.doFilter(request, response);
        } else {
            // determine the user based on the (already validated) token
            SecurityUser user = null;
            try {
                user = tokenUtils.getUserByToken(token);
            } catch(UsernameNotFoundException e) {
                // no user found for token
                redirectToLoginFailed(request, response);
                return;
            }

            if(!tokenUtils.validate(user, token)){
                // validation failed, removed expired token
                if (tokenUtils.isNotExpired(user)) {
                    redirectToLoginFailed(request, response);
                    return;
                } else {
                    redirectToLoginExpired(request, response);
                    return;
                }
            } else {

                // build an Authentication object with the user's info
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                        user.getPassword(), user.getAuthorities());
                authentication.setDetails(user);
                // set the authentication into the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // continue through the filter chain
                log.debug(String.format("Authenticated user(%s)", user.getLogin()));
                chain.doFilter(request, response);
            }
        }
    }

    private void redirectToLoginFailed(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String urlString = "/auth/failed";
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect(urlString);
    }

    private void redirectToLoginExpired(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String urlString = "/auth/expired";
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect(urlString);
    }

    private boolean isFromWhiteList(String servletPath) {
        for (String item : this.whitelist) {
            if(servletPath.equals(item) ||
                    (item.endsWith("*") && servletPath.startsWith(item.substring(0, item.length()-1)))){
                return true;
            }
        }
        return false;
    }
}
