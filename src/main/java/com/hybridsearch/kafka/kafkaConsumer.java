package com.hybridsearch.kafka;


import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @Author Anthony HE, anthony.zj.he@outlook.com
 * @Date 30/12/2022
 * @Description:
 */
public class kafkaConsumer implements Runnable{


    private KafkaConsumer<String, String> consumer;
    private String topic;
    private int numThreads;


    public kafkaConsumer(String topic, int numThreads, String zookeeper, String groupID){
//        this.consumer = new KafkaConsumer();
    }
    @Override
    public void run() {

    }
}
