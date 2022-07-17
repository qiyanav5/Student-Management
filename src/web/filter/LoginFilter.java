package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
    登陆验证
*/
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //强制转换，要不就没有getRequestURI()方法
        HttpServletRequest req =(HttpServletRequest) request;
        //获取资源的请求路径
        String uri = req.getRequestURI();
        //判断是否包含登录相关资源路径，要注意排除掉css，js，图片，验证码等资源
        if (uri.contains("/login.jsp") || uri.contains("/LoginServlet") ||uri.contains("/css/")||uri.contains("/js/")
        ||uri.contains("/fonts/") ||uri.contains("/CheckCodeServlet")){
            //包含就证明用户要登录。放行
            chain.doFilter(request, response);
        }else {
            //不登陆就需要验证用户是否登录
            //因为登陆时会在session中存admin，所以判断session中有没有admin就可以
            Object user = req.getSession().getAttribute("admin");
            if (user != null){
                //登陆了，放行
                chain.doFilter(request, response);
            }else {
                //没登陆，跳转登陆界面
                req.setAttribute("login_msg","尚未登陆，请登录");
                req.getRequestDispatcher("/login.jsp").forward(req,response);
            }
        }



    }
}
