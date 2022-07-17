package web.servlet;

import domain.Admin;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //创建对象
        Admin loginAdmin = new Admin();
        //获取数据
        String adminname = request.getParameter("adminname");
        String password = request.getParameter("password");
        String login_checkCode = request.getParameter("checkCode");
        //获取生成的验证码
        HttpSession session = request.getSession();
        String checkCode = (String)session.getAttribute("checkCode");
        //获取完就删除
        session.removeAttribute("checkCode");
        //封装数据
        loginAdmin.setAdminname(adminname);
        loginAdmin.setPassword(password);
        //判断
        //先判断验证码是否正确
        if (checkCode!=null && checkCode.equalsIgnoreCase(login_checkCode)){
            //验证码正确
            //调用service查询数据
            Admin admin = new UserServiceImpl().login(loginAdmin);
            if (admin!=null && loginAdmin!=null &&
                    admin.getAdminname().equals(loginAdmin.getAdminname())&&
                    admin.getPassword().equals(loginAdmin.getPassword())){
                //登陆成功
                //存储信息
                session.setAttribute("admin",admin);
                //重定向
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }else {
                //登陆失败
                request.setAttribute("login_error","用户名或密码错误");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        }else {
            //验证码错误
            request.setAttribute("checkCode_error","验证码错误");
            //转发到登陆页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }


    }
}
