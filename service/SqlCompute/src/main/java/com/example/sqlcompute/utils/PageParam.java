package com.example.sqlcompute.utils;


import com.gi.service.extend.dto.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * user: pkd
 * data: 2017/7/15
 * time: 15:21
 */
public class PageParam {

  public static Object[] build(Object[] params, Pageable pageable) {
    List<Object> list = new ArrayList<>();
    Collections.addAll(list, params);
    list.add(pageable.getPageSize());
    list.add((pageable.getPageNumber() - 1) * pageable.getPageSize());
    return list.toArray(new Object[list.size()]);
  }
}
