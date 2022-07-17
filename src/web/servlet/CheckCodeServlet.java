package web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/CheckCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 100;
        int height=50;
        //创建对象，在内存中的图片(验证码的图片对象)
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //美化图片
        //填充背景色
        Graphics graphics = image.getGraphics();//画笔对象
        graphics.setColor(Color.pink);//设置画笔颜色
        graphics.fillRect(0,0,width,height);//填充矩形
        //画边框
        graphics.setColor(Color.BLUE);
        graphics.drawRect(0,0,width-1,height-1);//画矩形,因为有宽度，所以宽高减一
        //写验证码
        //随机生成验证码
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random r=new Random();
        StringBuilder sb=new StringBuilder();
        for (int i=1;i<=4;i++){
            int index = r.nextInt(str.length());
            char c = str.charAt(index);
            sb.append(c);
            graphics.drawString(c+"",width/5*i,height/2);
        }
        String checkCode = sb.toString();
        //将验证码存入session
        HttpSession session = request.getSession();
        session.setAttribute("checkCode",checkCode);
        //画干扰线
        graphics.setColor(Color.GREEN);
        //随机生成坐标点
        for (int i=0;i<10;i++){
            int x1 = r.nextInt(width);
            int x2 = r.nextInt(width);
            int y1 = r.nextInt(height);
            int y2 = r.nextInt(height);
            graphics.drawLine(x1,y1,x2,y2);
        }

        //将图片输出到页面展示
        ImageIO.write(image,"jpg",response.getOutputStream());
    }
}
