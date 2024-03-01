package org.example.satdwn.util;


import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class UploadFileToS3 {
    private static String bucketName = "xmlfilesback";
    private static String path = "/home/ubuntu/uploadFile/";

    public static void upload(String fileName) {
        try {

            String filePath = path + fileName;
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_1)
                    .build();

            File file = new File(filePath);
            PutObjectRequest request = new PutObjectRequest(bucketName, filePath, file);
            s3Client.putObject(request);

        } catch (SdkClientException e) {
            e.printStackTrace();
        }

    }
}
