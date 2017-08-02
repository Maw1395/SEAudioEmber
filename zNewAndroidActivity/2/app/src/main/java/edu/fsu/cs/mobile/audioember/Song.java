package edu.fsu.cs.mobile.audioember;

import java.util.ArrayList;

/**
 * Created by robert on 7/30/17.
 */

public class Song {
    String Artist;
    String Title;
    int points;
    ArrayList<String> graphs = new ArrayList<String>();

    public Song(String a, String t, int p, ArrayList<String> g){Artist = a; Title = t; points = p; graphs = g;}

    public String getArtist() {
        return Artist;
    }

    public String getTitle() {
        return Title;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<String> getGraphs() {
        return graphs;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGraphs(ArrayList<String> graphs) {
        this.graphs = graphs;
    }

    public void addGraph(String g) {
        this.graphs.add(g);
    }
}
