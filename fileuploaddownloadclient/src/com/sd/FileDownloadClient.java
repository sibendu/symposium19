package com.sd;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloadClient {
	
	public static void main(String[] args) {
        String fileName = "StreamsExample.java";
		String objectStore = "https://objectstorage.us-ashburn-1.oraclecloud.com/n/indiafieldse/b/symposium/o/"; 
		String tempStore = "./";
		fileName =  args.length > 0 ? args[0] :fileName;
		objectStore = args.length > 1 ? args[1] :objectStore;
		
        try {
            process( objectStore + fileName, tempStore+fileName); 
            //downloadUsingStream(url, "/Users/pankaj/StreamsExample.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static void process(String urlStr, String file) throws IOException {
		downloadUsingNIO(urlStr, file);
		
		//FileUploadClient.upload(urlStr, "")
	}

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    
    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
}