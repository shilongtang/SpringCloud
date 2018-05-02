package com.example.sqlcompute.utils;

import org.springframework.stereotype.Component;


@Component
public class PostgreSQLPageSql implements PageSql {

  @Override
  public String buildPage(String sql) {
    return "select * from ( " + sql + " ) as tab limit ? offset ?";
  }

  @Override
  public String buildCount(String sql) {
    return "select count(1) from (" + sql + ") as tab";
  }
}
