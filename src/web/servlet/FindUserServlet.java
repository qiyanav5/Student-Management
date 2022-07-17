package web.servlet;

import domain.User;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/FindUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        String id = request.getParameter("id");
        //调用service查询
        User user = new UserServiceImpl().findUserById(id);
        //存储数据
        request.setAttribute("user",user);
        //转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request,response);
    }
}
