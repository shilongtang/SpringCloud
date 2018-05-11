package com.example.useraccount.utils;

/**
 * model: pkd
 * data: 2017/1/3
 * time: 9:58
 */
public class Pageable {

  private int pageNumber = 1; //当前页数, 从1开始
  private int pageSize = 10;   //每页显示的记录数
  private int totalCount;  //总记录数
  private int totalPage;  //总页数

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
    int i = this.totalCount / this.pageSize;
    return (this.totalCount % this.pageSize) == 0 ? i : (i + 1);
  }

  public boolean check() {
    return (pageNumber - 1) * pageSize < totalCount;
  }
}
