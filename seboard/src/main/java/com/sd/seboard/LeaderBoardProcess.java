package com.sd.seboard;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class LeaderBoardProcess {

	public static final String DELIM = "|";

	public static void main(String[] args) throws Exception {

		SymposiumDAO dao = new SymposiumDAO();
		Connection conn = dao.getConnection();

//		ArrayList<String> persons = new ArrayList<String>(Arrays.asList("sibendu", "kallol", "vidyut", "soumya",
//		"rajesh", "meenal", "santosh", "prashant", "anuj", "nitin", "sanjeev", "amit"));
//		ArrayList<Picture> pictures = simulatePictures(persons, 10000);

		ArrayList<String> persons = dao.getEmployees(conn);
		ArrayList<Picture> pictures = dao.getPictures(conn);
		
		if(pictures == null || pictures.size() == 0) {
			System.out.print("No pictures in database; creating simulaiton set ..");
			pictures = simulatePictures(persons, 1000);
		}
		
		System.out.println("Employee master has "+persons.size()+" records");
		for (int i = 0; i < persons.size(); i++) {
			System.out.print(persons.get(i) + " , ");
		}
		System.out.println("\n*************\n\n");

//		for (int i = 0; i < pictures.size(); i++) {
//			System.out.print(pictures.get(i).getFileName() + " : " + pictures.get(i).getPersonsInPic());
//		}
//		System.out.println("\n*************\n\n");

		conn.close();
		
		System.out.println("Starting sort process ..");
		ArrayList<SEBoardRecord> seBoard = process(persons, pictures);
		System.out.println("Sorting done, SE Board created ..");
		for (int i = 0; i < seBoard.size(); i++) {
			SEBoardRecord thisRecord = seBoard.get(i);
			System.out.println(thisRecord.getPersons() + " :: " + thisRecord.getPictures().size());
		}
	}

	public static ArrayList<SEBoardRecord> process(ArrayList<String> persons, ArrayList<Picture> pictures) {

		ArrayList<SEBoardRecord> seBoard = new ArrayList<SEBoardRecord>();

		String person1, person2, peopleInPic = null;
		int combinationChecked = 0;

		for (int i = 0; i < persons.size(); i++) {
			person1 = persons.get(i);

			for (int k = i + 1; k < persons.size(); k++) {
				person2 = persons.get(k);
				combinationChecked++;
				//System.out.println("Checking compatibility for " + person1 + " & " + person2);

				ArrayList<String> personsCombination = new ArrayList<String>();
				personsCombination.add(person1);
				personsCombination.add(person2);

				ArrayList<Picture> foundInPictures = new ArrayList<Picture>();

				// Check all pictures
				for (int m = 0; m < pictures.size(); m++) {
					if (checkPeopleInPicture(person1, person2, pictures.get(m).getPersonsInPic())) {
						// person1 & person2 were together in this picture
						foundInPictures.add(pictures.get(m));
					}
				}

				// Add a new record in SE Board for this combination
				seBoard.add(new SEBoardRecord(personsCombination, foundInPictures));
			}
		}

		System.out.println("Total Combination Checked = " + combinationChecked);

		// Sort it now
		Collections.sort(seBoard);
		System.out.println("Records sorted .. ");

		return seBoard;
	}

	public static boolean checkPeopleInPicture(String person1, String person2, String peopleInPicture) {

		if (peopleInPicture.contains(DELIM + person1 + DELIM) && peopleInPicture.contains(DELIM + person2 + DELIM)) {
			// They were together in this picture
			return true;
		}
		return false;
	}

	public static ArrayList<Picture> simulatePictures(ArrayList<String> persons, int numToSimulate) {

		ArrayList<Picture> pictures = new ArrayList<Picture>();

		for (int i = 0; i < numToSimulate; i++) {

			String peopleInThisPic = DELIM;
			int numOfPeopleInThisPic = (int) (Math.random() * persons.size());

			for (int k = 0; k < numOfPeopleInThisPic; k++) {
				peopleInThisPic = peopleInThisPic + persons.get((int) (Math.random() * persons.size())) + DELIM;
			}

			pictures.add(new Picture(peopleInThisPic, "pic-" + i + ".jpeg"));
		}

		return pictures;
	}
}
