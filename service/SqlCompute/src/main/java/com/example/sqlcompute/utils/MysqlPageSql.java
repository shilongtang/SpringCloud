package com.example.sqlcompute.utils;

/**
 * user: pkd
 * data: 2017/7/15
 * time: 15:14
 */
public class MysqlPageSql implements PageSql {


  @Override
  public String buildPage(String sql) {
    return "select * from ( " + sql + " ) as tab limit ?,?";
  }

  @Override
  public String buildCount(String sql) {
    return "select count(1) from (" + sql + ") as tab";
  }
}
