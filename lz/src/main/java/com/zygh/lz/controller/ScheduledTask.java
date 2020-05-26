package com.zygh.lz.controller;/*
package com.zygh.lz.controller;

import com.zygh.lz.admin.Patrolrecord;
import com.zygh.lz.mapper.PatrolrecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduledTask {

    @Autowired
    private PatrolrecordMapper patrolrecordMapper;

    //五秒下线一次
    @Scheduled(fixedRate = 5000)
    private void one() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(new Date());
        patrolrecord.setSysPatrolRecordId(3);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("111111111111执行静态定时任务时间: " + LocalDateTime.now());
    }
    //十秒
    @Scheduled(fixedRate = 6000)
    private void two() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(new Date());
        patrolrecord.setSysPatrolRecordId(51);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("22222222222222执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 7000)
    private void three() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(new Date());
        patrolrecord.setSysPatrolRecordId(102);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("55555555555555执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 8000)
    private void four() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(new Date());
        patrolrecord.setSysPatrolRecordId(103);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("840执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 9000)
    private void five() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(new Date());
        patrolrecord.setSysPatrolRecordId(89);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("333333333333执行静态定时任务时间: " + LocalDateTime.now());
    }

    //五秒下线一次
    @Scheduled(fixedRate = 12000)
    private void one1() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(null);
        patrolrecord.setSysPatrolRecordId(3);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("111111111111执行静态定时任务时间: " + LocalDateTime.now());
    }
    //十秒
    @Scheduled(fixedRate = 13000)
    private void two1() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(null);
        patrolrecord.setSysPatrolRecordId(51);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("22222222222222执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 14000)
    private void three1() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(null);
        patrolrecord.setSysPatrolRecordId(102);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
       // System.err.println("55555555555555执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 15000)
    private void four1() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(null);
        patrolrecord.setSysPatrolRecordId(103);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("840执行静态定时任务时间: " + LocalDateTime.now());
    }

    @Scheduled(fixedRate = 17000)
    private void five1() {
        Patrolrecord patrolrecord = new Patrolrecord();
        patrolrecord.setPatrolRecordEndtime(null);
        patrolrecord.setSysPatrolRecordId(89);
        patrolrecordMapper.updateByPrimaryKeySelectives(patrolrecord);
        //System.err.println("333333333333执行静态定时任务时间: " + LocalDateTime.now());
    }


}*/
