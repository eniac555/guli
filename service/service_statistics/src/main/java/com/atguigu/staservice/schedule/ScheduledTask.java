package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //  0/5 * * * * ?  表示每隔5秒执行一次

    //在每天凌晨一点，把前一天的数据进行添加，执行数据查询和添加
    @Scheduled(cron = "0 0 1 * * ? ")
    public void task1(){
        statisticsDailyService.registerCount(
                DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
