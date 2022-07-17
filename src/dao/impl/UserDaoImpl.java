package dao.impl;

import dao.UserDao;
import domain.Admin;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDs());
    @Override
    public List<User> findAll() {
        //使用jdbc操作数据库
        //定义sql
        String sql="select *from user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public Admin login(Admin admin) {
        try{
            //定义sql
            String sql="select *from admin where adminname= ? and password = ?";
            //执行sql
            Admin loginAdmin = template.queryForObject(sql,
                    new BeanPropertyRowMapper<Admin>(Admin.class),
                    admin.getAdminname(), admin.getPassword());
            return loginAdmin;
        }catch (DataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        //定义sql
        String sql="insert into user values(null,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,user.getName(),
                user.getGender(),
                user.getAge(),
                user.getAddress(),
                user.getQq(),
                user.getEmail());
    }

    @Override
    public void deleteUser(int id) {
        String sql="delete from user where id=?";
        template.update(sql,id);
    }

    @Override
    public User findUserById(int id) {
        String sql="select *from user where id=?";
        User user = template.queryForObject(sql,
                new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql="update user set gender=?,age=?,address=?,qq=?,email=? where id = ?";
        template.update(sql,
                user.getGender(),
                user.getAge(),
                user.getAddress(),
                user.getQq(),
                user.getEmail(),
                user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql="select count(*) from user where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数集合
        List<Object> params = new ArrayList<>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String values = condition.get(key)[0];
            //判断
            if (values !=null && !"".equals(values)){
                sb.append("and "+ key +" like ? ");
                params.add("%"+values+"%");//?条件的值
            }
        }
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql="select *from user where 1 = 1 ";
        StringBuilder sb=new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数集合
        List<Object> params = new ArrayList<>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String values = condition.get(key)[0];
            //判断
            if (values !=null && !"".equals(values)){
                sb.append("and "+ key +" like ? ");
                params.add("%"+values+"%");//?条件的值
            }
        }
        //添加分页
        sb.append(" limit ?,? ");
        //添加分页查询参数
        params.add(start);
        params.add(rows);
        return template.query(sb.toString(),
                new BeanPropertyRowMapper<User>(User.class),
                params.toArray());
    }

}
