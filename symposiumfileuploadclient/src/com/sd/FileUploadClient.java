package com.sd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;



public class FileUploadClient {

	public static void main(String[] args) {

		String filePath = "C:\\Users\\sibedas\\Desktop\\Tottochan.pdf"; 
		String service = "http://localhost:8080/upload";
		
		filePath = args.length > 0 ? args[0] :filePath;
		service =  args.length > 1 ? args[1] :service;
		
		String status = FileUploadClient.upload(filePath, service);
		System.out.println(status);
	}

	public static String upload(String filePath, String serviceUrl) {
		// the file we want to upload
		File inFile = new File(filePath);
		FileInputStream fis = null;
		String responseString = "";
		try {
			fis = new FileInputStream(inFile);
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

			// server back-end URL
			HttpPost httppost = new HttpPost(serviceUrl);
			MultipartEntity entity = new MultipartEntity();
			// set the file input stream and file name as arguments
			entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
			httppost.setEntity(entity);
			// execute the request
			HttpResponse response = httpclient.execute(httppost);

			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			responseString = EntityUtils.toString(responseEntity, "UTF-8");

			responseString = statusCode + ":" + responseString;

		} catch (ClientProtocolException e) {
			System.err.println("Unable to make connection");
			e.printStackTrace();
			responseString = "Error: "+e.getMessage();
		} catch (IOException e) {
			System.err.println("Unable to read file");
			e.printStackTrace();
			responseString = "Error: "+e.getMessage();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
			}
		}
		return responseString;
	}

}