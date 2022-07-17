package web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import java.util.*;

/*
    敏感词汇过滤器
*/
@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {
    private List<String> list=new ArrayList<>();
    public void init(FilterConfig config) throws ServletException {
        try {
            //获取文件真实路径
            ServletContext servletContext = config.getServletContext();
            //见Day12_response下servletContest包下ContextDemo03
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            //读取文件
            BufferedReader br=new BufferedReader(new FileReader(realPath, Charset.forName("utf-8")));
            String line=null;
            while ((line=br.readLine()) !=null){
                list.add(line);
            }
            br.close();
//            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //创建代理对象，增强getParameter方法
        ServletRequest proxy_request= (ServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //判断是否是getParameter方法
                if (method.getName().equals("getParameter")){
                    //增强返回值
                    //获取返回值
                    String value = (String) method.invoke(request, args);
                    if (value!=null){
                        //有返回值
                        for (String s : list) {
                            if (value.contains(s)){
                                value=value.replaceAll(s,"***");
                            }
                        }
                    }
                    return value;
                }

                //判断方法名是否是getParameterMap
                if (method.getName().equals("getParameterMap")) {
                    //增强返回值
                    //获取返回值
                    //注意要新建一个map，要不然会发生错误
                    Map<String, String[]> map = new HashMap<>((Map<String, String[]>) method.invoke(request, args));
                    //获得键值对集合
                    Set<Map.Entry<String, String[]>> entries = map.entrySet();
                    //遍历键值对集合
                    for (Map.Entry<String, String[]> entry : entries) {
                        //如果不为空
                        if (entry != null) {
                            //循环判断每一个键值对
                            for (int i = 0; i < entries.size(); i++) {
                                for (String s : list) {
                                    //值是一个数组
                                    String[] value = entry.getValue();
                                    //判断数组中元素
                                    for (int j=0;j<value.length;j++){
                                        if (value[j].contains(s)) {
                                            //替换
                                            value[j]=value[j].replaceAll(s,"***");
                                            //替换map
                                            map.replace(entry.getKey(),value);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return map;
                }
                //判断方法名是否是getParameterValue
                if (method.getName().equals("getParameterValue")){
                    //复制一个一样的数组，但是地址不同，内容相同
                    String[] str = ((String[]) method.invoke(request, args)).clone();
                    for (int i=0;i<str.length;i++){
                        for(String s:list){
                            if (str[i].contains(s)){
                                str[i]=str[i].replaceAll(s,"***");
                            }
                        }
                    }
                    return str;
                }
                return method.invoke(request,args);
            }
        });
        //放行
        chain.doFilter(proxy_request, response);
    }
}
