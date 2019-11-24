package com.sd.seboard;

import java.io.*;
import java.util.*;

public class SEBoardRecord implements Serializable, Comparable{
	private ArrayList<String> persons;
	private ArrayList<Picture> pictures;
	public SEBoardRecord() {
	}
	public SEBoardRecord(ArrayList<String> persons, ArrayList<Picture> pictures) {
		super();
		this.persons = persons;
		this.pictures = pictures;
	}
	public ArrayList<String> getPersons() {
		return persons;
	}
	public void setPersons(ArrayList<String> persons) {
		this.persons = persons;
	}
	public ArrayList<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(ArrayList<Picture> pictures) {
		this.pictures = pictures;
	}
	
	@Override
    public int compareTo(Object obj) {
		SEBoardRecord comparesTo = (SEBoardRecord)obj;
        int compareage= comparesTo.getPictures().size();
        /* For Ascending order*/
        return compareage - this.getPictures().size();

        /* For Ascending order do like this */
        //return this.studentage - compareage;
    }
}
