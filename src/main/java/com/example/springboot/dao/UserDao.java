package com.example.springboot.dao;

import com.example.springboot.entity.User;
import com.example.springboot.util.DBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangbohan
 * @Date 2023/10/23 23:50
 */

public class UserDao {
    private DBHelper dbHelper = new DBHelper();

    public User selectByUserAndPassword(String username, String password) throws SQLException {
        String sql = "select  id,user_name, password,nick_name,avatar from sys_user where user_name = ? and password = ?";
        return dbHelper.selectOne(sql, User.class, username, password);
    }

    public User selectByUsername(String username) throws SQLException {
        String sql = "select id,user_name,nick_name from sys_user where user_name = ?";
        return dbHelper.selectOne(sql, User.class, username);
    }

    /**
     * 插入用户数据，返回影响行数
     */
    public int insert(User user) throws SQLException {
        String sql = "insert into sys_user(user_name,password,nick_name) values (?,?,?)";
        return dbHelper.update(sql, user.getUserName(), user.getPassword(), user.getNickName());
    }

    /**
     * 插入用户数据，返回自增主键
     */
    public int insertByGeneralKey(User user) throws SQLException {
        String sql = "insert into sys_user(user_name,password,nick_name) values (?,?,?)";
        return dbHelper.insert(sql, user.getUserName(), user.getPassword(), user.getNickName());
    }

    public List<User> selectAll() throws SQLException {
        String sql = "select id,user_name,nick_name from sys_user";
        return dbHelper.selectList(sql, User.class);
    }

    public User selectById(Integer id) throws SQLException {
        String sql = "select id, user_name, nick_name from sys_user where id = ?";
        return dbHelper.selectOne(sql, User.class, id);
    }

    public int updateById(User user) throws SQLException {
        StringBuilder sql = new StringBuilder("update sys_user set ");
        List params = new ArrayList();
        if (user.getUserName() != null) {
            sql.append("user_name = ?, ");
            params.add(user.getUserName());
        }
        if (user.getNickName() != null) {
            sql.append("nick_name = ?, ");
            params.add(user.getNickName());
        }
        if (user.getAvatar() != null){
            sql.append("avatar = ?, ");
            params.add(user.getAvatar());
        }
        // 其他字段同理

        // 最后会多一个 , 需要去掉
        if (sql.toString().endsWith(", ")) {
            sql.delete(sql.length() - 2, sql.length());
        }

        sql.append(" where id = ?");
        params.add(user.getId());

        return dbHelper.update(sql.toString(), params.toArray());
    }

    public int deleteById(Integer id) throws SQLException {
        String sql = "delete from sys_user where id = ?";
        return dbHelper.update(sql, id);
    }
}
