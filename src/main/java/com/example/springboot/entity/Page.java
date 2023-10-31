package com.example.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yangbohan
 * @Date 2023/10/31 21:24
 */

@Data
public class Page implements Serializable {

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private int total;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 数据
     */
    private List data;

    /**
     * 上一页
     */
    private int prePage;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 是否有上一页
     */
    private boolean hasPrePage;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 页码
     */
    private int[] pageNumbers;

    public Page(int total) {
        this(1, 5, total, null);
    }

    public Page(int pageNum, int pageSize, int total) {
        this(pageNum, pageSize, total, null);
    }

    public Page(int pageNum, int pageSize, int total, List list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.data = list;
    }

    public int getTotalPage() {
        return (int)Math.ceil(total / 1.0 / pageSize);
    }

    public int getPrePage() {
        return pageNum - 1;
    }

    public int getNextPage() {
        return pageNum + 1;
    }

    public boolean getHasPrePage() {
        return pageNum != 1;
    }

    public boolean getHasNextPage() {
        return pageNum != getTotalPage();
    }

    public int[] getPageNumbers() {
        int start = 1; // 起始页码
        int end = getTotalPage(); // 结束页码
        // 页码显示 5 个
        if (getTotalPage() > 5) {

            start = pageNum - 2;
            end = pageNum + 2;
            // 修正
            if (start < 1) {
                start = 1;
                end = 5;
            }
            // 修正
            if (end > getTotalPage()) {
                end = getTotalPage();
                start = end - 4;
            }
        }
        int[] pageNumbers = new int[end - start + 1];
        for (int i = 0; i < pageNumbers.length; i++) {
            pageNumbers[i] = start + i;
        }
        return pageNumbers;
    }
}