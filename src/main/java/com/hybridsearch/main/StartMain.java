package com.hybridsearch.main;
import com.google.gson.Gson;
import com.hybridsearch.model.IndexConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;



/**
 * @Author Anthony Z.
 * @Date 21/12/2022
 * @Description:
 */
public class StartMain {

    private Logger logger = LoggerFactory.getLogger(StartMain.class);
    private String zookeeper;
    private String kafkaTopic;
    private int kafkaConsumerThreadNum;
    private int monitorSchedule = 1;

    /**
     * read args and configure kafka
     * @param args
     */
    public void init(String[] args){
        boolean passed = false;
        IndexConfig config = null;
        if(args.length > 0){
            String configJson = readString(args[0]);
            System.out.println(configJson);

            Gson gson = new Gson();
            kafkaTopic = config.kafkaTopic;
            kafkaConsumerThreadNum = config.kafkaConsumerThreadNum;
            monitorSchedule = config.monitorSchedule;
            passed = true;
        }

        if(!passed){
            logger.info("No config file given as the first argument. ");
            System.exit(1);
        }

        zookeeper = System.getenv("ZOOKEEPER_CONNECT");
        if(zookeeper == null || zookeeper.equals("")){
            zookeeper = config.zookeeper_address;
        }

    }

    private String readString(String filePath){
        String text = "";
        File file = new File(filePath);
        try{
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            char[] buffer = new char[512];
            int count;
            StringBuilder builder = new StringBuilder();

            while((count = reader.read(buffer)) != -1){
                builder.append(buffer, 0, count);
            }

            text = builder.toString();
            reader.close();
            fis.close();

        }catch (IOException ex){
            ex.printStackTrace();
        } finally {
            file = null;
        }

        return text;
    }


    public void startKafka(){

    }
    public void initFeature(){

    }

    public void startServer() throws Exception{
        logger.info("Starting server...");


    }
}

