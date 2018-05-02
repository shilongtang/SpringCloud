package com.example.sqlcompute.utils;

/**
 * user: pkd
 * data: 2017/7/15
 * time: 15:12
 */
public interface PageSql {

  String buildPage(String sql);

  String buildCount(String sql);
}
