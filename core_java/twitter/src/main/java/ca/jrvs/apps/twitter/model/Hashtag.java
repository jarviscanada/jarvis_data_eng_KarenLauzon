package ca.jrvs.apps.twitter.model;

import java.util.ArrayList;

public class Hashtag {

    private String text;
    private ArrayList<Integer> indices;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }
}
