package org.example.satdwn.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.satdwn.model.Response;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;


import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UploadFileToS3 {
    private static String bucketName = "xmlfilesback";
    private static String path = "https://xmlfilesback.s3.amazonaws.com/";
    public static Logger LOGGER = LogManager.getLogger(UploadFileToS3.class);


    public static void upload(String id_path) {
        try {

            String filePath = id_path;
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();

            File file = new File(filePath);
            PutObjectRequest request = new PutObjectRequest(bucketName, id_path + "/", file);
            s3Client.putObject(request);

        } catch (SdkClientException e) {
            e.printStackTrace();
        }

    }

    public static List<Response> getFilelFromAWS(String fileName) {
        List<Response> responses = new ArrayList<>();
        int expirationTime = 3600;
        try {

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();

            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
            request.setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000));
            String objectUrl = s3Client.generatePresignedUrl(request).toString();

            UploadFileToS3.createFile(fileName);
            Response response = ParserFile.getParseValues(fileName);
            response.setUrl(objectUrl);
            LOGGER.info("response from Util { " + response + " }");

            responses.add(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return responses;
    }

    public static void createFile(String fileName) {
        Region region = Region.US_EAST_1; // Ajusta a tu región
        S3Client s3 = S3Client.builder().region(region).build();
        String localFilePath = fileName;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        GetObjectResponse getObjectResponse = s3.getObject(getObjectRequest, Paths.get(localFilePath));
        LOGGER.info("Archivo descargado: " + localFilePath + getObjectResponse);


    }
}
