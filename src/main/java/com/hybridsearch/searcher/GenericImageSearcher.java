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
    public void findSimilar(IndexReader reader, LireFeature lireFeature){

        Bits liveDocs = MultiFields.getLiveDocs(reader);
        Document doc;
        double tmpDistance;
        int docs = reader.numDocs();

        // we read every doc from the index and compare it with the query
        for(int i = 0; i<docs; i++){

            if(reader.hasDeletions() && !liveDocs.get(i))
                continue; // if it's deleted, just ignore it

            try{
                doc = reader.document(i);
                tmpDistance = getDistance(doc, lireFeature);

                assert(tmpDistance >= 0);

                // distance is too low
                if(tmpDistance < this.threshold)
                    continue;

                // if the array is not full yet
                if(this.results.size() < maxHits){
                    this.results.add(new SearchResult(tmpDistance, doc.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue()));

                }else if(tmpDistance > results.last().score){
                    // if it's nearer to the sample than at least one of the curren set;
                    // remove the last node and add it as new one
                    this.results.remove(this.results.last());
                    this.results.add(new SearchResult(tmpDistance, doc.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue()));
                }

            }catch (IOException ex){
                System.err.println(ex);
            }
            
        }
    }



    @Override
    public TreeSet<SearchResult> getResults() {
        return null;
    }


    /**
     * which classes use it?
     * @param document
     * @param lireFeature
     * @return
     */
    protected double getDistance(Document document, LireFeature lireFeature){

        if(document.getField(fieldName).binaryValue() != null && document.getField(fieldName).binaryValue().length>0){
            cachedInstance.setByteArrayRepresentation(document.getField(fieldName).binaryValue().bytes);
            return lireFeature.getDistance(cachedInstance);
        }else{
            logger.warning("No feature stored in this document {" + extractorItem.getExtractorClass().getName() + ")");
        }

        return 0d;
    }
    protected double getDistance(double[] featureSearch, double[] featureIndex){

        double result = FaceTool.cosineSimilarity(featureSearch, featureIndex);
        return result*100;
    }

    public String toString(){
        return "Generic Searcher using" + extractorItem.getExtractorClass().getName();
    }
}
