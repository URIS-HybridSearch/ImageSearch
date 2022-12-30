package com.hybridsearch.main;

import java.util.logging.Logger;

/**
 * @Author Anthony Z.
 * @Date 21/12/2022
 * @Description:
 */
public class StartMain {
    Logger logger = new Logger(StartMain.class.getName());

    private String zookeeper;
    private String kafkaTopic;
    private int kafkaThreadNum;
    private int monitorSchedule = 1;

    public void init(String[] args){

    }
}
