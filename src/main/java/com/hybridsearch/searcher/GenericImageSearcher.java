package com.hybridsearch.searcher;

import com.hybridsearch.feature.HybridFeature;
import com.hybridsearch.model.DocumentModel;
import com.hybridsearch.model.FeatureCacheModel;
import com.hybridsearch.model.SearchResult;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import net.semanticmetadata.lire.indexers.parallel.ExtractorItem;
import net.semanticmetadata.lire.searchers.SimpleResult;
import org.apache.lucene.index.IndexReader;

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
}
