package com.example.eurekaclient.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 10 16 * * ?" )
    public void reportCurrentTime1() {
        System.out.println("每天定时任务：" + dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

    /**
     * 第一位，表示秒，取值0-59
     * 第二位，表示分，取值0-59
     * 第三位，表示小时，取值0-23
     * 第四位，日期天/日，取值1-31
     * 第五位，日期月份，取值1-12
     * 第六位，星期，取值1-7，1表示星期天，2表示星期一
     * 第七位，年份，可以留空，取值1970-2099
     */
   /** (*)星号：可以理解为每的意思，每秒，每分，每时，每日，每月，每星期，每年...
    *  (?)问号：问号只能出现在日和星期这两个位置，表示这个位置的值不确定，每天3点执行，所以第六位星期的位置，我们是不需要关注的，就是不确定的值。同时：日和星期是两个相互排斥的元素，通过问号来表明不指定值。比如，1月10日，比如是星期1，如果在星期的位置是另指定星期二，就前后冲突矛盾了。
    *  (-)减号：表达一个范围，如在小时字段中使用“10-12”，则表示从10到12点，即10,11,12
    *  (,)逗号：表达一个列表值，如在星期字段中使用“1,2,4”，则表示星期一，星期二，星期四
    *  (/)斜杠：如：x/y，x是开始值，y是步长，比如在第一位（秒） 0/15就是，从0秒开始，每15秒，最后就是0，15，30，45，60 另：* /y，等同于0/y
    *  (L)字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期
    *  (W)字符：指定离给定日期最近的工作日(周一到周五)
    *  (#)字符：表示该月第几个周X。6#3表示该月第3个周五"
    *  0 0 3 * * ?     每天3点执行
    *  0 5 3 * * ?     每天3点5分执行
    *  0 5 3 ? * *     每天3点5分执行，与上面作用相同
    *  0 5/10 3 * * ?  每天3点的 5分，15分，25分，35分，45分，55分这几个时间点执行
    *  0 10 3 ? * 1    每周星期天，3点10分 执行，注：1表示星期天
    *  0 10 3 ? * 1#3  每个月的第三个星期，星期天执行，#号只能出现在星期的位置
    *  cron="0 40 19 * * ?"
    */

}

