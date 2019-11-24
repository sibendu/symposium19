package com.sd.seboard;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LeaderBoardService {

	@GetMapping("/")
	public ResponseEntity<ArrayList<SEBoardRecord>> getSEBoard() throws IOException {
		System.out.println("SE Board proces invoked");
		ArrayList<SEBoardRecord> seBoard = new ArrayList<SEBoardRecord>();
		try {
			
			SymposiumDAO dao = new SymposiumDAO();
			Connection conn = dao.getConnection();

			ArrayList<String> persons = dao.getEmployees(conn);
			ArrayList<Picture> pictures = dao.getPictures(conn);
			
			conn.close();
			
			System.out.println("Employee master has "+persons.size()+" records");
			for (int i = 0; i < persons.size(); i++) {
				System.out.print(persons.get(i) + " , ");
			}
			System.out.println("\n*************");
			System.out.println("Pictures records = "+pictures.size());
			System.out.println("\n*************");
			
			System.out.println("Starting SE Board Process ..");
			ArrayList<SEBoardRecord>  processResponse = LeaderBoardProcess.process(persons, pictures);
			System.out.println("SE Board created; total records = "+processResponse.size());
			
			System.out.println("Preparing response with top 5 records ..");
			for (int i = 0; i < processResponse.size(); i++) {
				SEBoardRecord record = processResponse.get(i);
				if(i < 5) {
					if(record.getPictures().size() > 0) {
						seBoard.add(record);
					}
				}
			}
		}catch(Exception e) {
			
		}
		return new ResponseEntity<>(seBoard, HttpStatus.OK);
	}
	
}
