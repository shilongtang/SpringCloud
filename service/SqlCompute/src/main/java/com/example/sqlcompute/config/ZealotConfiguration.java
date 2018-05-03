package com.example.sqlcompute.config;

import com.blinkfox.zealot.bean.XmlContext;
import com.blinkfox.zealot.config.AbstractZealotConfig;

public class ZealotConfiguration extends AbstractZealotConfig {

  public void configXml(XmlContext xmlContext) {

    xmlContext.add("test", "sql/test.xml");
  }

  public void configTagHandler() {

  }
}
