package com.hybridsearch.kafka;

import com.google.gson.Gson;
import com.hybridsearch.model.IndexMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author Anthony HE, anthony.zj.he@outlook.com
 * @Date 8/1/2023
 * @Description:
 */
public class KafkaProcessorMultiThread implements Runnable{

    private List<IndexMessage> indexMessageList = new ArrayList<>();
    private List<IndexMessage> videoIndexMessage = new ArrayList<>();
    private Map<String, List<IndexMessage>> videoIndexMessageMap = new ConcurrentHashMap<>();
    private Gson gson;
    private LinkedBlockingQueue<String> imageFeatures;
    private long start = 0;
    private long videoStart = 0;
    private Logger logger = LoggerFactory.getLogger(KafkaProcessorMultiThread.class);


    public KafkaProcessorMultiThread(LinkedBlockingQueue imageFeatures){
        this.gson = new Gson();
        this.imageFeatures = imageFeatures;
    }

    public void process(String message){
        if(start == 0){
            start = System.currentTimeMillis();
        }
        if(videoStart == 0){
            videoStart = System.currentTimeMillis();
        }
        logger.info("Receive one Kafka message");
        if(message.startsWith("[{") && message.endsWith("}]")){
            IndexMessage[] indexMessages = gson.fromJson(message, IndexMessage[].class);
            for(IndexMessage indexMessage : indexMessages){
                if(indexMessage.getVideoName() == null){
                    indexMessageList.add(indexMessage);
                }else{
                    String videoName = indexMessage.getVideoName();
                    if(videoIndexMessageMap.containsKey(videoName)){
                        videoIndexMessageMap.get(videoName).add(indexMessage);
                    }else{
                        List<IndexMessage> indexMessageList1 = new ArrayList<>();
                        indexMessageList1.add((indexMessage));
                        videoIndexMessageMap.put(videoName, indexMessageList1);
                    }
                }
            }

            logger.info("Received array list size " + indexMessages.length);
        }else{

        }
    }
    @Override
    public void run() {

    }
}
