package com.example.springboot.dao;

import com.example.springboot.entity.FileModel;
import com.example.springboot.util.DBHelper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yangbohan
 * @Date 2023/10/26 20:10
 */

@Repository
public class FileDao {

    private DBHelper db = new DBHelper();


    public List<FileModel> selectAll() throws SQLException {
        String sql = "select * from file";
        return db.selectList(sql, FileModel.class);
    }


    public int insert(FileModel model) throws SQLException {
        String sql = "insert into file(name, path, size, type, create_user) values(?,?,?,?,?)";
        return db.update(sql, model.getName(), model.getPath(), model.getSize(), model.getType(), model.getCreateUser());
    }
}
