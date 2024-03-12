package org.example.satdwn.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.example.satdwn.model.Response;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;


public class UploadFileToS3 {
    private static String bucketName = "xmlfilesback";

    //private static String local_path = "/Users/marioalberto/IdeaProjects/upload/";
    private static String server_path= "/home/ubuntu/satUploadFile/";

    public static Logger LOGGER = LogManager.getLogger(UploadFileToS3.class);


    public static void upload(String user_id, String xml) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();

            File file = new File(server_path + xml);
            PutObjectRequest request = new PutObjectRequest(bucketName, user_id + "/" + xml, file);
            s3Client.putObject(request);

        } catch (SdkClientException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, List<Response>> getFilelFromAWS(String user_id) {
        List<Response> responses = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<String> paths = new ArrayList<>();
        Map<String, List<Response>> facturas = new HashMap<>();
        int expirationTime = 3600;
        try {

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();

            ListObjectsRequest request2 = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(user_id);

            ObjectListing objectListing;
            do {
                objectListing = s3Client.listObjects(request2);
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    // System.out.println("Nombre: " + objectSummary.getKey() + " - Tamaño: " + objectSummary.getSize());
                    GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectSummary.getKey(), HttpMethod.GET);
                    request.setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000));
                    String objectUrl = s3Client.generatePresignedUrl(request).toString();
                    request.getRequestParameters();
                    //LOGGER.info(" url {" + objectUrl + "}");
                    urls.add(objectUrl);
                    paths.add(objectSummary.getKey());

                }
                request2.setMarker(objectListing.getNextMarker());
            } while (objectListing.isTruncated());

            String localPath = null;
            for (int i = 1; i < urls.size(); i++) {

                String path = UploadFileToS3.createFile(paths.get(i));
                Response response = ParserFile.getParseValues(path);
                response.setUrl(urls.get(i));
                //LOGGER.info("response from Util { " + response + " }");
                responses.add(response);
                localPath = path;
            }
            String[] partes = localPath.split("/");
            facturas.put("Emitidas", responses);
            facturas.put("Recibidas", responses);

            FileUtils.deleteDirectory(new File(partes[0]));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return facturas;
    }

    public static String createFile(String fileName) {
        String localFilePath = null;
        try {

            String[] partes = fileName.split("/");
            //debe ser 8 por el rfc
            File directorio = new File(partes[0]);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            S3Client s3 = S3Client.builder().region(Region.US_EAST_1).build();
            localFilePath = directorio + "/" + partes[1];
            //LOGGER.info("Local path  " + localFilePath);
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            GetObjectResponse getObjectResponse = s3.getObject(getObjectRequest, Paths.get(localFilePath));

            //LOGGER.info(" --- " + Paths.get(localFilePath));
        } catch (S3Exception e) {
            LOGGER.error(e.getMessage());
        }
        return localFilePath;
    }

}
