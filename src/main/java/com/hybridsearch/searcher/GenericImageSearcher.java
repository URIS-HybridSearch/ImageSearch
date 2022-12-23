package com.hybridsearch.searcher;

import com.hybridsearch.feature.HybridFeature;
import com.hybridsearch.model.DocumentModel;
import com.hybridsearch.model.FeatureCacheModel;
import com.hybridsearch.model.SearchResult;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import net.semanticmetadata.lire.indexers.parallel.ExtractorItem;
import net.semanticmetadata.lire.searchers.SimpleResult;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * @Author Anthony Z.
 * @Date 21/12/2022
 * @Description:
 */
public class GenericImageSearcher {

    protected Logger logger = Logger.getLogger(getClass().getName());
    protected String fieldName;
    protected LireFeature cachedInstance = null;
    protected ExtractorItem extractorItem;
    public static ConcurrentHashMap<String, LinkedHashMap<String, byte[]>> features = new ConcurrentHashMap<>();
    public static LinkedHashMap<String, byte[]> todayFeatures = new LinkedHashMap<>();
    protected IndexReader reader = null;

    protected int maxHits = 20; // dont know
    protected double threshold = 0.0;
    protected TreeSet<SearchResult> results = new TreeSet<>(); // sorted set
    protected volatile double maxDistance;
    private double minDistance;

    protected LinkedBlockingQueue<Map.Entry> queue = new LinkedBlockingQueue<>(100);
    protected int numThreads = DocumentBuilder.NUM_OF_THREADS;

    private HybridFeature hybridFeature = null;
    protected LinkedBlockingQueue<DocumentModel> linkedBlockingQueue = new LinkedBlockingQueue<>();
    protected LinkedBlockingQueue<FeatureCacheModel> featureCacheModels = new LinkedBlockingQueue<>();
//    protected ConcurrentSkipListSet<SimpleResult> concurrentSkipListSet
//    protected ConcurrentSkipListSet<SearchResult> searchResults
    protected int numThreadsQueue = DocumentBuilder.NUM_OF_THREADS;
    protected int numThreadProducer = 16;
    protected int producerFlag = 0;


    class DocumentProducer implements Runnable{

        String threadName;
        IndexReader reader = null;
        int startIndex = 0;
        int endIndex = 0;

        private DocumentProducer(IndexReader reader){
            this.reader = reader;
        }

        private DocumentProducer(int id, IndexReader reader, int startIndex, int endIndex){
            this.threadName = "producer-" + id;
            this.reader = reader;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            if(reader == null){
                return;
            }

            for(int i = startIndex; i<endIndex; i++){
                try{
                    linkedBlockingQueue.add(new DocumentModel(i, reader.document(i)));
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            producerFlag++;
            if(producerFlag == numThreadProducer){
                for(int i = 1; i<numThreadsQueue*3; i++){
                    // 为什么这里是-i呀?
                    linkedBlockingQueue.add(new DocumentModel(-i, new Document()));
                }
            }
        }
    }

    class DocumentConsumer implements Runnable{

        private boolean consumerFinished = false;
        private String threadName = "";
        public DocumentConsumer(String threadName){
            this.threadName = threadName;
        }
        @Override
        public void run() {
            Document tmpDocment;
            int tmpId;
            DocumentModel tempDocumentModel;
            double tmpDistance;
            double tmpMaxDistance;

            while(!consumerFinished){
                try{
                    tempDocumentModel = linkedBlockingQueue.take();
                    tmpDocment = tempDocumentModel.getDocument();
                    tmpId = tempDocumentModel.getId();

                    if(tmpId >= 0){
                        long start = System.currentTimeMillis();
                        tmpDistance =
                    }
                }
            }
        }
    }

    class FeatureCacheProducer implements Runnable{

        @Override
        public void run() {

        }
    }

    class FeatureCacheConsumer implements Runnable{

        @Override
        public void run() {

        }
    }

    protected double getDistance(Document document, LireFeature lireFeature){

    }
}
