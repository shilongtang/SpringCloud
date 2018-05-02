package com.example.sqlcompute.utils;

import java.util.List;
import java.util.stream.Collectors;

public class ResultPageable {
    private int pageNumber; //当前页数, 从1开始
    private int pageSize;   //每页显示的记录数
    private int totalCount;  //总记录数
    private int totalPage;  //总页数
    private String list; //每页显示数据记录的集合

    public ResultPageable(int pageNumber, int pageSize, int totalCount, int totalPage, String list){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.list = list;
    }

    public String toJson(){
        StringBuilder json = new StringBuilder();
        json.append("{")
                .append("\"totalCount\":").append(totalCount).append(",")
                .append("\"totalPage\":").append(totalPage).append(",")
                .append("\"pageNumber\":").append(pageNumber).append(",")
                .append("\"pageSize\":").append(pageSize).append(",")
                .append("\"list\":")
                .append(list)
                .append("}");
        return json.toString();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setList(List<String> list) {
        this.list = list.toString();
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
