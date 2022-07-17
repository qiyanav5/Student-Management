package web.servlet;

import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/DeleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取所有的选中的id
        String[] uids = request.getParameterValues("uid");
        //调用service删除
        new UserServiceImpl().DeleteSelectedUser(uids);
        //跳转
        response.sendRedirect(request.getContextPath()+"/FindUserByPageServlet");
    }
}
