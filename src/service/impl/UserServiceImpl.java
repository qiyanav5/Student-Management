package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import domain.Admin;
import domain.Page;
import domain.User;
import service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Admin login(Admin admin) {
        return userDao.login(admin);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(String id) {
        userDao.deleteUser(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return userDao.findUserById(Integer.parseInt(id));
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void DeleteSelectedUser(String[] uids) {
        if (uids !=null && uids.length>0){
            //遍历数组
            for (String uid : uids) {
                userDao.deleteUser(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public Page<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition) {
        //创建新的Page对象
        Page<User> page = new Page<>();
        //设置参数
        int cp = Integer.parseInt(currentPage);
        int rs = Integer.parseInt(rows);
        page.setCurrentPage(cp);
        page.setRows(rs);

        //调用dao查询总记录数
        int totalCount = userDao.findTotalCount(condition);
        page.setTotalCount(totalCount);
        //调用dao查询list集合
        //计算开始的记录索引
        int start=(cp -1)*rs;
        List<User> list=userDao.findByPage(start,rs,condition);
        page.setList(list);
        //计算总页码
        int totalPage = (totalCount % rs) == 0 ? (totalCount/rs) : (totalCount/rs) +1 ;
        page.setTotalPage(totalPage);
        return page;
    }
}
