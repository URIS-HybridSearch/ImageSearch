package com.hybridsearch.model;

import org.apache.lucene.document.Document;

/**
 * @Author Anthony Z.
 * @Date 21/12/2022
 * @Description:
 */
public class DocumentModel {
    private Document document;
    private int id;

    public DocumentModel(int id, Document document){
        this.id = id;
        this.document = document;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
