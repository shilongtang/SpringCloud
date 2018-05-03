package com.example.sqlcompute.utils;

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
