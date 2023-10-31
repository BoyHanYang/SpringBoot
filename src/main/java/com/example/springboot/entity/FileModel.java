package com.example.springboot.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author yangbohan
 * @Date 2023/10/26 20:11
 */

@Data
public class FileModel implements Serializable {

    /**
     * 文件编号
     */
    private Integer id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 搜索时查询的日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchDate;


    /**
     * 文件创建时间
     * @DateTimeFormat 将字符串转换成指定格式的 Date 对象
     *
     */
    private LocalDateTime createTime;

    /**
     * 文件创建人
     */
    private Integer createUser;
}