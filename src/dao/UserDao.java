package dao;

import domain.Admin;
import domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    List<User> findAll();

    Admin login(Admin admin);

    void addUser(User user);

    void deleteUser(int id);

    User findUserById(int id);

    void updateUser(User user);

    //查询总记录数
    int findTotalCount(Map<String, String[]> condition);

    //分页查询每页记录
    List<User> findByPage(int start, int parseInt, Map<String, String[]> condition);
}
