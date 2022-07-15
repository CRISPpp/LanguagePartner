package com.languagePartner.util;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;

public class FileUploader {
    @Value("${minio.endpoint}")
    private static String endpoint;

    @Value("${minio.accesskey}")
    private static String accessKey;

    @Value("${minio.secretkey}")
    private static String secretKey;

    @Value("${minio.bucketName}")
    private static String bucketName;

    public void upload() throws Exception{
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(accessKey, secretKey)
                            .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket 'crisp' already exists.");
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("crisp")
                            .object("d334cb638497d4e2265ca8dd88ab2a8.jpg")
                            .filename("C:\\Users\\BACKPACKER\\Desktop\\d334cb638497d4e2265ca8dd88ab2a8.jpg")
                            .build());
            System.out.println(
                    "'C:\\Users\\BACKPACKER\\Desktop\\d334cb638497d4e2265ca8dd88ab2a8.jpg' is successfully uploaded as "
                            + "object 'd334cb638497d4e2265ca8dd88ab2a8.jpg' to bucket 'crisp'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}
