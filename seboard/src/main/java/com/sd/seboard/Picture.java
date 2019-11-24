package com.sd.seboard;

import java.io.Serializable;

public class Picture implements Serializable {
	//private String id;
	private String personsInPic;
	private String fileName;
	public Picture() {
	}
	public Picture(String personsInPic, String fileName) {
		super();
		this.personsInPic = personsInPic;
		this.fileName = fileName;
	}
//	public Picture(String id, String personsInPic, String fileName) {
//		super();
//		this.id = id;
//		this.personsInPic = personsInPic;
//		this.fileName = fileName;
//	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public String getPersonsInPic() {
		return personsInPic;
	}
	public void setPersonsInPic(String personsInPic) {
		this.personsInPic = personsInPic;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
