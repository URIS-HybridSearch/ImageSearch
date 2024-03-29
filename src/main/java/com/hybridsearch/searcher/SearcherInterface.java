package com.hybridsearch.searcher;

import com.hybridsearch.model.SearchResult;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author Anthony Z.
 * @Date 22/12/2022
 * @Description:
 */
public interface SearcherInterface {


    void findSimilar(IndexReader reader, LireFeature lireFeature);
    TreeSet<SearchResult> getResults();

}
