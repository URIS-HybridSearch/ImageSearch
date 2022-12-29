package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 22/12/2022
 * @Description:
 */
public class SearchResult implements Comparable<SearchResult> {
    public double score;
    public String indexKey;

    public SearchResult(double score, String indexKey) {
        this.score = score;
        this.indexKey = indexKey;
    }

    @Override
    public int compareTo(SearchResult o) {
        return Double.compare(o.score,this.score);
    }
}
