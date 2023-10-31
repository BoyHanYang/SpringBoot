package com.example.springboot.service;

import com.example.springboot.dao.UserDao;
import com.example.springboot.entity.User;
import com.example.springboot.ex.RegisterException;
import com.example.springboot.ex.ServiceException;
import com.example.springboot.util.MD5Util;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yangbohan
 * @Date 2023/10/24 0:02
 */

public class UserService {
    private UserDao userDao = new UserDao();
    public  User login(String username, String password) {
        if (!StringUtils.hasText(username) || (!StringUtils.hasText(password))) {
            return null;
        }
        password = MD5Util.encodePassword(password,username);
        try {
            return userDao.selectByUserAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void signUp(User user,String repass) throws RegisterException {
        if (ObjectUtils.isEmpty(user)||!StringUtils.hasText(user.getUserName())||
                !StringUtils.hasText(user.getPassword())||
        !StringUtils.hasText(repass)){
            throw new RegisterException(0,"参数校验失败");
        }
        // 校验两次密码是否一致

        if (!user.getPassword().equals(repass)){
            throw new RegisterException(2,"两次密码不一致");
        }

        try {
            User user1 = userDao.selectByUsername(user.getUserName());
            if (user1!=null){
                throw new RegisterException(1,"用户名已存在");
            }
            String encodePassword = MD5Util.encodePassword(user.getPassword(),user.getNickName());
            user.setPassword(encodePassword);
            int row =  userDao.insert(user);
            if (row!=1){
                throw new RegisterException(3,"未知错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RegisterException(3,"数据库异常");
        }
    }
    public List<User> list(){
        try {
            return userDao.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getById(Integer id){
//        System.out.println("getById===========>>>>>>>>>>"+id);
        if (id == null){
            return null;
        }
        try {
            return userDao.selectById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(User user) throws ServiceException {
        if (user == null || user.getId() == null){
            throw new ServiceException("修改用户信息：参数校验失败");
        }
        try {
            int rows = userDao.updateById(user);
            if (rows != 1){
                throw new ServiceException("修改用户信息：数据操作失败");
            }
        } catch (SQLException e) {
            throw new ServiceException("修改用户信息：数据库异常", e);
        }
    }

    public void delete(Integer id) throws ServiceException {
        if (id == null){
            throw new ServiceException("删除用户信息：参数校验失败");
        }
        try {
            int rows = userDao.deleteById(id);
            if ( rows != 1){
                throw new ServiceException("删除用户信息：数据操作失败");
            }
        } catch (SQLException e) {
            throw new ServiceException("删除用户信息：数据库异常", e);
        }
    }
}
