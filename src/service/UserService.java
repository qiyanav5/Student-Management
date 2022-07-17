package service;

import domain.Admin;
import domain.Page;
import domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询所有用户信息
    List<User> findAll();

    //登录
    Admin login(Admin admin);

    //添加用户
    void addUser(User user);

    //删除用户
    void deleteUser(String id);

    //查找用户
    User findUserById(String id);

    //修改
    void updateUser(User user);

    //删除选中
    void DeleteSelectedUser(String[] uids);

    //分页查询/分页条件查询
    Page<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
