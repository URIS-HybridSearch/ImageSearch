package com.hybridsearch.model;

/**
 * @Author Anthony Z.
 * @Date 23/12/2022
 * @Description:
 */
public class SearchFeature {

    public BBox bbox;
    public float score;
    public String blackTableFaceUrl = "";
    public float similarity = 0;
    public String name = "";
    public double blur = 0;
    public LandMark landMark;

    public SearchFeature(float[] arr) {
        bbox = new BBox(subArray(arr, 0, 4));
        score = arr[4];
    }

    public  SearchFeature(int x1, int y1, int x2, int y2, int score, double blur, LandMark landMark) {
        bbox = new BBox(x1, y1, x2, y2);
        this.score = score;
        this.blur = blur;
        this.landMark = landMark;
    }

    public float[] subArray(float[] src, int begin, int end) {
        float[] dest = new float[end - begin];
        System.arraycopy(src, begin, dest, 0, dest.length);
        return dest;
    }


    public class Point{
        public int x;
        public int y;

        public Point(float x, float y){
            this.x = Math.round(x);
            this.y = Math.round(y);
        }
    }
    public class BBox{
        public Point leftTop;
        public Point rightDown;

        public BBox(float[] arr){
            this.leftTop = new Point(arr[0], arr[1]);
            this.rightDown = new Point(arr[2], arr[3]);
        }
        public BBox(int x1, int y1, int x2, int y2){
            this.leftTop = new Point(x1, y1);
            this.rightDown = new Point(x2, y2);
        }
    }
}
