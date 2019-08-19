package org.winterframework.sso.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.winterframework.sso.authc.UsernamePasswordToken;
import org.winterframework.sso.exception.IncorrectCredentialsException;
import org.winterframework.sso.exception.UnknownAccountException;
import org.winterframework.sso.authc.SecurityManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YHG
 * @Date: 2019/8/17 11:29
 */
@Controller
public class WebController {

    @Resource
    private SecurityManager securityManager;

    @GetMapping(value = "/login")
    public String login(String from, HttpServletRequest request, HttpServletResponse response){
        if (from!=null){
            request.setAttribute("redirect_url", from);
            String token;
            if ((token = securityManager.loginCheck(request, response))!=null) {
                return "redirect:" + from + "?ssoSessionId=" + token;
            }
        }
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(String username, String password, HttpServletRequest request,
                            HttpServletResponse response){
        UsernamePasswordToken toke = new UsernamePasswordToken(username, password);
        String ssoSessionId;
        try {
            ssoSessionId = securityManager.login(toke, request, response);
        }catch (UnknownAccountException e){
            request.setAttribute("error", "用户不存在");
            return "redirect:/login?from=" + request.getParameter("redirect_url");
        }catch (IncorrectCredentialsException e){
            request.setAttribute("error", "密码错误");
            return "redirect:/login?from=" + request.getParameter("redirect_url");
        }
        String redirectUrl = request.getParameter("redirect_url") + "?ssoSessionId=" + ssoSessionId;
        return "redirect:" + redirectUrl;
    }

}
