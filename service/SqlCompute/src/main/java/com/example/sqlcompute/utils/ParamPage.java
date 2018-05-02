package com.example.sqlcompute.utils;


/**
 * user: pkd
 * data: 2017/7/15
 * time: 15:02
 */
public class ParamPage extends Param {

  private Pageable pageable;

  public Pageable getPageable() {
    return pageable;
  }

  public void setPageable(Pageable pageable) {
    this.pageable = pageable;
  }

}
