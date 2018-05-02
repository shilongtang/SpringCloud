package com.example.sqlcompute.utils;


/**
 * user: pkd
 * data: 2017/7/15
 * time: 14:56
 */
public class ResponseData<T> {

  private T data;
  private Pageable pageable;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Pageable getPageable() {
    return pageable;
  }

  public void setPageable(Pageable pageable) {
    this.pageable = pageable;
  }
}
