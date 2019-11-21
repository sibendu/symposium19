# symposium19

An API to upload files/images during Symposium:  

Pre-reqquisite: JDK-8+

To run server (on the machine where file needs to be uploaded):
1.	Set an env variable UPLOAD_LOCATION:  export UPLOAD_LOCATION=/tmp/symposium/pictures
2.	Run the API server:    java –jar fileuploadserver.jar
3.	Test by running:      java –jar fileuploadclient.jar  <image_path> <service_url>
e.g. java –jar fileuploadclient.jar  D:\\tmp\\sd.jpeg  http://server-ip:8080/upload 

For any client e.g. Symposium app:
1. Build runnable client jar fro Eclipse
2. Import client jar file, use this api :  String com.sd.upload(String filePath, String serviceUrl)  

