package com.hybridsearch.searcher;

import com.hybridsearch.feature.HybridFeature;
import com.hybridsearch.model.DocumentModel;
import com.hybridsearch.model.FeatureCacheModel;
import com.hybridsearch.model.SearchResult;
import com.hybridsearch.utils.FaceTool;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import net.semanticmetadata.lire.indexers.parallel.ExtractorItem;
import net.semanticmetadata.lire.searchers.SimpleResult;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.util.Bits;

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
public class GenericImageSearcher implements SearcherInterface{

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
    protected ConcurrentSkipListSet<SimpleResult> concurrentSkipListSet = new ConcurrentSkipListSet<>();
    protected ConcurrentSkipListSet<SearchResult> searchResults = new ConcurrentSkipListSet<>();
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
            Document tmpDocument;
            int tmpId;
            DocumentModel tempDocumentModel;
            double tmpDistance;
            double tmpMaxDistance;

            while(!consumerFinished){
                try{
                    tempDocumentModel = linkedBlockingQueue.take();
                    tmpDocument = tempDocumentModel.getDocument();
                    tmpId = tempDocumentModel.getId();

                    if(tmpId >= 0){
                        long start = System.currentTimeMillis();
                        tmpDistance = getDistance(tmpDocument, hybridFeature);

                        //  use assertion here to detect errors if any
                        assert (tmpDistance >= 0d);
                        if(concurrentSkipListSet.size() < maxHits){
                            // TODO: THE DETAILS to be added
                        }else{
                            // TODO: THE DETAILS to be added
                        }
                    }else{
                        consumerFinished = true;
                    }
                } catch (InterruptedException ex) {
                    ex.getMessage();
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

        private boolean consumeFinish = false;
        private String threadName = "";
        private double[] feature;

        public FeatureCacheConsumer(String threadName, double[] feature){
            this.threadName = threadName;
            this.feature = feature;
        }
        @Override
        public void run() {
            byte[] tmpBytes;
            String tmpKeys;
            FeatureCacheModel featureCacheModel;
            double tmpDistance;

            while(!consumeFinish){
                try{
                    featureCacheModel = featureCacheModels.take();
                    tmpBytes = featureCacheModel.getBytes();
                    tmpKeys = featureCacheModel.getKey();

                    if(!tmpKeys.equals("")){
                        // TODO: to add the details
                    }else{
                        consumeFinish = true;
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public void findSimilar() {
        Bits liveDocs = MultiFields.getLiveDocs(reader);
        int docs = reader.numDocs();
        // TODO: NOT yet finished

        // we read every doc from the index and compare it with the query
        for(int i = 0; i<docs; i++){



            // if the array is not full yet
            if(this.results.size() < maxHits){

            }
        }

    }

    @Override
    public TreeSet<SearchResult> getResults() {
        return null;
    }

//    protected double getDistance(Document document, LireFeature lireFeature){
//        double result;
//    }

    public String toString(){
        return "Generic Searcher using" + extractorItem.getExtractorClass().getName();
    }
}
