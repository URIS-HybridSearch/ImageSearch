package com.hybridsearch.model;

/**
 * @Author Anthony HE, anthony.zj.he@outlook.com
 * @Date 2/1/2023
 * @Description:
 */
public class IndexConfig {
    public String zookeeper_address = "";
    public String kafkaTopic = "";
    public int kafkaConsumerThreadNum;
    public int monitorSchedule;
}
