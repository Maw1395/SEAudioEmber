package edu.fsu.cs.mobile.audioember;

import java.util.ArrayList;

/**
 * Created by Kevin Williams on 12/4/2017.
 */

public class Pagination {
    /*
    Constants
     */
    public static final int TOTAL_NUM_ITEMS=200;
    public static final int ITEMS_PER_PAGE=50;
    public static final int ITEMS_REMAINING=TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
    public static final int LAST_PAGE=TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;

    //public GenreBeingSearched pagination;

    //public ArrayList<String> songList()
    //{
        //pagination = new GenreBeingSearched();
    //    ArrayList<String> songs = pagination.getSongs();
    //    return songs;
    //}

    ArrayList<String> songs = new ArrayList<>();

    /*
    GENERATE PAGE DATA, PASS US CURRENT PAGE.
     */
    public ArrayList<String> generatePage(int currentPage)
    {
        int startItem=currentPage*ITEMS_PER_PAGE+1;
        int numOfData=ITEMS_PER_PAGE;

        ArrayList<String> pageData=new ArrayList<>();

        if (currentPage==LAST_PAGE && ITEMS_REMAINING>0)
        {
            for (int i=startItem;i<startItem+ITEMS_REMAINING;i++)
            {
                pageData.add("Number " + i);
                //pagination.getSongs();
                //Log.i("
            }
        }else
        {
            for (int i=startItem;i<startItem+numOfData;i++)
            {
                pageData.add("Number " + i);
                //pagination.getSongs();
            }
        }
        return pageData;
        //return pagination;
    }
}
