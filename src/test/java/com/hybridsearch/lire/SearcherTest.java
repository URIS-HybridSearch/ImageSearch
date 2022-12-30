package com.hybridsearch.lire;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author Anthony HE, anthony.zj.he@outlook.com
 * @Date 30/12/2022
 * @Description:
 */
public class SearcherTest {

    public static void main(String[] args) {

        BufferedImage img = null;
        boolean passed = false;
        if(args.length >0){
            File f = new File(args[0]);
            if(f.exists()){
                try{
                    img = ImageIO.read(f);
                    passed = true;
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }

        if(!passed){
            System.err.println("No image given as first argument");
            System.err.println("Run \"Searcher <query image>\" to search for <query image>");
            System.exit(1);
        }

        long start = System.currentTimeMillis();
//        IndexReader ir = DirectoryReader.open(FSDirectory.open());
    }
}
